package com.minstudio.anymsg;

import java.nio.ByteBuffer;

import com.minstudio.anymsg.bootstrap.MessageContract;
import com.minstudio.anymsg.bootstrap.MessageContractBuilder;
import com.minstudio.anymsg.defs.MessageDef;

public interface MessageContractRepo {

    MessageContractBuilder defineNewMessage(int messageType);

    void addMessageDef(MessageContract contract);

    ByteBuffer encode(MessageDef messageDef, Message message);

    Message decode(ByteBuffer buffer);

}
