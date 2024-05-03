package test;


import DiagonalMatrix.DiagonalMatrix;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DiagonalMatrixTest {
    private List<List<Boolean>> matrix = new ArrayList<>();
    public DiagonalMatrixTest() {
        List<Boolean> row1 = new ArrayList<>(List.of(true, false, false, true, false, false, false, false, true, false, true, false, true, false, false, false));
        List<Boolean> row2 = new ArrayList<>(List.of(false, true, false, false, false, true, false, false, false, false, true, false, true, false, false, true));
        List<Boolean> row3 = new ArrayList<>(List.of(false, false, false, false, false, false, false, true, true, false, true, true, false, true, true, false));
        List<Boolean> row4 = new ArrayList<>(List.of(true, false, false, true, false, false, false, false, false, true, false, false, false, false, false, false));
        List<Boolean> row5 = new ArrayList<>(List.of(false, false, false, false, true, false, true, false, true, false, false, false, false, false, false, false));
        List<Boolean> row6 = new ArrayList<>(List.of(true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true));
        List<Boolean> row7 = new ArrayList<>(List.of(true, true, false, false, false, false, false, false, true, false, false, false, false, true, false, false));
        List<Boolean> row8 = new ArrayList<>(List.of(false, false, false, true, true, false, false, true, false, false, false, false, false, false, false, false));
        List<Boolean> row9 = new ArrayList<>(List.of(false, true, false, false, false, false, false, true, true, false, false, false, false, false, false, false));
        List<Boolean> row10 = new ArrayList<>(List.of(true, true, false, false, false, true, false, false, false, false, false, false, false, false, true, false));
        List<Boolean> row11 = new ArrayList<>(List.of(true, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false));
        List<Boolean> row12 = new ArrayList<>(List.of(false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false));
        List<Boolean> row13 = new ArrayList<>(List.of(false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false));
        List<Boolean> row14 = new ArrayList<>(List.of(false, true, false, false, false, false, true, true, false, false, false, true, false, true, false, false));
        List<Boolean> row15 = new ArrayList<>(List.of(true, false, false, true, false, false, false, false, false, false, false, false, false, false, true, true));
        List<Boolean> row16 = new ArrayList<>(List.of(false, true, false, false, false, false, false, true, false, true, false, true, true, false, false, false));
        matrix.add(row1);
        matrix.add(row2);
        matrix.add(row3);
        matrix.add(row4);
        matrix.add(row5);
        matrix.add(row6);
        matrix.add(row7);
        matrix.add(row8);
        matrix.add(row9);
        matrix.add(row10);
        matrix.add(row11);
        matrix.add(row12);
        matrix.add(row13);
        matrix.add(row14);
        matrix.add(row15);
        matrix.add(row16);
    }
    @Test
    public void testCorrectSetMatrix() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        Assert.assertEquals(matrix,diagonalMatrix.getMatrix());
        }
    @Test
    public void testSetOneElement() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.setMatrixValue(0,0,false);
        matrix.get(0).set(0, false);
        Assert.assertEquals(matrix,diagonalMatrix.getMatrix());
        matrix.get(0).set(0, true);
    }
    @Test
    public void testReadWord() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        Assert.assertEquals("1000010110001010",diagonalMatrix.readWord(1));
    }
    @Test
    public void testReadAddres() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        Assert.assertEquals("1000100010001000",diagonalMatrix.readAddressCol(3));
    }
    @Test
    public void testConjunction() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.conjunction(0,1,2);
        Assert.assertEquals("1000010000000010",diagonalMatrix.readWord(2));
    }
    @Test
    public void testRepeatFirstArg() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.repeatFirstArg(0,1);
        Assert.assertEquals("1001011001100010",diagonalMatrix.readWord(1));
    }
    @Test
    public void testNegativeFirstArg() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.negativeFirstArg(0,1);
        Assert.assertEquals("0110100110011101",diagonalMatrix.readWord(1));
    }
    @Test
    public void testSheffer() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.shefferArg(0,1,5);
        Assert.assertEquals("0111101111111101",diagonalMatrix.readWord(5));
    }
    @Test
    public void testSum() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.setMatrixValue(0,0,true);
        diagonalMatrix.sumAB("100");
        Assert.assertEquals("1001011001101110",diagonalMatrix.readWord(0));
    }
    @Test
    public void testFindElementMore() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.setMatrixValue(0,0,true);
        Assert.assertEquals("0000000100000010",diagonalMatrix.findElementMore(2));
    }
    @Test
    public void testFindElementLess1() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.setMatrixValue(0,0,true);
        Assert.assertEquals("Element are was not founded",diagonalMatrix.findElementLess(2));
    }
    @Test
    public void testFindElementLess2() {
        DiagonalMatrix diagonalMatrix = new DiagonalMatrix();
        diagonalMatrix.setMatrixValue(matrix);
        diagonalMatrix.setMatrixValue(0,0,true);
        Assert.assertEquals("1000100000010000",diagonalMatrix.findElementLess(3));
    }
}


