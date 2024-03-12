package logicalFunctions;

import java.util.*;
import java.util.regex.Pattern;

public class logicalFunctions {
    public logicalFunctions(String function) {
        this.function = function;
    }

    private final int shiftASCII = 48;
    private String function;
    private Set<Character> operands = new HashSet<>();
    private Map<Character, Integer> operandsIndex = new HashMap<>();

    private List<List<Character>> truthTable = new ArrayList<>();
    private final Map<String, Integer> operatorsPriority = Map.of("&", 3, "|", 2, "!", 4, "~", 1, "->", 1, ")", 5, "(", 5);
    private final List<String> operators = Arrays.asList("&", "|", "!", "~", "->", ")", "(");
    private String resultPDNF = "";
    private String resultPCNF = "";
    private String numericPDNF = "";
    private String numericPCNF = "";
    private int indexForm = 0;

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunction() {
        return this.function;
    }

    public List<List<Character>> getTruthTable() {
        return this.truthTable;
    }

    public String getResultPDNF() {
        return this.resultPDNF;
    }

    public String getResultPCNF() {
        return this.resultPCNF;
    }

    public String getNumericPDNF() {
        return this.numericPDNF;
    }

    public String getNumericPCNF() {
        return this.numericPCNF;
    }

    public Integer getIndexForm() {
        return this.indexForm;
    }

    public void printResultForms() {
        System.out.println("PDNF: " + this.resultPDNF);
        System.out.println("PCNF: " + this.resultPCNF);
        System.out.println("numeric PCNF: " + this.numericPCNF);
        System.out.println("numeric PDNF: " + this.numericPDNF);
        System.out.println("index Form: " + this.indexForm);
    }

    public void createIndexForm() {
        Stack<Integer> binaryCode = new Stack<>();
        for (List<Character> line : this.truthTable) {
            binaryCode.push((int) line.get(line.size() - 1) - shiftASCII);
        }
        for (int i = 0; i < Math.pow(2, this.operands.size()); i++) {
            if (binaryCode.pop() == 1) {
                this.indexForm += Math.pow(2, i);
            }
        }
    }

    public void createNumericForms() {
        StringBuilder bufferForm = new StringBuilder();
        bufferForm.append(getIndexLines('1').toString()).append("|");
        this.numericPDNF = bufferForm.toString();
        this.numericPDNF = this.numericPDNF.replace("[", "(");
        this.numericPDNF = this.numericPDNF.replace("]", ")");
        bufferForm.setLength(0);
        bufferForm.append(getIndexLines('0').toString()).append("&");
        this.numericPCNF = bufferForm.toString();
        this.numericPCNF = this.numericPCNF.replace("[", "(");
        this.numericPCNF = this.numericPCNF.replace("]", ")");
    }

