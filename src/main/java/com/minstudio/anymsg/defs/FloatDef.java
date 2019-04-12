package com.minstudio.anymsg.defs;

import com.minstudio.anymsg.codec.FloatCodec;

public class FloatDef extends FieldDef<Float> {

    public FloatDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new FloatCodec());
    }

}
