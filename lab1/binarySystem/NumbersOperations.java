package binarySystem;

import java.util.Objects;

public class NumbersOperations {
    final String binaryNull = "00000000000000000000000000000000";
    final int binarySize = 32;
    final int integerBinarySize = 16;
    final int mantissaSize = 23;
    final int orderSize = 8;
    final int lastOrderIndex = 7;
    final int addedExp = 127;
    final int mantissaIndexStart = 9;
    private String result;
    private String resultInDirectCode;
    private char bufferBit;
    private static int nulls = 0;

    public void printResult() {
        System.out.println("Result: " + result);
    }

    public String getResult() {
        return result;
    }

    public float outputBinaryFloatToDecimal(String number) {
        if (number.equals(binaryNull)) {
            return 0f;
        }
        BinaryNumber number127 = new BinaryNumber(addedExp);
        String orderPart = subtractionFloatNumbers(number.substring(1, mantissaIndexStart), number127.directCode.substring(orderSize, integerBinarySize));
        int order = getNumericOrder(orderPart);
        String integerPart = getIntegerPart(order, number);
        String fractionalPart = getFractionalPart(integerPart, number);
        int integerPartToDecimal = integerPartToDecimal(integerPart);
        float fractionalPartToDecimal = fractionalPartToDecimal(fractionalPart);
        float result = integerPartToDecimal + fractionalPartToDecimal;
        if (number.charAt(0) == '1') {
            result *= -1;
        }
        System.out.println("In decimal system: " + result);
        return result;
    }

    private int integerPartToDecimal(String integerPart) {
        int integerNumber = 0;
        int degree = 0;
        for (int i = integerPart.length() - 1; i >= 0; i--) {
            if (integerPart.charAt(i) == '1') {
                integerNumber += Math.pow(2, degree);
            }
            degree++;
        }
        return integerNumber;
    }

    private float fractionalPartToDecimal(String fractionalPart) {
        float fractionalNumber = 0.0f;
        int degree = -1;
        for (int i = 0; i < fractionalPart.length(); i++) {
            if (fractionalPart.charAt(i) == '1') {
                fractionalNumber += Math.pow(2, degree);
            }
            degree--;
        }
        return fractionalNumber;
    }

    private String getFractionalPart(String integerPart, String number) {
        return number.substring(orderSize + integerPart.length());
    }

    private String getIntegerPart(int order, String number) {
        String integerPart = "1";
        integerPart += number.substring(mantissaIndexStart, mantissaIndexStart + order);
        return integerPart;
    }

    private int getNumericOrder(String order) {
        int result = 0;
        for (int i = order.length() - 1; i >= 0; i--) {
            if (order.charAt(i) == '1') {
                result += Math.pow(2, lastOrderIndex - i);
            }
        }
        return result;
    }

    public String divOperation(BinaryNumber number1, BinaryNumber number2) {
        if (number2.getDecimalIntegerNumber() == 0) {
            throw new RuntimeException("Division by null is not possible");
        }
        nulls = 0;
        String divisible = number1.getDirectCode().substring(startPartOfNumber(number1.getDirectCode()));
        String divider = number2.getDirectCode().substring(startPartOfNumber(number2.getDirectCode()));
        IdentifyBitSign(number1.directCode.charAt(0), number2.directCode.charAt(0));
        divisionProcess(divisible.toCharArray(), divider.toCharArray());
        correctForm();
        if (Math.abs(number1.getDecimalIntegerNumber()) < Math.abs(number2.getDecimalIntegerNumber())) {
            BinaryFloatNumber floatNumber = new BinaryFloatNumber();
            if (result.charAt(0) == '0') {
                floatNumber.setOriginalNumber(-1.0f);
            } else {
                floatNumber.setOriginalNumber(1.0f);
            }
            floatNumber.translateToBinaryCode();
            sumFloatBinaryNumbers(floatNumber.getBinaryFloatNumber(), result);
        }
        return result;
    }

    private void correctForm() {
        BinaryNumber number = new BinaryNumber(result.indexOf(',') + 125);
        String orderPart = result.substring(0, 1);
        orderPart += number.getDirectCode().substring(orderSize);
        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder.deleteCharAt(result.indexOf(','));
        result = stringBuilder.toString();
        result += "000000000000000000000000";
        String mantissa = result.substring(2, 25);
        result = orderPart;
        result += mantissa;
    }

