package com.minstudio.anymsg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.minstudio.anymsg.Message;
import com.minstudio.anymsg.defs.FieldDef;
import com.minstudio.anymsg.defs.MessageDef;

class MessageImpl implements Message {

    private final Map<Integer, Object> fields;
    private final Map<Integer, Collection<Message>> groups;

    public MessageImpl() {
        this.fields = new HashMap<>();
        this.groups = new HashMap<>();
    }

    @Override
    public <P> P getField(FieldDef<P> fieldDef) {
        return (P)fields.get(fieldDef.getTag());
    }

    @Override
    public <P> void setField(FieldDef<P> fieldDef, P value) {
        fields.put(fieldDef.getTag(), value);
    }

    @Override
    public Collection<Message> getGroup(MessageDef messageDef) {
        Collection<Message> group = groups.get(messageDef.getTag());
        if(group == null) {
            group = new ArrayList<>();
            groups.put(messageDef.getTag(), group);
        }
        return group;
    }

    @Override
    public String convertToString() {
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<Integer, Object> field : fields.entrySet()) {
            builder.append(field.getKey());
            builder.append("=");
            builder.append(field.getValue());
            builder.append("|");
        }

        for(Map.Entry<Integer, Collection<Message>> group : groups.entrySet()) {
            builder.append(group.getKey());
            builder.append("=[");
            for(Message subMessage : group.getValue()) {
                builder.append(subMessage.convertToString());
                builder.append(",");
            }
            builder.append("]|");
        }
        return builder.toString();
    }
}
