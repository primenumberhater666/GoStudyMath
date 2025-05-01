package persistence;

import model.question.*;
import org.json.JSONObject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.*;

// CREDIT: JsonSerializationDemo found in EdX Project Phase 2
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            QuestionList ql = new QuestionList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass, we want error
        }
    }

    @Test
    void testWriterEmptyList() {
        try {
            QuestionList ql = new QuestionList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyList.json");
            writer.open();
            writer.write(ql);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyList.json");
            ql = reader.read();
            assertEquals(0, ql.getQuestions().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralList() {
        try {
            QuestionList ql = new QuestionList();
            Question q1 = new Question("TestQuestion1", false, 1, 3, "11");
            Question q2 = new Question("TestQuestion2", true, 2, 5, "11");
            Question q3 = new LinearEquation("LinearEquations1", false, 5, 1, "Linear Equations", 1, 3, 4, 1);
            Question q4 = new RightTriangle(5, 4);
            ql.addQuestion(q1);
            ql.addQuestion(q2);
            ql.addQuestion(q3);
            ql.addQuestion(q4);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralList.json");
            writer.open();
            writer.write(ql);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralList.json");
            ql = reader.read();
            List<Question> questions = ql.getQuestions();
            assertEquals(4, questions.size());
            checkQuestion("TestQuestion1", questions.get(0), false, 1);
            checkQuestion("TestQuestion2", questions.get(1), true, 2);
            checkQuestion("LinearEquations1", questions.get(2), false, 5);
            checkQuestion("RightTriangles1", questions.get(3), false, 0);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
