package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEventTracker {
    private EventTracker event1;
    //private EventEventTracker event2;
    private EventTracker event3;
    private EventTracker eventDifferentDescription;
    private EventTracker eventDifferentDate;

    @BeforeEach
    void setUp() {
        //Date fixedDate = new Date(); // Use the same date for controlled testing
        event1 = new EventTracker("Test event");
        //event2 = new EventEventTracker("Test event");
        event3 = event1; // Reference to the same object
        
        eventDifferentDescription = new EventTracker("Different event");

        // Simulate a different timestamp (adding a delay)
        try {
            Thread.sleep(10); // Ensure different timestamps
        } catch (InterruptedException ignored) {
            //expected. 
        }

        eventDifferentDate = new EventTracker("Test event");
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(event1, event3, "An object should be equal to itself.");
    }

    @Test
    void testEqualsDifferentDescriptions() {
        assertNotEquals(event1, eventDifferentDescription, "Events with different descriptions should not be equal.");
    }

    @Test
    void testEqualsDifferentDates() {
        assertNotEquals(event1, eventDifferentDate, "Events with different timestamps should not be equal.");
    }

    @Test
    void testEqualsNullObject() {
        assertNotEquals(event1, null, "An event should not be equal to null.");
    }

    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(event1, "Test event", "An EventEventTracker should not be equal to a different class.");
    }

    @Test
    void testHashCodeConsistency() {
        int hashCode1 = event1.hashCode();
        int hashCode2 = event1.hashCode();
        assertEquals(hashCode1, hashCode2, "Hash code should be consistent for the same object.");
    }

    @Test
    void testHashCodeEqualObjects() {
        // This is tricky since timestamps are different
        assertNotEquals(event1.hashCode(), eventDifferentDate.hashCode(), 
                "Events with different timestamps should have different hash codes.");
    }

    @Test
    void testHashCodeDifferentObjects() {
        assertNotEquals(event1.hashCode(), eventDifferentDescription.hashCode(), 
                "Events with different descriptions should have different hash codes.");
    }

    @Test
    void testToString() {
        String expected = event1.getDate().toString() + "\n" + event1.getDescription();
        assertEquals(expected, event1.toString(), "toString should return the correct format.");
    }


}
