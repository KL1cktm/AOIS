package test;

import minimization.MethodCarnot;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MethodCarnotTest {
    @Test
    public void minimizePCNF3Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("a&(b|c)");
        method.minimizePCNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("a");
        newLine.add("bc");
        Assert.assertEquals(newLine, method.getResult());
    }

    @Test
    public void minimizePDNF3Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("a&(b|c)");
        method.minimizePDNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("ac");
        newLine.add("ab");
        Assert.assertEquals(newLine, method.getResult());
    }

    @Test
    public void minimizePCNF2Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("(b|c)");
        method.minimizePCNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("bc");
        Assert.assertEquals(newLine, method.getResult());
    }

    @Test
    public void minimizePDNF2Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("(b|c)");
        method.minimizePDNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("c");
        newLine.add("b");
        Assert.assertEquals(newLine, method.getResult());
    }

    @Test
    public void minimizePCNF4Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("(a|c)->b|e");
        method.minimizePCNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("b!ce");
        newLine.add("!abe");
        Assert.assertEquals(newLine, method.getResult());
    }

    @Test
    public void minimizePDNF4Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("(a|c)->b|e");
        method.minimizePDNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("b");
        newLine.add("e");
        newLine.add("!a!c");
        Assert.assertEquals(newLine, method.getResult());
    }

    @Test
    public void minimizePCNF5Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("(a|c)->(b|e)&d");
        method.minimizePCNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("!cd");
        newLine.add("!acd");
        newLine.add("!ade");
        newLine.add("!ab!de");
        newLine.add("b!c!de");
        Assert.assertEquals(newLine, method.getResult());
    }

    @Test
    public void minimizePDNF5Test() {
        MethodCarnot method = new MethodCarnot();
        method.setLogicFormula("(a|c)->(b|e)&d");
        method.minimizePDNF();
        List<String> newLine = new ArrayList<>();
        newLine.add("!a!c");
        newLine.add("bd");
        newLine.add("!cde");
        newLine.add("!ab");
        newLine.add("cde");
        Assert.assertEquals(newLine, method.getResult());
    }
}