    private List<Integer> getIndexLines(char result) {
        List<Integer> correctLines = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, this.operands.size()); i++) {
            if (this.truthTable.get(i).get(this.operands.size()) == result) {
                correctLines.add(i);
            }
        }
        return correctLines;
    }

    public void createPCNF() {
        StringBuilder resultPDNF = new StringBuilder();
        for (int index : getIndexLines('0')) {
            resultPDNF.append(addPCNFElement(this.truthTable.get(index)));
        }
        this.resultPCNF = editResultToNF(resultPDNF, '&');
    }

    private String addPCNFElement(List<Character> line) {
        StringBuilder elementPDNF = new StringBuilder("(");
        List<Character> operand = new ArrayList<>(this.operands);
        for (int i = 0; i < this.operands.size(); i++) {
            if (line.get(i) == '1') {
                elementPDNF.append('!').append(operand.get(i)).append('|');
            } else {
                elementPDNF.append(operand.get(i)).append('|');
            }
        }
        elementPDNF.append(")");
        return elementPDNF.toString();
    }

    public void createPDNF() {
        StringBuilder resultPDNF = new StringBuilder();
        for (int index : getIndexLines('1')) {
            resultPDNF.append(addPDNFElement(this.truthTable.get(index)));
        }
        this.resultPDNF = editResultToNF(resultPDNF, '|');
    }

    private String editResultToNF(StringBuilder resultPDNF, char translationOperator) {
        String result;
        if (translationOperator == '&') {
            result = resultPDNF.toString().replace("|)", ")");
            result = result.replace(")(", ")&(");
        } else {
            result = resultPDNF.toString().replace("&)", ")");
            result = result.replace(")(", ")|(");
        }
        return result;
    }

    private String addPDNFElement(List<Character> line) {
        StringBuilder elementPDNF = new StringBuilder("(");
        List<Character> operand = new ArrayList<>(this.operands);
        for (int i = 0; i < this.operands.size(); i++) {
            if (line.get(i) == '0') {
                elementPDNF.append('!').append(operand.get(i)).append('&');
            } else {
                elementPDNF.append(operand.get(i)).append('&');
            }
        }
        elementPDNF.append(")");
        return elementPDNF.toString();
    }

    public void createTruthTable() {
        getNumOfRows();
        fillOperandsValues();
        String reversePolishEntry = createRPE();
        entryTruthTable(reversePolishEntry);
    }

    public void printTruthTable() {
        for (char operand : this.operands) {
            System.out.print(operand + "\t");
        }
        System.out.println("Result");
        for (List<Character> line : this.truthTable) {
            for (char token : line) {
                System.out.print(token + "\t");
            }
            System.out.println("");
        }
    }

    private void entryTruthTable(String reversePolishEntry) {
        int i = 0;
        for (char token : this.operands) {
            this.operandsIndex.put(token, i++);
        }
        Stack<Character> result = new Stack<>();
        putIntoTheStack(result, reversePolishEntry);
    }

    private void putIntoTheStack(Stack<Character> result, String reversePolishEntry) {
        for (int j = 0; j < Math.pow(2, this.operands.size()); j++) {
            for (int i = 0; i < reversePolishEntry.length(); i++) {
                if (checkAddedElement(result, reversePolishEntry.charAt(i), j)) {
                    performingTheOperation(result, reversePolishEntry.charAt(i));
                }
            }
            this.truthTable.get(j).add(result.pop());
        }
    }

    private boolean checkAddedElement(Stack<Character> truthValue, char tokenFromPolishEntry, int index) {
        for (char token : this.operands) {
            if (token == tokenFromPolishEntry) {
                truthValue.push(this.truthTable.get(index).get(this.operandsIndex.get(tokenFromPolishEntry)));
                return false;
            }
        }
        return true;
    }

    private void performingTheOperation(Stack<Character> stack, char token) {
        StringBuilder operator = new StringBuilder("");
        if (token == '-') {
            operator.append(token).append('>');
        } else {
            operator.append(token);
        }
        switch (operator.toString()) {
            case "&":
                conjunction(stack);
                break;
            case "|":
                disjunction(stack);
                break;
            case "->":
                implication(stack);
                break;
            case "~":
                equivalence(stack);
                break;
            case "!":
                negation(stack);
                break;
        }
    }

    private void equivalence(Stack<Character> stack) {
        char value1 = stack.pop();
        char value2 = stack.pop();
        if (value1 == value2) {
            stack.push('1');
        } else {
            stack.push('0');
        }
    }

    private void conjunction(Stack<Character> stack) {
        char value1 = stack.pop();
        char value2 = stack.pop();
        if (value1 == value2 && value1 == '1') {
            stack.push('1');
        } else {
            stack.push('0');
        }
    }

    private void disjunction(Stack<Character> stack) {
        char value1 = stack.pop();
        char value2 = stack.pop();
        if (value1 == '1' || value2 == '1') {
            stack.push('1');
        } else {
            stack.push('0');
        }
    }

    private void negation(Stack<Character> stack) {
        char value1 = stack.pop();
        if (value1 == '1') {
            stack.push('0');
        } else {
            stack.push('1');
        }
    }

    private void implication(Stack<Character> stack) {
        char value1 = stack.pop();
        char value2 = stack.pop();
        if ((int) value1 >= (int) value2) {
            stack.push('1');
        } else {
            stack.push('0');
        }
    }

    private String createRPE() {
        Stack<String> operations = new Stack<>();
        StringBuilder reversePolishEntry = new StringBuilder("");
        for (char token : this.function.toCharArray()) {
            if (checkOperands(token)) {
                reversePolishEntry.append(token);
            } else {
                if (token == '>') {
                    continue;
                }
                entryOperatorsToStack(reversePolishEntry, operations, token);
            }
        }
        getItFromTheStack(operations, reversePolishEntry);
        return reversePolishEntry.toString();
    }

    private void getItFromTheStack(Stack<String> operations, StringBuilder reversePolishEntry) {
        while (true) {
            if (!operations.isEmpty()) {
                reversePolishEntry.append(operations.pop());
            } else {
                return;
            }
        }
    }

    private void entryOperatorsToStack(StringBuilder reversePolishEntry, Stack<String> operations, char token) {
        StringBuilder operator = new StringBuilder("");
        if (token == '-') {
            operator.append(token).append(">");
        } else {
            operator.append(token);
        }
        workWithOperationStack(operations, reversePolishEntry, operator);
    }

    private void workWithOperationStack(Stack<String> operations, StringBuilder reversePolishEntry, StringBuilder operator) {
        while (true) {
            if (operations.isEmpty()) {
                operations.push(operator.toString());
                break;
            }
            if (this.operatorsPriority.get(operations.peek()) <= this.operatorsPriority.get(operator.toString())) {
                operations.push(operator.toString());
                writingPriorityParentheses(operations, reversePolishEntry);
                break;
            } else {
                if (operations.peek().equals("(")) {
                    operations.push(operator.toString());
                    break;
                }
                reversePolishEntry.append(operations.pop());
            }
        }
    }

    private void writingPriorityParentheses(Stack<String> operations, StringBuilder reversePolishEntry) {
        if (operations.peek().equals(")")) {
            operations.pop();
            while (true) {
                if (!operations.peek().equals("(")) {
                    reversePolishEntry.append(operations.pop());
                } else {
                    operations.pop();
                    return;
                }
            }
        }
    }

    private boolean checkOperands(char token) {
        for (char operand : this.operands) {
            if (operand == token) {
                return true;
            }
        }
        return false;
    }

    private void fillOperandsValues() {
        for (int i = 0; i < Math.pow(2, this.operands.size()); i++) {
            String truthLine = gettingBackToNormal(Integer.toBinaryString(i));
            List<Character> truthValue = new ArrayList<>();
            for (int j = 0; j < this.operands.size(); j++) {
                truthValue.add(truthLine.charAt(j));
            }
            this.truthTable.add(truthValue);
        }
    }

    private String gettingBackToNormal(String value) {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i < this.operands.size() - value.length(); i++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(value);
        return stringBuilder.toString();
    }

    private void getNumOfRows() {
        String operands = this.function;
        for (String token : operators) {
            operands = operands.replaceAll(Pattern.quote(token), "");
        }
        Set<Character> uniqueOperands = new HashSet<>();
        for (char currentOperand : operands.toCharArray()) {
            uniqueOperands.add(currentOperand);
        }
        this.operands.addAll(uniqueOperands);
    }
}
