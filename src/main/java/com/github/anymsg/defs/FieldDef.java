package com.github.anymsg.defs;

import java.nio.ByteBuffer;

import com.github.anymsg.codec.Codec;

public class FieldDef<P> extends BaseDef implements Codec<P> {

    private final Codec<P> codec;

    public FieldDef(String name, int fieldTag, boolean isOptional, Codec<P> codec) {
        super(fieldTag, isOptional, name);
        this.codec = codec;
    }

    public final Class<P> getType() {
        return codec.getType();
    }

    @Override
    public P decode(ByteBuffer buffer) {
        return codec.decode(buffer);
    }

    @Override
    public void encode(ByteBuffer buffer, P item) {
        codec.encode(buffer, item);
    }

    @Override
    public final String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "name=" + name +
                ", fieldType=" + codec.getType() +
                ", tag=" + tag +
                ", isOptional=" + isOptional +
                '}';
    }
}
