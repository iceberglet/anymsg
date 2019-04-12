package com.minstudio.anymsg.defs;

import com.minstudio.anymsg.codec.StringCodec;

public class StringDef extends FieldDef<String> {

    public StringDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new StringCodec());
    }

}
