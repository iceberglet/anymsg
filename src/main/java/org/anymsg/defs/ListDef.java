package org.anymsg.defs;

import java.util.List;

import org.anymsg.codec.Codec;
import org.anymsg.codec.ListCodec;

public class ListDef<T> extends FieldDef<List<T>> {

    public ListDef(String name,
                   int fieldTag,
                   boolean isOptional,
                   Codec<T> codec) {
        super(name, fieldTag, isOptional, new ListCodec<>(codec));
    }
}
