package com.github.iceberglet.anymsg.defs;

public abstract class BaseDef {

    final int tag;

    final boolean isOptional;

    final String name;

    public BaseDef(int tag, boolean isOptional, String name) {
        this.tag = tag;
        this.isOptional = isOptional;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getTag() {
        return tag;
    }

    public boolean isOptional() {
        return isOptional;
    }

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
}
