package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class TestTechEventsClass {

    TechEvents events;
    Event event1;
    Event event2;
    Event event3;
    Event event4;
    List<Event> list;

    @BeforeEach
    void runBefore() {
        list = new ArrayList<>();
        events = new TechEvents();
        event1 = new Event("Hackathon", 3);
        event2 = new Event("Workshop", 5);
        event3 = new Event("Seminar", 7);
        event4 = new Event("Conference", 10);

    }

    @Test
    void testUsersConstructor() {
        assertEquals(0, events.getTotalNumberOfEvents());
        assertEquals(0, events.getEvents().size());
    }

    @Test
    void testGetEvents() {
        assertEquals(0, events.getTotalNumberOfEvents());
        events.addEvent(event1);
        assertEquals(1, events.getEvents().size());
    }

    @Test
    void testAddEvent() {
        assertEquals(0, events.getTotalNumberOfEvents());
        events.addEvent(event1);
        assertEquals(1, events.getTotalNumberOfEvents());
        assertEquals(1, events.getEvents().size());

        //makes sure that the event was added. 
        assertTrue(events.getEvents().contains(event1));
    }

    @Test
    void testGetTotalNumberOfEvents() {
        assertEquals(0, events.getTotalNumberOfEvents());
        events.addEvent(event1);
        assertEquals(1, events.getTotalNumberOfEvents());
        Event event2 = new Event("Event2", 2);
        events.addEvent(event2);
        assertEquals(2, events.getTotalNumberOfEvents());
    }

    @Test
    void testFindEventsInRange() {
        TechEvents events = new TechEvents();
        Event e1 = new Event("Hackathon", 3);
        Event e2 = new Event("Workshop", 5);
        Event e3 = new Event("Seminar", 10);

        events.addEvent(e1);
        events.addEvent(e2);
        events.addEvent(e3);

        List<Event> range1 = events.findEventsInRange(1, 4);
        assertEquals(1, range1.size());
        assertTrue(range1.contains(e1));

        List<Event> range2 = events.findEventsInRange(5, 10);
        assertEquals(2, range2.size());
        assertTrue(range2.contains(e2));
        assertTrue(range2.contains(e3));

        List<Event> range3 = events.findEventsInRange(11, 15);
        assertEquals(0, range3.size());
    }

    @Test
    void testFindEventWithGivenName() {
        events.addEvent(event1);
        events.addEvent(event2);

        // Test finding an event by name
        assertEquals(event1, events.findEventWithGivenName("Hackathon"));
        assertEquals(event2, events.findEventWithGivenName("Workshop"));

        // Test when event is not found
        assertEquals(null, events.findEventWithGivenName("Non-Existent Event"));
    }

    @Test
    void testRemoveEvent() {
        events.addEvent(event1);
        events.addEvent(event2);
        events.addEvent(event3);

        assertEquals(3, events.getTotalNumberOfEvents());

        // Test removing an existing event
        assertTrue(events.removeEvent("Workshop"));
        assertEquals(2, events.getTotalNumberOfEvents());
        assertEquals(null, events.findEventWithGivenName("Workshop"));
        
        // Test removing another existing event
        assertTrue(events.removeEvent("Hackathon"));
        assertEquals(1, events.getTotalNumberOfEvents());
        assertEquals(null, events.findEventWithGivenName("Hackathon"));

        // Test removing a non-existent event
        assertFalse(events.removeEvent("Non-Existent Event"));
        assertEquals(1, events.getTotalNumberOfEvents());
    }
}




