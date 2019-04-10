package com.minstudio.anymsg.bootstrap;

import java.nio.ByteBuffer;

import com.minstudio.anymsg.Message;
import com.minstudio.anymsg.defs.MessageDef;

/**
 * Defines the contract for a single type of message.
 */
public interface MessageContract {


    /**
     * Decodes the bytebuffer into a Message under the current contract.
     * @param buffer buffer
     * @return the Message.
     */
    Message decode(ByteBuffer buffer);

    /**
     * Encodes the message into the ByteBuffer
     * @param buffer buffer to encode stuff into
     * @param message message to encode
     */
    void encode(ByteBuffer buffer, Message message);

    /**
     * Returns the group def of this contract, if it about a sub message
     * @return the group def
     */
    MessageDef getGroupDef();
}
