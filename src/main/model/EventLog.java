package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class EventLog implements Iterable<EventTracker> {

    /** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<EventTracker> events;

    /**
	 * Prevent external construction.
	 * (Singleton Design Pattern).
	 */
    private EventLog() {
        events = new ArrayList<EventTracker>();
    }

    /**
	 * Gets instance of EventLog - creates it
	 * if it doesn't already exist.
	 * (Singleton Design Pattern)
	 * 
	 * @return instance of EventLog
	 */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }


    /**
	 * Adds an event to the event log.
	 * 
	 * @param e the event to be added
	 */
    public void logEvent(EventTracker e) {
        events.add(e);
    }


    /**
	 * Clears the event log and logs the event.
	 */
    public void clear() {
        events.clear();
        logEvent(new EventTracker("Event log cleared."));
    }


    @Override 
    public Iterator<EventTracker> iterator() {
        return events.iterator();
    }

    /**
	 * Returns a string representation of the events logged so far, for printing.
	 */
    public String toString() {
        String str = "";
        for (EventTracker e: theLog) {
            str += e.toString();
            str += "\n";
        }
        return str;
    }

}
