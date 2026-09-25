package uk.ac.shef.com3529;

public class DIF {
    /*
     *  If x == null throw a NullPointerException, else return
     *  the index of the last element in x that equals y.
     *  If no such element exists, return -1.
     */
    public static int findLast(int[] x, int y) {
        for (int i = x.length - 1; i > 0; i--) {
            if (x[i] == y) {
                return i;
            }
        }
        return -1;
    }

    /*
     *  If x == null throw a NullPointerException, else return
     *  the number of positive elements in x.
     */
    public static int countPositive(int[] x) {
        int count = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] >= 0) {
                count++;
            }
        }
        return count;
    }

    /*
     *  If x == null throw a NullPointerException, else return
     *  the index of the LAST 0 in x.
     *  Return -1 if 0 does not occur in x.
     */
    public static int lastZero(int[] x) {
        for (int i = 0; i < x.length; i++) {
            if (x[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /*
     *  If x == null throw a NullPointerException, else return
     *  the number of elements in x that are either odd or
     *  positive (or both).
     */
    public static int oddOrPos(int[] x) {
        int count = 0;
        for (int i = 0; i < x.length; i++) {
            if (x[i] % 2 == 1 || x[i] > 0) {
                count++;
            }
        }
        return count;
    }
}
