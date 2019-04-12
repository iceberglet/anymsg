package org.anymsg;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;
import java.util.Collection;

import org.anymsg.bootstrap.MessageContract;
import org.anymsg.defs.LongDef;
import org.anymsg.defs.MessageDef;
import org.anymsg.exceptions.CodecException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.anymsg.defs.DoubleDef;
import org.anymsg.defs.StringDef;

public class MessageContractImplTest {

    private static final MessageDef EXECUTION_REPORT = new MessageDef("ExecutionReport", 0, false);
    private static final MessageDef REPORT_LEG = new MessageDef("ExecutionReportLegs", 1, false);
    private static final MessageDef COUNTERPARTIES = new MessageDef("Counterparties", 2, false);

    private static final StringDef CLIENT_ORDER_ID = new StringDef("ClientOrderID", 10, false);
    private static final StringDef EXEC_ID = new StringDef("ExecID", 11, false);
    private static final DoubleDef AVG_PX = new DoubleDef("AvgPx", 12, false);
    private static final DoubleDef ORDER_QTY = new DoubleDef("OrderQty", 13, false);
    private static final LongDef TRANSACTION_TIME = new LongDef("TransactionTime", 14, true);

    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private MessageContract contract;
    private MessageContract overridingDecodeContract;

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {
        contract = null;
        overridingDecodeContract = null;
    }

    @Test
    public void test_simple() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(EXEC_ID, "Exec#242134");
        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
        assertEquals("Exec#242134", after.getField(EXEC_ID));
    }

    @Test
    public void test_multiple_nested_messages() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID)
                .addSubMessageDef(REPORT_LEG)
                    .addFieldDef(ORDER_QTY)
                    .addFieldDef(AVG_PX)
                    .doneGroupDef()
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(EXEC_ID, "Exec#242134");
        Collection<Message> subMessages = before.getGroup(REPORT_LEG);
        Message a = new MessageImpl();
        a.setField(ORDER_QTY, 1_000_000D);
        a.setField(AVG_PX, 12.32);
        Message b = new MessageImpl();
        b.setField(ORDER_QTY, 2_000_000D);
        b.setField(AVG_PX, 22.32);
        subMessages.add(a);
        subMessages.add(b);

        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
        assertEquals("Exec#242134", after.getField(EXEC_ID));
        assertEquals(2, after.getGroup(REPORT_LEG).size());
    }

    @Test
    public void test_multiple_types_of_nested_messages() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID)
                .addSubMessageDef(REPORT_LEG)
                .addFieldDef(ORDER_QTY)
                .addFieldDef(AVG_PX)
                .doneGroupDef()
                .addSubMessageDef(COUNTERPARTIES)
                .addFieldDef(CLIENT_ORDER_ID)
                .doneGroupDef()
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(EXEC_ID, "Exec#242134");
        Collection<Message> subMessages = before.getGroup(REPORT_LEG);
        Message a = new MessageImpl();
        a.setField(ORDER_QTY, 1_000_000D);
        a.setField(AVG_PX, 12.32);
        subMessages.add(a);

        Collection<Message> subMessages2 = before.getGroup(COUNTERPARTIES);
        Message b = new MessageImpl();
        b.setField(CLIENT_ORDER_ID, "SomeOrder");
        subMessages2.add(b);
        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
        assertEquals("Exec#242134", after.getField(EXEC_ID));
        assertEquals(1, after.getGroup(REPORT_LEG).size());
        Assert.assertEquals(1_000_000D, after.getGroup(REPORT_LEG).iterator().next().getField(ORDER_QTY), 0.0001);
        assertEquals(1, after.getGroup(COUNTERPARTIES).size());
        assertEquals("SomeOrder", after.getGroup(COUNTERPARTIES).iterator().next().getField(CLIENT_ORDER_ID));
    }

    @Test
    public void test_second_degree_nesting() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID)
                .addSubMessageDef(REPORT_LEG)
                    .addFieldDef(ORDER_QTY)
                    .addFieldDef(AVG_PX)
                    .addSubMessageDef(COUNTERPARTIES)
                        .addFieldDef(EXEC_ID)
                        .doneGroupDef()
                .doneGroupDef()
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(EXEC_ID, "Exec#242134");
        Message a = new MessageImpl();
        a.setField(ORDER_QTY, 1_000_000D);
        a.setField(AVG_PX, 12.32);
        before.getGroup(REPORT_LEG).add(a);
        Message b = new MessageImpl();
        b.setField(EXEC_ID, "nested");
        a.getGroup(COUNTERPARTIES).add(b);

        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
        assertEquals("Exec#242134", after.getField(EXEC_ID));
        assertEquals(1, after.getGroup(REPORT_LEG).size());
        assertEquals("nested", after.getGroup(REPORT_LEG).iterator().next().getGroup(COUNTERPARTIES).iterator().next().getField(EXEC_ID));
    }

    @Test
    public void test_parsing_optional_field_that_does_not_exist() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(TRANSACTION_TIME)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
        assertNull(after.getField(TRANSACTION_TIME));
    }

    @Test
    public void test_parsing_optional_field_that_exists() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(TRANSACTION_TIME)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(TRANSACTION_TIME, 4500L);
        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
        assertEquals(4500L, (long)after.getField(TRANSACTION_TIME));
    }

    @Test
    public void test_no_def_found_for_field_when_encoding() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(EXEC_ID, "Exec#242134");
        Message after = getAround(before);
        assertNull(after.getField(EXEC_ID));
    }

    @Test(expected = CodecException.class)
    public void test_no_def_found_for_field_when_decoding() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID)
                .build();
        overridingDecodeContract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(EXEC_ID, "Exec#242134");
        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
    }

    @Test(expected = CodecException.class)
    public void test_field_missing_but_required_in_def_when_encoding() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        getAround(before);
    }

    @Test(expected = CodecException.class)
    public void test_field_missing_but_required_in_def_when_decoding() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .build();
        overridingDecodeContract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        before.setField(EXEC_ID, "Exec#242134");
        Message after = getAround(before);
        assertEquals("Cl#234523", after.getField(CLIENT_ORDER_ID));
    }

    @Test
    public void test_no_def_found_for_group_when_encoding() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        Message a = new MessageImpl();
        a.setField(AVG_PX, 1_000_000D);
        before.getGroup(REPORT_LEG).add(a);

        Message after = getAround(before);
        assertEquals(0, after.getGroup(REPORT_LEG).size());
    }

    @Test(expected = CodecException.class)
    public void test_no_def_found_for_group_when_decoding() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addSubMessageDef(REPORT_LEG)
                    .addFieldDef(AVG_PX)
                    .doneGroupDef()
                .build();
        overridingDecodeContract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        Message a = new MessageImpl();
        a.setField(AVG_PX, 1_000_000D);
        before.getGroup(REPORT_LEG).add(a);
        getAround(before);
    }

    @Test(expected = CodecException.class)
    public void test_group_missing_but_required_in_def() {
        contract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .build();
        overridingDecodeContract = new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addSubMessageDef(REPORT_LEG)
                .addFieldDef(AVG_PX)
                .doneGroupDef()
                .build();
        Message before = new MessageImpl();
        before.setField(CLIENT_ORDER_ID, "Cl#234523");
        getAround(before);
    }

    private Message getAround(Message before) {
        buffer.clear();
        contract.encode(buffer, before);
        buffer.flip();
        if(overridingDecodeContract != null) {
            return overridingDecodeContract.decode(buffer);
        } else {
            return contract.decode(buffer);
        }
    }
}