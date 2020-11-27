package actions.query;

import common.MapMethods;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.Input;
import fileio.SerialInputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryShows extends QueryVideos {
    /**
     * Do serial query by action criteria
     */
    public String doQuery(final Input input, final ActionInputData action) {
        String message = "";

        switch (action.getCriteria()) {
            case "ratings":
                message = getRatings(input, action);
                break;
            case "favorite":
                message = getFavorite(input, action, input.getSerials());
                break;
            case "longest":
                message = getLongest(input, action);
                break;
            case "most_viewed":
                message = getViewed(input, action, input.getSerials());
                break;
            default:
                break;
        }
        return message;
    }
    /**
     * Return a list of shows sorted by average rating
     */
    public String getRatings(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getSerials().size()) {
            queryNumber = input.getSerials().size();
        }
        // Create map of serial titles and average ratings
        Map<String, Double> showMap = new HashMap<>();

        // Traverse serial list
        for (SerialInputData serial : input.getSerials()) {
            // If the show has the year and genre given by query
            if (serial.checkVideoFilters(action)) {
                double serialAverage = 0;

                // Calculate each season's average rating and add to serial average
                for (Season season : serial.getSeasons()) {
                    serialAverage += season.calculateAverage();
                }
                // If at least one season has been rated, add serial to map
                if (serialAverage != 0) {
                    showMap.put(serial.getTitle(), serialAverage / serial.getNumberSeason());
                }

            }
        }
        // Create sorted list of show titles
        List<String> titleList = new MapMethods().getListDouble(showMap, queryNumber,
                action.getSortType());

        return  "Query result: " + titleList.toString();
    }

    /**
     * Return a list of serials sorted by duration
     */
    public String getLongest(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getSerials().size()) {
            queryNumber = input.getSerials().size();
        }

        // Create map of serial titles and durations
        Map<String, Integer> durationMap = new HashMap<>();

        // Traverse serial list
        for (SerialInputData serial : input.getSerials()) {
            // If the serial has the year and genre given by query, add to map
            if (serial.checkVideoFilters(action)) {
                int duration = 0;
                for (Season season : serial.getSeasons()) {
                    duration += season.getDuration();
                }
                durationMap.put(serial.getTitle(), duration);
            }
        }

        // Create sorted list of serial titles
        List<String> titleList = new MapMethods().getListInteger(durationMap, queryNumber,
                action.getSortType());
        return  "Query result: " + titleList.toString();
    }

}
