package com.minstudio.anymsg.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.minstudio.anymsg.bootstrap.MessageContract;
import com.minstudio.anymsg.bootstrap.MessageContractBuilder;
import com.minstudio.anymsg.defs.FieldDef;
import com.minstudio.anymsg.defs.MessageDef;
import com.minstudio.anymsg.exceptions.DefinitionException;

public class MessageContractBuilderImpl implements MessageContractBuilder {

    private final MessageContractBuilder parent;
    private final List<FieldDef> fieldDefs;
    private final Set<MessageContractBuilderImpl> subMsgContracts;
    private final MessageDef myMessageDef;
    private final int layer;

    public MessageContractBuilderImpl(MessageContractBuilder parent,
                                      MessageDef myMessageDef) {
        this(parent, myMessageDef, 0);
    }

    MessageContractBuilderImpl(MessageContractBuilder parent,
                               MessageDef myMessageDef,
                               int layer) {
        this.parent = parent;
        this.fieldDefs = new ArrayList<>();
        this.subMsgContracts = new HashSet<>();
        this.myMessageDef = myMessageDef;
        this.layer = layer;
    }

    @Override
    public MessageContractBuilder addFieldDef(FieldDef<?> fieldDef) {
        fieldDefs.add(fieldDef);
        return this;
    }

    @Override
    public MessageContractBuilder addSubMessageDef(MessageDef messageDef) {
        MessageContractBuilderImpl subBuilder = new MessageContractBuilderImpl(this, messageDef, this.layer + 1);
        subMsgContracts.add(subBuilder);
        return subBuilder;
    }

    @Override
    public MessageContractBuilder doneGroupDef() {
        if (this.parent == null) {
            throw new DefinitionException("Unexpected call to end group definition. You are already at top layer");
        }
        return parent;
    }

    @Override
    public MessageContract build() {
        //if this is not parent building
        if (this.parent != null) {
            throw new DefinitionException("Invalid Layer. (Hint: Did you forget to call #doneGroupDef()?");
        }
        return doBuild();
    }

    private MessageContract doBuild() {
        return new MessageContractImpl(fieldDefs,
                subMsgContracts.stream().map(MessageContractBuilderImpl::doBuild).collect(Collectors.toList()),
                myMessageDef);
    }
}
