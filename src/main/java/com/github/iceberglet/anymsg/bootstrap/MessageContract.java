package com.github.iceberglet.anymsg.bootstrap;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

import com.github.iceberglet.anymsg.Message;
import com.github.iceberglet.anymsg.defs.MessageDef;

/**
 * Defines the contract for a single type of message.
 */
public interface MessageContract {
    
    /**
     * Decodes the bytebuffer into a Message under the current contract.
     * @param buffer buffer to read stuff from
     * @param messageSupplier supplier of messages and subgroups, if needed
     * @return the message decoded
     */
    Message decode(ByteBuffer buffer, Supplier<Message> messageSupplier);

    /**
     * Encodes the message into the ByteBuffer
     *
     * @param buffer  buffer to encode stuff into
     * @param message message to encode
     */
    void encode(ByteBuffer buffer, Message message);

    /**
     * Returns the message def of this contract, or group def if this is a sub message
     *
     * @return the message def
     */
    MessageDef getMessageDef();
}
