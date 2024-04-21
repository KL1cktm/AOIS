package minimization;

import java.util.*;

public abstract class CarnotMap {
    protected int lineLength;
    private char cell;
    protected List<List<Integer>> indexes = new ArrayList<>();
    protected List<List<Character>> table = new ArrayList<>();
    protected List<Character> normalForm;
    protected List<Character> keys;
    protected List<Character> keysVert = new ArrayList<>();
    protected List<Character> keysHor = new ArrayList<>();
    protected List<String> horizonValue = new ArrayList<>();
    protected List<String> verticalValue = new ArrayList<>();
    private List<String> result = new ArrayList<>();

    public abstract void createMap();

    public abstract void removeKeys();
    public List<String> getResult() {
        return this.result;
    }
    public void setNormalForm(List<Character> normalForm) {
        this.normalForm = normalForm;
    }

    public void setCell(char cell) {
        this.cell = cell;
    }

    public void printTable() {
        System.out.println(this.table);
    }

    protected int fillLineOfMap(List<Character> line, int num) {
        for (int j = 0; j < 4; j++) {
            if (j == 2) {
                num++;
                line.add(this.normalForm.get(num));
                num = num - 1;
            } else {
                line.add(this.normalForm.get(num++));
            }
            if (j == 3) {
                num = num + 1;
            }
        }
        return num;
    }

    protected void printTruthTable() {
        for (int i = 0; i < this.horizonValue.size(); i++) {
            System.out.print("\t" + this.horizonValue.get(i));
        }
        System.out.println("");
        for (int i = 0; i < this.verticalValue.size(); i++) {
            System.out.print(this.verticalValue.get(i) + "\t");
            for (char truth : this.table.get(i)) {
                System.out.print(truth + "\t");
            }
            System.out.println("");
        }
    }

    public void findRectangle() {
        for (int i = 0; i < this.table.get(0).size(); i++) {
            processFindRectangle(i);
        }
        for (int i = 0; i < this.indexes.size(); i++) {
            for (int j = i + 1; j < this.indexes.size(); j++) {
                if (this.indexes.get(i).equals(this.indexes.get(j))) {
                    this.indexes.remove(j--);
                }
            }
        }
        deleteOddList();
        sortListOfIndex();
        definitionRectangleResult();
        printRectangleArea();
    }

    public void printRectangleArea() {
        int num = 0;
        for (int i = 0; i < this.indexes.size(); i++) {
            System.out.println("Rectangle number " + num++ + ": " + this.indexes.get(i));
        }
    }

    private void deleteOddList() {
        for (int i = 0; i < this.indexes.size(); i++) {
            if (this.indexes.get(i).size() == 6 || (this.indexes.get(i).size() % 2 == 1 && this.indexes.get(i).size() != 1)) {
                this.indexes.remove(i--);
            }
        }
    }

    private void processFindRectangle(int num) {
        for (int i = 0; i < this.table.size(); i++) {
            List<Integer> indexes = new ArrayList<>();
            for (int j = num; j < this.table.get(i).size(); j++) {
                if (this.table.get(i).get(j) == this.cell) {
                    indexes.add(this.lineLength * i + j);
                }
                logicCheckRectangleSize(indexes, i);
                if (this.table.get(i).get(j) != this.cell && indexes.size() > 0) {
                    break;
                }
                if (j == this.table.get(i).size() - 1 && indexes.size() == 1) {
                    findShiftElements(indexes, i);
                }
            }
        }
    }

    private void logicCheckRectangleSize(List<Integer> indexes, int i) {
        if ((indexes.size() & (indexes.size() - 1)) == 0 && indexes.size() != 0) {
            this.indexes.add(indexes);
            List<Integer> index = new ArrayList<>();
            index.addAll(indexes);
            findLowerElements(index, i);
            this.indexes.add(index);
        }
    }

    private void findShiftElements(List<Integer> indexes, int arrayNumber) {
        List<Integer> newIndex = new ArrayList<>();
        newIndex.addAll(indexes);
        for (int i = 0; i < this.table.get(arrayNumber).size() - 1; i++) {
            if (this.table.get(arrayNumber).get(i) == this.cell) {
                newIndex.add(this.lineLength * arrayNumber + i);
            }
            logicCheckRectangleSize(newIndex, arrayNumber);
            if (this.table.get(arrayNumber).get(i) != this.cell) {
                this.indexes.add(newIndex);
                return;
            }
        }
    }

