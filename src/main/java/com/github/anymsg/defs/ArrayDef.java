package com.github.anymsg.defs;

import com.github.anymsg.codec.ArrayCodec;
import com.github.anymsg.codec.Codec;

public class ArrayDef<T> extends FieldDef<T[]> {

    public ArrayDef(String name,
                    int fieldTag,
                    boolean isOptional,
                    Codec<T> codec) {
        super(name, fieldTag, isOptional, new ArrayCodec<>(codec));
    }
}
