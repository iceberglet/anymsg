package com.github.anymsg.codec;

import java.nio.ByteBuffer;

public class IntegerCodec implements Codec<Integer> {

    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public Integer decode(ByteBuffer buffer) {
        return buffer.getInt();
    }

    @Override
    public void encode(ByteBuffer buffer, Integer item) {
        if (item == null) {
            throw new IllegalArgumentException("Null Value is not permitted");
        } else {
            buffer.putInt(item);
        }
    }
}
