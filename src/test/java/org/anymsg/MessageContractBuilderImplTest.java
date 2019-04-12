package org.anymsg;

import org.anymsg.defs.MessageDef;
import org.anymsg.defs.StringDef;
import org.anymsg.exceptions.DefinitionException;
import org.junit.Test;

public class MessageContractBuilderImplTest {

    private static final MessageDef EXECUTION_REPORT = new MessageDef("ExecutionReport", 0, false);

    private static final StringDef CLIENT_ORDER_ID = new StringDef("ClientOrderID", 10, false);
    private static final StringDef EXEC_ID_1 = new StringDef("ExecID", 11, false);
    private static final StringDef EXEC_ID_2 = new StringDef("ExecID2", 11, false);
    private static final StringDef EXEC_ID_3 = new StringDef("ExecID", 12, false);

    private static final MessageDef REPORT_LEG = new MessageDef("ExecutionReportLegs", 1, false);

    @Test
    public void test_normal_nested_def() {
        new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID_1)
                .build();
    }

    @Test(expected = DefinitionException.class)
    public void test_duplicate_tag() {
        new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID_1)
                .addFieldDef(EXEC_ID_2)
                .build();

    }

    @Test(expected = DefinitionException.class)
    public void test_duplicate_field_name() {
        new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID_1)
                .addFieldDef(EXEC_ID_3)
                .build();
    }

    @Test(expected = DefinitionException.class)
    public void test_build_without_ending_subgroup_def() {
        new MessageContractBuilderImpl(null, EXECUTION_REPORT)
                .addFieldDef(CLIENT_ORDER_ID)
                .addFieldDef(EXEC_ID_1)
                .addSubMessageDef(REPORT_LEG)
                .build();
    }
}