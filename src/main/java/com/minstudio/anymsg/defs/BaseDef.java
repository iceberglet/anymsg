package com.minstudio.anymsg.defs;

public abstract class BaseDef {

    final int tag;

    final boolean isOptional;

    BaseDef(int tag, boolean isOptional) {
        this.tag = tag;
        this.isOptional = isOptional;
    }

    public int getTag() {
        return tag;
    }

    public boolean isOptional() {
        return isOptional;
    }
}
