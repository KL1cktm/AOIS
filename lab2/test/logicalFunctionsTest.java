package test;

import logicalFunctions.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class logicalFunctionsTest {
    @Test
    public void testCreateTruthTable() {
        logicalFunctions logicalFunc = new logicalFunctions("(a|b)&!c");
        logicalFunc.createTruthTable();
        assertEquals(8, logicalFunc.getTruthTable().size());
    }

    @Test
    public void testCreatePDNF() {
        logicalFunctions logicalFunc = new logicalFunctions("((a&(b->c))~(!d))");
        logicalFunc.createTruthTable();
        logicalFunc.createPDNF();
        assertEquals("(!a&!b&!c&d)|(!a&!b&c&d)|(!a&b&!c&d)|(!a&b&c&d)|(a&!b&!c&!d)|(a&!b&c&!d)|(a&b&!c&d)|(a&b&c&!d)", logicalFunc.getResultPDNF());
    }

    @Test
    public void testCreatePCNF() {
        logicalFunctions logicalFunc = new logicalFunctions("((a&(b->c))~(!d))");
        logicalFunc.createTruthTable();
        logicalFunc.createPCNF();

        assertEquals("(a|b|c|d)&(a|b|!c|d)&(a|!b|c|d)&(a|!b|!c|d)&(!a|b|c|!d)&(!a|b|!c|!d)&(!a|!b|c|d)&(!a|!b|!c|!d)", logicalFunc.getResultPCNF());
    }

    @Test
    public void testCreateNumericForms() {
        logicalFunctions logicalFunc = new logicalFunctions("((a&(b->c))~(!d))");
        logicalFunc.createTruthTable();
        logicalFunc.createNumericForms();

        // Assuming A and B are operands
        assertEquals("(1, 3, 5, 7, 8, 10, 13, 14)|", logicalFunc.getNumericPDNF());
        assertEquals("(0, 2, 4, 6, 9, 11, 12, 15)&", logicalFunc.getNumericPCNF());
    }

    @Test
    public void testCreateIndexForm() {
        logicalFunctions logicalFunc = new logicalFunctions("(a|b)&!c");
        logicalFunc.createTruthTable();
        logicalFunc.createIndexForm();
        assertEquals(42, logicalFunc.getIndexForm().intValue());
    }

    @Test
    public void testCorrectTruthTable() {
        logicalFunctions logicalFunc = new logicalFunctions("(a|b)");
        logicalFunc.createTruthTable();
        List<List<Character>> result = new ArrayList<>();
        List<Character> line1 = new ArrayList<>(Arrays.asList('0', '0', '0'));
        result.add(line1);
        List<Character> line2 = new ArrayList<>(Arrays.asList('0', '1', '1'));
        result.add(line2);
        List<Character> line3 = new ArrayList<>(Arrays.asList('1', '0', '1'));
        result.add(line3);
        List<Character> line4 = new ArrayList<>(Arrays.asList('1', '1', '1'));
        result.add(line4);
        assertArrayEquals(result.toArray(), logicalFunc.getTruthTable().toArray());
    }
}
