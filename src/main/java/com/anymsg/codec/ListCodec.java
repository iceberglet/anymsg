package com.anymsg.codec;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ListCodec<T> implements Codec<List<T>> {

    private final Codec<T> underlying;

    public ListCodec(Codec<T> underlying) {
        this.underlying = underlying;
    }

    @Override
    public Class<List<T>> getType() {
        return (Class<List<T>>) (Object) (List.class);
    }

    @Override
    public List<T> decode(ByteBuffer buffer) {
        int arrayNumbers = buffer.getInt();
        if(arrayNumbers == 0) {
            return null;
        }
        List<T> res = new ArrayList<>(arrayNumbers);
        for(int i = 0; i < arrayNumbers; ++i) {
            res.add(underlying.decode(buffer));
        }
        return res;
    }

    @Override
    public void encode(ByteBuffer buffer, List<T> item) {
        if(item == null) {
            buffer.putInt(0);
        } else {
            buffer.putInt(item.size());
            for(T t : item) {
                underlying.encode(buffer, t);
            }
        }
    }
}
