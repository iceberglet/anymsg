package com.github.iceberglet.anymsg.defs;

import com.github.iceberglet.anymsg.codec.ArrayCodec;
import com.github.iceberglet.anymsg.codec.Codec;

public class ArrayDef<T> extends FieldDef<T[]> {

    public ArrayDef(String name,
                    int fieldTag,
                    boolean isOptional,
                    Codec<T> codec) {
        super(name, fieldTag, isOptional, new ArrayCodec<>(codec));
    }
}
