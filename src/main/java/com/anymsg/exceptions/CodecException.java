package com.anymsg.exceptions;

import com.anymsg.Message;

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
