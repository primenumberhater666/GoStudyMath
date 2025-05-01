package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.sound.sampled.Line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.question.*;

public class QuestionTest {

    private Question q1;

    @BeforeEach
    void runBefore() {

        q1 = new Question("q1");

    }

    @Test
    void testConstructor() {
        assertEquals("q1", q1.getQuestionName());
        assertEquals(0, q1.getNumAttempts());
        assertFalse(q1.getAnswered());
    }

    @Test
    void testSecondaryConstructor() {
        Question q2 = new Question("test", true, 1 ,1, "11");
        assertEquals("test", q2.getQuestionName());
        assertEquals(1, q2.getNumAttempts());
        assertEquals(1, q2.getSolution());
        assertTrue(q2.getAnswered());
    }

    @Test
    void testAddAttempts() {
        assertEquals(0, q1.getNumAttempts());
        q1.addAttempt();
        assertEquals(1, q1.getNumAttempts());
        q1.addAttempt();
        q1.addAttempt();
        assertEquals(3, q1.getNumAttempts());
    }

    @Test
    void testSetAnswered() {
        q1.setAnswered();
        assertTrue(q1.getAnswered());
        q1.setAnswered();
        assertTrue(q1.getAnswered());
    }

    @Test
    void testSetUnanswered() {
        q1.setUnanswered();
        assertFalse(q1.getAnswered());
        q1.setUnanswered();
        assertFalse(q1.getAnswered());
    }

    @Test
    void testSetSolution() {
        q1.setSolution(3);
        assertEquals(3, q1.getSolution());
        q1.setSolution(5);
        assertEquals(5, q1.getSolution());
    }

    @Test
    void testGetSolution() {
        q1.setSolution(2);
        assertEquals(2, q1.getSolution());
    }

    @Test
    void testCheckAnswer() {
        q1.setSolution(5);
        assertTrue(q1.checkAnswer(5));
        assertFalse(q1.checkAnswer(3));
    }

    @Test
    void testGetQuestionType() {
        Question q0 = new LinearEquation(2, 5, 11);
        assertEquals("Solve for x in the following equation: 2x + 5 = 11", q0.toString());
        assertEquals("Linear Equations", q0.getQuestionType());
    }

    @Test
    void testToString() {
        q1.addAttempt();
        q1.addAttempt();
        q1.setUnanswered();
        assertTrue(q1.toString(true).contains("has not been correctly answered"));
        q1.setAnswered();
        assertTrue(q1.toString(true).contains("has been correctly answered"));
        assertTrue(q1.toString(true).contains("has been attempted 2 time(s)"));
    }

}
