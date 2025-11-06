package ui;

import javax.swing.*;

import model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TechEventsAppConsoleRunner is responsible for running the gui-based interface of the TechEventsHub application.
 * It handles interactions between users and organizers, allowing them to create or find users, 
 * browse and manage events, and save progress.
 */

public class TechEventsHubGUI extends JFrame implements ActionListener {

    private static final int WIDTH = 800; // Width of the screen.
    private static final int HEIGHT = 600; // Height of the screen.

    private JButton continueButton; // First-Screen Continue Button.


    private JTextArea eventsTextArea; // To display posted events
    private JSplitPane splitPane; // To manage the split view
    private JTextArea calendarTextArea; // To display the user's calendar events

    private JTextField nameField; // To manage the focus of the screen.

    TechEventsAppConsoleRunner consoleApp;
    User currentUser;

    // Constructor for initializing the frame
    public TechEventsHubGUI() {
        setTitle("Tech Events Hub"); // Title of the JFrame.
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        drawFrontScreen();
        


        consoleApp = new TechEventsAppConsoleRunner();
        currentUser = null;
    }

    /**
     * Requires: None.
     * Modifies: The content pane of the JFrame.
     * Effects: Draws the front screen with a welcome label and a continue button.
     */
    public void drawFrontScreen() {
        clearContentPane();

        JLabel welcomeLabel = new JLabel("Welcome to Tech Events Hub App", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        continueButton = new JButton("Click to Continue");
        continueButton.setFont(new Font("Arial", Font.PLAIN, 18));
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(continueButton);

        add(welcomeLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton) {
            showLoadInstanceScreen();
        }
    }

    /**
     * Requires: None.
     * Modifies: The content pane of the JFrame.
     * Effects: Displays a screen asking the user if they want to load a previous instance.
     */
    public void showLoadInstanceScreen() {
        clearContentPane();
        JPanel loadInstancePanel = createLoadInstancePanel();
        addLoadInstancePanelToFrame(loadInstancePanel);
        refreshFrame();
    }

    /**
     * Creates and returns the panel that allows the user to load a new instance.
     * 
     * @return a JPanel for loading an instance
     */
    private JPanel createLoadInstancePanel() {
        JPanel loadInstancePanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Would you like to load the previous instance?", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        loadInstancePanel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = createLoadInstanceButtonPanel();
        loadInstancePanel.add(buttonPanel, BorderLayout.SOUTH);

        return loadInstancePanel;
    }

    /**
     * Creates and returns the button panel associated with loading an instance.
     * 
     * @return a JPanel containing buttons for loading an instance
     */
    private JPanel createLoadInstanceButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton loadButton = new JButton("Yes, Load");
        loadButton.addActionListener(e -> {
            loadPreviousInstance();
            showPrefaceScreen();
        });
        buttonPanel.add(loadButton);

        JButton skipButton = new JButton("No, Skip");
        skipButton.addActionListener(e -> showPrefaceScreen());
        buttonPanel.add(skipButton);

        return buttonPanel;
    }

    /**
     * Adds the load instance panel to the main frame.
     * 
     * @param panel the panel to add to the frame
     */
    private void addLoadInstancePanelToFrame(JPanel panel) {
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Requires: None.
     * Modifies: The consoleApp's state.
     * Effects: Loads the previous instance of the application.
     */
    private void loadPreviousInstance() {
        consoleApp.loadProgress();
    }

    /**
     * Requires: None.
     * Modifies: The content pane of the JFrame.
     * Effects: Displays the preface screen with options for the user to select their role.
     */
    public void showPrefaceScreen() {
        clearContentPane();
        JPanel prefacePanel = createPrefacePanel();
        addPrefacePanelToFrame(prefacePanel);
        refreshFrame();
    }

    /**
     * Creates and returns the preface panel, likely containing introductory content.
     * 
     * @return a JPanel displaying preface or welcome information
     */
    private JPanel createPrefacePanel() {
        JPanel prefacePanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Who are you?", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        prefacePanel.add(label, BorderLayout.CENTER);

        JPanel buttonPanel = createPrefaceButtonPanel();
        prefacePanel.add(buttonPanel, BorderLayout.SOUTH);

        return prefacePanel;
    }

    /**
     * Creates and returns the button panel for the preface screen.
     * 
     * @return a JPanel containing buttons for the preface screen
     */
    private JPanel createPrefaceButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton organizerButton = new JButton("Organizer trying to post an event.");
        organizerButton.addActionListener(e -> handleOrganizerScreen());
        buttonPanel.add(organizerButton);

        JButton studentButton = new JButton("I am a UBC Student looking for events!");
        studentButton.addActionListener(e -> handleStudentScreen());
        buttonPanel.add(studentButton);

        JButton saveExitButton = new JButton("I would like to save progress and exit!");
        saveExitButton.addActionListener(e -> saveAndExit());
        buttonPanel.add(saveExitButton);

        return buttonPanel;
    }

