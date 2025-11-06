package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of users. 
 * This class maintains a list of users and provides methods to add users, 
 * find users by name, and retrieve the number of users in the collection.
 */

public class Users implements Writable {

    private List<User> listOfUsers;

    
    /**
     * Effects: Initializes an empty list of users.
     */
    public Users() {
        listOfUsers = new ArrayList<>();
    }

    public List<User> getUsers() {
        return listOfUsers;
    }

    /**
     * Effects: If the user is not already in the list, the user is added to the list.
     */
    public void addUser(User u) {

        boolean b = true;
        for (User e: listOfUsers) {
            if (e.getName().equals(u.getName())) {
                b = false;
            }
        }
        if (b) {
            listOfUsers.add(u);
            EventLog.getInstance().logEvent(
					new EventTracker("Added user: " + u.getName() + " on the list of user!"));
        }

    }

    /**
     * Effects - Returns the number of Users.
     */
    public int getNumberOfUsers() {
        return listOfUsers.size();
    }

    /**
     * Requires: name (a non-null string representing the user's name).
     * Modifies: None
     * Effects: Returns the user with the specified name, or null if no user is found.
     */
    public User findUser(String name) {
        for (User u: listOfUsers) {
            if (u.isRightUser(name)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Modifies: none.
     * Effects: Returns a JSONObject representation of the Users object. 
     *          The JSON object contains a key "users", which maps to a JSON array 
     *          containing the JSON representation of each user in the listOfUsers.
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("users", usersToJson());
        return json;
    }

    /**
     * Converts the list of users to a JSON array.
     * Effects: Returns a JSON array representation of the users in this Users object.
     */
    private JSONArray usersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (User u : listOfUsers) {
            jsonArray.put(u.toJson());
        }

        return jsonArray;
    }



}
