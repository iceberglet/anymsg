package com.minstudio.anymsg.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.minstudio.anymsg.exceptions.DefinitionException;

public class MessageContractBuilderImplTest {

    @Test
    public void test_normal_nested_def() {

    }

    @Test(expected = DefinitionException.class)
    public void test_duplicate_tag() {

    }

    @Test(expected = DefinitionException.class)
    public void test_duplicate_field_name() {

    }

    @Test(expected = DefinitionException.class)
    public void test_build_without_ending_subgroup_def() {

    }
}