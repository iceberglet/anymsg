package com.minstudio.anymsg.defs;

import com.minstudio.anymsg.codec.LongCodec;

public class LongDef extends FieldDef<Long> {

    public LongDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new LongCodec());
    }

}
