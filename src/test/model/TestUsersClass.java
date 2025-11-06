package model;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestUsersClass {

    private Users usersInstance;
    private ArrayList<User> list;
    private User user1;
    
    @BeforeEach
    void runBefore() {
        usersInstance = new Users();
        list = new ArrayList<>();
        user1 = new User("Dhweya", "Student");
    }

    //Should create a users instance, with empty list. 
    @Test
    void testUsersConstructor() {
        assertEquals(0, usersInstance.getUsers().size());
    }

    //Tests the getUsers method, must return a corresponding arrayList.
    @Test
    void getUsersTestMethod() {
        assertEquals(list, usersInstance.getUsers());
    }

    //Should add a user to the end of the list of users. 
    @Test
    void addUserTestMethod() {
        assertEquals(0, usersInstance.getUsers().size());
        assertEquals(list, usersInstance.getUsers());

        usersInstance.addUser(user1);
        list.add(user1);

        assertEquals(1, usersInstance.getUsers().size());
        assertEquals(list, usersInstance.getUsers());

        //did not add a repeated user. 
        usersInstance.addUser(user1);
        assertEquals(1, usersInstance.getUsers().size());
        assertEquals(list, usersInstance.getUsers());



    }

    //Should add a user to the end of the list of users. 
    @Test
    void getNumberOfUsers() {
        assertEquals(0, usersInstance.getNumberOfUsers());
        usersInstance.addUser(user1);
        assertEquals(1, usersInstance.getNumberOfUsers());
    }

    // Tests the findUser method, should return the correct user by name.
    @Test
    void testFindUser() {
        usersInstance.addUser(user1);

        User foundUser = usersInstance.findUser("Dhweya");
        assertEquals(user1, foundUser);

        User notFoundUser = usersInstance.findUser("NonExisting");
        assertEquals(null, notFoundUser);
    }

    // Test to verify that the first if statement in addUser (checking for existing user) works
    @Test
    void addUserAlreadyExistsTestMethod() {
        // Add the user to the list
        usersInstance.addUser(user1);
        list.add(user1);  // Manually add user to list to keep it in sync for assertion
        
        // Check that the list contains the user
        assertEquals(1, usersInstance.getUsers().size());
        assertEquals(list, usersInstance.getUsers());
        
        // Create another user with the same name (should be considered duplicate)
        User user2 = new User("Dhweya", "Student");
        
        // Try adding the duplicate user
        usersInstance.addUser(user2);
        
        // Assert that the size of the list is still 1, meaning the duplicate was not added
        assertEquals(1, usersInstance.getUsers().size());
        assertEquals(list, usersInstance.getUsers());
    }


}