    private void divisionProcess(char[] number1, char[] number2) {
        String divisiblePart = "";
        divisiblePart += number1[0];
        int addedPosition = 1;
        for (int i = 1; i < 45; i++) {
            nulls++;
            divisiblePart = deleteNulls(divisiblePart);
            if (!compareNumbers(divisiblePart, number2)) {
                if (addedPosition < number1.length) {
                    divisionNotHappen(addedPosition, number1);
                    divisiblePart += number1[addedPosition];
                    addedPosition++;
                } else {
                    divisiblePart += writeToResult(number1.length, addedPosition, divisiblePart);
                    addedPosition++;
                }
            } else {
                divisiblePart = resultOfDivision(divisiblePart.toCharArray(), number2);
            }
        }
    }

    private void divisionNotHappen(int addedPosition, char[] number) {
        if ((result.length() > 1 && nulls > 1) || nulls == 0) {
            nulls = -1;
            result += "0";
        }
        if (addedPosition + 1 == number.length) {
            nulls = 1;
        }
    }

    private String resultOfDivision(char[] divisible, char[] divider) {
        nulls = 0;
        result += "1";
        if (divisible.length > divider.length) {
            char[] updateDivider = new char[divider.length + 1];
            updateDivider[0] = '0';
            for (int i = 0; i < divider.length; i++) {
                updateDivider[i + 1] = divider[i];
            }
            return subtraction(divisible, updateDivider);
        } else {
            return subtraction(divisible, divider);
        }
    }

    private String subtraction(char[] divisible, char[] divider) {
        String inverseDivisible = "";
        for (int i = divider.length - 1; i >= 0; i--) {
            if (divisible[i] == '1' && divider[i] == '0') {
                inverseDivisible += '1';
            } else if (divisible[i] == divider[i]) {
                inverseDivisible += '0';
            } else if (divisible[i] == '0' && divider[i] == '1') {
                inverseDivisible += '1';
                divisible = changeDivisible(divisible, i - 1);
            }
        }
        String newDivisible = "";
        for (int i = inverseDivisible.length() - 1; i >= 0; i--) {
            newDivisible += inverseDivisible.charAt(i);
        }
        return newDivisible;
    }

    private char[] changeDivisible(char[] divisible, int index) { //index with -1
        for (; index >= 0; index--) {
            if (divisible[index] == '0') {
                divisible[index] = '1';
            } else {
                divisible[index] = '0';
                return divisible;
            }
        }
        return null;
    }

    private String deleteNulls(String divisible) {
        for (int i = 0; i < divisible.length(); i++) {
            if (divisible.charAt(i) == '1') {
                divisible = divisible.substring(i);
                return divisible;
            }
        }
        return "";
    }

    private String writeToResult(int size, int position, String divisible) {
        if (position == size && !Objects.equals(divisible, "") && nulls > 1) {
            nulls = 1;
            result += "0,";
            return "0";
        }
        if (position > size) {
            if (nulls > 1) {
                result += "0";
                nulls = 1;
            }
            return "0";
        }
        nulls = 1;
        result += ",";
        return "0";
    }

    private boolean compareNumbers(String number1, char[] number2) {
        if (number1.length() < number2.length) {
            return false;
        } else if (number1.length() > number2.length) {
            return true;
        } else {
            for (int i = 0; i < number2.length; i++) {
                if (number1.charAt(i) == number2[i] && (number1.charAt(i) == '1' || number2[i] == '0')) {
                    continue;
                } else if (number1.charAt(i) == '0' && number2[i] == '1') {
                    return false;
                }
                return true;
            }
            return true;
        }
    }

    private int startPartOfNumber(String number) {
        for (int i = 1; i < number.length(); i++) {
            if (number.charAt(i) == '1') {
                return i;
            }
        }
        return number.length() - 1;
    }

    private void IdentifyBitSign(char signBit1, char signBit2) {
        if (signBit1 == signBit2 && (signBit1 == '1' || signBit1 == '0')) {
            result = "0";
        } else {
            result = "1";
        }
    }

