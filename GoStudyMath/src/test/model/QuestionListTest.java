package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.question.*;

import java.util.*;

public class QuestionListTest {

    private Question q1;
    private Question q2;
    private Question q3;
    private QuestionList ql1;
    private ArrayList<Question> testQuestionList;

    @BeforeEach
    void runBefore() {
        q1 = new Question("q1");
        q2 = new Question("q2");
        q3 = new Question("q3");
        ql1 = new QuestionList();
        testQuestionList = new ArrayList<Question>();

    }

    @Test
    void testConstructor() {
        assertEquals(0, ql1.getQuestions().size());
        assertEquals(testQuestionList, ql1.getQuestions());
    }

    @Test
    void testAddOneQuestion() {
        ql1.addQuestion(q1);
        testQuestionList.add(q1);
        assertEquals(1, ql1.getQuestions().size());
        assertEquals(testQuestionList, ql1.getQuestions());
    }

    @Test
    void testAddThreeQuestions() {
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        ql1.addQuestion(q3);
        testQuestionList.add(q1);
        testQuestionList.add(q2);
        testQuestionList.add(q3);
        assertEquals(3, ql1.getQuestions().size());
        assertEquals(testQuestionList, ql1.getQuestions());
    }

    @Test
    void testGetQuestionNull() {
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        ql1.addQuestion(q3);
        assertNull(ql1.getQuestion("q4"));
    }

    @Test
    void testGetFirstQuestion() {
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        ql1.addQuestion(q3);
        assertEquals(q1, ql1.getQuestion("q1"));
    }

    @Test
    void testGetMiddleQuestion() {
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        ql1.addQuestion(q3);
        assertEquals(q2, ql1.getQuestion("q2"));
    }
    
    @Test 
    void testDeleteOnlyQuestion() {
        ql1.addQuestion(q1);
        ql1.delQuestion("q1");
        assertEquals(0, ql1.getQuestions().size());
        assertEquals(testQuestionList, ql1.getQuestions());
    }

    @Test
    void testDeleteFirstQuestion() {
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        ql1.addQuestion(q3);
        testQuestionList.add(q1);
        testQuestionList.add(q2);
        testQuestionList.add(q3);
        ql1.delQuestion("q1");
        testQuestionList.remove(q1);
        assertEquals(2, ql1.getQuestions().size());
        assertEquals(testQuestionList, ql1.getQuestions());

    }

    @Test
    void testDeleteQuestionInMiddle() {
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        ql1.addQuestion(q3);
        testQuestionList.add(q1);
        testQuestionList.add(q2);
        testQuestionList.add(q3);
        ql1.delQuestion("q2");
        testQuestionList.remove(q2);
        assertEquals(2, ql1.getQuestions().size());
        assertEquals(testQuestionList, ql1.getQuestions());

    }

    @Test
    void testDeleteNameNotInList() {
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        testQuestionList.add(q1);
        testQuestionList.add(q2);
        ql1.delQuestion("q99");
        assertEquals(2, ql1.getQuestions().size());
        assertEquals(testQuestionList, ql1.getQuestions());

    }

    @Test 
    void testGetAverageAttempts() {
        q1.addAttempt();
        q1.addAttempt();
        q1.addAttempt();
        q2.addAttempt();
        ql1.addQuestion(q1);
        ql1.addQuestion(q2);
        assertEquals(2, ql1.getAverageAttempts());
    }

}
