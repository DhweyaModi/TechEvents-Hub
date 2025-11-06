package persistence;

import ui.TechEventsHubAppRunner;

import org.json.JSONObject;

import java.io.*;

/**
 * Represents a writer that writes the JSON representation of the workroom to a file.
 * This class facilitates writing data into a file using JSON format.
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    /**
     * Constructs a JsonWriter to write to the specified destination file.
     *
     * @param destination the path to the file where data will be written
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /**
     * Modifies: this (opens the writer)
     * Effects: Opens the writer to write data to the specified destination file.
     *          Throws a FileNotFoundException if the destination file cannot be opened for writing.
     */
    public void open() throws FileNotFoundException {
        // Create a PrintWriter object to write to the file at the specified destination
        writer = new PrintWriter(new File(destination));
    }

    /**
     * Modifies: this (writes data to the file)
     * Effects: Converts the TechEventsHubAppRunner object into JSON representation 
     *          and writes it to the destination file.
     *
     * @param appRunner the TechEventsHubAppRunner object to be converted and written
     */
    public void write(TechEventsHubAppRunner appRunner) {
        // Convert the appRunner object into a JSONObject
        JSONObject json = appRunner.toJson();
        // Save the generated JSON to the file
        saveToFile(json.toString(TAB));
    }

    /**
     * Modifies: this (closes the writer)
     * Effects: Closes the writer to stop writing to the file.
     */
    public void close() {
        // Close the PrintWriter to finish the writing process
        writer.close();
    }

    /**
     * Modifies: this (writes the string to the file)
     * Effects: Writes the given string to the file.
     * 
     * @param json the string to be written to the file
     */
    private void saveToFile(String json) {
        // Write the provided JSON string to the file
        writer.print(json);
    }
}
