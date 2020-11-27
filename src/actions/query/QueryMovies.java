package actions.query;

import common.MapMethods;
import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QueryMovies extends QueryVideos {

    /**
     * Do movie query by action criteria
     */
    public String doQuery(final Input input, final ActionInputData action) {
        String message = "";

        // Check criteria for the movie query
        switch (action.getCriteria()) {
            case "ratings":
                message = getRatings(input, action);
                break;
            case "favorite":
                message = getFavorite(input, action, input.getMovies());
                break;
            case "longest":
                message = getLongest(input, action);
                break;
            case "most_viewed":
                message = getViewed(input, action, input.getMovies());
                break;
            default:
                break;
        }
        return message;
    }

    /**
     * Return a list of movies sorted by average rating
     */
    public String getRatings(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getMovies().size()) {
            queryNumber = input.getMovies().size();
        }

        // Create map of movie titles and average ratings
        Map<String, Double> movieMap = new HashMap<>();

        // Traverse movie list
        for (MovieInputData movie : input.getMovies()) {
            // If the movie has the year and genre given by query
            if (movie.checkVideoFilters(action)) {
                // If the movie has been rated at least once,
                // calculate average and add to map
                if (!movie.getMovieRatings().isEmpty()) {
                    movieMap.put(movie.getTitle(), movie.calculateAverage());
                }
            }
        }

        // Create sorted list of movie titles
        List<String> titleList = new MapMethods().getListDouble(movieMap, queryNumber,
                action.getSortType());

        return "Query result: " + titleList.toString();
    }


    /**
     * Return a list of movies sorted by duration
     */
    public String getLongest(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getMovies().size()) {
            queryNumber = input.getMovies().size();
        }
        // Create map of movies titles and durations
        Map<String, Integer> movieMap = new HashMap<>();

        // Traverse movie list
        for (MovieInputData movie : input.getMovies()) {
            // If the movie has the year and genre given by query, add to map
            if (movie.checkVideoFilters(action)) {
                movieMap.put(movie.getTitle(), movie.getDuration());
            }
        }

        // Create sorted list of movie titles
        List<String> titleList = new MapMethods().getListInteger(movieMap, queryNumber,
                action.getSortType());
        return  "Query result: " + titleList.toString();
    }

}
