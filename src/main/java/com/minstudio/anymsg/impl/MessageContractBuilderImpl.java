package com.minstudio.anymsg.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.minstudio.anymsg.bootstrap.MessageContract;
import com.minstudio.anymsg.bootstrap.MessageContractBuilder;
import com.minstudio.anymsg.defs.FieldDef;
import com.minstudio.anymsg.defs.MessageDef;

public class MessageContractBuilderImpl implements MessageContractBuilder {

    private final MessageContractBuilder parent;
    private final List<FieldDef> fieldDefs;
    private final List<MessageContractBuilder> subMsgContracts;
    private final MessageDef myMessageDef;

    public MessageContractBuilderImpl(MessageContractBuilder parent,
                                      MessageDef myMessageDef
    ) {
        this.parent = parent;
        this.fieldDefs = new ArrayList<>();
        this.subMsgContracts = new ArrayList<>();
        this.myMessageDef = myMessageDef;
    }

    @Override
    public MessageContractBuilder addFieldDef(FieldDef<?> fieldDef) {
        fieldDefs.add(fieldDef);
        return this;
    }

    @Override
    public MessageContractBuilder addSubMessageDef(MessageDef messageDef) {
        MessageContractBuilder subBuilder = new MessageContractBuilderImpl(this, messageDef);
        subMsgContracts.add(subBuilder);
        return subBuilder;
    }

    @Override
    public MessageContractBuilder doneGroupDef() {
        return parent;
    }

    @Override
    public MessageContract build() {
        return new MessageContractImpl(fieldDefs,
                subMsgContracts.stream().map(MessageContractBuilder::build).collect(Collectors.toList()),
                myMessageDef);
    }
}
