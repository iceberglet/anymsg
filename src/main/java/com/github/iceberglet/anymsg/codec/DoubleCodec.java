package com.github.iceberglet.anymsg.codec;

import java.nio.ByteBuffer;

public class DoubleCodec implements Codec<Double> {

    @Override
    public Class<Double> getType() {
        return Double.class;
    }

    @Override
    public Double decode(ByteBuffer buffer) {
        return buffer.getDouble();
    }

    @Override
    public void encode(ByteBuffer buffer, Double item) {
        if(item == null) {
            throw new IllegalArgumentException("Null value is not supported!");
        }
        buffer.putDouble(item);
    }
}
