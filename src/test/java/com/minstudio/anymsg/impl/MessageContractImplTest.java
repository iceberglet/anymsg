package com.minstudio.anymsg.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.minstudio.anymsg.exceptions.CodecException;

public class MessageContractImplTest {


    @Before
    public void setup() {

    }

    @Test
    public void test_simple() {

    }

    @Test
    public void test_nested_message() {

    }

    @Test
    public void test_second_degree_nesting() {

    }

    @Test
    public void test_multiple_submessage_of_same_type() {

    }

    @Test
    public void test_parsing_optional_field_that_does_not_exist() {

    }

    @Test
    public void test_parsing_optional_field_that_exists() {

    }

    @Test(expected = CodecException.class)
    public void test_no_def_found_for_field_when_encoding() {

    }

    @Test(expected = CodecException.class)
    public void test_no_def_found_for_field_when_decoding() {

    }

    @Test(expected = CodecException.class)
    public void test_field_missing_but_required_in_def() {

    }

    @Test(expected = CodecException.class)
    public void test_no_def_found_for_group_when_encoding() {

    }

    @Test(expected = CodecException.class)
    public void test_no_def_found_for_group_when_decoding() {

    }

    @Test(expected = CodecException.class)
    public void test_group_missing_but_required_in_def() {

    }
}