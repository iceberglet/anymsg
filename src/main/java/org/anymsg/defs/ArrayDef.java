package org.anymsg.defs;

import org.anymsg.codec.ArrayCodec;
import org.anymsg.codec.Codec;

public class ArrayDef<T> extends FieldDef<T[]> {

    public ArrayDef(String name,
                    int fieldTag,
                    boolean isOptional,
                    Codec<T> codec) {
        super(name, fieldTag, isOptional, new ArrayCodec<>(codec));
    }
}
