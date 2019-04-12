package com.anymsg;

import java.nio.ByteBuffer;

import com.anymsg.bootstrap.MessageContract;
import com.anymsg.bootstrap.MessageContractBuilder;
import com.anymsg.defs.MessageDef;

interface MessageContractRepo {

    MessageContractBuilder defineMessage(MessageDef messageDef);

    void addMessageDef(MessageContract contract);

    void encode(ByteBuffer byteBuffer, MessageDef messageDef, Message message);

    Message decode(ByteBuffer buffer);

}
