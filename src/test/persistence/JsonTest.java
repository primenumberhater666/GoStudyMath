package persistence;

import model.*;
import model.question.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
// CREDIT: JsonSerializationDemo found in EdX Project Phase 2

public class JsonTest {
    protected void checkQuestion(String name, Question q, Boolean ans, int attempts) {
        assertEquals(name, q.getQuestionName());
        assertEquals(attempts, q.getNumAttempts());
        assertEquals(ans, q.getAnswered());
    }
}
