package com.minstudio.anymsg.defs;

import java.nio.ByteBuffer;

public class DoubleDef extends FieldDef<Double> {

    public DoubleDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, Double.class);
    }

    @Override
    public Double decode(ByteBuffer buffer) {
        return buffer.getDouble();
    }

    @Override
    public void encode(ByteBuffer buffer, Double item) {
        buffer.putDouble(item);
    }
}
