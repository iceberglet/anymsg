package com.anymsg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.anymsg.bootstrap.MessageContract;
import com.anymsg.bootstrap.MessageContractBuilder;
import com.anymsg.defs.BaseDef;
import com.anymsg.defs.FieldDef;
import com.anymsg.defs.MessageDef;
import com.anymsg.exceptions.DefinitionException;

class MessageContractBuilderImpl implements MessageContractBuilder {

    private final MessageContractBuilder parent;
    private final List<FieldDef> fieldDefs;
    private final Set<MessageContractBuilderImpl> subMsgContracts;
    private final MessageDef myMessageDef;
    private final int layer;

    private final Set<Integer> addedTags;
    private final Set<String> addedNames;


    MessageContractBuilderImpl(MessageContractBuilder parent,
                                      MessageDef myMessageDef) {
        this(parent, myMessageDef, 0);
    }

    private MessageContractBuilderImpl(MessageContractBuilder parent,
                               MessageDef myMessageDef,
                               int layer) {
        this.parent = parent;
        this.fieldDefs = new ArrayList<>();
        this.subMsgContracts = new HashSet<>();
        this.myMessageDef = myMessageDef;
        this.layer = layer;
        this.addedTags = new HashSet<>();
        this.addedNames = new HashSet<>();
    }

    @Override
    public MessageContractBuilder addFieldDef(FieldDef<?> fieldDef) {
        checkForDuplicate(fieldDef);
        fieldDefs.add(fieldDef);
        return this;
    }

    @Override
    public MessageContractBuilder addSubMessageDef(MessageDef messageDef) {
        checkForDuplicate(messageDef);
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

    private void checkForDuplicate(BaseDef baseDef) {
        if(!addedNames.add(baseDef.getName())) {
            throw new DefinitionException("Field/Group Name Already Defined in Message: " + baseDef);
        }
        if(!addedTags.add(baseDef.getTag())) {
            throw new DefinitionException("Field/Group Tag Already Defined in Message: " + baseDef);
        }
    }
}
