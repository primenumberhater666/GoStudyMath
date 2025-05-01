package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.question.*;

import java.util.*;

public class RightTriangleTest {

    private RightTriangle rt1;

    @BeforeEach
    void runBefore() {
        rt1 = new RightTriangle(15, 12);
    }

    @Test
    void testConstructor() {
        assertEquals("RightTriangles" + (rt1.getNumCreated() - 1), rt1.getQuestionName());
        assertEquals(0,rt1.getNumAttempts());
        assertFalse(rt1.getAnswered());
        assertEquals((int)rt1.getAC(), (int)rt1.getHypotenuse(rt1.getAB(), (int)rt1.getBC()));
        assertEquals((int)(rt1.getAB() * rt1.getBC()), (int)(rt1.getBD() * rt1.getAC()));
    }

    @Test
    void testGetHypotenuse() {
        assertEquals(5, (int)rt1.getHypotenuse(3, 4));
        assertEquals(13, (int)rt1.getHypotenuse(5, 12));
        assertEquals(5000, (int)rt1.getHypotenuse(4000, 3000));
    }

    // Source used for type check:
    // https://stackoverflow.com/questions/4344871/how-can-i-know-if-object-is-string-type-object

    @Test
    // Only checks if it is a string, since object relies on random numbers
    void testToString() {
        assertTrue(rt1.toString().getClass().equals(String.class));
    }

}
