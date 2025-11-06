<<<<<<< HEAD

# My Personal Project - TechEvents Hub


**What will the application do?**
TechEvents Hub is a *centralized platform where tech-related clubs and organizations at UBC can post and manage events*. Students can easily browse upcoming career fairs, hackathons, networking events, and conferences through a calendar view. The app will also include filtering options, allowing users to find events based on categories like workshops, speaker sessions, and recruiting events.

**Who will use it?**
This application is designed for *UBC students* interested in tech-related opportunities, as well as clubs and organizations that want to promote their events efficiently.

<<<<<<< HEAD
=======
**Why is this project of interest to you?**
As a student, staying informed about tech events can be difficult, especially when *information is scattered across emails and social media.* TechEvents Hub solves this problem by consolidating everything in one place. This project is exciting to me because it addresses a real issue and has practical value for students in the tech community.

## User Stories: 
- As a club organizer, I want to be able to add an event to the calendar, specifying the event name, date, time, and description, so that students can see the event details.
- As a club organizer, I want to be able to add more than 1 events to the UBC post. 
- As a student, I want to be able to view a list of all upcoming events so that I can stay informed about tech-related activities.
- As a student, I want to be able to add a particular event to my calender. 
- As a student, I want to be able to view the events in my calender. 
- As a User and Organizer, I want to be able to save progress of the current state of UBC posts, students, and their calenders. 
- As a user, I want to be able to switch between (sign-in and sign-out) various other users including organizers. 

Version 2 Added User Stories:
- As a user, when I select the quit option from the application menu, I want to be reminded to save my to-do list to file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my to-do list from file.


//MORE ADVANCED WILL DEPLOY LATER. 
- As a student, I want to be able to filter events by timeline so that I can find events that match my interests.
- As a student, I want to be able to click on an event to view more details, including the time, date, and organizer and url information.


## Logical consideration that must be resolved for more efficiencies next time: 

- Remove category from User. 
- Remove the overloaded methods for creating events. 
- Add an Event Id, so user can add events to their calender through the event id. 
- Filter events functionality.
- What if the event that needs to be searched isn’t found?


Make more effective:
- Calenderview, find an easier way to print a list of events, use toString or something? Or iterable interface. 
- Should not be able to add same events twice into your calender. 
- Display a different message if calender is empty. 
- What if the existing user with the given name could not be found? 
- What if the event that needs to be searched isn’t found.
- Find an event using its id. 
- Work on filtering events or sorting. 


## PROJECT PHASE 4 TASK 2:

Thu Apr 03 16:15:00 PDT 2025
Added event to general calendar: WorkShop1 on Day 1
Thu Apr 03 16:15:07 PDT 2025
Added event to general calendar: WorkShop3 on Day 3
Thu Apr 03 16:15:13 PDT 2025
Added event to general calendar: WorkShop22 on Day 22
Thu Apr 03 16:15:23 PDT 2025
Removed event from calendar: WorkShop22 from Day 22
Thu Apr 03 16:15:41 PDT 2025
Added user: Dhweya on the list of user!
Thu Apr 03 16:15:43 PDT 2025
Added event to user's calendar: User - Dhweya | Event - WorkShop1
Thu Apr 03 16:15:44 PDT 2025
Added event to user's calendar: User - Dhweya | Event - WorkShop3

## PROJECT PHASE 4 TASK 3: 

One key area for refactoring is the structure of the application runners. Currently, TechEventsHubAppRunner, TechEventsAppConsoleRunner, and TechEventsHubGUI exist as separate components without a clear hierarchy. A better design would be to introduce an abstract AppRunner class, which defines shared functionality such as initializing the application and managing dependencies. Then, both TechEventsAppConsoleRunner and TechEventsHubGUI can extend this abstract class, ensuring a cleaner separation of concerns while avoiding redundant code. This would make it easier to maintain and extend the application, allowing for future UI implementations without disrupting the core logic.

Another improvement involves the relationship between EventTracker and EventLog. Currently, these components are directly connected, which increases coupling. A more flexible approach would be to implement the Observer pattern, where EventLog acts as a listener that subscribes to EventTracker. This way, whenever an event occurs, EventTracker can notify multiple subscribers without needing to explicitly reference them. This change would make the system more extensible, allowing additional components (e.g., real-time monitoring tools or logging mechanisms) to track events without modifying the existing code. By applying these refactorings, the system would become more modular, maintainable, and adaptable to future changes.

