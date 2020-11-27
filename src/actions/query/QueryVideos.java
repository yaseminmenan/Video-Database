package actions.query;

import common.MapMethods;
import fileio.ActionInputData;
import fileio.Input;
import fileio.ShowInput;
import fileio.UserInputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class QueryVideos {
    /**
     * Return a list of videos sorted by number of views
     */
    public String getViewed(final Input input, final ActionInputData action,
                            final List<? extends ShowInput> videoList) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > videoList.size()) {
            queryNumber = videoList.size();
        }

        // Create map of videos titles and number of views
        Map<String, Integer> viewsMap = new HashMap<>();

        // Traverse videos list
        for (ShowInput video : videoList) {
            // If the videos has the year and genre given by query
            if (video.checkVideoFilters(action)) {
                int views = 0;

                // Check if the video is found in each user's history, add the number of views
                for (UserInputData user : input.getUsers()) {
                    if (user.getHistory().containsKey(video.getTitle())) {
                        views += user.getHistory().get(video.getTitle());
                    }
                }
                // If the video has ben viewed at least once, add to map
                if (views != 0) {
                    viewsMap.put(video.getTitle(), views);
                }
            }
        }
        // Create a sorted list of video titles
        List<String> titleList = new MapMethods().getListInteger(viewsMap, queryNumber,
                action.getSortType());

        return  "Query result: " + titleList.toString();
    }

    /**
     * Return a sorted list of the videos that have appeared in user's favorite lists
     */
    public String getFavorite(final Input input, final ActionInputData action,
                              final List<? extends ShowInput> videoList) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > videoList.size()) {
            queryNumber = videoList.size();
        }

        // Create map of video titles and the number of times it has appeared on favorite list
        Map<String, Integer> favoriteMap = new HashMap<>();

        // Traverse videos
        for (ShowInput video : videoList) {
            // If the video has the year and genre given by query, calculate
            // how many times it has appeared on a user's favorite list
            if (video.checkVideoFilters(action)) {
                int favorite = 0;

                // Traverse user list
                for (UserInputData user : input.getUsers()) {
                    if (user.getFavoriteVideos().contains(video.getTitle())) {
                        favorite++;
                    }
                }
                // If the video has been found in at least one favorite list, add to map
                if (favorite != 0) {
                    favoriteMap.put(video.getTitle(), favorite);
                }
            }
        }

        // Create sorted list of video titles
        List<String> titleList = new MapMethods().getListInteger(favoriteMap, queryNumber,
                action.getSortType());
        return  "Query result: " + titleList.toString();
    }

}
