package com.github.anymsg;

import java.nio.ByteBuffer;

import com.github.anymsg.bootstrap.MessageContract;
import com.github.anymsg.bootstrap.MessageContractBuilder;
import com.github.anymsg.defs.MessageDef;

interface MessageContractRepo {

    MessageContractBuilder defineMessage(MessageDef messageDef);

    void addMessageDef(MessageContract contract);

    void encode(ByteBuffer byteBuffer, MessageDef messageDef, Message message);

    Message decode(ByteBuffer buffer);

}
