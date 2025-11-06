package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

class TestEventLog {
    private EventLog eventLog;
    private EventTracker event1;
    private EventTracker event2;

    @BeforeEach
    void setUp() {
        eventLog = EventLog.getInstance();
        eventLog.clear(); // Ensures each test starts with a fresh log
        event1 = new EventTracker("Test Event 1");
        event2 = new EventTracker("Test Event 2");
    }

    @Test
    void testSingletonInstance() {
        EventLog anotherInstance = EventLog.getInstance();
        assertSame(eventLog, anotherInstance, "EventLog should be a singleton");
    }

    @Test
    void testLogEvent() {
        eventLog.logEvent(event1);
        eventLog.logEvent(event2);
        
        Iterator<EventTracker> iterator = eventLog.iterator();
        assertTrue(iterator.hasNext());

    }

    @Test
    void testClear() {
        eventLog.logEvent(event1);
        eventLog.clear();
        
        Iterator<EventTracker> iterator = eventLog.iterator();
        assertTrue(iterator.hasNext()); // "Event log cleared." event should be present
        assertEquals("Event log cleared.", iterator.next().getDescription());
        assertFalse(iterator.hasNext()); // No other events should remain
    }
    
    @Test
    void testToString() {
        eventLog.logEvent(event1);
        eventLog.logEvent(event2);
    
        String logString = eventLog.toString();
    
        // Ensure descriptions exist in order, ignoring timestamps
        assertTrue(logString.contains("Event log cleared."), "Output should contain 'Event log cleared.' event.");
        assertTrue(logString.contains(event1.getDescription()), "Output should contain the first event description.");
        assertTrue(logString.contains(event2.getDescription()), "Output should contain the second event description.");
    
        // Ensure events are in order
        int indexCleared = logString.indexOf("Event log cleared.");
        int indexEvent1 = logString.indexOf(event1.getDescription());
        int indexEvent2 = logString.indexOf(event2.getDescription());
    
        assertTrue(indexCleared < indexEvent1, "Clear event should come before event1.");
        assertTrue(indexEvent1 < indexEvent2, "Event1 should come before event2.");
    }
}
