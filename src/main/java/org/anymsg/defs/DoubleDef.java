package org.anymsg.defs;

import org.anymsg.codec.DoubleCodec;

public class DoubleDef extends FieldDef<Double> {

    public DoubleDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new DoubleCodec());
    }

}
