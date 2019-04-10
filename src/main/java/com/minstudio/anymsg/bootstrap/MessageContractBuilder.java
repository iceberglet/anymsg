package com.minstudio.anymsg.bootstrap;

import com.minstudio.anymsg.defs.FieldDef;
import com.minstudio.anymsg.defs.MessageDef;

public interface MessageContractBuilder {

    /**
     * @param fieldDef the field definition to be added at this level.
     * @return the same message contract for further field definition
     */
    MessageContractBuilder addFieldDef(FieldDef<?> fieldDef);

    /**
     * adding a group definition
     * @param messageDef the def
     * @return the message contract at the subgroup level.
     */
    MessageContractBuilder addSubMessageDef(MessageDef messageDef);

    /**
     * Signals that the group definition is done, and returns one level up.
     * @return the super group. null if this is already at the top.
     */
    MessageContractBuilder doneGroupDef();

    MessageContract build();

}
