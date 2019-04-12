package com.anymsg;

import java.util.Collection;

import com.anymsg.defs.FieldDef;
import com.anymsg.defs.MessageDef;

public interface Message {

    <P> P getField(FieldDef<P> fieldDef);

    <P> void setField(FieldDef<P> fieldDef, P value);

    /**
     * @param messageDef group definition
     * @return a view of collections where one can add messages to.
     *         any manipulation of the returned collection will be reflected on the message itself.
     */
    Collection<Message> getGroup(MessageDef messageDef);

    String convertToString();

}
