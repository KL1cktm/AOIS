package binarySystem;

public class BinaryNumber {
    final int binarySize = 16;
    private int decimalIntegerNumber;
    protected String directCode;
    protected String reverseCode;
    protected String additionalCode;

    public BinaryNumber() {
        this.decimalIntegerNumber = 0;
    }
    public BinaryNumber(int decimalIntegerNumber){
        this.decimalIntegerNumber = decimalIntegerNumber;
        translationToBinary();
    }
    protected void setAdditionalCode(String additionalCode) {
        this.additionalCode=additionalCode;
    }
    private void translationToBinary() {
        directNumberTranslation();
        reverseNumberTranslation();
        additionalNumberTranslation();
    }
    public void setDecimalIntegerNumber(int decimalIntegerNumber) {
        this.decimalIntegerNumber = decimalIntegerNumber;
        translationToBinary();
    }

    public int getDecimalIntegerNumber() {
        return  decimalIntegerNumber;
    }
    public void printBinary() {
        System.out.println("Original number: " + decimalIntegerNumber);
        reverseNumberTranslation();
        additionalNumberTranslation();
        System.out.println("Direct Code: " + directCode + "\nReverse Code: " + reverseCode + "\nAdditional Code: " + additionalCode);
    }
    public String getDirectCode() {
        return directCode;
    }
    public int directNumberTranslation() {
        int size;
        int bufferNumber = decimalIntegerNumber;
        char signBit = '0';
        if (decimalIntegerNumber < 0) {
            signBit = '1';
            bufferNumber *= -1;
        }
        String bufferTranslate = "";
        while (bufferNumber != 0) {
            bufferTranslate += bufferNumber % 2;
            bufferNumber = bufferNumber / 2;
        }
        bufferTranslate = inverseTranslation(bufferTranslate, signBit);
        size = bufferTranslate.length();
        this.directCode = setStandardBinaryForm(bufferTranslate);
        return size;
    }

    private String inverseTranslation(String bufferTranslate, char signBit) {
        StringBuilder correctBinaryNumber = new StringBuilder();
        correctBinaryNumber.append(signBit);
        for (int i = bufferTranslate.length() - 1; i > -1; i--) {
            correctBinaryNumber.append(bufferTranslate.charAt(i));
        }
        return correctBinaryNumber.toString();
    }

    public void reverseNumberTranslation() {
        reverseCode = "";
        if (directCode.charAt(0) == '0') {
            reverseCode = directCode;
            return;
        }
        reverseCode += directCode.charAt(0);
        for (Character bit : directCode.substring(1).toCharArray()) {
            if (bit == '1') {reverseCode += '0';}
            else {reverseCode += '1';}
        }
        reverseCode = setStandardBinaryForm(reverseCode);
    }
    public void additionalNumberTranslation() {
        directNumberTranslation();
        if (directCode.charAt(0) == '0') {
            additionalCode = directCode;
            return;
        }
        String temporaryCode = "";
            temporaryCode += directCode.charAt(0);
        for (Character bit : directCode.substring(1).toCharArray()) {
            if (bit == '1') {temporaryCode += '0';}
            else {temporaryCode += '1';}
        }
        char[] bitArray = temporaryCode.toCharArray();
        for (int i = temporaryCode.length() - 1; i > -1 ; i--) {
            if (temporaryCode.charAt(i) == '1') {
                bitArray[i] = '0';
            }
            else {
                bitArray[i] = '1';
                break;
            }
        }
        additionalCode = setStandardBinaryForm(new String(bitArray));
    }
    private String setStandardBinaryForm(String startForm) {
        if (startForm.length() == binarySize) {return startForm;}
        else {
            String standardForm = "";
            standardForm += startForm.charAt(0);
            for (int i=0;i<binarySize-startForm.length();i++) {
                standardForm += '0';
            }
            standardForm += startForm.substring(1);
            return standardForm;
        }
    }
}