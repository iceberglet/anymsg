package com.minstudio.anymsg;

import java.nio.ByteBuffer;

import com.minstudio.anymsg.bootstrap.MessageContract;
import com.minstudio.anymsg.bootstrap.MessageContractBuilder;
import com.minstudio.anymsg.defs.MessageDef;

interface MessageContractRepo {

    MessageContractBuilder defineMessage(MessageDef messageDef);

    void addMessageDef(MessageContract contract);

    void encode(ByteBuffer byteBuffer, MessageDef messageDef, Message message);

    Message decode(ByteBuffer buffer);

}
