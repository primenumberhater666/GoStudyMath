package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.*;

import javax.annotation.processing.FilerException;
import javax.imageio.ImageIO;

import persistence.*;
import java.io.IOException;

import model.events.*;
import model.events.Event;
import model.question.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// GoStudyMath GUI
// REFERENCES:
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextFieldDemoProject/src/components/TextFieldDemo.java
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ScrollDemo2Project/src/components/ScrollDemo2.java
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// https://stackoverflow.com/questions/16295942/java-swing-adding-action-listener-for-exit-on-close

public class NewGooey extends JFrame {
    private static final String JSON_STORE = "./data/questionData.json";
    private DefaultListModel<String> listModel;
    private JList<String> questionList;
    private QuestionList userQuestions;
    private Map<String, Question> stringToQuestion;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String[] QUESTION_TYPES = { "Linear Equations", "Right Triangles" };

    // EFFECTS: Starts the GUI application
    public NewGooey() {
        setTitle("Question Manager");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // List model and JList setup
        // https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
        userQuestions = new QuestionList();
        stringToQuestion = new HashMap<>();

        listModel = new DefaultListModel<>();
        questionList = new JList<>(listModel);
        questionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        display();

        addWindowListener(new WindowAdapter() {
            @Override
            // EFFECTS: Closes the window and prints the eventLog to console.
            public void windowClosing(WindowEvent e) {
                printEvents(EventLog.getInstance());
                e.getWindow().dispose();
                System.exit(0);
            }
        });
    }

