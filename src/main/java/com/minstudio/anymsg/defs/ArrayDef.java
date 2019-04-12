package com.minstudio.anymsg.defs;

import com.minstudio.anymsg.codec.ArrayCodec;
import com.minstudio.anymsg.codec.Codec;

public class ArrayDef<T> extends FieldDef<T[]> {

    public ArrayDef(String name,
                    int fieldTag,
                    boolean isOptional,
                    Codec<T> codec) {
        super(name, fieldTag, isOptional, new ArrayCodec<>(codec));
    }
}
