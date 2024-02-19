package binarySystem;

public class BinaryFloatNumber {
    final int binarySize = 32;
    final int integerBinarySize = 16;
    final int mantissaSize = 23;
    final int orderSize = 8;
    final int addedExp = 127;
    private float originalNumber;
    private String signNull;
    private String binaryOriginalNumber;
    public BinaryFloatNumber(){
        originalNumber = 0.0f;
    }
    public BinaryFloatNumber(float number) {
        originalNumber = number;
        translateToBinaryCode();
    }
    public void setOriginalNumber(float number) {
        originalNumber = number;
        translateToBinaryCode();
    }

    public void setOriginalNumber(String number) {
        signNull = number;
    }

    public float getOriginalNumber() {
        return originalNumber;
    }
    public void printBinaryFloatNumber() {
        System.out.println("Binary Code: " + binaryOriginalNumber);
    }
    public String getBinaryFloatNumber() {
        return binaryOriginalNumber;
    }
    public void translateToBinaryCode() {
        binaryOriginalNumber = "";
        if (signNull != null) translateSignNullToUnSign();
        if (!binaryOriginalNumber.equals("")) return;
        setSignNumber();
        if (originalNumber == 0.0f) {
            binaryOriginalNumber = "00000000000000000000000000000000";
            return;
        }
        if (Math.abs(originalNumber) < 1) {
            inverseStepOfOrder();
            return;
        }
        String integerPart = integerPartOfNumber(Math.abs(originalNumber));
        String decimalPart = decimalPartOfNumber(Math.abs(originalNumber));
        int orderOfTheNumber = integerPart.length();
        String orderPart = transferToBinaryCode(orderOfTheNumber);
        String mantissa = getMantissa(integerPart, decimalPart);
        binaryOriginalNumber += orderPart + mantissa;
    }

    private void setSignNumber() {
        if (originalNumber >= 0) {
            binaryOriginalNumber += '0';
        } else {
            binaryOriginalNumber += '1';
        }
    }

    private String integerPartOfNumber(float positiveOriginalNumber) {
        int integerNumber = (int) positiveOriginalNumber;
        BinaryNumber number = new BinaryNumber(integerNumber);
        //number.setDecimalIntegerNumber();
        number.directNumberTranslation();
        String integerPart = number.getDirectCode();
        for (int i = 0; i < integerBinarySize; i++) {
            if (integerPart.charAt(i) == '1') {
                integerPart = integerPart.substring(i + 1, integerBinarySize);
                break;
            }
        }
        return integerPart;
    }

    private String decimalPartOfNumber(float positiveOriginalNumber) {
        float decimalPart = positiveOriginalNumber - (int) positiveOriginalNumber;
        String decimalBitPart = "";
        return getDecimalBitPart(decimalPart,decimalBitPart);
    }
    private String getDecimalBitPart(float decimalPart,String decimalBitPart) {
        for (int i = 0; i < mantissaSize; i++) {
            decimalPart = decimalPart * 2;
            int integerPart = (int) (decimalPart);
            decimalBitPart += integerPart;
            decimalPart = decimalPart - (int) decimalPart;
        }
        return decimalBitPart;
    }
    private String transferToBinaryCode(int value) {
        value = value + addedExp;
        BinaryNumber number = new BinaryNumber(value);
        //number.setDecimalIntegerNumber(value);
        number.directNumberTranslation();
        String orderPart = number.getDirectCode();
        orderPart = orderPart.substring(orderSize, integerBinarySize);
        return orderPart;
    }

    private String getMantissa(String integerPart, String decimalPart) {
        String mantissa = integerPart;
        mantissa += decimalPart;
        mantissa = mantissa.substring(0, mantissaSize);
        return mantissa;
    }

    private String inverseStepOfOrder() {
        String mantissa = enlargedMantissa(Math.abs(originalNumber));
        int order = negativeOrderOfNumber(mantissa);
        mantissa = mantissa.substring(order);
        BinaryNumber bufNumber = new BinaryNumber(-1 * order + addedExp);
        bufNumber.directNumberTranslation();
        String orderPart = bufNumber.getDirectCode();
        orderPart = orderPart.substring(orderSize);
        binaryOriginalNumber += orderPart + mantissa;
        return binaryOriginalNumber;
    }
    private String enlargedMantissa(float positiveOriginalNumber) {
        float decimalPart = positiveOriginalNumber - (int) positiveOriginalNumber;
        String decimalBitPart = "";
        while (true) {
            decimalPart = decimalPart * 2;
            int integerPart = (int) (decimalPart);
            decimalBitPart += integerPart;
            decimalPart = decimalPart - (int) decimalPart;
            if (integerPart == 1) {
                break;
            }
        }
        return getDecimalBitPart(decimalPart,decimalBitPart);
    }

    private int negativeOrderOfNumber(String mantissa) {
        int i = 0;
        for (Character bit : mantissa.toCharArray()) {
            i++;
            if (bit == '1') {
                return i;
            }
        }
        return i;
    }

    private void translateSignNullToUnSign() {
        if (signNull.equals("-0")) {
            binaryOriginalNumber = "1";
            originalNumber = 0.0f;
            for (int i = 1; i < binarySize; i++) {
                binaryOriginalNumber += "0";
            }
        } else if (signNull.equals("+0")) {
            originalNumber = 0.0f;
            for (int i = 0; i < binarySize; i++) {
                binaryOriginalNumber += "0";
            }
        } else {
            originalNumber = Float.parseFloat(signNull);
        }
    }
}
