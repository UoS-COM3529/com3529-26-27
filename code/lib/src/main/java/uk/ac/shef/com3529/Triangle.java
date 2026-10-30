package uk.ac.shef.com3529;

import java.util.concurrent.ThreadLocalRandom;

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

    public static int[] generateRandomTriangle(Type type) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        switch (type) {
            case EQUILATERAL:
                int side = random.nextInt(1, 101);
                return new int[]{side, side, side};
            case ISOSCELES:
                int equalSide = random.nextInt(2, 101);
                int thirdSide = random.nextInt(1, equalSide * 2);
                if (thirdSide == equalSide) {
                    thirdSide = equalSide - 1;
                }
                return new int[]{equalSide, equalSide, thirdSide};
            case SCALENE:
                int shortestSide = random.nextInt(2, 51);
                int middleSide = random.nextInt(shortestSide + 1, 100);
                int longestSide = random.nextInt(middleSide + 1,
                        Math.min(101, shortestSide + middleSide));
                return new int[]{shortestSide, middleSide, longestSide};
            default:
                throw new IllegalArgumentException("Unsupported triangle type: " + type);
        }
    }
}

class InvalidTriangleException extends RuntimeException {

    public InvalidTriangleException(String msg) {
        super(msg);
    }
}

