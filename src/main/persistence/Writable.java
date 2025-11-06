package persistence;

import org.json.JSONObject;

/**
 * Interface that represents a writable object.
 * Classes that implement this interface should provide a method to convert the object into its JSON representation.
 */
public interface Writable {

    /**
     * Modifies: none
     * Effects: Returns the current object as a JSONObject representation.
     *
     * @return a JSONObject representing the current state of the object
     */
    JSONObject toJson();
}
