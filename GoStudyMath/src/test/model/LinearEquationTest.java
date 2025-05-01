package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.question.*;

public class LinearEquationTest {

    private LinearEquation leq1;

    @BeforeEach
    void runBefore() {

        leq1 = new LinearEquation(1, 3, 5);

    }

    @Test
    // Only tests range for a,b,c since they are random
    void testConstructor() {
        assertEquals("LinearEquations" + (leq1.getNumCreated() - 1), leq1.getQuestionName());
        assertEquals(0, leq1.getNumAttempts());
        assertFalse(leq1.getAnswered());
        assertTrue(leq1.getA() > 0 && leq1.getA() <= 20);
        assertTrue(leq1.getB() > 0 && leq1.getB() <= 20);
        assertTrue(leq1.getC() > 0 && leq1.getC() <= 20);
    }

    @Test
    void testSolutionIsInteger() {
        boolean booleanSol = (0 == (leq1.getC() - leq1.getB()) % leq1.getA());
        assertTrue(booleanSol);
    }

    // Source used for type check:
    // https://stackoverflow.com/questions/4344871/how-can-i-know-if-object-is-string-type-object

    @Test
    // Only checks if it is a string, since object relies on random numbers
    void testToString() {
        assertTrue(leq1.toString().getClass().equals(String.class));
    }

}
