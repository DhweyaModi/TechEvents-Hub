package ui;

import org.json.JSONObject;
import persistence.Writable;

//import java.util.ArrayList;
import java.util.List;

import model.TechEvents;
import model.Event;
import model.Users;
import model.User;
import model.EventLog;

/**
 * TechEventsHubAppRunner is responsible for managing the events and users within the TechEventsHub application.
 * It provides functionality for adding events, viewing events, finding users, and managing user calendars.
 * This class acts as the backend for the application, storing data and interacting with the users and events.
 */

public class TechEventsHubAppRunner implements Writable {
    private TechEvents appEvents;
    private Users appUsers;

    /**
     * Constructor for TechEventsHubAppRunner.
     * 
     * Requires: None.
     * Modifies: appEvents, appUsers.
     * Effects: Initializes the appEvents and appUsers objects to manage events and users.
     */
    public TechEventsHubAppRunner() {
        appEvents = new TechEvents();
        appUsers = new Users();
    }

    /**
     * Constructor for TechEventsHubAppRunner. Used for loading an existing instance.
     * 
     * Requires: appEvents and appUsers are valid and correctly set up.
     * Modifies: appEvents, appUsers.
     * Effects: Initializes the appEvents and appUsers objects to manage events and users.
     */
    public TechEventsHubAppRunner(TechEvents appEvents, Users appUsers) {
        this.appEvents = appEvents;
        this.appUsers = appUsers;
    }

    // Requires: day > 0
    // Modifies: this.appEvents
    // Effects: Creates an Event with the specified name, organizer, and day, and adds it to appEvents.
    public void addEvent(String name, String organizer, int day, String time, String url) {
        Event event = new Event(name, organizer, day, time, url);
        appEvents.addEvent(event);
        appEvents.logEventAddition(event);
        
    }

    /**
     * Requires: name (String) of the user must be unique, category (String) must be valid.
     * Modifies: this.appUsers.
     * Effects: Creates a new User object with the specified name and category, 
     * and adds it to the list of users managed by appUsers. 
     * Returns the created User object.
     */
    public User addUser(String name, String category) {
        User u = new User(name, category);
        appUsers.addUser(u);
        return u;
    }

    /**
     * Requires: None.
     * Modifies: None.
     * Effects: Displays all events in appEvents. 
     * If no events exist, a message indicating there are no events is displayed.
     */
    public void viewEvents() {
        List<Event> listOfEvents = appEvents.getEvents();
        if (listOfEvents.isEmpty()) {
            System.out.println("There are no events!");
        } else {
            for (Event e: listOfEvents) {
                System.out.println(e);
            }
        }
        
    }

    /**
     * Requires: None.
     * Modifies: None.
     * Effects: Returns all events in appEvents in String format. 
     * If no events exist, a message indicating there are no events is displayed.
     */
    public String returnPostedEvents() {
        String s = "";
        List<Event> listOfEvents = appEvents.getEvents();
        if (listOfEvents.isEmpty()) {
            System.out.println("There are no events!");
        } else {
            for (Event e: listOfEvents) {
                s += e.toString() + "\n";
            }
        }
        return s;
    }

    /**
     * Requires: u (User object) is a valid User. 
     * Modifies: None.
     * Effects: Returns a string representation of all events in the specified user's 
     * calendar by calling the getStringOfEventsInCalender method of User.
     */
    public String viewEventsInUserCalender(User u) {
        return u.getStringOfEventsInCalender();
    }

    /**
     * Requires: name (String) of the user must be unique.
     * Modifies: None.
     * Effects: Searches for and returns a User object with the specified name. If no user is found, makes the user.
     */
    public User findUser(String name) {
        User currentUser = appUsers.findUser(name);
        if (currentUser == null) {
            currentUser = new User(name, "Student");
            appUsers.addUser(currentUser);
        }
        return currentUser;
    }

    /**
     * Requires: name (String) of the event must be unique.
     * Modifies: None.
     * Effects: Searches for and returns an Event object with the specified name. If no event is found, returns null.
     */
    public Event findEvent(String name) {
        Event eventWanted = appEvents.findEventWithGivenName(name);
        return eventWanted;
    }

    /**
     * Requires: Event and User are part of appEvents and appUsers.
     * Modifies: s (User object).
     * Effects: Adds the specified Event object to the calendar of the specified User.
     */
    public void addEventToStudent(Event e, User s) {
        s.addEvent(e);
    }


    /**
     * Requires: min and max are valid day values (0 <= min, max <= 365).
     * Modifies: None.
     * Effects: Returns a string representation of all events within the specified day range.
     */
    public String returnFilteredEventsByDayRange(int min, int max) {
        List<Event> filteredEvents = appEvents.findEventsInRange(min, max);
        StringBuilder filteredEventsString = new StringBuilder();

        if (filteredEvents.isEmpty()) {
            filteredEventsString.append("No events found in the specified range.");
        } else {
            for (Event e : filteredEvents) {
                filteredEventsString.append(e.toString()).append("\n");
            }
        }

        return filteredEventsString.toString();
    }

    /**
     * Requires: name (String) of the event must be unique.
     * Modifies: this.appEvents.
     * Effects: Removes the event with the specified name from appEvents.
     *          Returns true if the event was found and removed, false otherwise.
     */
    public boolean removeEvent(String name) {
        return appEvents.removeEvent(name);
    }
    

    /**
     * Converts the TechEventsHubAppRunner object to a JSON representation.
     * 
     * @return a JSON object representing the appEvents and appUsers.
     * 
     * MODIFIES: None.
     * EFFECTS: Returns a JSONObject for appEvents and appUsers.
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("appEvents", appEvents.toJson());
        json.put("appUsers", appUsers.toJson());
        return json;
    }

    public TechEvents getAppEvents() {
        return appEvents;
    }

    public Users getAppUsers() {
        return appUsers;
    }

    public String getLog() {
        return EventLog.getInstance().toString();
    }

    
}
