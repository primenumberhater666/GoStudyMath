package persistence;

import model.*;
import model.question.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CREDIT: JsonSerializationDemo found in EdX Project Phase 2
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            QuestionList ql = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyQuestionList() {
        JsonReader reader = new JsonReader("./data/testEmptyQuestionList.json");
        try {
            QuestionList ql = reader.read();
            assertEquals(0, ql.getQuestions().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testGeneralQuestionList() {
        JsonReader reader = new JsonReader("./data/testGeneralQuestionList.json");
        try {
            QuestionList ql = reader.read();
            List<Question> questions = ql.getQuestions();
            assertEquals(3, ql.getQuestions().size());
            checkQuestion("LinearEquation1", questions.get(0), true, 0);

            checkQuestion("Generic Question", questions.get(1), false, 3);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
