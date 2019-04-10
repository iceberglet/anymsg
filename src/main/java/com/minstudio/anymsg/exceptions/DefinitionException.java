package com.minstudio.anymsg.exceptions;

import com.minstudio.anymsg.defs.FieldDef;

public class DefinitionException extends RuntimeException {

    public DefinitionException (FieldDef def) {
        this("Duplicate Definition: " + def);
    }

    private DefinitionException(String message) {
        super(message);
    }
}
