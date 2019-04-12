package com.anymsg.defs;

import com.anymsg.codec.DoubleCodec;

public class DoubleDef extends FieldDef<Double> {

    public DoubleDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new DoubleCodec());
    }

}
