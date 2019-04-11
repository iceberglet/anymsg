package com.minstudio.anymsg.defs;

import java.nio.ByteBuffer;

public class StringDef extends FieldDef<String> {

    public StringDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, String.class);
    }

    @Override
    public String decode(ByteBuffer buffer) {
        int length = buffer.getInt();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes);
    }

    @Override
    public void encode(ByteBuffer buffer, String item) {
        buffer.putInt(item.length());
        buffer.put(item.getBytes());
    }
}