    public String multiOperation(BinaryNumber number1, BinaryNumber number2) {
        if (checkMultiRange(number1, number2)) {
            return ("Out of range, the command is not executed");
        }
        String summand1;
        String summand2;
        if (number2.directCode.charAt(15) == '0') {
            summand1 = "0000000000000000";
        } else {
            summand1 = number1.directCode;
        }
        for (int i = 14; i >= 0; i--) {
            if (number2.directCode.charAt(i) == '0') {
                summand2 = "0000000000000000";
            } else {
                summand2 = number1.directCode;
            }
            summand1 = dynamicSum(summand1, summand2, (byte) (15 - i));
        }
        summand1 = summand1.substring(summand1.length() - integerBinarySize);
        result = resultInDirectCode = changeSignResult(number1.directCode, number2.directCode, summand1);
        return result;
    }

    private String changeSignResult(String originalNumber1, String originalNumber2, String summand) {
        StringBuilder stringBuilder = new StringBuilder(summand);
        if (originalNumber1.charAt(0) != originalNumber2.charAt(0)) {
            stringBuilder.setCharAt(0, '1');
        } else {
            stringBuilder.setCharAt(0, '0');
        }
        return stringBuilder.toString();
    }

    public String sumOperation(BinaryNumber number1, BinaryNumber number2) {
        result = "";
        number1.additionalNumberTranslation();          //реализовать это в доп функции(вызова)
        number2.additionalNumberTranslation();          //-//-
        bufferBit = '0';
        for (int i = 15; i >= 0; i--) {
            result += bitAdding(number1.additionalCode.charAt(i), number2.additionalCode.charAt(i));
        }
        result = inverseCode();
        if (checkRange(number1, number2)) {
            return "Out of range, the command is not executed";
        }
        return result;
    }

    public String subOperation(BinaryNumber number1, BinaryNumber number2) {
        result = "";
        bufferBit = '0';
        BinaryNumber divisible = number1;
        divisible.additionalNumberTranslation();          //реализовать это в доп функции(вызова)
        int originalNumber = number2.getDecimalIntegerNumber();
        BinaryNumber newNumber = new BinaryNumber(originalNumber * -1);
        sumOperation(divisible, newNumber);
        return result;
    }

    private char bitAdding(char bit1, char bit2) {
        if (bit1 == '0' && bit2 == '0') {
            if (bufferBit == '0') {
                return '0';
            } else {
                bufferBit = '0';
                return '1';
            }
        }
        if (bit1 == '1' && bit2 == '1') {
            if (bufferBit == '0') {
                bufferBit = '1';
                return '0';
            } else {
                bufferBit = '1';
                return '1';
            }
        }
        if ((bit1 == '0' && bit2 == '1') || (bit1 == '1' && bit2 == '0')) {
            if (bufferBit == '0') {
                return '1';
            } else {
                bufferBit = '1';
                return '0';
            }
        }
        return '-';
    }

    private String inverseCode() {
        String bufferString = "";
        for (int i = 15; i >= 0; i--) {
            bufferString += result.charAt(i);
        }
        return bufferString;
    }

    private boolean checkRange(BinaryNumber num1, BinaryNumber num2) {
        if (num1.additionalCode.charAt(0) == '0' && num2.additionalCode.charAt(0) == '0' && result.charAt(0) == '1') {
            result = "";
            return true;
        } else if (num1.additionalCode.charAt(0) == '1' && num2.additionalCode.charAt(0) == '1' && result.charAt(0) == '0') {
            result = "";
            return true;
        }
        return false;
    }

    public String dynamicSum(String code1, String code2, byte order) {
        String summand = "";
        code1 = "0".concat(code1);
        for (int i = 0; i < order; i++) {
            code2 += '0';
        }
        bufferBit = '0';
        for (int i = code1.length() - 1; i >= 0; i--) {
            summand += bitAdding(code1.charAt(i), code2.charAt(i));
        }
        String inverseCode = "";
        for (int i = summand.length() - 1; i >= 0; i--) {
            inverseCode += summand.charAt(i);
        }
        return inverseCode;
    }

