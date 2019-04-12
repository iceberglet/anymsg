package org.anymsg.defs;

import org.anymsg.codec.StringCodec;

public class StringDef extends FieldDef<String> {

    public StringDef(String name, int fieldTag, boolean isOptional) {
        super(name, fieldTag, isOptional, new StringCodec());
    }

}
