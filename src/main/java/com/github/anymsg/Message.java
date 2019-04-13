package com.github.anymsg;

import java.util.Collection;

import com.github.anymsg.defs.MessageDef;
import com.github.anymsg.defs.FieldDef;

public interface Message {

    <P> P getField(FieldDef<P> fieldDef);

    /**
     * Sets the field to certain value.
     * @param fieldDef field def. it is used here instead of tag in order to enforce type safety
     * @param value value to set
     * @param <P> type
     */
    <P> void setField(FieldDef<P> fieldDef, P value);

    /**
     * @param messageDef group definition
     * @return a view of collections where one can add messages to.
     *         any manipulation of the returned collection will be reflected on the message itself.
     */
    Collection<Message> getGroup(MessageDef messageDef);

    /**
     * Checks whether the tag is present (field or group is set to some value, even null)
     * @param tag the tag
     * @return tag
     */
    boolean isTagPresent(int tag);

    /**
     * Clears the message
     */
    void clear();

    /**
     * Formats this message into FIX-like string
     * @return message string
     */
    String formatToString();

}
