package com.github.iceberglet.anymsg;

import java.nio.ByteBuffer;

import com.github.iceberglet.anymsg.bootstrap.MessageContract;
import com.github.iceberglet.anymsg.bootstrap.MessageContractBuilder;
import com.github.iceberglet.anymsg.defs.MessageDef;

interface MessageContractRepo {

    MessageContractBuilder defineMessage(MessageDef messageDef);

    void addMessageDef(MessageContract contract);

    void encode(ByteBuffer byteBuffer, MessageDef messageDef, Message message);

    Message decode(ByteBuffer buffer);

}
