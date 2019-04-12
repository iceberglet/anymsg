package com.anymsg.defs;

import com.anymsg.codec.LongCodec;

public class LongDef extends FieldDef<Long> {

    public LongDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new LongCodec());
    }

}
