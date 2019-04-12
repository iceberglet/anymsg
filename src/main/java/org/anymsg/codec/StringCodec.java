package org.anymsg.codec;

import java.nio.ByteBuffer;

public class StringCodec implements Codec<String> {

    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String decode(ByteBuffer buffer) {
        int length = buffer.getInt();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes);
    }

    @Override
    public void encode(ByteBuffer buffer, String item) {
        if(item == null) {
            throw new IllegalArgumentException("Null value is not supported!");
        }
        buffer.putInt(item.length());
        buffer.put(item.getBytes());
    }
}
