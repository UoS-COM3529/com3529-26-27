package uk.ac.shef.com3529;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TriangleTestMCDC {

    @Test
    public void shouldNotClassifyInvalidTriangle_T13() {
        // MCDC T13
        assertThrows(InvalidTriangleException.class, () -> Triangle.classify(1, 1, 2));
    }

    @Test
    public void shouldNotClassifyScalene_23() {
        // MCDC T23
        assertNotEquals(Triangle.Type.ISOSCELES, Triangle.classify(2, 3, 4));
    }

    @Test
    public void shouldNotClassifyEquilateral_T26() {
        // MCDC T26
        assertNotEquals(Triangle.Type.ISOSCELES, Triangle.classify(3, 3, 3));
    }

    @Test
    public void shouldClassifyIsosceles_T20() {
        // MCDC T20
        assertEquals(Triangle.Type.ISOSCELES, Triangle.classify(3, 4, 4));
    }

    @Test
    public void shouldClassifyIsosceles_T29() {
        // MCDC T29
        assertEquals(Triangle.Type.ISOSCELES, Triangle.classify(3, 3, 4));
    }

}