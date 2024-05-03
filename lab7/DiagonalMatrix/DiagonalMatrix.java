package DiagonalMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiagonalMatrix {
    private final int MATRIX_SIZE = 16;
    private List<List<Boolean>> matrix = new ArrayList<>();

    public DiagonalMatrix() {
        Random random = new Random();
        for (int i = 0; i < this.MATRIX_SIZE; i++) {
            List<Boolean> row = new ArrayList<>();
            for (int j = 0; j < this.MATRIX_SIZE; j++) {
                row.add(random.nextBoolean());
            }
            this.matrix.add(row);
        }
    }

    public void setMatrixValue(List<List<Boolean>> matrix) {
        if (matrix.size() != this.MATRIX_SIZE) {
            throw new RuntimeException("Matrix should be have this.MATRIX_SIZE rows and this.MATRIX_SIZE cols");
        }
        int numRow = 0;
        for (List<Boolean> row : matrix) {
            if (row.size() != this.MATRIX_SIZE) {
                throw new RuntimeException("Matrix should be have this.MATRIX_SIZE rows and this.MATRIX_SIZE cols");
            }
            for (int i = 0; i < this.MATRIX_SIZE; i++) {
                this.matrix.get(numRow).set(i, row.get(i));
            }
            numRow++;
        }
    }

    public void setMatrixValue(int rowNumber, int elementNumber, boolean element) {
        this.matrix.get(rowNumber).set(elementNumber, element);
    }

    public List<List<Boolean>> getMatrix() {
        int rowNumber = 0;
        for (List<Boolean> row : this.matrix) {
            System.out.print((rowNumber++) + ".\t");
            for (boolean element : row) {
                String value = element ? "1 " : "0 ";
                System.out.print(value);
            }
            System.out.println();
        }
        return this.matrix;
    }

    public String readWord(int numberWord) {
        StringBuilder firstPart = new StringBuilder("");
        StringBuilder lastPart = new StringBuilder("");
        for (int i = 0; i < numberWord; i++) {
            char element = this.matrix.get(i).get(numberWord) ? '1' : '0';
            lastPart.append(element);
        }
        for (int i = numberWord; i < this.MATRIX_SIZE; i++) {
            char element = this.matrix.get(i).get(numberWord) ? '1' : '0';
            firstPart.append(element);
        }
        firstPart.append(lastPart);
        return firstPart.toString();
    }

    public String readAddressCol(int numberCol) {
        int index = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = numberCol; i < this.MATRIX_SIZE; i++) {
            char value = this.matrix.get(i).get(index++) ? '1' : '0';
            stringBuilder.append(value);
            if (i == 15) {
                i = -1;
            }
            if (index == this.MATRIX_SIZE) {
                break;
            }
        }
        return stringBuilder.toString();
    }

    private void writeResult(int index, String column) {
        List<Boolean> result = new ArrayList<>();
        for (char token : column.toCharArray()) {
            result.add(token == '1' ? true : false);
        }
        for (int i = 0; i < this.MATRIX_SIZE; i++) {
            this.matrix.get(i).set(index, result.get(i));
        }
    }

    private void changeString(StringBuilder stringBuilder, int resultWordIndex) {
        String firstPart = stringBuilder.substring(this.MATRIX_SIZE - resultWordIndex);
        String lastPart = stringBuilder.substring(0, this.MATRIX_SIZE - resultWordIndex);
        stringBuilder.setLength(0);
        stringBuilder.append(firstPart).append(lastPart);
    }

    public void conjunction(int firstWordIndex, int secondWordIndex, int resultWordIndex) {
        String firstWord = readWord(firstWordIndex);
        String secondWord = readWord(secondWordIndex);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < firstWord.length(); i++) {
            if (firstWord.charAt(i) == secondWord.charAt(i) && firstWord.charAt(i) == '1') {
                stringBuilder.append('1');
            } else {
                stringBuilder.append('0');
            }
        }
        changeString(stringBuilder, resultWordIndex);
        writeResult(resultWordIndex, stringBuilder.toString());
    }

    public void repeatFirstArg(int firstWordIndex, int resultWordIndex) {
        String firstWord = readWord(firstWordIndex);
        StringBuilder stringBuilder = new StringBuilder(firstWord);
        changeString(stringBuilder, resultWordIndex);
        writeResult(resultWordIndex, stringBuilder.toString());
    }

    public void negativeFirstArg(int firstWordIndex, int resultWordIndex) {
        String firstWord = readWord(firstWordIndex);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < firstWord.length(); i++) {
            if (firstWord.charAt(i) == '1') {
                stringBuilder.append('0');
            } else {
                stringBuilder.append('1');
            }
        }
        changeString(stringBuilder, resultWordIndex);
        writeResult(resultWordIndex, stringBuilder.toString());
    }

    public void shefferArg(int firstWordIndex, int secondWordIndex, int resultWordIndex) {
        String firstWord = readWord(firstWordIndex);
        String secondWord = readWord(secondWordIndex);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.MATRIX_SIZE; i++) {
            if (firstWord.charAt(i) == '0' || secondWord.charAt(i) == '0') {
                stringBuilder.append('1');
            } else {
                stringBuilder.append('0');
            }
        }
        changeString(stringBuilder, resultWordIndex);
        writeResult(resultWordIndex, stringBuilder.toString());
    }

    private String inverseString(StringBuilder stringBuilder, boolean next) {
        StringBuilder newString = new StringBuilder();
        if (!next) {
            stringBuilder.append("0");
        } else {
            stringBuilder.append("1");
        }
        for (int i = stringBuilder.length() - 1; i >= 0; i--) {
            newString.append(stringBuilder.toString().charAt(i));
        }
        return newString.toString();
    }

    private boolean sumDifferenceBit(char a, char b, boolean next, StringBuilder stringBuilder) {
        if (!next) {
            stringBuilder.append("1");
            return false;
        } else {
            stringBuilder.append("0");
            return true;
        }
    }

    private boolean sumEquivalentPositiveElements(StringBuilder stringBuilder, boolean next) {
        if (!next) {
            stringBuilder.append("0");
        } else {
            stringBuilder.append("1");
        }
        return true;
    }

    private boolean sumEquivalentNegativeElements(StringBuilder stringBuilder, boolean next) {
        if (!next) {
            stringBuilder.append("0");
        } else {
            stringBuilder.append("1");
        }
        return false;
    }

    private String calculatePartsOfWord(String part1, String part2) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean next = false;
        for (int i = 3; i >= 0; i--) {
            if (part1.charAt(i) == part2.charAt(i)) {
                if (part1.charAt(i) == '1') {
                    next = sumEquivalentPositiveElements(stringBuilder, next);
                } else {
                    next = sumEquivalentNegativeElements(stringBuilder, next);
                }

            } else {
                next = sumDifferenceBit(part1.charAt(i), part2.charAt(i), next, stringBuilder);
            }
        }
        return inverseString(stringBuilder, next);
    }

    public void sumAB(String v) {
        for (int i = 0; i < this.MATRIX_SIZE; i++) {
            if (readWord(i).substring(0, 3).equals(v)) {
                String firstPart = readWord(i).substring(3, 7);
                String secondPart = readWord(i).substring(7, 11);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(v).append(firstPart).append(secondPart).append(calculatePartsOfWord(firstPart, secondPart));
                changeString(stringBuilder, i);
                writeResult(i, stringBuilder.toString());
            }
        }
    }

    private int calculateG(int g, char a, char s, int l) {
        a = (a == '0') ? '1' : '0';
        l = (l == 0) ? 1 : 0;
        char res = (a == '1' && s == '1' && l == 1) ? '1' : '0';
        if (res == '1' || g == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private int calculateL(int l, char a, char s, int g) {
        s = (s == '0') ? '1' : '0';
        g = (g == 0) ? 1 : 0;
        char res = (a == '1' && s == '1' && g == 1) ? '1' : '0';
        if (res == '1' || l == 1) {
            return 1;
        } else {
            return 0;
        }
    }
    private int checkBorderOfList(int size,int value) {
        if (size == 1) {
            return 0;
        } else if (size == 0) {
            return -1;
        } else {
            return value;
        }
    }
    public String findElementMore(int sourceElementIndex) {
        List<String> elements = findBiggestElements(sourceElementIndex);
        int minimal = 0;
        for (int i = minimal + 1; i < elements.size(); i++) {
            int g = 0;
            int l = 0;
            for (int j = 0; j < 16; j++) {
                int bufferG = calculateG(g, elements.get(minimal).charAt(j), elements.get(i).charAt(j), l);
                int bufferL = calculateL(l, elements.get(minimal).charAt(j), elements.get(i).charAt(j), g);
                g = bufferG;
                l = bufferL;
            }
            if (g < l) {
                minimal = i--;
            }
        }
        minimal = checkBorderOfList(elements.size(),minimal);
        if (minimal == -1) {
            return "Element are was not founded";
        } else {
            return elements.get(minimal);
        }
    }

    public String findElementLess(int sourceElementIndex) {
        List<String> elements = findLowerElements(sourceElementIndex);
        int maximum = 0;
        for (int i = maximum + 1; i < elements.size(); i++) {
            int g = 0;
            int l = 0;
            for (int j = 0; j < 16; j++) {
                int bufferG = calculateG(g, elements.get(maximum).charAt(j), elements.get(i).charAt(j), l);
                int bufferL = calculateL(l, elements.get(maximum).charAt(j), elements.get(i).charAt(j), g);
                g = bufferG;
                l = bufferL;
            }
            if (g > l) {
                maximum = i--;
            }
        }
        maximum = checkBorderOfList(elements.size(),maximum);
        if (maximum == -1) {
            return "Element are was not founded";
        } else {
            return elements.get(maximum);
        }
    }

    public List<String> findBiggestElements(int sourceElementIndex) {
        List<String> biggestElements = new ArrayList<>();
        String startElement = readWord(sourceElementIndex);
        for (int i = 0; i < 16; i++) {
            int g = 0;
            int l = 0;
            if (i == sourceElementIndex) {
                continue;
            }
            String bufferElement = readWord(i);
            for (int j = 0; j < 16; j++) {
                int bufferG = calculateG(g, startElement.charAt(j), bufferElement.charAt(j), l);
                int bufferL = calculateL(l, startElement.charAt(j), bufferElement.charAt(j), g);
                g = bufferG;
                l = bufferL;
            }
            if (g > l) {
                biggestElements.add(bufferElement);
            }
        }
        return biggestElements;
    }

    public List<String> findLowerElements(int sourceElementIndex) {
        List<String> lowerElements = new ArrayList<>();
        String startElement = readWord(sourceElementIndex);
        for (int i = 0; i < 16; i++) {
            int g = 0;
            int l = 0;
            if (i == sourceElementIndex) {
                continue;
            }
            String bufferElement = readWord(i);
            for (int j = 0; j < 16; j++) {
                int bufferG = calculateG(g, startElement.charAt(j), bufferElement.charAt(j), l);
                int bufferL = calculateL(l, startElement.charAt(j), bufferElement.charAt(j), g);
                g = bufferG;
                l = bufferL;
            }
            if (g < l) {
                lowerElements.add(bufferElement);
            }
        }
        return lowerElements;
    }
}
