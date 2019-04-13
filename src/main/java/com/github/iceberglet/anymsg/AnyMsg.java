package com.github.iceberglet.anymsg;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.github.iceberglet.anymsg.bootstrap.MessageContract;
import com.github.iceberglet.anymsg.bootstrap.MessageContractBuilder;
import com.github.iceberglet.anymsg.defs.MessageDef;
import com.github.iceberglet.anymsg.exceptions.DefinitionException;

public class AnyMsg implements MessageContractRepo {

    private final Map<Integer, MessageContract> defs = new HashMap<>();

    private final Supplier<Message> messageSupplier;

    public AnyMsg(Supplier<Message> messageSupplier) {
        this.messageSupplier = messageSupplier;
    }

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
        return defs.get(messageType).decode(buffer, messageSupplier);
    }
}
