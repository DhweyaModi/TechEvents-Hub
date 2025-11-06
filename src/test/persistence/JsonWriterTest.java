package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TechEventsHubAppRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the JsonWriter class. Tests include writing and reading data 
 * to and from a JSON file and verifying correct behavior with file creation and 
 * data persistence.
 */
public class JsonWriterTest {

    private static final String TEST_FILE = "./data/testWriter.json";
    private JsonWriter writer;
    private TechEventsHubAppRunner appRunner;

    /**
     * Sets up a new instance of JsonWriter and TechEventsHubAppRunner before each test.
     * Adds sample events and users to the appRunner.
     */
    @BeforeEach
    public void setUp() {
        writer = new JsonWriter(TEST_FILE);
        appRunner = new TechEventsHubAppRunner();
        appRunner.addEvent("Tech Talk 2025", "UBC Tech Club", 5, "14:00", "http://techtalk2025.com");
        appRunner.addEvent("AI Workshop", "AI Society", 10, "09:00", "http://aiworkshop.com");
        appRunner.addUser("Alice", "Student");
        appRunner.addUser("Bob", "Student");
        appRunner.addEventToStudent(appRunner.findEvent("Tech Talk 2025"), appRunner.findUser("Alice"));
    }

    /**
     * Tests the writing of data to a JSON file and ensures the file is created.
     */
    @Test
    public void testWriteToFile() {
        try {
            writer.open();
            writer.write(appRunner);
            writer.close();

            // Verify that the file was created
            File file = new File(TEST_FILE);
            assertTrue(file.exists());
        } catch (FileNotFoundException e) {
            fail("Exception should not be thrown");
        }
    }

    /**
     * Tests the writing of data and then reading it back from the JSON file.
     * Verifies that the data was correctly written and loaded.
     */
    @Test
    public void testWriteAndRead() {
        try {
            writer.open();
            writer.write(appRunner);
            writer.close();

            // Read the data back
            JsonReader reader = new JsonReader(TEST_FILE);
            TechEventsHubAppRunner loadedAppRunner = reader.read();

            // Check if the data matches expected values
            assertEquals(2, loadedAppRunner.getAppEvents().getEvents().size());
            assertEquals(2, loadedAppRunner.getAppUsers().getUsers().size());
            assertNotNull(loadedAppRunner.findEvent("Tech Talk 2025"));
            assertNotNull(loadedAppRunner.findUser("Alice"));
            assertEquals(1, loadedAppRunner.findUser("Alice").getEvents().getTotalNumberOfEvents());
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }

    /**
     * Tests the behavior when trying to write to an invalid file path.
     * Ensures that the proper exception is thrown.
     */
    @Test
    public void testFileNotFound() {
        JsonWriter invalidWriter = new JsonWriter("./invalid/file/path.json");
        try {
            invalidWriter.open();
            invalidWriter.write(appRunner);
            fail("Exception should have been thrown");
        } catch (FileNotFoundException e) {
            // Expected behavior
        }
    }
}