    private boolean checkMultiRange(BinaryNumber num1, BinaryNumber num2) {
        int bitSize = 0;
        //int bitSize = (int) (Math.ceil((Math.log(num1.getDecimalIntegerNumber()) / Math.log(2))) + Math.ceil(Math.log(num2.getDecimalIntegerNumber()) / Math.log(2)) + 2);
        for (int i = 1; i < integerBinarySize; i++) {
            if (num1.directCode.charAt(i) == '1') {
                bitSize += i - 1;
                break;
            }
        }
        for (int i = 1; i < integerBinarySize; i++) {
            if (num2.directCode.charAt(i) == '1') {
                bitSize += i - 1;
                break;
            }
        }

        if (bitSize > integerBinarySize - 1) {
            return false;
        }
        return true;
    }

    public int outputFromAdditionalCode() {
        if (result.charAt(0) == '1') {
            resultInDirectCode = "1";
            for (int i = 1; i < integerBinarySize; i++) {
                resultInDirectCode += (result.charAt(i) == '1') ? "0" : "1";
            }
            resultInDirectCode = translateAdditionalToDirect(resultInDirectCode);
            //+1
        } else {
            resultInDirectCode = result;
        }
        return outputFromDirectedCode();
    }

    public int outputFromDirectedCode() {
        int originalNumber = 0;
        int j = 0;
        for (int i = 15; i > 0; i--) {
            if (resultInDirectCode.charAt(i) == '1') {
                originalNumber += Math.pow(2, j);
            }
            j++;
        }
        originalNumber *= (resultInDirectCode.charAt(0) == '1') ? (-1) : 1;
        System.out.println("Original number: " + originalNumber);
        return originalNumber;
    }

    public void sumFloatNumbers(BinaryFloatNumber number1, BinaryFloatNumber number2) {
        if (Math.abs(number1.getOriginalNumber()) >= Math.abs(number2.getOriginalNumber())) {
            sumFloatBinaryNumbers(number1.getBinaryFloatNumber(), number2.getBinaryFloatNumber());
        } else {
            sumFloatBinaryNumbers(number2.getBinaryFloatNumber(), number1.getBinaryFloatNumber());
        }
    }

    private void sumFloatBinaryNumbers(String number1, String number2) {
        result = "";
        if (checkOppositeNumbers(number1, number2)) {
            return;
        }
        if (checkSign(number1, number2)) {
            return;
        }
        int difference = getOrderDifference(number1, number2);
        String newMantissa = sumMantissa(number1, number2, difference);
        result = normalFloatView(number1, number2, newMantissa);
    }

    private boolean checkOppositeNumbers(String number1, String number2) {
        if (number1.charAt(0) != number2.charAt(0) && number1.substring(1).equals(number2.substring(1))) {
            for (int i = 0; i < binarySize; i++) {
                result += "0";
            }
            return true;
        }
        return false;
    }

    private boolean checkSign(String number1, String number2) {
        result = "";
        if (number1.charAt(0) == number2.charAt(0)) {
            return false;
        }
        int difference = getOrderDifference(number1, number2);
        String diminutive = chooseDiminutive(number1, number2);
        String deductible = chooseDeductible(diminutive, number1, number2);
        String mantissa = reductionToSingleSizeMantissa(diminutive, deductible, difference);
        changeShift(mantissa);
        return true;
    }

    private void changeShift(String mantissa) {
        BinaryNumber number = new BinaryNumber();
        for (int i = 0; i < mantissa.length(); i++) {
            if (mantissa.charAt(i) == '1') {
                number.setDecimalIntegerNumber(i);
                //number.directNumberTranslation();
                mantissa = mantissa.substring(i + 1);
                break;
            }
        }
        String orderPart = result.substring(1, mantissaIndexStart);
        orderPart = subOrder(orderPart, number.directCode.substring(orderSize)); //sum
        collectTheResult(orderPart, mantissa);
    }

    private String subOrder(String orderPart, String deductible) {
        String inverseResult = "";
        for (int i = orderPart.length() - 1; i >= 0; i--) {
            if (orderPart.charAt(i) == deductible.charAt(i)) {
                inverseResult += '0';
            } else {
                inverseResult += '1';
                if (orderPart.charAt(i) == '0') {
                    orderPart = changeOrderPartForSubOperation(orderPart, i);
                }
            }
        }
        return inverseCode(inverseResult);
    }

