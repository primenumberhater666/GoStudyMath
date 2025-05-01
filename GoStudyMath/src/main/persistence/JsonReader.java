package persistence;

import model.question.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a JSON reader that reads a QuestionList from Json data 
// CREDIT: JsonSerializationDemo found in EdX Project Phase 2
public class JsonReader {

    private String source; 

    // EFFECTS: constructs reader to read from source file

    public JsonReader(String source) {
        this.source = source; 
    }
    // EFFECTS: reads QuestionList from file and returns it;
    // throws IOException if an error occurs reading data from file

    public QuestionList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseQuestionList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses QuestionList from JSON object and returns it
    private QuestionList parseQuestionList(JSONObject jsonObject) {
        QuestionList ql = new QuestionList();
        addQuestions(ql, jsonObject);
        return ql;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addQuestions(QuestionList ql, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("questions");
        for (Object json : jsonArray) {
            JSONObject nextQuestion = (JSONObject) json;
            addQuestion(ql, nextQuestion);
        }
    }


    // MODIFIES: ql
    // EFFECTS: parses Question from JSON object and adds it to QuestionList
    private void addQuestion(QuestionList ql, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int numAttempts = jsonObject.getInt("numAttempts");
        boolean answered = jsonObject.getBoolean("answered");
        int sol = jsonObject.getInt("solution");
        String questionType = jsonObject.getString("type");
        if (questionType.equals("Linear Equations")) {
            int a = jsonObject.getInt("a");
            int b = jsonObject.getInt("b");
            int c = jsonObject.getInt("c");
            int total = jsonObject.getInt("total");
            Question q = new LinearEquation(name, answered, numAttempts, sol, questionType, a, b, c, total);
            ql.addQuestion(q);
        } else if (questionType.equals("Right Triangles")) {
            int ab = jsonObject.getInt("ab");
            int bd = jsonObject.getInt("bd");
            int ac = jsonObject.getInt("ac");
            int bc = jsonObject.getInt("bc");
            int total = jsonObject.getInt("total");
            Question q = new RightTriangle(name, answered, numAttempts, sol, questionType, ab, bd, ac, bc, total);
            ql.addQuestion(q);
        } else {
            ql.addQuestion(new Question(name, answered, numAttempts, sol, questionType));
        }
    }

}
