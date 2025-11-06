package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import model.Event;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

/**
 * TechEventsAppConsoleRunner is responsible for running the console-based interface of the TechEventsHub application.
 * It handles interactions between users and organizers, allowing them to create or find users, 
 * browse and manage events, and save progress.
 */

public class TechEventsAppConsoleRunner {

    private static final String JSON_STORE = "./data/TechEventsApp.json";

    private Scanner input;
    private TechEventsHubAppRunner appRunner;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    /**
     * Constructor for the TechEventsAppConsoleRunner.
     * 
     * Requires: None.
     * Modifies: input, appRunner.
     * Effects: Initializes a new instance of Scanner and TechEventsHubAppRunner.
     */
    public TechEventsAppConsoleRunner() {
        input = new Scanner(System.in);
        appRunner = new TechEventsHubAppRunner();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    /**
     * Constructor for TechEventsAppConsoleRunner that allows passing a specific appRunner instance.
     * 
     * Requires: appRunner (instance of TechEventsHubAppRunner).
     * Modifies: input, appRunner.
     * Effects: Initializes a new instance of Scanner and assigns the provided appRunner.
     */
    public TechEventsAppConsoleRunner(TechEventsHubAppRunner appRunner) {
        input = new Scanner(System.in);
        this.appRunner = appRunner;
    }

    /**
     * Requires: None.
     * Modifies: None.
     * Effects: Prompts the user for their role and loops until they choose to exit. Executes role-specific behavior.
     */

    public void loadPreviousInstanceScript() {
        System.out.println("Would you like to load the latest instance?");
        System.out.println("0: Yes!, 1: No.");
        int integer = input.nextInt();
        if (integer == 0) {
            loadProgress();
        }
        this.preface();
    }

    private void preface() {
        System.out.println("Who are you?");
        System.out.println("1: Organizer trying to post an event.");
        System.out.println("2: I am a UBC Student looking for events!");
        System.out.println("3: I would like to save progress and exit!");


        int integer = input.nextInt();

        while (integer != 3) {
            this.chooseTypeOfUsage(integer);
            System.out.println();
            System.out.println("------- Welcome Back -----");
            System.out.println("Who are you?");
            System.out.println("1: Organizer trying to post an event.");
            System.out.println("2: I am a UBC Student looking for events!");
            System.out.println("3: I would like to save progress and exit!");
            integer = input.nextInt();

        }
        saveProgress();
        System.out.println("Bye Bye! Your progress is saved!");
       

    }

    /**
     * Requires: i (1 for Organizer, 2 for Student).
     * Modifies: None.
     * Effects: Calls the appropriate method for Organizer or Student based on input.
     */
    public void chooseTypeOfUsage(int i) {
        if (i == 1) {
            this.organizer();
        } else {
            this.student();
        }
    }

 /**
     * Requires: None.
     * Modifies: currentUser (User object).
     * Effects: Lets the user create a new user or map an existing user and then perform tasks (view or add events).
     */
    public void student() {
        User currentUser;
        System.out.println("---------------------------");
        System.out.println("Hello User!");
        System.out.println("1: Create new user. ");
        System.out.println("2: I am an existing user. ");
        System.out.println("3: Go back. ");
        int current = input.nextInt();
        while (current != 3) {
            if (current == 1) {
                currentUser = this.createNewUserScript();
            } else {
                currentUser = this.mapExistingUser();
            }

            userTasks(currentUser);

            System.out.println();
            System.out.println(" --- Welcome Back User ---");
            System.out.println("1: Create new user. ");
            System.out.println("2: I am an existing user. ");
            System.out.println("3: Go back. ");
            current = input.nextInt();

        }
        System.out.println("Went back!");

    }

    /**
     * Requires: Name must be unique.
     * Modifies: Users, User.
     * Effects: Creates a new user and returns the created User object.
     */ 
    public User createNewUserScript() {
        System.out.println();
        System.out.println("What is your name?");
        String name = input.next();
        return appRunner.addUser(name, "Student");
    }
   

    /**
     * Maps an existing user by asking for their name.
     * Requires: Name is unique.
     * Effects: Finds and returns the User object if found.
     */
    public User mapExistingUser() {
        System.out.println();
        System.out.println("What is your name?");
        String name = input.next();
        return appRunner.findUser(name);
    }


    /**
     * Maps or creates the user with the given name. 
     * Requires: Name is unique.
     * Effects: Finds and returns the User object if found.
     */
    public User mapOrCreateUser(String name) {
        return appRunner.findUser(name);
    }

    /**
     * Requires: currentUser (User object).
     * Modifies: None.
     * Effects: Prompts the user to browse events, add events, or view calendar.
     */
    public void userTasks(User currentUser) {
        System.out.println();
        System.out.println("1: Browse and add events to my calender.");
        System.out.println("2: View events in my calender.");
        System.out.println("3: Go back.");
        int current = input.nextInt();

        while (current != 3) {
            if (current == 1) {
                browseEvents();
                addEventToStudentCalenderScript(currentUser);
            } else {
                System.out.println("---- Calender View -----");
                viewEventsInMyCalender(currentUser);
            }

            System.out.println();
            System.out.println(" --- What would you like to do next? ---");
            System.out.println("1: Browse and add events to my calender.");
            System.out.println("2: View events in my calender.");
            System.out.println("3: Go back.");
            current = input.nextInt();

        }
        System.out.println("Going back.");
    }

    /**
     * Requires: currentUser is already a part of Users list.
     * Modifies: currentUser (User object), TechEvents, TechEventsHubAppRunner.
     * Effects: Prompts the user to input the name of an event to be added to their calendar. 
     * If the event is found, it is added to the user's calendar; otherwise, a message indicating failure is displayed.
     */
    public void addEventToStudentCalenderScript(User currentUser) {
        System.out.println();
        System.out.println("Type the name of the event you want added: ");
        String eventName = input.next();
        Event eventFound = findEventWithGivenName(eventName);
        if (eventFound == null) {
            System.out.println("Was not able to find the given event, no event added :(");
        } else {
            appRunner.addEventToStudent(eventFound, currentUser);
            System.out.println("Event is added to your calender!");
        }
    }

    public void addEventToStudentCalender(String eventName, User currentUser) {
        Event eventFound = findEventWithGivenName(eventName);
        appRunner.addEventToStudent(eventFound, currentUser);
           
    }

    /**
     * Requires: currentUser (User object) is a part of Users.
     * Effects: Displays all events in the user's calendar by calling the viewEventsInUserCalender method of appRunner.
     */
    public void viewEventsInMyCalender(User currentUser) {
        System.out.println(appRunner.viewEventsInUserCalender(currentUser));
    } 

    public String returnEventsInMyCalender(User currentUser) {
        return appRunner.viewEventsInUserCalender(currentUser);
    }

    /**
     * Requires: name (String) of the event to find.
     * Effects: Searches for an event by the given name and returns the corresponding Event object.
     *  If no event is found, it returns null.
     */
    public Event findEventWithGivenName(String name) {
        return appRunner.findEvent(name);
    }

    /**
     * Modifies: Event, TechEvents, TechEventsHubAppRunner.
     * Effects: Allows the organizer to choose between viewing events, adding an event, or exiting. 
     * The loop continues until the user chooses to exit.
     */
    public void organizer() {
        System.out.println();
        System.out.println("1: I would like to view events. ");
        System.out.println("2: I would like to add an event. ");
        System.out.println("3: I would like to exit. ");
        int current = input.nextInt();
        while (current != 3) {
            if (current == 1) {
                browseEvents();
            } else if (current == 2) {
                postAnEventScript();
            }
            System.out.println();
            System.out.println(" --- Welcome Back Organizer ---");
            System.out.println("1: I would like to view events. ");
            System.out.println("2: I would like to add an event. ");
            System.out.println("3: I would like to exit. ");
            current = input.nextInt();
        }
        System.out.println("Exited!");
        System.out.println();
    }

    /**
     * Requires: Day >= 0, eventName to be unique.
     * Modifies: TechHubEvents, Event, TechEventsHubAppRunner.
     * Effects: Prompts the user to enter the necessary details to create and post an event.
     */
    public void postAnEventScript() {
        String eventName;
        String organizer; 
        int day;
        String time;
        String url;

        System.out.println("Let's post an Event!");
        System.out.println();
        System.out.println("Enter event name:");
        eventName = input.next();
        System.out.println("Enter the day it is on:");
        day = input.nextInt();
        System.out.println("Enter organization name (optional):");
        organizer = input.next();
        System.out.println("Enter the time the event is held (optional):");
        time = input.next();
        System.out.println("Enter event url (optional):");
        url = input.next();
        System.out.println();
        System.out.println("Event Is Posted!");
        appRunner.addEvent(eventName, organizer, day, time, url);
    }

    public void postAnEvent(String eventName, String organizer, Integer eventDay, String time, String url) {
        appRunner.addEvent(eventName, organizer, eventDay, time, url);
    }


     /**
     * Saves the current state of the application by writing the appRunner object to a JSON file.
     * This method is responsible for persisting the user's progress to the specified file.
     * 
     * Modifies: None.
     * Effects: Writes the appRunner state to the JSON_STORE file.
     * If the file cannot be written to, an error message is displayed.
     */
    public void saveProgress() {
        try {
            // Open the writer to begin writing to the JSON file
            jsonWriter.open();

            // Write the current state of the appRunner to the file
            jsonWriter.write(appRunner);

            // Close the writer after writing is complete
            jsonWriter.close();

            // Inform the user that the progress has been saved
            System.out.println("Saved " + "TechEventsApp.json" + " to " + JSON_STORE);
            System.out.println(appRunner.getLog());
        } catch (FileNotFoundException e) {
            // Handle the case where the file could not be opened or written to
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    /**
     * Loads the previously saved state of the application from a JSON file.
     * This method retrieves the appRunner object from the file and restores the application's progress.
     * 
     * Modifies: appRunner (restores state from the file).
     * Effects: Reads the appRunner state from the JSON_STORE file.
     * If the file cannot be read, an error message is displayed.
     */
    public void loadProgress() {
        try {
            // Read the saved state of the appRunner from the file
            appRunner = jsonReader.read();

            // Inform the user that the progress has been successfully loaded
            System.out.println("Loaded " + "TechEventsApp.json" + " from " + JSON_STORE);
        } catch (IOException e) {
            // Handle the case where the file could not be read
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    /**
    * Requires: None.
    * Modifies: None.
    * Effects: Displays all available events to the user.
    */
    public void browseEvents() {
        System.out.println("-------");
        System.out.println("Here are the events available:");
        appRunner.viewEvents();
        System.out.println("-------");
    }

    public String getPostedEvents() {
        return appRunner.returnPostedEvents();
    }

    /**
     * Requires: min and max are valid day values (0 <= min, max <= 365).
     * Modifies: None.
     * Effects: Returns a string representation of all events within the specified day range.
     */
    public String returnFilteredEventsByDayRange(int min, int max) {
        return appRunner.returnFilteredEventsByDayRange(min, max);
    }

    /**
     * Requires: name (String) of the event must be unique.
     * Modifies: this.appEvents.
     * Effects: Removes the event with the specified name from appEvents.
     *          Returns true if the event was found and removed, false otherwise.
     */
    public boolean removeEvent(String name) {
        return appRunner.removeEvent(name);
    }


    



}

