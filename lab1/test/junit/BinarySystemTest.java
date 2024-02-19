package test.junit;
import binarySystem.*;
import org.junit.*;

public class BinarySystemTest {
    @Test
    public void translateIntegerValueToBinary(){
        BinaryNumber number = new BinaryNumber(15);
        Assert.assertEquals("0000000000001111",number.getDirectCode());
        Assert.assertEquals(15,number.getDecimalIntegerNumber());
    }
    @Test
    public void translateFloatValueToBinary(){
        float originalValue = 275.423f;
        BinaryFloatNumber number = new BinaryFloatNumber(originalValue);
        BinaryFloatNumber number2 = new BinaryFloatNumber(0.0015f);
        number2.translateToBinaryCode();
        number.translateToBinaryCode();
        Assert.assertEquals(originalValue,number.getOriginalNumber(),0.00001f);
        Assert.assertEquals("01000011100010011011011000100101",number.getBinaryFloatNumber());
        Assert.assertEquals(0.0015f,number2.getOriginalNumber(),0.00001f);
    }
    @Test
    public void basicOperations() {
        BinaryNumber num1 = new BinaryNumber(132);
        BinaryNumber num2 = new BinaryNumber(-421);
        NumbersOperations operations = new NumbersOperations();
        Assert.assertEquals("1111111011011111",operations.sumOperation(num1,num2));
        Assert.assertEquals("0000001000101001",operations.subOperation(num1,num2));
        Assert.assertEquals("Out of range, the command is not executed",operations.multiOperation(num1,num2));
        String divResult = "11000000010011000001111100000111";
        Assert.assertEquals("10111110101000001000100000110100", operations.divOperation(num1,num2));
        Assert.assertEquals(divResult, operations.divOperation(num2,num1));
        Assert.assertEquals(-3.18939f,operations.outputBinaryFloatToDecimal(divResult),0.00001f);
        num1.setDecimalIntegerNumber(17);
        num2.setDecimalIntegerNumber(65);
        Assert.assertEquals("0000010001010001",operations.multiOperation(num1,num2));
    }
    @Test
    public void sumFloatNumbers() {
        BinaryFloatNumber float1 = new BinaryFloatNumber(6841.71f);
        BinaryFloatNumber float2 = new BinaryFloatNumber(1342.32f);
        NumbersOperations operations = new NumbersOperations();
        operations.sumFloatNumbers(float1,float2);
        Assert.assertEquals(8184.03f,operations.outputBinaryFloatToDecimal(operations.getResult()),0.001f);
        float1.setOriginalNumber(-3214.321f);
        float2.setOriginalNumber(3214.321f);
        operations.sumFloatNumbers(float1,float2);
        Assert.assertEquals(0f,operations.outputBinaryFloatToDecimal(operations.getResult()),0.001f);
        float1.setOriginalNumber(321.321f);
        float2.setOriginalNumber(-518.64f);
        operations.sumFloatNumbers(float1,float2);
        Assert.assertEquals(-197.319f,operations.outputBinaryFloatToDecimal(operations.getResult()),0.001f);
    }
    @Test
    public void signNullFloatNumber() {
        BinaryFloatNumber float1 = new BinaryFloatNumber();
        BinaryFloatNumber float2 = new BinaryFloatNumber();
        float1.setOriginalNumber("+0");
        float2.setOriginalNumber("-0");
        float1.translateToBinaryCode();
        float2.translateToBinaryCode();
        Assert.assertEquals("00000000000000000000000000000000",float1.getBinaryFloatNumber());
        Assert.assertEquals("10000000000000000000000000000000",float2.getBinaryFloatNumber());
    }
    @Test
    public void outputIntegerNumber() {
        BinaryNumber number1 = new BinaryNumber(16);
        BinaryNumber number2 = new BinaryNumber(13);
        NumbersOperations operation = new NumbersOperations();
        operation.sumOperation(number1,number2);
        Assert.assertEquals(29,operation.outputFromAdditionalCode());
        operation.multiOperation(number1,number2);
        Assert.assertEquals(208,operation.outputFromDirectedCode());
    }
}
