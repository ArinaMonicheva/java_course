import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ComplexNums test = new ComplexNums();
        System.out.println(test);
        ComplexNums test2 = new ComplexNums(-2, -5);
        System.out.println(test2);
        ComplexNums test3 = new ComplexNums(4, 8);
        System.out.println(test2.add(test3));
        try {
            ComplexNums test4 = null;
            System.out.println(test2.add(test4));
        }
        catch (IllegalArgumentException e) {
            System.out.println("Na-ah, try again");
        }
        System.out.println(test2.sub(test3));
        System.out.println(test2.mul(test3));
        System.out.println(test2.div(test3));
        System.out.println(test2.convertToTrigonometric());
        System.out.println(test2.convertToAlgebraic(-1.95, 5.39));
        test2.setImaginaryPart(-3);
        System.out.println(test2);

        try {
            System.out.println("Enter numbers for real and imaginary parts");
            double realPart = input.nextDouble(), imaginaryPart = input.nextDouble();
            ComplexNums test4 = new ComplexNums(realPart, imaginaryPart);
            System.out.println(test4);
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("Wrong input");
        }

        Matrix newMatrix = new Matrix(3, 3);
        System.out.println(newMatrix);
        for (int i = 0; i < newMatrix.getHeight(); i++) {
            for (int j = 0; j < newMatrix.getWidth(); j++) {
                newMatrix.setValue(new ComplexNums(i + j + Math.random()), i, j);
            }
        }
        System.out.println(newMatrix);
        System.out.println(newMatrix.getValue(0, 2));
        System.out.println(newMatrix.transpose());
        ComplexNums matrixLikeObject[][] = new ComplexNums[3][3];
        for (int i = 0; i < matrixLikeObject.length; i++) {
            for (int j = 0; j < matrixLikeObject[0].length; j++) {
                matrixLikeObject[i][j] = new ComplexNums(i + j + Math.random());
            }
        }
        Matrix newMatrix2 = new Matrix(matrixLikeObject);
        System.out.println(newMatrix2);
        System.out.println(newMatrix.add(newMatrix2));
        System.out.println(newMatrix.sub(newMatrix2));
        System.out.println(newMatrix.mul(newMatrix2));
        System.out.println(newMatrix.selfDeterminant());

        try {
            System.out.println("Print number by which the matrix would be multiplied");
            System.out.println(newMatrix.mulByNumber(new ComplexNums(input.nextDouble())));
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("Wrong input");
        }

        try {
            System.out.println("Print indexes from where execute the value");
            System.out.println(newMatrix.getValue(input.nextInt(), input.nextInt()));
        }
        catch (java.util.InputMismatchException e) {
            System.out.println("Wrong input");
        }
    }
}
