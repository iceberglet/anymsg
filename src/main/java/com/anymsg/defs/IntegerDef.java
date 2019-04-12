package com.anymsg.defs;

import com.anymsg.codec.IntegerCodec;

public class IntegerDef extends FieldDef<Integer> {

    public IntegerDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new IntegerCodec());
    }
}
