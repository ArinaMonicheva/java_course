public class ComplexNums {
    private double realPart;
    private double imaginaryPart;
    private double complexAbs;
    private double angle;

    ComplexNums() {
        this.realPart = 0;
        this.imaginaryPart = 0;
        this.angle = 0;
        this.complexAbs = 0;
    }

    ComplexNums(double realPart) {
        this.realPart = realPart;
        this.imaginaryPart = 0;
        this.complexAbs = Math.abs(realPart);
        this.angle = 0;
    }

    ComplexNums(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
        this.complexAbs = countAbs();
        this.angle = countAngle();
    }

    private double countAbs() {
        return Math.sqrt(Math.pow(this.realPart, 2)
                + Math.pow(this.imaginaryPart, 2));
    }

    private double countAngle() {
        if (this.realPart > 0) {
            return Math.atan(this.imaginaryPart / this.realPart);
        }
        else if (this.realPart < 0 && this.imaginaryPart > 0) {
            return Math.PI + Math.atan(this.imaginaryPart / this.realPart);
        }
        else {
            return -Math.PI + Math.atan(this.imaginaryPart / this.realPart);
        }
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public double getRealPart() {
        return realPart;
    }

    public double getAngle() {
        return angle;
    }

    public double getComplexAbs() {
        return complexAbs;
    }

    public void setImaginaryPart(double imaginaryPart) {
        this.imaginaryPart = imaginaryPart;
        this.complexAbs = countAbs();
        this.angle = countAngle();
    }

    public void setRealPart(double realPart) {
        this.realPart = realPart;
        this.complexAbs = countAbs();
        this.angle = countAngle();
    }

    public ComplexNums add(ComplexNums other) throws IllegalArgumentException {
        if (other != null) {
            return new ComplexNums(this.realPart + other.realPart,
                    this.imaginaryPart + other.imaginaryPart);
        }
        throw new IllegalArgumentException("Empty object");
    }

    public ComplexNums sub(ComplexNums other) throws IllegalArgumentException {
        if (other != null) {
            return new ComplexNums(this.realPart - other.realPart,
                    this.imaginaryPart - other.imaginaryPart);
        }
        throw new IllegalArgumentException("Empty object");
    }

    public ComplexNums mul(ComplexNums other) throws IllegalArgumentException {
        if (other != null) {
            return new ComplexNums(this.realPart * other.realPart -
                    this.imaginaryPart * other.imaginaryPart, this.realPart *
                    other.imaginaryPart + other.realPart * this.imaginaryPart
            );
        }
        throw new IllegalArgumentException("Empty object");
    }

    public ComplexNums mulByRealNumber(double mulOn) {
            return new ComplexNums(this.realPart * mulOn,
                    this.realPart * mulOn
            );
    }

    public ComplexNums div(ComplexNums other) throws IllegalArgumentException {
        if (other != null) {
            if (other.realPart == 0 && other.imaginaryPart == 0) {
                throw new IllegalArgumentException("Division by zero");
            }
            double denominator = Math.pow(other.realPart, 2) +
                    Math.pow(other.imaginaryPart, 2);
            return new ComplexNums((this.realPart * other.realPart +
                    this.imaginaryPart * other.imaginaryPart) / denominator,
                    (other.realPart *
                            this.imaginaryPart - this.realPart * other.imaginaryPart) / denominator
            );
        }
        throw new IllegalArgumentException("Empty object");
    }

    public ComplexNums convertToAlgebraic(double angle, double complexAbs)
            throws IllegalArgumentException {
        if (complexAbs >= 0) {
            return new ComplexNums(Math.cos(angle) * complexAbs,
                    Math.sin(angle) * complexAbs);
        }
        throw new IllegalArgumentException("Abs can't be negative");
    }

    @Override
    public String toString() {
        if (this.realPart == 0 && this.imaginaryPart == 0) {
            return "0";
        }
        else if (this.imaginaryPart == 0) {
            return String.format("%.2f", this.realPart);
        }
        else if (this.realPart == 0) {
            return String.format("%.2fi", this.imaginaryPart);
        }
        else if (this.imaginaryPart >= 0) {
            return String.format("%.2f + %.2fi", this.realPart, this.imaginaryPart);
        }
        return String.format("%.2f %.2fi", this.realPart, this.imaginaryPart);
    }

    public String convertToTrigonometric() {
        return String.format("%.2f(cos(%.2f) + isin(%.2f))\n",
                this.complexAbs, this.angle, this.angle);
    }

}