    private String changeOrderPartForSubOperation(String orderPart, int index) {
        String inverseOrderPart = "";
        for (int i = 0; i < orderSize - index; i++) {
            inverseOrderPart += "0";
        }
        for (int i = index - 1; i >= 0; i--) {
            if (orderPart.charAt(i) == '0') {
                inverseOrderPart += '1';
            } else {
                inverseOrderPart += '0';
                break;
            }
        }
        for (int i = lastOrderIndex - inverseOrderPart.length(); i >= 0; i--) {
            inverseOrderPart += orderPart.charAt(i);
        }
        return inverseCode(inverseOrderPart);
    }

    private void collectTheResult(String orderPart, String mantissa) {
        if (mantissa.length() > mantissaSize) {
            mantissa = mantissa.substring(0, mantissaSize);
        } else {
            int size = mantissa.length();
            for (int i = 0; i < mantissaSize - size; i++) {
                mantissa += "0";
            }
        }
        result = result.substring(0, 1);
        result += orderPart + mantissa;
    }

    private String reductionToSingleSizeMantissa(String diminutive, String deductible, int difference) {
        String addingNulls = "";
        for (int i = 0; i < Math.abs(difference); i++) {
            addingNulls += "0";
        }
        diminutive += addingNulls;
        deductible = addingNulls + deductible;
        return subtractionFloatNumbers(diminutive, deductible);
    }

    private String subtractionFloatNumbers(String diminutive, String deductible) {
        String inverseResult = "";
        for (int i = diminutive.length() - 1; i >= 0; i--) {
            inverseResult += subtraction(diminutive.charAt(i), deductible.charAt(i));
            String newDivisible = changeDiminutive(diminutive, deductible.charAt(i), i);
            if (!newDivisible.equals(diminutive)) {
                diminutive = newDivisible;
            }
        }
        inverseResult = inverseCode(inverseResult);
        return inverseResult;
    }

    private String changeDiminutive(String diminutive, char deductible, int index) {
        if (diminutive.charAt(index) == deductible || diminutive.charAt(index) == '1') {
            return diminutive;
        } else {
            String newDiminutive = "";
            for (int i = 0; i < diminutive.length() - index; i++) {
                newDiminutive += '0';
            }
            boolean flag = true;
            for (int i = index - 1; i >= 0; i--) {
                if (diminutive.charAt(i) == '0' && flag) {
                    newDiminutive += '1';
                } else if (diminutive.charAt(i) == '1' && flag) {
                    newDiminutive += '0';
                    flag = false;
                    continue;
                }
                if (!flag) {
                    newDiminutive += diminutive.charAt(i);
                }
            }
            return inverseCode(newDiminutive);
        }
    }

    private char subtraction(char number1, char number2) {
        if (number1 == number2) {
            return '0';
        }
        return '1';
    }

    private String chooseDeductible(String diminutive, String number1, String number2) {
        if (diminutive.equals("1" + number1.substring(mantissaIndexStart, binarySize))) {
            return "1" + number2.substring(mantissaIndexStart, binarySize);
        }
        return "1" + number1.substring(mantissaIndexStart, binarySize);
    }

    private String chooseDiminutive(String number1, String number2) {
        String addingBit = "1";
        for (int i = 1; i < binarySize; i++) {
            if (number1.charAt(i) != number2.charAt(i)) {
                if (number1.charAt(i) == '1') {
                    addingBit += number1.substring(mantissaIndexStart, binarySize);
                    result += number1.substring(0, mantissaIndexStart);
                    return addingBit;
                } else {
                    addingBit += number2.substring(mantissaIndexStart, binarySize);
                    result += number2.substring(0, mantissaIndexStart);
                    return addingBit;
                }
            }
        }
        return "";
    }

    private String normalFloatView(String number1, String number2, String mantissa) {
        String order = number1.substring(1, mantissaIndexStart);
        String addingOrderBit = (mantissa.charAt(2) == ',') ? "00000001" : "00000000";
        order = sumOrder(order, addingOrderBit);
        String finalMantissa = changeFinalMantissa(mantissa);
        String signBit = "";
        signBit += number1.charAt(0);
        signBit += order + finalMantissa;
        return signBit;
    }

