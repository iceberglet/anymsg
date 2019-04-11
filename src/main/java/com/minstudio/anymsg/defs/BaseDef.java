package com.minstudio.anymsg.defs;

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
}
