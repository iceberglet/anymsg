package com.minstudio.anymsg.exceptions;

import com.minstudio.anymsg.Message;

public class CodecException extends RuntimeException {

    private final Message msg;

    public CodecException(String errorMessage, Message msg) {
        super(errorMessage);
        this.msg = msg;
    }

    public Message getMsg() {
        return msg;
    }
}