    private String changeFinalMantissa(String mantissa) {
        if (mantissa.charAt(2) == ',') {
            String addingBit = "";
            addingBit += mantissa.charAt(1);
            mantissa = addingBit + mantissa.substring(3);
        } else {
            mantissa = mantissa.substring(2);
        }
        for (int i = 0; i < 23 - mantissa.length(); i++) {
            mantissa += "0";
        }
        return mantissa;
    }

    private String sumOrder(String number1, String number2) {
        bufferBit = '0';
        String order = "";
        for (int i = lastOrderIndex; i >= 0; i--) {
            order += bitAdding(number1.charAt(i), number2.charAt(i));
        }
        order = inverseCode(order);
        return order;
    }

    private String sumMantissa(String number1, String number2, int difference) {
        String mantissa1 = number1.substring(mantissaIndexStart, binarySize);
        String mantissa2 = number2.substring(mantissaIndexStart, binarySize);
        if (difference > 0) {
            String addingBits = "";
            for (int i = 0; i < difference; i++) {
                addingBits += "0";
            }
            mantissa1 += addingBits;
            StringBuilder stringBuilder = new StringBuilder(addingBits);
            stringBuilder.setCharAt(difference - 1, '1');
            mantissa2 = stringBuilder.toString() + mantissa2;
        }
        String finalMantissa = sumEquivalentMantissa(mantissa1, mantissa2);
        String hiddenPartOfMantissa = getHiddenPartOfMantissa(finalMantissa, mantissa1, difference);
        finalMantissa = cutRedundantBit(finalMantissa, mantissa1);
        return hiddenPartOfMantissa + "," + finalMantissa;
    }

    private String cutRedundantBit(String finalMantissa, String mantissa) {
        if (finalMantissa.length() > mantissa.length()) {
            finalMantissa = finalMantissa.substring(1);
        }
        return finalMantissa;
    }

    private String getHiddenPartOfMantissa(String finalMantissa, String mantissa1, int difference) {
        String redundantBit = "0";
        if (finalMantissa.length() > mantissa1.length()) {
            redundantBit = "1";
        }
        if (difference > 0) {
            return sumEquivalentMantissa("1", redundantBit);
        } else {
            redundantBit = "0" + redundantBit;
            return sumEquivalentMantissa("10", redundantBit);
        }
    }

    private String sumEquivalentMantissa(String mantissa1, String mantissa2) {
        bufferBit = '0';
        String inverseMantissa = "";
        for (int i = mantissa1.length() - 1; i >= 0; i--) {
            inverseMantissa += bitAdding(mantissa1.charAt(i), mantissa2.charAt(i));
        }
        if (bufferBit == '1') {
            inverseMantissa += bufferBit;
        }
        return inverseCode(inverseMantissa);

    }

    private int getOrderDifference(String number1, String number2) {
        number1 = number1.substring(1, mantissaIndexStart);
        number2 = number2.substring(1, mantissaIndexStart);
        return getDifferenceBetweenOrder(number1, number2);
    }

    private int getDifferenceBetweenOrder(String order1, String order2) {
        int difference = 0;
        int j = 0;
        for (int i = lastOrderIndex; i >= 0; i--) {
            if (order1.charAt(i) == '1') {
                difference += Math.pow(2, j);
            }
            j++;
        }
        j = 0;
        for (int i = lastOrderIndex; i >= 0; i--) {
            if (order2.charAt(i) == '1') {
                difference -= Math.pow(2, j);
            }
            j++;
        }
        return difference;
    }

    private String translateAdditionalToDirect(String binaryCode) {
        bufferBit = '0';
        String singleCode = "";
        for (int i = 0; i < binaryCode.length() - 1; i++) {
            singleCode += "0";
        }
        singleCode += "1";
        String result = "";
        for (int i = binaryCode.length() - 1; i >= 0; i--) {
            result += bitAdding(binaryCode.charAt(i), singleCode.charAt(i));
        }
        return inverseCode(result);
    }

    private String inverseCode(String binaryCode) {
        String resultOperation = "";
        for (int i = binaryCode.length() - 1; i >= 0; i--) {
            resultOperation += binaryCode.charAt(i);
        }
        return resultOperation;
    }
}
