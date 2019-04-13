package com.github.iceberglet.anymsg;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import com.github.iceberglet.anymsg.bootstrap.MessageContract;
import com.github.iceberglet.anymsg.defs.DoubleDef;
import com.github.iceberglet.anymsg.defs.IntegerDef;
import com.github.iceberglet.anymsg.defs.MessageDef;
import com.github.iceberglet.anymsg.defs.StringDef;
import org.junit.Test;

public class Example {

    //--------------------- User needs to keep its field definitions -----------------------------
    private final MessageDef CLIENT_ORDER = new MessageDef("CLIENT_ORDER", 0, false);
    private final StringDef CLIENT_ORDER_ID = new StringDef("Client Order ID", 1, false);
    private final IntegerDef ORDER_QTY = new IntegerDef("Order Quantity", 2, false);
    private final DoubleDef ORDER_PRICE = new DoubleDef("Order Price", 3, false);
    private final StringDef PRODUCT_ID = new StringDef("Product ID", 4, false);

    private final AnyMsg anyMsg = new AnyMsg(MessageImpl::new);
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    public void defineAContract() {
        MessageContract clientOrder = anyMsg.defineMessage(CLIENT_ORDER)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(ORDER_QTY)
                .addFieldDef(ORDER_PRICE)
                .addFieldDef(PRODUCT_ID)
                .build();
        anyMsg.addMessageDef(clientOrder);
    }

    public void encodeMessage() {
        Message message = new MessageImpl();
        message.setField(CLIENT_ORDER_ID, "#20190304_24392");
        message.setField(ORDER_QTY, 45);
        message.setField(ORDER_PRICE, 355.00);
        message.setField(PRODUCT_ID, "#M341_NINTENDO");
        anyMsg.encode(buffer, CLIENT_ORDER, message);
    }

    public Message decodeMessage() {
        buffer.flip();
        Message message = anyMsg.decode(buffer);
        assertEquals("#20190304_24392", message.getField(CLIENT_ORDER_ID));
        assertEquals(45, (int)message.getField(ORDER_QTY));
        assertEquals(355.00, message.getField(ORDER_PRICE), 0.0001);
        assertEquals("#M341_NINTENDO", message.getField(PRODUCT_ID));
        return message;
    }

    @Test
    public void testExample() {
        defineAContract();
        encodeMessage();
        decodeMessage();
    }
}
