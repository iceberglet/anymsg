package org.anymsg;

import java.nio.ByteBuffer;

import org.anymsg.bootstrap.MessageContract;
import org.anymsg.bootstrap.MessageContractBuilder;
import org.anymsg.defs.MessageDef;

interface MessageContractRepo {

    MessageContractBuilder defineMessage(MessageDef messageDef);

    void addMessageDef(MessageContract contract);

    void encode(ByteBuffer byteBuffer, MessageDef messageDef, Message message);

    Message decode(ByteBuffer buffer);

}
