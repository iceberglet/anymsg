package com.minstudio.anymsg.codec;

import java.nio.ByteBuffer;

public class LongCodec implements Codec<Long> {

    @Override
    public Class<Long> getType() {
        return Long.class;
    }

    @Override
    public Long decode(ByteBuffer buffer) {
        return buffer.getLong();
    }

    @Override
    public void encode(ByteBuffer buffer, Long item) {
        if(item == null) {
            throw new IllegalArgumentException("Null Value is not permitted");
        } else {
            buffer.putLong(item);
        }
    }
}
