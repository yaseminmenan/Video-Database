package actions.query;

import common.MapMethods;
import fileio.ActionInputData;
import fileio.Input;
import fileio.UserInputData;

import java.util.*;

public class QueryUsers {
    /**
     * Return sorted list of usernames by number of videos rated
     */
    public String doQuery(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getUsers().size()) {
            queryNumber = input.getUsers().size();
        }
        // Create map of usernames and number of videos rated
        Map<String, Integer> userMap = new HashMap<>();

        // Traverse user list
        for (UserInputData user : input.getUsers()) {

            //Add the user to map if it has rated at least one video
            if (user.getRatingsNumber() > 0) {
                userMap.put(user.getUsername(), user.getRatingsNumber());
            }
        }

        // Create sorted list of usernames
        List<String> usernameList = new MapMethods().getListInteger(userMap, queryNumber,
                action.getSortType());
        return  "Query result: " + usernameList.toString();
    }
}
