package DiagonalMatrix;

public class Main {
    public static void main(String[] args)
    {
        DiagonalMatrix matrix = new DiagonalMatrix();
        matrix.setMatrixValue(0,0,true);
        matrix.setMatrixValue(1,0,true);
        matrix.setMatrixValue(2,0,true);

        matrix.setMatrixValue(3,0,false);
        matrix.setMatrixValue(4,0,false);
        matrix.setMatrixValue(5,0,false);
        matrix.setMatrixValue(6,0,true);

        matrix.setMatrixValue(7,0,true);
        matrix.setMatrixValue(8,0,true);
        matrix.setMatrixValue(9,0,true);
        matrix.setMatrixValue(10,0,true);
        matrix.getMatrix();
        System.out.println(matrix.readWord(2));
        System.out.println(matrix.readAddressCol(3));
//        matrix.conjunction(1,2,3);
//        matrix.repeatFirstArg(1,2);
//        matrix.negativeFirstArg(0,1);
//        matrix.shefferArg(0,1,2);
        matrix.sumAB("111");
        matrix.getMatrix();
        System.out.println(matrix.findElementMore(5));
        System.out.println(matrix.findElementLess(5));
    }
}