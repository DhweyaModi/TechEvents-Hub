package model;



import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;





/**
 * Represents a collection of technical events and provides methods to
 * manage and query events based on various criteria.
 */

public class TechEvents implements Writable {
    private List<Event> listOfEvents;

    /**
     * Constructs an empty list of events.
     *
     * Requires: none.
     * Modifies: this.
     * Effects: Initializes an empty list of events.
     */
    public TechEvents() {
        listOfEvents = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return listOfEvents;
    }

    // Modifies - this.
    // Effects - Adds a new event to the listOfEvents;
    public void addEvent(Event e) {
        listOfEvents.add(e);
        
    }

    // Modifies - EventLog
    // Effects - Logs the specific event addition to the calender. 
    public void logEventAddition(Event e) {
        EventLog.getInstance().logEvent(
					new EventTracker("Added event to general calendar: " 
                    + e.getName() + " on Day " + e.getDay()));
    }

    // Effects - returns the total number of events;
    public int getTotalNumberOfEvents() {
        return listOfEvents.size();
    }

    // Requires - 0 <= x < size;
    // Effects - returns the event at given index.
    public Event getEventAtIndex(int x) {
        return listOfEvents.get(x);
    }

    // Requires - 0 <= min, max <= 365
    // Modifies - none.
    // Effect - returns a list of events that are in the date range. 
    public List<Event> findEventsInRange(int min, int max) {
        List<Event> eventsInRange = new ArrayList<>();
        for (Event e: listOfEvents) {
            if (e.isInRange(min, max)) {
                eventsInRange.add(e);
            }
        }
        return eventsInRange;
    }

    // Modifies - this.
    // Effects - Removes an event from the listOfEvents by its name.
    public boolean removeEvent(String name) {
        Event eventToRemove = findEventWithGivenName(name);
        if (eventToRemove != null) {
            listOfEvents.remove(eventToRemove);
            EventLog.getInstance().logEvent(
					new EventTracker("Removed event from calendar: " + eventToRemove.getName() 
                        + " from Day " + eventToRemove.getDay()));
            return true; // Event was found and removed
        }
        return false; // Event was not found
    }

    /**
     * Finds an event by its name.
     * Effects: Returns the event with the specified name, or null if no such event exists.
     */
    public Event findEventWithGivenName(String name) {
        for (Event e: listOfEvents) {
            if (e.isCorrectEvent(name)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Modifies: none.
     * Effects: Returns a JSONObject representation of the TechEvents object. 
     *          The JSON object contains a key "listofevents", which maps to a JSON array 
     *          containing the JSON representation of each event in the listOfEvents.
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listofevents", this.eventsToJson());
        return json;
    }

    /**
     * Converts the list of events to a JSON array.
     * Effects: Returns a JSON array representation of the events in this TechEvents object.
     */
    private JSONArray eventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event e : listOfEvents) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

}
