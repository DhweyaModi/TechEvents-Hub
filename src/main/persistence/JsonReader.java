package persistence;

import model.Event;
import model.TechEvents;
import model.User;
import model.Users;
import ui.TechEventsHubAppRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.*;

/**
 * Utility class for reading JSON files and converting them into application objects.
 */
public class JsonReader {

    private final String source;

    /**
     * Constructs a JsonReader to read from the specified source file.
     *
     * @param source the path to the JSON file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /**
     * Modifies: none.
     * Effects: Reads the content of the JSON file at the specified source path, 
     *          parses the JSON data to extract users and events, 
     *          and returns a TechEventsHubAppRunner object containing 
     *          populated Users and TechEvents objects.
     *          Throws an IOException if there is an error reading the file.
     */
    public TechEventsHubAppRunner read() throws IOException {
        // Read all bytes from the file and convert to a string
        String content = new String(Files.readAllBytes(Paths.get(source)), StandardCharsets.UTF_8);
        // Parse the string content into a JSONObject
        JSONObject jsonObject = new JSONObject(content);

        // Parse the users from the JSON object
        Users users = parseUsers(jsonObject.getJSONObject("appUsers"));
        // Parse the events from the JSON object
        TechEvents techEvents = parseEvents(jsonObject.getJSONObject("appEvents"));

        // Create and populate the TechEventsHubAppRunner with parsed data
        TechEventsHubAppRunner appRunner = new TechEventsHubAppRunner(techEvents, users);
        return appRunner;
    }

    /**
     * Modifies: none.
     * Effects: Parses the "appUsers" section of the JSON object and creates a Users object 
     *          populated with User objects and their associated events. 
     *          Returns the populated Users object.
     */
    private Users parseUsers(JSONObject jsonObject) {
        // Create a new Users object to store all the parsed users
        Users users = new Users();
        // Extract the "users" array from the JSON object
        JSONArray usersArray = jsonObject.getJSONArray("users");

        // Iterate through each user in the array
        for (int i = 0; i < usersArray.length(); i++) {
            // Get the current user JSON object
            JSONObject userJson = usersArray.getJSONObject(i);
            // Parse the user and add them to the Users object
            User user = parseUser(userJson);
            users.addUser(user);
        }

        // Return the populated Users object
        return users;
    }

    /**
     * Requires: jsonObject contains a valid "name", "category", and "events" field.
     * Modifies: none.
     * Effects: Parses a single user from the provided JSONObject, extracting the name, category, 
     *          and associated events. Returns the created User object.
     */
    private User parseUser(JSONObject jsonObject) {
        // Extract the name and category from the JSON object
        String name = jsonObject.getString("name");
        String category = jsonObject.getString("category");
        // Create a new User object with the parsed name and category
        User user = new User(name, category);

        // Parse the events for this user (if any)
        JSONObject eventsObject = jsonObject.getJSONObject("events");
        TechEvents userTechEvents = parseEvents(eventsObject);

        // Add the parsed events to the user's event list
        for (Event event : userTechEvents.getEvents()) {
            user.addEvent(event);
        }

        // Return the populated User object
        return user;
    }

    /**
     * Modifies: none.
     * Effects: Parses the "appEvents" section of the JSON object and creates a TechEvents object 
     *          populated with Event objects. Returns the populated TechEvents object.
     */
    private TechEvents parseEvents(JSONObject jsonObject) {
        // Create a new TechEvents object to store all the parsed events
        TechEvents techEvents = new TechEvents();
        // Extract the "listofevents" array from the JSON object
        JSONArray eventsArray = jsonObject.getJSONArray("listofevents");

        // Iterate through each event in the array
        for (int i = 0; i < eventsArray.length(); i++) {
            // Get the current event JSON object
            JSONObject eventJson = eventsArray.getJSONObject(i);
            // Parse the event and add it to the TechEvents object
            Event event = parseEvent(eventJson);
            techEvents.addEvent(event);
        }

        // Return the populated TechEvents object
        return techEvents;
    }

    /**
     * Requires: jsonObject contains valid "name", "organizer", "day", "time", and "url" fields.
     * Modifies: none.
     * Effects: Parses a single event from the provided JSONObject, extracting the event's name, 
     *          organizer, day, time, and URL. Returns the created Event object.
     */
    private Event parseEvent(JSONObject jsonObject) {
        // Extract the name, organizer, day, time, and URL fields from the JSON object
        String name = jsonObject.getString("name");
        String organizer = jsonObject.getString("organizer");
        int day = jsonObject.getInt("day");
        String time = jsonObject.getString("time");
        String url = jsonObject.getString("url");

        // Create and return a new Event object with the extracted data
        return new Event(name, organizer, day, time, url);
    }
}
