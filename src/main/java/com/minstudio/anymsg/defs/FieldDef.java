package com.minstudio.anymsg.defs;

import java.nio.ByteBuffer;

public abstract class FieldDef<P> extends BaseDef {

    private final Class<P> fieldType;

    public FieldDef(int fieldTag, boolean isOptional, Class<P> fieldType) {
        super(fieldTag, isOptional);
        this.fieldType = fieldType;
    }

    public final Class<P> getFieldType() {
        return fieldType;
    }

    /**
     * Decodes the item. The buffer will be read further
     * @param buffer the buffer
     * @return the item decoded
     */
    public abstract P decode(ByteBuffer buffer);

    /**
     * encodes the item into buffer
     * @param buffer buffer to encode into
     */
    public abstract void encode(ByteBuffer buffer, P item);

    @Override
    public final int hashCode() {
        return Integer.hashCode(this.tag);
    }

    @Override
    public final boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(obj instanceof FieldDef) {
            return this.tag == ((FieldDef) obj).tag;
        }
        return false;
    }

    @Override
    public final String toString() {
        return this.getClass().getSimpleName() +
                "{" +
                "fieldType=" + fieldType +
                ", tag=" + tag +
                ", isOptional=" + isOptional +
                '}';
    }
}
