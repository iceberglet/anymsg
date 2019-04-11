package com.minstudio.anymsg.defs;

import java.nio.ByteBuffer;

public class LongDef extends FieldDef<Long> {

    public LongDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, Long.class);
    }

    @Override
    public Long decode(ByteBuffer buffer) {
        return buffer.getLong();
    }

    @Override
    public void encode(ByteBuffer buffer, Long item) {
        buffer.putLong(item);
    }
}
