package com.minstudio.anymsg.defs;

import com.minstudio.anymsg.codec.DoubleCodec;

public class DoubleDef extends FieldDef<Double> {

    public DoubleDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new DoubleCodec());
    }

}
