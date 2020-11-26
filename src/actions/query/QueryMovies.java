package actions.query;

import common.MapMethods;
import fileio.*;

import java.util.*;

public class QueryMovies {

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
                message = getFavorite(input, action);
                break;
            case "longest":
                message = getLongest(input, action);
                break;
            case "most_viewed":
                message = getViewed(input, action);
                break;
            default:
                break;
        }
        return message;
    }

    /**
     * Return a list of movies sorted by number of views
     */
   public String getViewed(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getMovies().size()) {
            queryNumber = input.getMovies().size();
        }
        // Create map of movie titles and number of views
        Map<String, Integer> viewsMap = new HashMap<>();

        // Traverse movie list
        for (MovieInputData movie : input.getMovies()) {
            // If the movie has the year and genre given by query
            if (movie.checkVideoFilters(action)) {
                int views = 0;

                // Check if the movie is found in each user's history, add the number of views
                for (UserInputData user : input.getUsers()) {
                    if (user.getHistory().containsKey(movie.getTitle())) {
                        views += user.getHistory().get(movie.getTitle());
                        }
                }
                // If the movie has ben viewed at least once, add to map
                if (views != 0) {
                    viewsMap.put(movie.getTitle(), views);
                }
            }
        }
        // Create a sorted list of movie titles
        List<String> titleList = new MapMethods().getListInteger(viewsMap, queryNumber,
                action.getSortType());
        return "Query result: " + titleList.toString();
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

    /**
     * Return a sorted list of the movies that have appeared in user's favorite lists
     */
    public String getFavorite(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getMovies().size()) {
            queryNumber = input.getMovies().size();
        }

        // Create map of movie titles and the number of times it has appeared on favorite list
        Map<String, Integer> favoriteMap = new HashMap<>();

        // Traverse movie list
        for (MovieInputData movie : input.getMovies()) {
            // If the movie has the year and genre given by query, calculate
            // how many times it has appeared on a user's favorite list
            if (movie.checkVideoFilters(action)) {
                int favorite = 0;

                // Traverse user list
                for (UserInputData user : input.getUsers()) {
                    if (user.getFavoriteVideos().contains(movie.getTitle())) {
                        favorite++;
                    }
                }
                // If the serial has been found in at least one favorite list, add to map
                if (favorite != 0) {
                    favoriteMap.put(movie.getTitle(), favorite);
                }
            }
        }

        // Create sorted list of movie titles
        List<String> titleList = new MapMethods().getListInteger(favoriteMap, queryNumber,
                action.getSortType());
        return "Query result: " + titleList.toString();
    }

}
