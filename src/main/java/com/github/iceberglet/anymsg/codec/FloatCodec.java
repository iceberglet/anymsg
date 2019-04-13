package com.github.iceberglet.anymsg.codec;

import java.nio.ByteBuffer;

public class FloatCodec implements Codec<Float> {

    @Override
    public Class<Float> getType() {
        return Float.class;
    }

    @Override
    public Float decode(ByteBuffer buffer) {
        return buffer.getFloat();
    }

    @Override
    public void encode(ByteBuffer buffer, Float item) {
        if(item == null) {
            throw new IllegalArgumentException("Null Value is not permitted");
        } else {
            buffer.putFloat(item);
        }
    }
}