    private void findLowerElements(List<Integer> indexes, int arrayNumber) { /// передавать индекс строки в которой найден прошлый прямоугольник
        List<Integer> arrayIndex = translateToUnderArrayIndex(indexes, arrayNumber);
        if (arrayNumber++ == this.table.size() - 1) {
            arrayNumber = 0;
        }
        List<Integer> newIndexes = new ArrayList<>();
        for (int i = 0; i < arrayIndex.size(); i++) {
            if (this.table.get(arrayNumber).get(arrayIndex.get(i)) == this.cell) {
                newIndexes.add(this.lineLength * arrayNumber + arrayIndex.get(i));
            }
        }
        if (newIndexes.size() == indexes.size()) {
            indexes.addAll(newIndexes);
            secondStepOfFindingLowerElements(indexes, --arrayNumber);
        }
    }

    private void secondStepOfFindingLowerElements(List<Integer> indexes, int arrayNumber) {
        if (arrayNumber > 0) {
            return;
        }
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < indexes.size() / 2; i++) {
            index.add(indexes.get(i));
        }
        index = translateToUnderArrayIndex(index, arrayNumber++);
        List<Integer> newIndex = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            arrayNumber++;
            if (arrayNumber >= this.table.size()) {
                break;
            }
            for (int j = 0; j < index.size(); j++) {
                if (this.table.get(arrayNumber).get(index.get(j)) == this.cell) {   //get(index.get(j))
                    newIndex.add(arrayNumber * this.lineLength + index.get(j));   /// j
                } else {
                    return;
                }
            }
        }
        indexes.addAll(newIndex);
    }

    private List<Integer> translateToUnderArrayIndex(List<Integer> indexes, int arrayNumber) {
        List<Integer> arrayIndex = new ArrayList<>();
        for (int i = 0; i < indexes.size(); i++) {
            int num = indexes.get(i) % this.lineLength;
//            int num = indexes.get(i) - this.lineLength * arrayNumber;
            arrayIndex.add(num);
        }
        return arrayIndex;
    }

    private void definitionRectangleResult() {
        sortListOfIndex();
        for (int i = 0; i < this.indexes.size(); i++) {
            for (int j = i + 1; j < this.indexes.size(); j++) {
                if (this.indexes.get(i).containsAll(this.indexes.get(j))) {
                    this.indexes.remove(j--);
                }
            }
        }
    }

    private void sortListOfIndex() {
        Collections.sort(this.indexes, (listA, listB) -> Integer.compare(listB.size(), listA.size()));
    }
    public void definitionRectangleCNF() {
        definitionRectangle();
        rewriteResultForCNF();
        printResult('&');
    }
    public void definitionRectangleDNF() {
        definitionRectangle();
        printResult('|');
    }
    private void rewriteResultForCNF() {
        List<String> result = new ArrayList<>();
        for (int i=0;i<this.result.size();i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j=0;j<this.result.get(i).length();j++) {
                if (this.result.get(i).charAt(j) == '!') {
                    stringBuilder.append(this.result.get(i).charAt(++j));
                } else {
                    stringBuilder.append("!");
                    stringBuilder.append(this.result.get(i).charAt(j));
                }
            }
            result.add(stringBuilder.toString());
        }
        this.result = result;
    }
    private void printResult(char token) {
        for (int i=0;i<this.result.size();i++) {
            System.out.print("(");
            System.out.print(this.result.get(i));
            System.out.print(")");
            if (i != this.result.size()-1) {
                System.out.print(token);
            }
        }
        System.out.println("");
    }
    private void definitionRectangle() {
        List<List<Integer>> lineIndex = new ArrayList<>();
        List<List<Integer>> lineNum = new ArrayList<>();  //номер строки
        setTableIndex(lineIndex, lineNum);
        deleteRepeatIndexes(lineIndex);
        deleteRepeatIndexes(lineNum);
        divisionIndexes(lineIndex, lineNum);
//        System.out.println(lineIndex);
//        System.out.println(lineNum);
    }

    private void divisionIndexes(List<List<Integer>> lineIndex, List<List<Integer>> lineNum) {
        for (int i = 0; i < lineIndex.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(checkVertical(lineNum.get(i)));
            stringBuilder.append(checkHorizon(lineIndex.get(i)));
            this.result.add(stringBuilder.toString());
        }
//        System.out.println(this.result);
    }

    private String checkHorizon(List<Integer> lineIndex) {
        List<String> horizon = new ArrayList<>();
        for (int i = 0; i < lineIndex.size(); i++) {
            horizon.add(this.horizonValue.get(lineIndex.get(i)));
        }
        if (horizon.size() == 1) {
            return addToPartHorizonPCNF(horizon).toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < horizon.get(0).length(); i++) {
            int num = 0;
            for (int j = 0; j < horizon.size() - 1; j++) {
                if (horizon.get(j).charAt(i) == horizon.get(j + 1).charAt(i)) {
                    num++;
                }
                if (num == horizon.size() - 1) {
//                if (num == horizon.get(0).length() - 1) {
                    if (horizon.get(j).charAt(i) == '0') {
                        stringBuilder.append("!");
                        stringBuilder.append(this.keysHor.get(i));
                    } else {
                        stringBuilder.append(this.keysHor.get(i));
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private String checkVertical(List<Integer> lineNum) {
        List<String> vertical = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lineNum.size(); i++) {
            vertical.add(this.verticalValue.get(lineNum.get(i)));
        }
        if (vertical.size() == 1) {
            return addToPartVerticalPCNF(vertical).toString();
        }
        for (int i = 0; i < vertical.get(0).length(); i++) {
            int num = 0;
            for (int j = 0; j < vertical.size() - 1; j++) {
                if (vertical.get(j).charAt(i) == vertical.get(j + 1).charAt(i)) {
                    num++;
                }
                if (num == vertical.size() - 1) {
                    if (vertical.get(j).charAt(i) == '0') {
                        stringBuilder.append("!");
                        stringBuilder.append(this.keysVert.get(i));
                    } else {
                        stringBuilder.append(this.keysVert.get(i));
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private StringBuilder addToPartHorizonPCNF(List<String> line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.get(0).length(); i++) {
            if (line.get(0).charAt(i) == '0') {
                stringBuilder.append("!");
                stringBuilder.append(this.keysHor.get(i));
            } else {
                stringBuilder.append(this.keysHor.get(i));
            }
        }
        return stringBuilder;
    }

    private StringBuilder addToPartVerticalPCNF(List<String> line) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.get(0).length(); i++) {
            if (line.get(0).charAt(i) == '0') {
                stringBuilder.append("!");
                stringBuilder.append(this.keysVert.get(i));
            } else {
                stringBuilder.append(this.keysVert.get(i));
            }
        }
        return stringBuilder;
    }

    private void deleteRepeatIndexes(List<List<Integer>> values) {
        for (int i = 0; i < values.size(); i++) {
            for (int j = 0; j < values.get(i).size(); j++) {
                for (int c = j + 1; c < values.get(i).size(); c++) {
                    if (values.get(i).get(j) == values.get(i).get(c)) {
                        values.get(i).remove(c--);
                    }
                }
            }
        }
    }

    private void setTableIndex(List<List<Integer>> lineIndex, List<List<Integer>> lineNum) {
        for (int i = 0; i < this.indexes.size(); i++) {
            List<Integer> line = new ArrayList<>();
            List<Integer> index = new ArrayList<>();
            for (int j = 0; j < this.indexes.get(i).size(); j++) {
                line.add(getLineNum(this.indexes.get(i).get(j)));
                int value = this.indexes.get(i).get(j) % this.lineLength;
                index.add(value);
            }
            lineIndex.add(index);
            lineNum.add(line);
        }
    }

    private int getLineNum(int index) {
        if (index < this.lineLength) {
            return 0;
        } else if (index >= this.lineLength && index < 2 * this.lineLength) {
            return 1;
        } else if (index >= 2 * this.lineLength && index < 3 * this.lineLength) {
            return 2;
        } else {
            return 3;
        }
    }
}

