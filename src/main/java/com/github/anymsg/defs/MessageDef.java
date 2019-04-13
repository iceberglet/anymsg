package com.github.anymsg.defs;

public class MessageDef extends BaseDef {

    /**
     * @param name name of the message
     * @param fieldTag the message tag
     * @param isOptional only valid/used if this is a sub message type.
     */
    public MessageDef(String name, int fieldTag, boolean isOptional) {
        super(fieldTag, isOptional, name);
    }
}
