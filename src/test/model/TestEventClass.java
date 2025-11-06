package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestEventClass {

    Event event1;
    Event event2;
    Event event3;
    Event event4;

    @BeforeEach
    void runBefore() {
        event1 = new Event("Hackathon", 3);
        event2 = new Event("Workshop", "Tech Org", 5);
        event3 = new Event("Seminar", "Edu Org", 7, "http://seminar.com");
        event4 = new Event("Conference", "Biz Org", 10, "10:00 AM", "http://conference.com");
    }

    @Test
    void testEventConstructorWithNameAndDay() {
        assertEquals("Hackathon", event1.getName());
        assertEquals(3, event1.getDay());
        assertEquals("Not Provided", event1.getOrganizer());
        assertEquals("Not Provided", event1.getTime());
        assertEquals("Not Provided", event1.getUrl());
    }

    @Test
    void testEventConstructorWithNameOrganizerAndDay() {
        assertEquals("Workshop", event2.getName());
        assertEquals(5, event2.getDay());
        assertEquals("Tech Org", event2.getOrganizer());
        assertEquals("Not Provided", event2.getTime());
        assertEquals("Not Provided", event2.getUrl());
    }

    @Test
    void testEventConstructorWithNameOrganizerDayAndUrl() {
        assertEquals("Seminar", event3.getName());
        assertEquals(7, event3.getDay());
        assertEquals("Edu Org", event3.getOrganizer());
        assertEquals("Not Provided", event3.getTime());
        assertEquals("http://seminar.com", event3.getUrl());
    }

    @Test
    void testEventConstructorWithAllFields() {
        assertEquals("Conference", event4.getName());
        assertEquals(10, event4.getDay());
        assertEquals("Biz Org", event4.getOrganizer());
        assertEquals("10:00 AM", event4.getTime());
        assertEquals("http://conference.com", event4.getUrl());
    }

    @Test
    void testSetOrganizer() {
        event1.setOrganizer("New Org");
        assertEquals("New Org", event1.getOrganizer());
    }

    @Test
    void testSetTime() {
        event1.setTime("2:00 PM");
        assertEquals("2:00 PM", event1.getTime());
    }

    @Test
    void testSetUrl() {
        event1.setUrl("http://hackathon.com");
        assertEquals("http://hackathon.com", event1.getUrl());
    }
    
    @Test
    void testGetName() {
        assertEquals("Hackathon", event1.getName());
    }

    @Test
    void testGetDay() {
        assertEquals(3, event1.getDay());
    }

    @Test
    void testGetOrganizer() {
        assertEquals("Not Provided", event1.getOrganizer());
    }

    @Test
    void testGetTime() {
        assertEquals("Not Provided", event1.getTime());
    }

    @Test
    void testGetUrl() {
        assertEquals("Not Provided", event1.getUrl());
    }

    @Test
    void testIsInRange() {
        // Event 1: day = 3
        assertTrue(event1.isInRange(1, 3));  // 3 is within the range 1-3
        assertTrue(event1.isInRange(3, 5)); // 3 is within the range 3-5
        assertFalse(event1.isInRange(4, 10)); // 3 is not within the range 4-10

        // Event 4: day = 10
        assertTrue(event4.isInRange(5, 10)); // 10 is within the range 5-10
        assertTrue(event4.isInRange(10, 15)); // 10 is within the range 10-15
        assertFalse(event4.isInRange(1, 9));  // 10 is not within the range 1-9
    }

    @Test
    void testIsInRangeEdgeCases() {
        // Edge cases for event2: day = 5
        assertTrue(event2.isInRange(5, 5)); // Single day range where min == max == event day
        assertFalse(event2.isInRange(6, 6)); // Single day range outside event day

        // Edge cases for event3: day = 7
        assertTrue(event3.isInRange(7, 7)); // Single day range where min == max == event day
        assertFalse(event3.isInRange(8, 10)); // Single day range outside event day
    }

    @Test
    void testIsCorrectEvent() {
        // Test when the name matches
        assertTrue(event1.isCorrectEvent("Hackathon"));
        assertTrue(event2.isCorrectEvent("Workshop"));
        
        // Test when the name does not match
        assertFalse(event1.isCorrectEvent("Seminar"));
        assertFalse(event2.isCorrectEvent("Conference"));
    }




}
