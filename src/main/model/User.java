package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a user in the system who is associated with tech events.
 * A user has a unique name, a category (such as "Student", "Staff", or "Organizer"), 
 * and a list of tech events they are involved with.
 */

public class User implements Writable {

    private String name;
    private String category;
    private TechEvents events;



    // Requires - category can only be of "Student", "Staff", "Organizer" type. Name must be unique.
    // Modifies - this
    // Effects - Creates a new user with given name and type and with an empty list of events. 
    public User(String name, String category) {
        this.name = name;
        this.category = category;
        events = new TechEvents();

    }

    public String getName() {
        return name;
    }

    public void setNewName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public TechEvents getEvents() {
        return events;
    }

    /**
     * Effects: Returns a string representation of all the events in the user's calendar.
     */
    public String getStringOfEventsInCalender() {
        String s = "";
        for (Event e: events.getEvents()) {
            s += e.toString();
            s += "\n";
        }
        return s;
    }

    /**
     * Requires - event is not already in the list.
     * Modifies - this
     * Effects - Adds the given event to the user's list of events.
     */
    public  void addEvent(Event e) {
        events.addEvent(e);
        EventLog.getInstance().logEvent(
					new EventTracker("Added event to user's calendar: User - " + getName() 
                    + " | Event - " + e.getName()));
    }

    /**
     * Requires - name must match the user's name exactly.
     * Effects - Checks if the given name matches the user's name.
     * Modifies - this (no modification).
     */
    public boolean isRightUser(String name) {
        return (this.name).equals(name);
    }

    /**
     * Modifies: none.
     * Effects: Returns a JSONObject representation of the User object. 
     *          The JSON object contains the user's name, category, and the JSON representation 
     *          of their associated events. The events are converted using the `toJson()` method 
     *          of the `TechEvents` class.
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("events", events.toJson());
        return json;
    }

}
