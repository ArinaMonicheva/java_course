import java.util.Arrays;

public class Matrix {
    private ComplexNums [][]matrixBody;
    private final int height;
    private final int width;

    Matrix(int newHeight, int newWidth) throws IllegalArgumentException {
        if (newWidth < 1 || newHeight < 1) {
            throw new IllegalArgumentException("Illegal value for matrix dimensions");
        }
        this.matrixBody = new ComplexNums[newHeight][newWidth];
        this.height = newHeight;
        this.width = newWidth;
    }

    Matrix(ComplexNums[][] matrixLikeObject) throws NullPointerException {
        if (matrixLikeObject == null) {
            throw new NullPointerException("Empty object");
        }
        this.matrixBody = matrixLikeObject;
        this.height = matrixLikeObject.length;
        this.width = matrixLikeObject[0].length;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(this.height, this.width);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                transposedMatrix.matrixBody[j][i] = this.matrixBody[i][j];
            }
        }

        return transposedMatrix;
    }

    public Matrix transpose(ComplexNums[][] matrixLikeObject) throws IllegalArgumentException {
        if (matrixLikeObject != null) {
            Matrix transposedMatrix = new Matrix(matrixLikeObject[0].length,
                    matrixLikeObject.length);
            for (int i = 0; i < matrixLikeObject.length; i++) {
                for (int j = 0; j < matrixLikeObject[i].length; j++) {
                    transposedMatrix.matrixBody[j][i] = matrixLikeObject[i][j];
                }
            }

            return transposedMatrix;
        }
        throw new IllegalArgumentException("Empty object");
    }

    public void setValue(ComplexNums value, int heightIndex, int widthIndex)
            throws IndexOutOfBoundsException {
        if (heightIndex > this.height - 1 || heightIndex < 0 ||
                widthIndex > this.width - 1 || widthIndex < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        this.matrixBody[heightIndex][widthIndex] = value;
    }

    public ComplexNums getValue(int heightIndex, int widthIndex)
            throws IndexOutOfBoundsException {
        if (heightIndex > this.height - 1 || heightIndex < 0 ||
                widthIndex > this.width - 1 || widthIndex < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return this.matrixBody[heightIndex][widthIndex];
    }

    public Matrix add(Matrix other) throws IndexOutOfBoundsException {
        if (other.height != this.height || other.width != this.width) {
            throw new IndexOutOfBoundsException("Different matrix dimensions");
        }
        Matrix summedMatrix = new Matrix(this.height, this.width);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                summedMatrix.matrixBody[i][j] =
                        this.matrixBody[i][j].add(other.matrixBody[i][j]);
            }
        }

        return summedMatrix;
    }

    public Matrix sub(Matrix other) throws IndexOutOfBoundsException {
        if (other.height != this.height || other.width != this.width) {
            throw new IndexOutOfBoundsException("Different matrix dimensions");
        }
        Matrix residualMatrix = new Matrix(this.height, this.width);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                residualMatrix.matrixBody[i][j] =
                        this.matrixBody[i][j].sub(other.matrixBody[i][j]);
            }
        }

        return residualMatrix;
    }

    public Matrix mulByNumber(ComplexNums mulOn) {
        Matrix mulMatrix = new Matrix(this.height, this.width);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                mulMatrix.matrixBody[i][j] =
                        this.matrixBody[i][j].mul(mulOn);
            }
        }

        return mulMatrix;
    }

    public Matrix mul(Matrix other) throws IndexOutOfBoundsException {
        if (other.height != this.width) {
            throw new IndexOutOfBoundsException("Inappropriate matrix dimensions");
        }
        Matrix mulMatrix = new Matrix(this.height, other.width);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < other.width; j++) {
                mulMatrix.matrixBody[i][j] = new ComplexNums();
                int tempN = 0;
                while (tempN < this.width) {
                    mulMatrix.matrixBody[i][j] =
                            mulMatrix.matrixBody[i][j].add(
                                    this.matrixBody[i][tempN].mul(
                                            other.matrixBody[tempN][j]));
                    tempN++;
                }
            }
        }

        return mulMatrix;
    }

    public ComplexNums selfDeterminant() {
        return countDeterminant(this);
    }

    public ComplexNums countDeterminant(Matrix other)
            throws UnsupportedOperationException { // only for 1x1, 2x2, 3x3
        if (other.width != other.height) {
            throw new UnsupportedOperationException(
                    "Can't calculate determinant of a non-square matrix");
        }
        ComplexNums determinant = new ComplexNums();

        if (other.width == 1) {
            return other.matrixBody[0][0];
        }
        else if (other.width == 2) {
            return (other.matrixBody[0][0].mul(
                    other.matrixBody[1][1])).sub(
                    other.matrixBody[0][1].mul(
                            other.matrixBody[1][0]));
        }

        else if (other.width == 3) {
            for (int i = 0; i < other.width; i++) {
                Matrix subMatrix = findSubmatrix(other, i, 0);
                ComplexNums temp = (other.matrixBody[i][0].mulByRealNumber(
                        Math.pow(-1, 1+i+1))).mul(countDeterminant(subMatrix));
                determinant = determinant.add(temp);
            }
        }

        return determinant;
    }

    public Matrix findSubmatrix(Matrix mainMatrix, int removeLine, int removeColumn) // only for 3x3
            throws IndexOutOfBoundsException {
        if (removeLine > mainMatrix.height - 1 || removeLine < 0 ||
                removeColumn > mainMatrix.width - 1 || removeColumn < 0 ||
                mainMatrix.width > 3 || mainMatrix.height > 3) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Matrix subMatrix = new Matrix(mainMatrix.height - 1,
                mainMatrix.width - 1);
        int currentI = 0, currentJ = 0;
                for (int i = 0; i < mainMatrix.height; i++) {
                    if (i != removeLine) {
                        currentJ = 0;
                        for (int j = 0; j < mainMatrix.width; j++) {
                            if (j != removeColumn) {
                                subMatrix.matrixBody[currentI][currentJ++] =
                                        mainMatrix.matrixBody[i][j];
                            }
                        }
                        currentI++;
                    }
                }
        return subMatrix;
    }

    @Override
    public String toString() {
        String toString = "";
        int j = 0;
        for (int i = 0; i < this.height; i++) {
            toString += "[";
            for (j = 0; j < this.width - 1; j++) {
                if (this.matrixBody[i][j] == null) {
                    toString += "    , ";
                }
                else {
                    toString += this.matrixBody[i][j].toString() + ", ";
                }
            }
            if (this.matrixBody[i][j] == null) {
                toString += "    ";
            }
            else {
                toString += this.matrixBody[i][j].toString();
            }
            toString += "]\n";
        }
        return toString;
    }
}
