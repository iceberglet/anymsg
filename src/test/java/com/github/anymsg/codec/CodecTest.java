package com.github.anymsg.codec;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class CodecTest {

    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    @Test
    public void test_integer_codec() {
        buffer.clear();
        Codec<Integer> codec = new IntegerCodec();
        codec.encode(buffer, 5);
        buffer.flip();
        assertEquals(5, (int)codec.decode(buffer));
    }

    @Test
    public void test_long_codec() {
        buffer.clear();
        Codec<Long> codec = new LongCodec();
        codec.encode(buffer, -54623L);
        buffer.flip();
        assertEquals(-54623L, (long)codec.decode(buffer));
    }

    @Test
    public void test_double_codec() {
        buffer.clear();
        Codec<Double> codec = new DoubleCodec();
        codec.encode(buffer, 1.336);
        buffer.flip();
        assertEquals(1.336, (double)codec.decode(buffer), 0.000001);
    }

    @Test
    public void test_float_codec() {
        buffer.clear();
        Codec<Float> codec = new FloatCodec();
        codec.encode(buffer, 1.336f);
        buffer.flip();
        assertEquals(1.336f, codec.decode(buffer), 0.000001);
    }

    @Test
    public void test_string_codec() {
        buffer.clear();
        Codec<String> codec = new StringCodec();
        codec.encode(buffer, "MaryLand");
        buffer.flip();
        assertEquals("MaryLand", codec.decode(buffer));
    }

    @Test
    public void test_string_array_codec() {
        buffer.clear();
        ArrayCodec<String> codec = new ArrayCodec<>(new StringCodec());
        codec.encode(buffer, new String[]{"MaryLand", "New Jersey", "Holo|Deck"});
        buffer.flip();
        String[] decoded = codec.decode(buffer);
        assertEquals("MaryLand", decoded[0]);
        assertEquals("New Jersey", decoded[1]);
        assertEquals("Holo|Deck", decoded[2]);
    }


    @Test
    public void test_string_list_codec() {
        buffer.clear();
        ListCodec<String> codec = new ListCodec<>(new StringCodec());
        codec.encode(buffer, Arrays.asList("MaryLand", "New Jersey", "Holo|Deck"));
        buffer.flip();
        List<String> decoded = codec.decode(buffer);
        assertEquals("MaryLand", decoded.get(0));
        assertEquals("New Jersey", decoded.get(1));
        assertEquals("Holo|Deck", decoded.get(2));
    }
}