package model.question;

import java.util.*;

import org.json.JSONObject;

// Represents a Linear Equation question variant with the same fields as a question, 
// and a value for the solution of the given variant. The equations generated will be in the form of ax + b = c.
// The id of the LinearEquation is the number of previous LinearEquations, incremented by 1. 

public class LinearEquation extends Question {

    private int a0;
    private int b0;
    private int c0;
    private static int NUMCREATED = 1;
    

    /*
     * EFFECTS: questionName of the question is set to LinearEquationX,
     * where X is the number of variants generated so far (of LinearEquation).
     * Creates a linear equation in the form ax + b = c, where a, b, c are natural
     * numbers in the range
     * (0,20] and x is the solution of the equation.(c - b) / a is always an
     * integer.
     * 
     */

    public LinearEquation(int a, int b, int c) {
        super("LinearEquations" + NUMCREATED);
        this.a0 = a;
        this.b0 = b;
        this.c0 = c;
        this.questionType = "Linear Equations";
        NUMCREATED++;
        solution = (c - b) / a;
    }

    public LinearEquation(String name, boolean ans, int attempts, int sol, String type, int a, int b, int c, int tot) {
        super(name);
        this.a0 = a;
        this.b0 = b;
        this.c0 = c;
        this.questionType = "Linear Equations";
        this.numAttempts = attempts;
        this.solution = sol;
        this.answered = ans;
        NUMCREATED = tot; 
    }


    public int getA() {
        return a0;
    }

    public int getB() {
        return b0;
    }

    public int getC() {
        return c0;
    }

    public int getNumCreated() {
        return NUMCREATED;
    }

    /*
     * EFFECTS: Returns string representation of LinearEquation
     */
    @Override
    public String toString() {
        if (a0 == 1) {
            return "Solve for x in the following equation: x + " + b0 + " = " + c0;
        }
        return "Solve for x in the following equation: " + a0 + "x + " + b0 + " = " + c0;
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
        json.put("a", a0);
        json.put("b", b0);
        json.put("c", c0);
        json.put("total", NUMCREATED);
        return json;
    }

}
