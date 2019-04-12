package org.anymsg.codec;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

public class ArrayCodec<T> implements Codec<T[]> {

    private final Codec<T> underlying;
    private final Class clazz;

    public ArrayCodec(Codec<T> underlying) {
        this.underlying = underlying;
        this.clazz = Array.newInstance(underlying.getType(), 0).getClass();
    }

    @Override
    public Class<T[]> getType() {
        return clazz;
    }

    @Override
    public T[] decode(ByteBuffer buffer) {
        int arrayNumbers = buffer.getInt();
        if(arrayNumbers == 0) {
            return null;
        }
        T[] res = (T[])Array.newInstance(underlying.getType(), arrayNumbers);
        for(int i = 0; i < arrayNumbers; ++i) {
            res[i] = underlying.decode(buffer);
        }
        return res;
    }

    @Override
    public void encode(ByteBuffer buffer, T[] item) {
        if(item == null) {
            buffer.putInt(0);
        } else {
            buffer.putInt(item.length);
            for(T t : item) {
                underlying.encode(buffer, t);
            }
        }
    }
}
