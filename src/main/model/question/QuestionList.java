package model.question;

import java.util.*;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;

import model.events.*;

// Represents a list of questions that the user has created. 

public class QuestionList {

    private ArrayList<Question> questions;

    /*
     * EFFECTS: questions of the QuestionList is initialized as an empty ArrayList
     * containing Questions
     */

    public QuestionList() {
        questions = new ArrayList<Question>();
    }
    /*
     * MODIFIES: this
     * EFFECTS: q is added to the end of questions
     */

    public void addQuestion(Question q) {
        questions.add(q);
        String msg = q.getQuestionName() + " added to user question list.";
        Event e = new Event(msg);
        EventLog.getInstance().logEvent(e);
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns the Question with questionName name. If no question with
     * name is found,
     * return null.
     */
    public Question getQuestion(String name) {
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getQuestionName().equals(name)) {
                return questions.get(i);
            }
        }
        return null;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Question with questionName name is removed from questions. If no
     * question with questionName is found, do nothing.
     */
    public void delQuestion(String name) {
        Question q = getQuestion(name);
        if (q != null) {
            String msg = q.getQuestionName() + " removed from user question list.";
            questions.remove(q);
            Event e = new Event(msg);
            EventLog.getInstance().logEvent(e);
        }  
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    // REQUIRES: questions.size() > 0
    // EFFECTS: returns the average number of attempts required for problems in
    // questions
    public int getAverageAttempts() {
        int total = 0;
        for (Question q : questions) {
            total += q.getNumAttempts();
        }
        int avg =  total / questions.size();
        String msg = "Average of " + total + " attempts obtained from user list.";
        Event e = new Event(msg);
        EventLog.getInstance().logEvent(e);
        return avg; 
    }

    // CREDIT: edX Phase 2 project example
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("questions", questionsToJson());
        return json;
    }

    // EFFECTS: returns Questions in questions as a JSON array
    private JSONArray questionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Question q : questions) {
            jsonArray.put(q.toJson());
        }

        return jsonArray;
    }

}
