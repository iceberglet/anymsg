package com.minstudio.anymsg.defs;

import java.util.List;

import com.minstudio.anymsg.codec.Codec;
import com.minstudio.anymsg.codec.ListCodec;

public class ListDef<T> extends FieldDef<List<T>> {

    public ListDef(String name,
                   int fieldTag,
                   boolean isOptional,
                   Codec<T> codec) {
        super(name, fieldTag, isOptional, new ListCodec<>(codec));
    }
}
