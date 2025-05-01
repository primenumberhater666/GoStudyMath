
package persistence;

import model.question.*;
import org.json.JSONObject;

import java.io.*;

// Represents a JSON writer that writes a representation of a Question variant to file
// CREDIT: JsonSerializationDemo found in EdX Project Phase 2
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructor for writer that writes to destination file

    public JsonWriter(String dest) {
        this.destination = dest; 
    }
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of QuestionList to file
    public void write(QuestionList ql) {
        JSONObject json = ql.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