    /**
     * Creates and returns the button panel for the save and exit screen.
     * 
     * @return a JPanel containing buttons for the save and exit screen
     */
    private void saveAndExit() {
        consoleApp.saveProgress();
        JOptionPane.showMessageDialog(null, "Progress saved! Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        System.out.println("Exited");
    }

    /**
     * Adds the preface panel to the main frame.
     * 
     * @param panel the preface panel to add to the frame
     */
    private void addPrefacePanelToFrame(JPanel panel) {
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Requires: None.
     * Modifies: The content pane of the JFrame.
     * Effects: Displays the organizer screen with a split pane for posted events and event management.
     */
    public void handleOrganizerScreen() {
        clearContentPane();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);

        JPanel postedEventsPanel = createPostedEventsPanel();
        splitPane.setLeftComponent(postedEventsPanel);

        JPanel organizerPanel = createOrganizerPanel();
        splitPane.setRightComponent(organizerPanel);

        add(splitPane, BorderLayout.CENTER);

        refreshFrame();
    }

    /**
     * Creates and returns the panel that allows organizers to interact with the system.
     * 
     * @return a JPanel for organizer-specific functionalities
     */
    private JPanel createOrganizerPanel() {
        JPanel organizerPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        organizerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addEventNameField(organizerPanel);
        addEventDayField(organizerPanel);
        addOrganizerField(organizerPanel);
        addEventTimeField(organizerPanel);
        addEventUrlField(organizerPanel);
        addPostEventButton(organizerPanel);
        addRemoveEventButton(organizerPanel);
        addBackButton(organizerPanel);

        return organizerPanel;
    }

    /**
     Effects: JPanel for event name field functionalities
     */
    private void addEventNameField(JPanel panel) {
        JLabel eventNameLabel = new JLabel("Event Name:");
        JTextField eventNameField = new JTextField();
        panel.add(eventNameLabel);
        panel.add(eventNameField);
    }

    /**
     Effects: JPanel for event day field functionalities
     */
    private void addEventDayField(JPanel panel) {
        JLabel eventDayLabel = new JLabel("Event Day (e.g., 1 for Day 1):");
        JTextField eventDayField = new JTextField();
        panel.add(eventDayLabel);
        panel.add(eventDayField);
    }

    /**
     Effects: JPanel for organizer name field functionalities
     */
    private void addOrganizerField(JPanel panel) {
        JLabel organizerLabel = new JLabel("Organizer Name (optional):");
        JTextField organizerField = new JTextField();
        panel.add(organizerLabel);
        panel.add(organizerField);
    }

    /**
     Effects: JPanel for event time field functionalities
     */
    private void addEventTimeField(JPanel panel) {
        JLabel eventTimeLabel = new JLabel("Event Time (optional):");
        JTextField eventTimeField = new JTextField();
        panel.add(eventTimeLabel);
        panel.add(eventTimeField);
    }

    /**
     Effects: JPanel for event url field functionalities
     */
    private void addEventUrlField(JPanel panel) {
        JLabel eventUrlLabel = new JLabel("Event URL (optional):");
        JTextField eventUrlField = new JTextField();
        panel.add(eventUrlLabel);
        panel.add(eventUrlField);
    }

    /**
     Effects: JPanel for event posting button functionalities
     */
    private void addPostEventButton(JPanel panel) {
        JButton postEventButton = new JButton("Post Event");
        postEventButton.addActionListener(e -> handlePostEvent(panel));
        panel.add(postEventButton);
    }

    /**
     * Handles the process of posting an event based on input from the provided panel.
     * 
     * @param panel the panel containing event input fields
     */
    private void handlePostEvent(JPanel panel) {
        String eventName = ((JTextField) panel.getComponent(1)).getText();
        String eventDayText = ((JTextField) panel.getComponent(3)).getText();
        String organizer = ((JTextField) panel.getComponent(5)).getText();
        String time = ((JTextField) panel.getComponent(7)).getText();
        String url = ((JTextField) panel.getComponent(9)).getText();

        if (!eventName.isEmpty() && !eventDayText.isEmpty()) {
            try {
                int eventDay = Integer.parseInt(eventDayText);
                consoleApp.postAnEvent(eventName, organizer, eventDay, time, url);
                JOptionPane.showMessageDialog(null, "Event posted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshEventsList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Event day must be a valid number!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Event name and day cannot be empty!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the process of removing an event based on input from the provided panel.
     * 
     * @param panel the panel containing event input fields
     */
    private void addRemoveEventButton(JPanel panel) {
        JButton removeEventButton = new JButton("Remove Event");
        removeEventButton.addActionListener(e -> {
            String eventName = JOptionPane.showInputDialog("Enter the name of the event to remove:");
            if (eventName != null && !eventName.isEmpty()) {
                boolean removed = consoleApp.removeEvent(eventName);
                if (removed) {
                    JOptionPane.showMessageDialog(null, "Event removed successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshEventsList();
                } else {
                    JOptionPane.showMessageDialog(null, "Event not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (nameField != null && nameField.isVisible()) {
                setFocusToComponent(nameField);
            }
        });
        panel.add(removeEventButton);
    }


    /**
     * Sets focus to the specified UI component.
     * 
     * @param component the component to set focus on
     */
    private void setFocusToComponent(Component component) {
        if (component != null && component.isVisible()) {
            component.requestFocusInWindow();
        }
    }

    /**
     * Creates and returns the panel displaying events that have been posted.
     * 
     * @return a JPanel showing the list of posted events
     */
    private JPanel createPostedEventsPanel() {
        JPanel postedEventsPanel = new JPanel(new BorderLayout());
        JLabel postedEventsLabel = new JLabel("Posted Events", SwingConstants.CENTER);
        postedEventsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        postedEventsPanel.add(postedEventsLabel, BorderLayout.NORTH);

        eventsTextArea = new JTextArea();
        eventsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(eventsTextArea);
        postedEventsPanel.add(scrollPane, BorderLayout.CENTER);

        refreshEventsList();

        return postedEventsPanel;
    }

    /**
     * Refreshes the list of posted events displayed in the UI.
     */
    private void refreshEventsList() {
        String events = consoleApp.getPostedEvents();
        eventsTextArea.setText(events);
    }

    /**
     * Adds a "Back" button to the given panel to navigate to the previous screen.
     * 
     * @param panel the panel to which the back button is added
     */
    private void addBackButton(JPanel panel) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showPrefaceScreen());
        panel.add(backButton);
    }

    /**
     * Requires: None.
     * Modifies: The content pane of the JFrame.
     * Effects: Displays the student screen with options for current or new users.
     */
    public void handleStudentScreen() {
        clearContentPane();
        JPanel studentSelectionPanel = createStudentSelectionPanel();
        addStudentSelectionPanelToFrame(studentSelectionPanel);
        refreshFrame();
    }

    /**
     * Creates and returns the panel allowing students to select between options (e.g., view events, register).
     * 
     * @return a JPanel for student selection options
     */
    private JPanel createStudentSelectionPanel() {
        JPanel studentSelectionPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Are you a current user?", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        studentSelectionPanel.add(label, BorderLayout.NORTH);

        JPanel buttonPanel = createStudentSelectionButtonPanel();
        studentSelectionPanel.add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showPrefaceScreen());
        studentSelectionPanel.add(backButton, BorderLayout.SOUTH);

        return studentSelectionPanel;
    }

    /**
     * Creates and returns the button panel for the student selection screen.
     * 
     * @return a JPanel containing buttons for student navigation
     */
    private JPanel createStudentSelectionButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton currentUserButton = new JButton("Yes, I am a current user");
        currentUserButton.addActionListener(e -> showStudentUserCreatorScreen("current"));
        buttonPanel.add(currentUserButton);

        JButton newUserButton = new JButton("No, I am new");
        newUserButton.addActionListener(e -> showStudentUserCreatorScreen("new"));
        buttonPanel.add(newUserButton);

        return buttonPanel;
    }

    // Effects: adds a given panel to the frame.
    private void addStudentSelectionPanelToFrame(JPanel panel) {
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Requires: userType is either "current" or "new".
     * Modifies: The content pane of the JFrame.
     * Effects: Displays a screen for the user to enter their name and proceed.
     */
    public void showStudentUserCreatorScreen(String userType) {
        clearContentPane();
        JPanel nameInputPanel = createNameInputPanel();
        addNameInputPanelToFrame(nameInputPanel);
        refreshFrame();
    }

    /**
     * Creates and returns the panel that allows the user to input their name.
     * 
     * @return a JPanel containing a text field for name input and corresponding buttons
     */
    private JPanel createNameInputPanel() {
        JPanel nameInputPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Please enter your name:", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        nameInputPanel.add(label, BorderLayout.NORTH);

        nameField = new JTextField();
        nameInputPanel.add(nameField, BorderLayout.CENTER);

        JPanel buttonPanel = createNameInputButtonPanel();
        nameInputPanel.add(buttonPanel, BorderLayout.SOUTH);

        return nameInputPanel;
    }

    /**
     * Creates and returns the button panel for the name input screen.
     * Includes "Submit" and "Back" buttons with corresponding actions.
     * 
     * @return a JPanel with buttons for submitting a name or navigating back
     */
    private JPanel createNameInputButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> handleNameSubmission());
        buttonPanel.add(submitButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> handleStudentScreen());
        buttonPanel.add(backButton);

        return buttonPanel;
    }

    /**
     * Handles the submission of the entered name.
     * If the name is valid, finds the corresponding user, shows a welcome message,
     * and transitions to the student split screen. Otherwise, shows an error message.
     */
    private void handleNameSubmission() {
        String name = nameField.getText();
        if (!name.isEmpty()) {
            currentUser = findGivenUser(name);
            JOptionPane.showMessageDialog(null, "Welcome, " + currentUser.getName() + "!", 
                    "User Created", JOptionPane.INFORMATION_MESSAGE);
            handleStudentSplitScreen();
        } else {
            JOptionPane.showMessageDialog(null, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds the name input panel to the main frame in the center region.
     * 
     * @param panel the panel to be added to the frame
     */
    private void addNameInputPanelToFrame(JPanel panel) {
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Requires: name != null.
     * Modifies: consoleApp.
     * Effects: Maps or creates a user with the given name and returns the user.
     */
    public User findGivenUser(String name) {
        return consoleApp.mapOrCreateUser(name);
    }

    /**
     * Requires: None.
     * Modifies: The content pane of the JFrame.
     * Effects: Displays the student split screen with all events and the user's calendar.
     */
    public void handleStudentSplitScreen() {
        clearContentPane();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);

        JPanel allEventsPanel = createAllEventsPanel();
        splitPane.setLeftComponent(allEventsPanel);

        JPanel userCalendarPanel = createUserCalendarPanel();
        splitPane.setRightComponent(userCalendarPanel);

        add(splitPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showPrefaceScreen());
        add(backButton, BorderLayout.SOUTH);

        refreshFrame();
    }

    /**
     * Creates and returns the panel displaying all available events.
     * Includes a scrollable list of events with buttons to add them to the user's calendar.
     * 
     * @return a JPanel containing all events
     */
    private JPanel createAllEventsPanel() {
        JPanel allEventsPanel = new JPanel(new BorderLayout());
        JLabel allEventsLabel = new JLabel("All Events", SwingConstants.CENTER);
        allEventsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        allEventsPanel.add(allEventsLabel, BorderLayout.NORTH);

        JPanel eventsListPanel = createEventsListPanel();
        JScrollPane scrollPane = new JScrollPane(eventsListPanel);
        allEventsPanel.add(scrollPane, BorderLayout.CENTER);

        return allEventsPanel;
    }

    /**
     * Creates a vertical list panel containing individual event panels.
     * Retrieves all events from the backend and adds them as rows.
     * 
     * @return a JPanel with all event entries
     */
    private JPanel createEventsListPanel() {
        JPanel eventsListPanel = new JPanel();
        eventsListPanel.setLayout(new BoxLayout(eventsListPanel, BoxLayout.Y_AXIS));

        String allEvents = consoleApp.getPostedEvents();
        String[] events = allEvents.split("\n");

        for (String event : events) {
            JPanel eventPanel = createEventPanel(event);
            eventsListPanel.add(eventPanel);
        }

        return eventsListPanel;
    }

    /**
     * Creates a panel for a single event with its name and an "Add to Calendar" button.
     * 
     * @param event the event string to display
     * @return a JPanel representing a single event entry
     */
    private JPanel createEventPanel(String event) {
        JPanel eventPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel eventLabel = new JLabel(event);
        JButton addButton = new JButton("Add to Calendar");
        addButton.addActionListener(e -> handleAddEvent(event));
        eventPanel.add(eventLabel);
        eventPanel.add(addButton);

        return eventPanel;
    }

    /**
     * Handles the logic when the "Add to Calendar" button is clicked for an event.
     * Extracts the event name and adds it to the user's calendar.
     * 
     * @param event the event string to add
     */
    private void handleAddEvent(String event) {
        String eventName = event.split(" : ")[0].trim();
        eventName = eventName.replace("[", "").replace("]", "").trim();
        addEventToUserCalendar(eventName);
    }

    /**
     * Adds the given event name to the current user's calendar via the backend,
     * and refreshes the calendar display afterward.
     * 
     * @param eventName the name of the event to add
     */
    private void addEventToUserCalendar(String eventName) {
        String cleanedEventName = eventName.replace("[", "").replace("]", "").trim();
        consoleApp.addEventToStudentCalender(cleanedEventName, currentUser);
        refreshUserCalendar();
    }

    /**
     * Updates the calendar text area with the current user's saved events.
     */
    private void refreshUserCalendar() {
        String calendarEvents = consoleApp.returnEventsInMyCalender(currentUser);
        calendarTextArea.setText(calendarEvents);
    }

    /**
     * Creates and returns the panel displaying the user's calendar.
     * Includes a scrollable list of events and a filter bar for day ranges.
     * 
     * @return a JPanel showing the user's calendar
     */
    private JPanel createUserCalendarPanel() {
        JPanel userCalendarPanel = new JPanel(new BorderLayout());
        JLabel userCalendarLabel = new JLabel("Your Calendar", SwingConstants.CENTER);
        userCalendarLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userCalendarPanel.add(userCalendarLabel, BorderLayout.NORTH);

        calendarTextArea = new JTextArea();
        calendarTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(calendarTextArea);
        userCalendarPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addFilterByDayRangeButton(filterPanel);
        userCalendarPanel.add(filterPanel, BorderLayout.NORTH);

        refreshUserCalendar();

        return userCalendarPanel;
    }

    /**
     * Adds UI components for filtering the calendar by a day range,
     * including input fields and filter/clear buttons.
     * 
     * @param panel the panel to which the filter controls will be added
     */
    private void addFilterByDayRangeButton(JPanel panel) {
        JLabel filterLabel = new JLabel("Filter by Day Range:");
        JTextField minField = new JTextField(5);
        JLabel toLabel = new JLabel("to");
        JTextField maxField = new JTextField(5);
        JButton filterButton = new JButton("Filter");
        JButton clearFilterButton = new JButton("Clear Filter");

        filterButton.addActionListener(e -> handleFilter(minField, maxField));
        clearFilterButton.addActionListener(e -> handleClearFilter(minField, maxField));

        panel.add(filterLabel);
        panel.add(minField);
        panel.add(toLabel);
        panel.add(maxField);
        panel.add(filterButton);
        panel.add(clearFilterButton);
    }

    /**
     * Filters calendar events based on the user-entered day range
     * and updates the calendar display with the filtered results.
     * 
     * @param minField the text field for the minimum day
     * @param maxField the text field for the maximum day
     */
    private void handleFilter(JTextField minField, JTextField maxField) {
        try {
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            String filteredEvents = consoleApp.returnFilteredEventsByDayRange(min, max);
            calendarTextArea.setText(filteredEvents);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid day numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears the filter fields and refreshes the calendar to show all events.
     * 
     * @param minField the text field for the minimum day
     * @param maxField the text field for the maximum day
     */
    private void handleClearFilter(JTextField minField, JTextField maxField) {
        minField.setText("");
        maxField.setText("");
        refreshUserCalendar();
    }

    /**
     * Clears all components from the content pane.
     */
    private void clearContentPane() {
        getContentPane().removeAll();
    }

    /**
     * Refreshes the frame by revalidating and repainting it.
     */
    private void refreshFrame() {
        revalidate();
        repaint();
    }

    // // Main method for testing
    // public static void main(String[] args) {
    //     SwingUtilities.invokeLater(() -> {
    //         TechEventsHubGUI gui = new TechEventsHubGUI();
    //         gui.setVisible(true);
    //     });
    // }
}

