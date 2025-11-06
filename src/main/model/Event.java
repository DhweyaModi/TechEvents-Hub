package model;

import org.json.JSONObject;
import persistence.Writable;



/**
 * Represents an event with details such as its name, organizer, day, time, and URL.
 * 
 * The class provides functionality to create and manage events, including methods
 * to check if an event matches certain criteria and to retrieve or modify its attributes.
 */

public class Event implements Writable {
    private String name;
    private String organizer;
    private int day;
    private String time;
    private String url;

    //CAN DELETE. 
    public Event(String name, int day) {
        this.name = name;
        this.organizer = "Not Provided";
        this.day = day;
        this.time = "Not Provided";
        this.url = "Not Provided";
    }

    //CAN DELETE.
    public Event(String name, String organizer, int day) {
        this.name = name;
        this.organizer = organizer;
        this.day = day;
        this.time = "Not Provided";
        this.url = "Not Provided";
    }

    //CAN DELETE.
    public Event(String name, String organizer, int day, String url) {
        this.name = name;
        this.organizer = organizer;
        this.day = day;
        this.time = "Not Provided";
        this.url = url;
    }

    /**
     * Requires: name is unique and day >= 0.
     * Modifies: this.
     * Effects: Initializes the Event with the specified name and day, 
     *          and sets other fields to default values.
     */
    public Event(String name, String organizer, int day, String time, String url) {
        this.name = name;
        this.organizer = organizer;
        this.day = day;
        this.time = time;
        this.url = url;

    }

    /**
     * Requires: name is not null.
     * Modifies: none.
     * Effects: Returns true if the event's name matches the given name; false otherwise.
     */
    public boolean isCorrectEvent(String name) {
        return this.name.equals(name);
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public int getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }


    // Requires - 0 <= min, max <= 365
    // Modifies - none. 
    // Effects - returns true if the given event is in range. 
    public boolean isInRange(int min, int max) {
        return day >= min && day <= max;
    }

    // Effects - Returns a formatted string representation of the event.
    public String toString() {
        return "[" + getName() + " : Day " + getDay() + "]";
    }

    /**
     * Modifies: none.
     * Effects: Returns a JSONObject representation of the event. 
     *          The JSON object includes the event's name, organizer, day, time, and URL as key-value pairs.
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("organizer", organizer);
        json.put("day", day);
        json.put("time", time);
        json.put("url", url);
        return json;
    }
    

}
