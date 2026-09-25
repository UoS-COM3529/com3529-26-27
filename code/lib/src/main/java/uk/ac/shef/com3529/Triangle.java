package uk.ac.shef.com3529;

public class Triangle {

    public enum Type {
        SCALENE,
        EQUILATERAL,
        ISOSCELES;
    }

    public static Type classify(int side1, int side2, int side3) {
        Type type;

        if (side1 > side2) {
            int temp = side1;
            side1 = side2;
            side2 = temp;
        }
        if (side1 > side3) {
            int temp = side1;
            side1 = side3;
            side3 = temp;
        }
        if (side2 > side3) {
            int temp = side2;
            side2 = side3;
            side3 = temp;
        }

        if (side1 + side2 <= side3) {
            throw new InvalidTriangleException("(" + side1 + ", " + side2 + ", " + side3 + ") is not a valid triangle");
        } else {
            type = Type.SCALENE;
            if (side1 == side2) {
                if (side2 == side3) {
                    type = Type.EQUILATERAL;
                } else {
                    type = Type.ISOSCELES;
                }
            } else if (side2 == side3) {
                type = Type.ISOSCELES;
            }
        }

        return type;
    }
}

class InvalidTriangleException extends RuntimeException {

    public InvalidTriangleException(String msg) {
        super(msg);
    }
}

