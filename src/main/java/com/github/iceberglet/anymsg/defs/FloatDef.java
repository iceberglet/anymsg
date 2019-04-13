package com.github.iceberglet.anymsg.defs;

import com.github.iceberglet.anymsg.codec.FloatCodec;

public class FloatDef extends FieldDef<Float> {

    public FloatDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new FloatCodec());
    }

}
