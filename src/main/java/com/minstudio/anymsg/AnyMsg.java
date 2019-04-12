package com.minstudio.anymsg;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.minstudio.anymsg.bootstrap.MessageContract;
import com.minstudio.anymsg.bootstrap.MessageContractBuilder;
import com.minstudio.anymsg.defs.MessageDef;
import com.minstudio.anymsg.exceptions.DefinitionException;

public class AnyMsg implements MessageContractRepo {

    private Map<Integer, MessageContract> defs = new HashMap<>();

    @Override
    public MessageContractBuilder defineMessage(MessageDef messageDef) {
        return new MessageContractBuilderImpl(null, messageDef);
    }

    @Override
    public void addMessageDef(MessageContract contract) {
        if (defs.put(contract.getMessageDef().getTag(), contract) != null) {
            throw new DefinitionException("ALready defined a message of type " + contract.getMessageDef().getTag() +
                    " previous definition will be replaced");
        }
    }

    @Override
    public void encode(ByteBuffer buffer, MessageDef messageDef, Message message) {
        buffer.putInt(messageDef.getTag());
        defs.get(messageDef.getTag()).encode(buffer, message);
    }

    @Override
    public Message decode(ByteBuffer buffer) {
        int messageType = buffer.getInt();
        return defs.get(messageType).decode(buffer);
    }
}
