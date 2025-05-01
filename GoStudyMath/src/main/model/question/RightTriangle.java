package model.question;

import java.util.*;

import org.json.JSONObject;

// Represents a question variant on right triangles, with same fields as Question
// All variants are based off of a right triangle ABC with angle ABC being 90 degrees, 
// and a segment BD that is perpendicular to AC, such that ABD and BDC are also right triangles.
// The id of the RightTriangle is the number of previous RightTriangle, incremented by 1. 

public class RightTriangle extends Question {

    private int ab;
    private double bc;
    private double ac;
    private int bd;
    private static int NUMCREATED = 1;

    /*
     * EFFECTS: questionName of the question is set to RightTrianglesX,
     * where X is the number of variants generated so far (of RightTriangle).
     * Creates a right triangle ABC with sides and length AD of (rounded) integer length.
     * The solution is floor(AC) + floor(BC)
     */
    public RightTriangle(int ab0, int bd0) {
        super("RightTriangles" + NUMCREATED);
        this.ab = ab0;
        this.bd = bd0;
        this.questionType = "Right Triangles";

        solveTriangle(ab0, bd0);
        NUMCREATED++;
    }
    
    // EFFECTS: this constructor is needed for persistence
    public RightTriangle(String name, boolean ans, int attempts, int sol, String type, 
                         int ab, int bd, double ac, double bc, int tot) {
        super(name);
        this.ab = ab;
        this.bd = bd;
        this.ac = ac;
        this.bc = bc;
        this.questionType = "Linear Equations";
        this.numAttempts = attempts;
        this.solution = sol;
        this.answered = ans;
        NUMCREATED = tot; 
    }

    // EFFECTS: returns sqrt(a^2 + b^2)
    public double getHypotenuse(int a, int b) {
        return (int)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    // REQUIRES: ab > bd 
    // MODIFIES: this
    // EFFECTS: sets ac, bc, and solution to required values
    public void solveTriangle(int ab, int bd) {
        double ac = Math.pow(ab, 2) / Math.sqrt(Math.pow(ab, 2) - Math.pow(bd, 2));
        this.ac = ac; 
        double bc = Math.sqrt(ac * (ac - Math.sqrt(Math.pow(ab, 2) - Math.pow(bd, 2))));
        this.bc = bc;
        this.solution = (int)Math.floor(ac + bc);
    }

    public int getAB() {
        return ab;
    }

    public double getBC() {
        return bc;
    }

    public double getAC() {
        return ac;
    }

    public int getBD() {
        return bd;
    }

    public int getNumCreated() {
        return NUMCREATED;
    }

    /*
     * EFFECTS: Returns string representation of LinearEquation
     */

    @Override
    public String toString() {
        return "Let ABC be a right triangle with angle ABC being 90 degrees. \n" 
            + "Let D be on AC such that AC is perpendicular to BD. \nGiven that AB = " 
            + ab + " and BD = " + bd + ", \nFind the integer that is less than or \n equal to the sum of AC and BC.";
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
        json.put("ab", ab);
        json.put("bd", bd);
        json.put("bc", bc);
        json.put("ac", ac);
        json.put("total", NUMCREATED);
        return json;
    }


}
