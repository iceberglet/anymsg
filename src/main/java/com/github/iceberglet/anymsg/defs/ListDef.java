package com.github.iceberglet.anymsg.defs;

import java.util.List;

import com.github.iceberglet.anymsg.codec.Codec;
import com.github.iceberglet.anymsg.codec.ListCodec;

public class ListDef<T> extends FieldDef<List<T>> {

    public ListDef(String name,
                   int fieldTag,
                   boolean isOptional,
                   Codec<T> codec) {
        super(name, fieldTag, isOptional, new ListCodec<>(codec));
    }
}
