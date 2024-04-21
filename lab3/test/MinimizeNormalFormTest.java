package test;

import minimization.*;
import org.junit.Test;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinimizeNormalFormTest {
    @Test
    public void minimizePCNFTest() {
        MinimizeNormalForm minForm = new MinimizeNormalForm();
        minForm.setLogicForm("A&(B|C)");
        minForm.minimizePCNF();
        assertEquals(2, minForm.getResult().size());
        assertTrue(minForm.getResult().contains("BC"));
        assertTrue(minForm.getResult().contains("A"));
    }

    @Test
    public void minimizePDNFTest() {
        MinimizeNormalForm minForm = new MinimizeNormalForm();
        minForm.setLogicForm("A&(B|C)");
        minForm.minimizePDNF();
        assertEquals(2, minForm.getResult().size());
        assertTrue(minForm.getResult().contains("AB"));
        assertTrue(minForm.getResult().contains("AC"));
    }

    @Test
    public void minimizePCNFByTableTest() {
        MinimizeNormalForm minForm = new MinimizeNormalForm();
        minForm.setLogicForm("A&(B|C)");
        minForm.minimizePCNFByTable();
        assertEquals(2, minForm.getResult().size());
        assertTrue(minForm.getResult().contains("BC"));
        assertTrue(minForm.getResult().contains("A"));
    }

    @Test
    public void minimizePDNFByTableTest() {
        MinimizeNormalForm minForm = new MinimizeNormalForm();
        minForm.setLogicForm("A&(B|C)");
        minForm.minimizePDNFByTable();
        assertEquals(2, minForm.getResult().size());
        assertTrue(minForm.getResult().contains("AB"));
        assertTrue(minForm.getResult().contains("AC"));
    }
}
