package model.question;

import org.json.*;

import model.events.*;
import persistence.*;

// Represents a single variant of a specific type of question with the number of attempts a user has taken, 
// the status of whether the question has been correctly answered, and the name of the question.

public class Question implements Writable {

    protected int numAttempts;
    protected boolean answered;
    protected String questionName;
    protected int solution;
    protected String questionType; 
    /*
     * REQUIRES: the length of name > 0
     * EFFECTS: questionName of the question is set to name, answered is set to
     * false, numAttempts is set to 0.
     */

    public Question(String name) {
        numAttempts = 0;
        answered = false;
        questionName = name;
    }
    
    /*
     * REQUIRES: the length of name > 0
     * EFFECTS: questionName of the question is set to name, answered is set to
     * ans, numAttempts is set to attempts, solution is set to sol
     */

    public Question(String name, boolean ans, int attempts, int sol, String type) {
        numAttempts = attempts;
        answered = ans;
        questionName = name;
        solution = sol; 
        questionType = type;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public boolean getAnswered() {
        return answered;
    }

    public String getQuestionName() {
        return questionName;
    }

    public int getSolution() {
        return solution;
    }

    public String getQuestionType() {
        return questionType; 
    }

    /*
     * MODIFIES: this
     * EFFECTS: returns true if answer is the same number as solution. If so,
     * mark question as answered.
     * NOTE: METHOD NOT COVERED IN TEST DUE TO RANDOMNESS
     */

    public boolean checkAnswer(int answer) {
        return answer == this.getSolution();
    }

    /*
     * EFFECTS: set Solution to sol
     */

    public void setSolution(int sol) {
        solution = sol;
    }
    
    /*
     * EFFECTS: set answered to true
     */

    public void setAnswered() {
        answered = true;
    }

    /*
     * EFFECTS: set answered to false
     */

    public void setUnanswered() {
        answered = false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: increments numAttempts by 1
     */

    public void addAttempt() {
        numAttempts++;
    }

    /*
     * EFFECTS: Returns string representation of Question
     */
    public String toString(Boolean b) {
        String response = null;
        if (!answered) {
            response = "has not been correctly answered";
        } else {
            response = "has been correctly answered";
        }
        return questionName + " has been attempted " + numAttempts + " time(s), it " + response;
    }

    // EFFECTS: returns Questions in questions as a JSON array
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("numAttempts", numAttempts);
        json.put("name", questionName);
        json.put("answered", answered);
        json.put("solution", solution);
        json.put("type", questionType);
        return json;
    }
    
}
