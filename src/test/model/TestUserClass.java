package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestUserClass {

    User user1;
    TechEvents events;
    Event event;

    @BeforeEach
    void runBefore() {
        user1 = new User("Dhweya", "Student");
        events = new TechEvents();
        event = new Event("Event1", 12);

    }
    
    @Test
    void testUsersConstructor() {
        assertEquals("Dhweya", user1.getName());
        assertEquals("Student", user1.getCategory());
    }

    @Test
    void testGetName() {
        assertEquals("Dhweya", user1.getName());
    }

    @Test
    void testGetCategory() {
        assertEquals("Student", user1.getCategory());
    }

    @Test
    void testSetNewName() {
        assertEquals("Dhweya", user1.getName());
        user1.setNewName("DM");
        assertEquals("DM", user1.getName());

    }

    @Test
    void testGetEvents() {
        assertEquals(events.getTotalNumberOfEvents(), user1.getEvents().getTotalNumberOfEvents());
        //objects will obviously be different, so look at their test sizes. 
    }

    @Test
    void testAddEvent() {
        assertEquals(events.getTotalNumberOfEvents(), user1.getEvents().getTotalNumberOfEvents());
        user1.addEvent(event);
        events.addEvent(event);
        assertEquals(events.getTotalNumberOfEvents(), user1.getEvents().getTotalNumberOfEvents());

        // Additional check to ensure the event was added.
        assertTrue(user1.getEvents().getEvents().contains(event));
    }


    @Test
    void testGetStringOfEventsInCalendar() {
        user1.addEvent(event);
        String expected = event.toString() + "\n"; // assuming Event's toString() is well-defined.
        assertEquals(expected, user1.getStringOfEventsInCalender());
    }

    @Test
    void testIsRightUser() {
        assertTrue(user1.isRightUser("Dhweya"));
        assertTrue(!user1.isRightUser("WrongName"));
    }


}
