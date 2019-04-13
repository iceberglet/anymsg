package com.github.iceberglet.anymsg.exceptions;

import com.github.iceberglet.anymsg.Message;

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
