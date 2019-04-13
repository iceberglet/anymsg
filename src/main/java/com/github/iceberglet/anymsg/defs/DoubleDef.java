package com.github.iceberglet.anymsg.defs;

import com.github.iceberglet.anymsg.codec.DoubleCodec;

public class DoubleDef extends FieldDef<Double> {

    public DoubleDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new DoubleCodec());
    }

}
