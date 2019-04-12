package com.anymsg.defs;

import java.util.List;

import com.anymsg.codec.Codec;
import com.anymsg.codec.ListCodec;

public class ListDef<T> extends FieldDef<List<T>> {

    public ListDef(String name,
                   int fieldTag,
                   boolean isOptional,
                   Codec<T> codec) {
        super(name, fieldTag, isOptional, new ListCodec<>(codec));
    }
}
