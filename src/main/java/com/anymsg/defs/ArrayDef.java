package com.anymsg.defs;

import com.anymsg.codec.ArrayCodec;
import com.anymsg.codec.Codec;

public class ArrayDef<T> extends FieldDef<T[]> {

    public ArrayDef(String name,
                    int fieldTag,
                    boolean isOptional,
                    Codec<T> codec) {
        super(name, fieldTag, isOptional, new ArrayCodec<>(codec));
    }
}