    // EFFECTS: Displays the GUI
    @SuppressWarnings("methodlength")
    private void display() {
        JScrollPane scrollPane = new JScrollPane(questionList);

        JButton addButton = new JButton("Add Question");
        addButton.addActionListener(e -> addQuestionText());

        JButton removeButton = new JButton("Remove Question");
        removeButton.addActionListener(e -> removeQuestion());

        JButton averageButton = new JButton("Get Attempt Average");
        averageButton.addActionListener(e -> getAttemptAverage());

        JButton saveButton = new JButton("Save Questions");
        saveButton.addActionListener(e -> saveQuestionList());

        JButton loadButton = new JButton("Load Questions");
        loadButton.addActionListener(e -> loadQuestionList());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(averageButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        try {
            JFrame frame = new JFrame("Welcome to goStudyMath!");
            frame.setSize(600, 600);
            Image myPicture = ImageIO.read(new File("./data/pi.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            frame.add(picLabel);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(this);
            frame.setSize(400, 250);
        } catch (IOException e) {
            System.out.println("failed to find image.");
        }

        // Click Listener
        // (https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html)
        questionList.addMouseListener(new MouseAdapter() {
            @Override
            // EFFECTS: triggers next window based on mouse click
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = questionList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String s = listModel.getElementAt(index);
                        displayQuestion(stringToQuestion.get(s));
                    }
                }
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: Displays gui for adding a specific type of question
    private void addQuestionText() {
        JDialog dialog = new JDialog(this, "Add New Question", true);
        dialog.setLayout(new GridLayout(4, 1));
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);

        JComboBox<String> typeDropdown = new JComboBox<>(QUESTION_TYPES);
        dialog.add(new JLabel("Select Question Type:"));
        dialog.add(typeDropdown);
        JButton confirmButton = new JButton("Add Question");
        confirmButton.addActionListener(e -> {
            String type = (String) typeDropdown.getSelectedItem();
            listModel.addElement(detQuestionType(type));
            dialog.dispose();
        });

        dialog.add(confirmButton);
        dialog.setVisible(true);
    }

    // REQUIRES: type to be one of the valid question types
    // EFFECTS: generates new question; returns desired toString of that type
    @SuppressWarnings("methodlength")
    private String detQuestionType(String type) {
        if (type.equals("Linear Equations")) {
            int a = 999;
            int b = 3;
            int c = 5;
            while ((c - b) % a != 0) {
                a = (int) (1 + Math.random() * 50);
                b = (int) (1 + Math.random() * 50);
                c = (int) (1 + Math.random() * 50);
            }
            Question q = new LinearEquation(a, b, c);
            String qs = q.toString(false);
            userQuestions.addQuestion(q);
            stringToQuestion.put(qs, q);
            return qs;
        }
        if (type.equals("Right Triangles")) {
            int ab = 0;
            int bd = 0;
            while (ab <= bd || ab + bd < 5) {
                ab = (int) (1 + Math.random() * 13);
                bd = (int) (1 + Math.random() * 13);
            }
            Question q = new RightTriangle(ab, bd);
            String qs = q.toString(false);
            userQuestions.addQuestion(q);
            stringToQuestion.put(qs, q);
            return qs;
        }
        // More question types to be added.
        return null; // this theoretically should never be reached, since types are predetermined.
    }

    // MODIFIES: this
    // EFFECTS: Removes a question from the users list of questions
    private void removeQuestion() {
        int selectedIndex = questionList.getSelectedIndex();
        if (selectedIndex != -1) {
            String s = listModel.get(selectedIndex);
            listModel.remove(selectedIndex);
            userQuestions.delQuestion(stringToQuestion.get(s).getQuestionName());
            stringToQuestion.remove(s);
        } else {
            // https://stackoverflow.com/questions/6270354/how-to-open-warning-information-error-dialog-in-swing
            JOptionPane.showMessageDialog(this, "Please select a question to remove.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: opens a new JFrame window that displays the average number of
    // attempts taken in userQuestions
    private void getAttemptAverage() {
        JDialog frame = new JDialog(this, "Average Number of Attempts", true);
        frame.setLayout(new GridLayout(6, 1));
        frame.setLocationRelativeTo(this);
        frame.setSize(400, 250);
        if (!userQuestions.getQuestions().isEmpty()) {
            int avg = userQuestions.getAverageAttempts();
            JLabel avgText = new JLabel("Average Number of Attempts Used per Question: " + avg);
            frame.add(avgText);
            avgText.setVerticalAlignment(SwingConstants.CENTER);
            avgText.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            JLabel avgText = new JLabel("Average Number of Attempts Used per Question: " + 0);
            frame.add(avgText);
            avgText.setVerticalAlignment(SwingConstants.CENTER);
            avgText.setHorizontalAlignment(SwingConstants.CENTER);
        }
        frame.setSize(600, 600);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: saves user questions to file
    private void saveQuestionList() {
        try {
            jsonWriter.open();
            jsonWriter.write(userQuestions);
            jsonWriter.close();
            JDialog dialog = new JDialog(this, "Saving Data", true);
            JOptionPane.showMessageDialog(dialog, "Successfully Saved Questions!");
        } catch (FileNotFoundException e) {
            JDialog dialog = new JDialog(this, "Saving Data", true);
            JOptionPane.showMessageDialog(dialog, "Unable to write to json file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads user questions on file
    public void loadQuestionList() {
        try {
            if (!userQuestions.getQuestions().isEmpty()) {
                userQuestions.getQuestions().clear();
                listModel.removeAllElements();
            }
            userQuestions = jsonReader.read();
            for (int i = 0; i < userQuestions.getQuestions().size(); i++) {
                Question q = userQuestions.getQuestions().get(i);
                String qs = q.toString(false);
                listModel.addElement(qs);
                stringToQuestion.put(qs, q);

            }
            JDialog dialog = new JDialog(this, "Load Data", true);
            JOptionPane.showMessageDialog(dialog, "Successfully loaded questions!");
        } catch (IOException e) {
            JDialog dialog = new JDialog(this, "Load Data", true);
            JOptionPane.showMessageDialog(dialog, "Unable to load .json file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the question (called from double-clicking a question in the
    // list)
    @SuppressWarnings("methodlength")
    private void displayQuestion(Question q) {
        JDialog dialog = new JDialog(this, "Answer Question", true);
        dialog.setLayout(new GridLayout(6, 1));
        dialog.setLocationRelativeTo(this);
        dialog.setSize(1200, 500);
        if (q.getQuestionType().equals("Right Triangles")) {
            dialog.setSize(1600, 500);
        }
        JLabel questionLabel = new JLabel("Question: " + q.toString());
        dialog.add(questionLabel);

        JLabel typeLabel = new JLabel("Type: " + q.getQuestionType());
        dialog.add(typeLabel);

        JLabel attemptsLabel = new JLabel("Attempts: " + q.getNumAttempts());
        dialog.add(attemptsLabel);

        JTextField answerField = new JTextField();
        dialog.add(answerField);

        JPanel buttonPanel = new JPanel();
        JButton checkAnswerButton = new JButton("Check Answer");

        checkAnswerButton.addActionListener(e -> {
            try {
                int answer = Integer.parseInt(answerField.getText());
                if (q.getSolution() == answer) {
                    if (!q.getAnswered()) {
                        q.addAttempt();
                        q.setAnswered();
                        updateQuestion(q);
                    }
                    JOptionPane.showMessageDialog(dialog, "Correct Answer!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "The answer is incorrect!", "Incorrect",
                            JOptionPane.ERROR_MESSAGE);
                    if (!q.getAnswered()) {
                        q.addAttempt();
                    }
                    dialog.dispose();
                    displayQuestion(q);
                }

                // Refresh list display
                // https://stackoverflow.com/questions/46219776/how-do-i-refresh-a-jlist
                questionList.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid integer!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(checkAnswerButton);
        dialog.add(buttonPanel);
        dialog.setVisible(true);
    }

    // REQUIRES: question.toString() (super method) exists in listModel
    // MODIFIES: question, listModel
    // EFFECTS: Updates the list object to reflect the question.
    private void updateQuestion(Question q) {
        for (int i = 0; i < listModel.getSize(); i++) {
            String s = listModel.getElementAt(i);
            if (stringToQuestion.get(s).equals(q)) {
                listModel.setElementAt(q.toString(false), i);
                stringToQuestion.put(q.toString(false), q);
            }
        }
    }

    // EFFECTS: Prints all events from eventLog to console.
    public void printEvents(EventLog el) {
        for (Event e : el) {
            System.out.println(e.toString());
        }
    }

    // Override hashcode and equals
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((listModel == null) ? 0 : listModel.hashCode());
        result = prime * result + ((questionList == null) ? 0 : questionList.hashCode());
        result = prime * result + ((stringToQuestion == null) ? 0 : stringToQuestion.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("methodlength") // needed since you cant modify equals()
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NewGooey other = (NewGooey) obj;
        if (listModel == null) {
            if (other.listModel != null) {
                return false;
            }
        } else if (!listModel.equals(other.listModel)) {
            return false;
        }
        if (questionList == null) {
            if (other.questionList != null) {
                return false;
            }
        } else if (!questionList.equals(other.questionList)) {
            return false;
        }
        if (stringToQuestion == null) {
            if (other.stringToQuestion != null) {
                return false;
            }
        } else if (!stringToQuestion.equals(other.stringToQuestion)) {
            return false;
        }
        return true;
    }
}
