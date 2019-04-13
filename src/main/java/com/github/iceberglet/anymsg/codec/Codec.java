package com.github.iceberglet.anymsg.codec;

import java.nio.ByteBuffer;

import com.github.iceberglet.anymsg.annotations.NotNull;

public interface Codec<P> {

    /**
     * Get the type that this codec is concerned with.
     * @return the type
     */
    Class<P> getType();

    /**
     * Decodes the item. The buffer will be read further
     * @param buffer the buffer
     * @return the item decoded
     */
    P decode(ByteBuffer buffer);

    /**
     * Encodes the item into buffer. Null value is usually not permitted
     * @param buffer buffer to encode into
     * @param item item
     */
    void encode(ByteBuffer buffer, @NotNull P item);

}
