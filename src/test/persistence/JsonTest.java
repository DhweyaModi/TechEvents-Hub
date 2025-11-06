package persistence;

import model.Event;
import model.TechEvents;
import model.User;
import model.Users;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A helper class for JSON testing. Provides methods to check the correctness of 
 * event, user, and tech event data during testing. These methods validate the 
 * attributes of users, events, and tech events against expected values.
 */
public class JsonTest {

    /**
     * Asserts that the event's name, organizer, day, time, and URL match expected values.
     * 
     * @param name - expected event name
     * @param organizer - expected event organizer
     * @param day - expected event day
     * @param time - expected event time
     * @param url - expected event URL
     * @param event - the event object to be checked
     */
    protected void checkEvent(String name, String organizer, int day, String time, String url, Event event) {
        assertEquals(name, event.getName());
        assertEquals(organizer, event.getOrganizer());
        assertEquals(day, event.getDay());
        assertEquals(time, event.getTime());
        assertEquals(url, event.getUrl());
    }

    /**
     * Asserts the total number of events in TechEvents matches expected value.
     * 
     * @param numberOfEvents - expected total number of events
     * @param techEvents - TechEvents object to be checked
     */
    protected void checkTechEvents(int numberOfEvents, TechEvents techEvents) {
        assertEquals(numberOfEvents, techEvents.getTotalNumberOfEvents());
    }

    /**
     * Asserts that the user's name and category match expected values.
     * 
     * @param name - expected user name
     * @param category - expected user category
     * @param user - the user object to be checked
     */
    protected void checkUser(String name, String category, User user) {
        assertEquals(name, user.getName());
        assertEquals(category, user.getCategory());
    }

    /**
     * Asserts the total number of users in Users matches expected value.
     * 
     * @param numberOfUsers - expected total number of users
     * @param users - Users object to be checked
     */
    protected void checkUsers(int numberOfUsers, Users users) {
        assertEquals(numberOfUsers, users.getNumberOfUsers());
    }
}
