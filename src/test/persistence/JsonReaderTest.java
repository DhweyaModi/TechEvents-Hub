package persistence;


import org.junit.jupiter.api.Test;
import ui.TechEventsHubAppRunner;
import model.Users;
import model.TechEvents;
import model.User;
import model.Event;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
     * Tests the behavior when JSON attempts to read the given file. 
     */
class JsonReaderTest extends JsonTest {

    /**
     * Tests the behavior when attempting to read a non-existent file.
     * 
     * MODIFIES: None.
     * EFFECTS: Asserts that an IOException is thrown when trying to read from a non-existent file.
     */
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected"); // The test should fail if no IOException is thrown
        } catch (IOException e) {
            // Passes as IOException is expected
        }
    }

    /**
     * Tests reading an empty file.
     * 
     * MODIFIES: None.
     * EFFECTS: Asserts that an empty file results in an empty TechEventsHubAppRunner object
     * with zero users and zero events.
     */
    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFile.json");
        try {
            TechEventsHubAppRunner appRunner = reader.read();
            assertNotNull(appRunner); // Ensure appRunner is not null
            assertEquals(0, appRunner.getAppUsers().getNumberOfUsers()); // Ensure no users
            assertEquals(0, appRunner.getAppEvents().getEvents().size()); // Ensure no events
        } catch (IOException e) {
            fail("Couldn't read from file"); // Fail the test if reading from file fails
        }
    }

    /**
     * Tests reading a file with general data, including users and events.
     * 
     * MODIFIES: None.
     * EFFECTS: Asserts that the data read from the file matches the expected values for users and events.
     */
    @Test
    void testReaderGeneralData() {
        JsonReader reader = new JsonReader("./data/testReader.json");
        try {
            TechEventsHubAppRunner appRunner = reader.read();
            assertNotNull(appRunner); // Ensure appRunner is not null

            // Check the user data
            Users users = appRunner.getAppUsers();
            assertEquals(1, users.getNumberOfUsers()); // Ensure there is one user
            User user1 = users.findUser("Alice");
            assertNotNull(user1); // Ensure user "Alice" exists
            assertEquals("Developer", user1.getCategory()); // Ensure user "Alice" has correct category
            assertEquals(1, user1.getEvents().getTotalNumberOfEvents()); // Ensure user has 1 event
            checkEvent("Tech Talk", "Organizer1", user1.getEvents().getEventAtIndex(0)); // Check event details for user

            // Check the tech event data
            TechEvents techEvents = appRunner.getAppEvents();
            assertEquals(2, techEvents.getEvents().size()); // Ensure there are 2 events
            checkEvent("Tech Talk", "Organizer1", techEvents.getEvents().get(0)); // Check first event details
            checkEvent("Hackathon", "Organizer2", techEvents.getEvents().get(1)); // Check second event details

        } catch (IOException e) {
            fail("Couldn't read from file"); // Fail the test if reading from file fails
        }
    }

    /**
     * Helper method to check event details.
     * 
     * MODIFIES: None.
     * EFFECTS: Asserts that the event name and organizer match the expected values.
     */
    private void checkEvent(String expectedName, String expectedOrganizer, Event event) {
        assertEquals(expectedName, event.getName()); // Check if the event name matches
        assertEquals(expectedOrganizer, event.getOrganizer()); // Check if the event organizer matches
    }
}
