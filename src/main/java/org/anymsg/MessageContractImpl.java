package org.anymsg;

import static org.anymsg.Constants.END_OF_MESSAGE;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;

import org.anymsg.defs.FieldDef;
import org.anymsg.defs.MessageDef;
import org.anymsg.exceptions.CodecException;
import org.anymsg.bootstrap.MessageContract;

/**
 * Each message is encoded in the following ways:
 * <p>
 * | [field_tag_int, field_content] | [group_tag_int, group_content] |
 * <p>
 * When encoding, it will go down the definition list and decode one field after another. For each tag,
 * - If the same field exists in the message, encode
 * - If the field does not exist (null), but the field is optional, skip
 * - If the field does not exist (null), and the field is NOT marked optional, throw an error saying field is missing
 * - encode a END_OF_MESSAGE marker
 * When encoding, it will keep on reading tag_int, compare it with the contract's next item
 * - END_OF_MESSAGE? Check if definition is exhausted
 * - Same, then decode
 * - Different, but the item is marked as optional, then decode
 * - Different, and it is not optional, then throw an error saying some field is missing
 */
class MessageContractImpl implements MessageContract {

    private final List<FieldDef> fieldDefs;
    private final List<MessageContract> subMsgContracts;
    private final MessageDef myMessageDef;

    MessageContractImpl(List<FieldDef> fieldDefs, List<MessageContract> subMsgContracts, MessageDef myMessageDef) {
        this.fieldDefs = fieldDefs;
        this.subMsgContracts = subMsgContracts;
        this.myMessageDef = myMessageDef;
    }

    @Override
    public MessageDef getMessageDef() {
        return myMessageDef;
    }

    @Override
    public Message decode(ByteBuffer buffer) {
        Message message = new MessageImpl();

        //next tag is read forward iff the current one is satisfied.
        //exception is thrown if the current tag cannot be satisfied.
        int nextTag = buffer.getInt();
        for (FieldDef def : fieldDefs) {
            if (def.getTag() == nextTag) {
                message.setField(def, def.decode(buffer));
                nextTag = buffer.getInt();
            } else if (!def.isOptional()) {
                throw new CodecException("Field Not Found In Message: " + def + " Found Tag " + nextTag + " instead.", message);
            }
        }

        //keep reading next group
        for (MessageContract contract : subMsgContracts) {
            MessageDef messageDef = contract.getMessageDef();
            if (messageDef == null) {
                throw new CodecException("Invalid Message Contract with no MessageDef: " + contract, message);
            }
            //now nextTag is supposed to be a group tag.
            Collection<Message> subMessages = message.getGroup(messageDef);
            while(nextTag == messageDef.getTag()) {
                subMessages.add(contract.decode(buffer));
                nextTag = buffer.getInt();
            }
            if (subMessages.isEmpty() && !messageDef.isOptional()) {
                throw new CodecException("Group Not Found In Message: " + messageDef, message);
            }
        }

        if(nextTag != END_OF_MESSAGE) {
            throw new CodecException("Unknown Message Tag: " + nextTag, message);
        }
        return message;
    }

    /**
     * @param buffer  buffer to put stuff into
     * @param message message to encode
     */
    @Override
    public void encode(ByteBuffer buffer, Message message) {
        //1. encode all the immediate fields.
        for (FieldDef fieldDef : fieldDefs) {
            encodeField(buffer, fieldDef, message);
        }

        //2. encode all groups
        for (MessageContract subGroupContract : subMsgContracts) {
            encodeGroups(buffer, subGroupContract, message);
        }

        buffer.putInt(END_OF_MESSAGE);
    }

    private void encodeField(ByteBuffer buffer, FieldDef fieldDef, Message message) {
        Object toEncode = message.getField(fieldDef);
        if (toEncode == null) {
            if (!fieldDef.isOptional()) {
                throw new CodecException("Missing Field In Message: " + fieldDef, message);
            }
        } else {
            //do encoding
            buffer.putInt(fieldDef.getTag());
            if (!fieldDef.getType().isAssignableFrom(toEncode.getClass())) {
                throw new CodecException("Field Type Mismatch: Field: " + fieldDef + " Type obtained: " + toEncode.getClass(), message);
            }
            fieldDef.encode(buffer, toEncode);
        }
    }

    private void encodeGroups(ByteBuffer buffer, MessageContract contract, Message message) {
        MessageDef messageDef = contract.getMessageDef();
        if (messageDef == null) {
            throw new CodecException("Invalid Message Contract with no MessageDef: " + contract, message);
        }
        Collection<Message> subMessages = message.getGroup(messageDef);
        for (Message subMsg : subMessages) {
            buffer.putInt(messageDef.getTag());
            contract.encode(buffer, subMsg);
        }
    }
}
