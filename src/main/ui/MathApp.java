package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.annotation.processing.FilerException;

import persistence.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.question.*;

// GoStudyMath application (design idea borrowed from EdX Phase 1 Teller App)
public class MathApp {

    private static final String JSON_STORE = "./data/questionData.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private QuestionList userQuestions;
    private Scanner userInput = new Scanner(System.in);
    private boolean loop = false; 

    // EFFECTS: starts the application
    public MathApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        loop = true;
        String input = null;

        initQuestions();

        while (loop) {
            displayOptions();
            input = userInput.next();

            if (input.equals("e")) {
                loop = false;
            } else {
                processCommand(input);
            }
        }

        System.out.println("Program Exited.");
    }

    // MODIFIES: this
    // EFFECTS: initialize a QuestionList for the user
    private void initQuestions() {
        userQuestions = new QuestionList();
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    @SuppressWarnings("methodlength")
    private void processCommand(String cmd) {
        Question q;
        if (cmd.equals("a")) {
            int a = 999;
            int b = 3;
            int c = 5;
            while ((c - b) % a != 0) {
                a = (int) (1 + Math.random() * 20);
                b = (int) (1 + Math.random() * 20);
                c = (int) (1 + Math.random() * 20);
            }
            q = new LinearEquation(a, b, c);
            addUserQuestion(q);
            displayQuestion(q);
        } else if (cmd.equals("b")) {
            displayCurrentQuestions();
        } else if (cmd.equals("c")) {
            System.out.println("Enter the name of the question you want to view.");
            String questionChoice = userInput.next();
            displayQuestion(userQuestions.getQuestion(questionChoice));

        } else if (cmd.equals("d")) {
            System.out.println("Enter the name of the question you want to delete.");
            String questionChoice = userInput.next();
            removeQuestion(questionChoice);
            System.out.println("Question successfully deleted.");
            System.out.println("Enter 'b' to go back your list of questions");
            System.out.println("Enter 'e' to exit the application");
        } else if (cmd.equals("f")) {
            saveQuestions();
        } else if (cmd.equals("g")) {
            loadQuestions();
        } else {
            System.out.println("Please enter a valid command!");
        }
    }

    // EFFECTS: Display initial user options
    private void displayOptions() {
        System.out.println("Enter the number/character corresponding with the choices below.");
        System.out.println("a. Start new Linear Equation variant");
        System.out.println("b. View attempted questions/Load questions from file");
        System.out.println("e. Exit application");
    }

    // EFFECTS: displays the list of questions the user has done/is doing
    private void displayCurrentQuestions() {
        if (!userQuestions.getQuestions().isEmpty()) {
            for (int i = 0; i < userQuestions.getQuestions().size(); i++) {
                System.out.println(userQuestions.getQuestions().get(i).toString(true));
            }
            System.out.println("Enter the number/character corresponding with the choices below.");
            System.out.println("c. View a question from the list");
            System.out.println("d. Delete a question from the list");
            System.out.println("f. Save question list to file");
            System.out.println("g. Load question list from file");
            System.out.println("Enter 'e' to exit the application.");

        } else {
            System.out.println("You have no previously attempted questions!");
            System.out.println("f. Save question list to file");
            System.out.println("g. Load question list from file");
            System.out.println("Enter 'e' to exit the application.");
        }
    }

    // EFFECTS: displays a specific question variant
    private void displayQuestion(Question q) {
        System.out.println(q.toString());
        System.out.println("Enter 'e' to exit the application");
        checkUserAnswer(q);

    }

    // EFFECTS: checks the answer and displays whether the answer is correct

    private void checkUserAnswer(Question q) {
        String answer;
        int sol = q.getSolution();
        answer = userInput.next();
        q.addAttempt();
        if (answer.equals("e")) {
            loop = false;
        } else if (answer.equals(Integer.toString(sol))) {
            System.out.println("That's the correct answer!");
            q.setAnswered();
        } else {
            System.out.println("Try Again!");
            checkUserAnswer(q);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a question from userQuestions
    private void removeQuestion(String name) {
        userQuestions.delQuestion(name);
    }

    // MODIFIES: this
    // EFFECTS: adds a question to userQuestions
    private void addUserQuestion(Question q) {
        userQuestions.addQuestion(q);
    }

    // CREDIT: edX Project Phase 2 example project 

    // EFFECTS: Saves user questions (and question data) to file
    public void saveQuestions() {
        try {
            jsonWriter.open();
            jsonWriter.write(userQuestions);
            jsonWriter.close();
            System.out.println("Saved question progress to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads user questions on file 
    public void loadQuestions() {
        try {
            userQuestions = jsonReader.read();
            System.out.println("Loaded questions from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
