package actions.recommendation;

import common.MapMethods;
import entertainment.Season;
import fileio.ActionInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Recommendation {

    /**
     * Do recommendation by action type
     */
    public String doRecommendation(final Input input, final ActionInputData action) {
        String message = "";

        // Check action type for recommendation
        switch (action.getType()) {
            case "standard":
                message = getStandard(input, action);
                break;
            case "best_unseen":
                message = getBestUnseen(input, action);
                break;
            case "popular":
                message = getPopular(input, action);
                break;
            case "favorite":
                message = getFavorite(input, action);
                break;
            case "search":
                message = getSearch(input, action);
                break;
            default:
                break;
        }
        return message;
    }

    /**
     * Return first unseen video
     */
    public String getStandard(final Input input, final ActionInputData action) {

        // Traverse the movie list for first unseen movie
        for (MovieInputData movie : input.getMovies()) {
            // If the movie isn't in the user's history, return the movie
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                return "StandardRecommendation result: " + movie.getTitle();
            }
        }

        // Traverse the serial list for first unseen serial
        for (SerialInputData serial : input.getSerials()) {
            // If the serial isn't in the user's history, return the serial
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                return "StandardRecommendation result: " + serial.getTitle();
            }
        }

        // The user has seen all the videos
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Return the highest rated unseen video
     */
    public String getBestUnseen(final Input input, final ActionInputData action) {
        // Create movie map for unseen movies and their average rating
        LinkedHashMap<String, Double> movieMap = new LinkedHashMap<>();

        // Traverse movie list
        for (MovieInputData movie : input.getMovies()) {
            // If the movie isn't in the user's history, add to map
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                movieMap.put(movie.getTitle(), movie.calculateAverage());
            }
        }

        // Check if movie map is empty
        if (!movieMap.isEmpty()) {
            String title = movieMap.entrySet().iterator().next().getKey();
            double value = movieMap.entrySet().iterator().next().getValue();

            // Find the highest rated movie and return
            for (Map.Entry<String, Double> entry : movieMap.entrySet()) {
                if (entry.getValue() > value) {
                    title = entry.getKey();
                    value = entry.getValue();
                }
            }
            return "BestRatedUnseenRecommendation result: " + title;
        }

        // Create serial map for unseen serials and their average rating
        LinkedHashMap<String, Double> serialMap = new LinkedHashMap<>();

        // Traverse serial map
        for (SerialInputData serial : input.getSerials()) {
            // If the serial isn't in the user's history, add to map
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                double serialAverage = 0;

                // Calculate and add each season's average rating
                for (Season season : serial.getSeasons()) {
                    serialAverage += season.calculateAverage();
                }

                // If at least one season has been rated
                if (serialAverage != 0) {
                    serialMap.put(serial.getTitle(), serialAverage / serial.getNumberSeason());
                }
            }
        }

        // Check if serial map is empty
        if (!serialMap.isEmpty()) {
            String title = serialMap.entrySet().iterator().next().getKey();
            double value = serialMap.entrySet().iterator().next().getValue();

            // Find the highest rated serial and return
            for (Map.Entry<String, Double> entry : serialMap.entrySet()) {
                if (entry.getValue() > value) {
                    title = entry.getKey();
                    value = entry.getValue();
                }
            }
            return "BestRatedUnseenRecommendation result: " + title;
        }

        // The user has seen all the videos
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Return the first unseen movie of the most popular genre
     */
    public String getPopular(final Input input, final ActionInputData action) {
        // If the user's subscription is not premium, the recommendation cannot be applied
        if (input.getUser(action.getUsername()).getSubscriptionType().equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }

        // Initialise a map of video genres
        Map<String, Integer> genreMap = new MapMethods().getGenreMap();

        // Traverse users' histories and add to map values
        for (UserInputData user : input.getUsers()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                // Check if video is movie
                if (input.getMovies().contains(input.getMovie(entry.getKey()))) {
                    for (String genre : input.getMovie(entry.getKey()).getGenres()) {
                        genreMap.put(genre, genreMap.get(genre) + entry.getValue());
                    }
                // Check if video is serial
                } else {
                    for (String genre : input.getSerial(entry.getKey()).getGenres()) {
                        genreMap.put(genre, genreMap.get(genre) + entry.getValue());
                    }
                }

            }
        }
        // Sort the genre map
        genreMap = new MapMethods().sortIntegerMap(genreMap, "desc");

        // Traverse the sorted genre map
        for (Map.Entry<String, Integer> genres : genreMap.entrySet()) {
            for (MovieInputData movie : input.getMovies()) {
                // If the movie contains the genre, check if it hasn't been seen and return title
                if (movie.getGenres().contains(genres.getKey())) {
                    if (!input.getUser(action.getUsername()).getHistory().
                            containsKey(movie.getTitle())) {
                        return "PopularRecommendation result: " + movie.getTitle();
                    }
                }
            }
            for (SerialInputData serial : input.getSerials()) {
                // If the movie contains the genre, check if it hasn't been seen and return title
                if (serial.getGenres().contains(genres.getKey())) {
                    if (!input.getUser(action.getUsername()).getHistory().
                            containsKey(serial.getTitle())) {
                        return "PopularRecommendation result: " + serial.getTitle();
                    }
                }

            }
        }

        // The user has seen all the videos
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Return the video that is the most common on the users' favorites that the user has not seen
     */
    public String getFavorite(final Input input, final ActionInputData action) {
        // If the user's subscription is not premium, the recommendation cannot be applied
        if (input.getUser(action.getUsername()).getSubscriptionType().equals("BASIC")) {
            return "FavoriteRecommendation cannot be applied!";
        }

        // Create map
        LinkedHashMap<String, Integer> videoMap = new LinkedHashMap<>();

        // Traverse movie list
        for (MovieInputData movie : input.getMovies()) {
            // Retain the number of times the video has appeared in favorites
            int isFavorite = 0;
            // If the user hasn't seen the movie, check how many times it
            // appears in users' favorites
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                for (UserInputData user : input.getUsers()) {
                    if (user.getFavoriteVideos().contains(movie.getTitle())) {
                        isFavorite++;
                    }
                }
                // If the movie has appeared at least once, add to map
                if (isFavorite != 0) {
                    videoMap.put(movie.getTitle(), isFavorite);
                }

            }
        }
        // Traverse serial list
        for (SerialInputData serial : input.getSerials()) {
            // Retain the number of times the video has appeared in favorites
            int isFavorite = 0;
            // If the user hasn't seen the serial, check how many times it
            // appears in users' favorites
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                for (UserInputData user : input.getUsers()) {
                    if (user.getFavoriteVideos().contains(serial.getTitle())) {
                        isFavorite++;
                    }
                }
                // If the serial has appeared at least once, add to map
                if (isFavorite != 0) {
                    videoMap.put(serial.getTitle(), isFavorite);
                }

            }
        }

        // Check if the map is not empty
        if (!videoMap.isEmpty()) {
            String title = videoMap.entrySet().iterator().next().getKey();
            int value = videoMap.entrySet().iterator().next().getValue();

            // Find the video with the most appearances in favorites and return the title
            for (Map.Entry<String, Integer> entry : videoMap.entrySet()) {
                if (entry.getValue() > value) {
                    title = entry.getKey();
                    value = entry.getValue();
                }
            }
            return "FavoriteRecommendation result: " + title;
        }

        // The user has seen all the videos
        return "FavoriteRecommendation cannot be applied!";
    }

    /**
     * Returns a list of videos that contain a given genre sorted by average ratings
     */
    public String getSearch(final Input input, final ActionInputData action) {
        // If the user's subscription is not premium, the recommendation cannot be applied
        if (input.getUser(action.getUsername()).getSubscriptionType().equals("BASIC")) {
            return "SearchRecommendation cannot be applied!";
        }
        // Create map for videos and average ratings
        Map<String, Double> searchMap = new HashMap<>();

        // Traverse movie map
        for (MovieInputData movie : input.getMovies()) {
            // If the user hasn't seen the movie, check that the movie contains
            // the given genre and add to map
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                if (movie.getGenres().contains(action.getGenre())) {
                    searchMap.put(movie.getTitle(), movie.calculateAverage());
                }
            }

        }
        // Traverse serial map
        for (SerialInputData serial : input.getSerials()) {
            // If the user hasn't seen the serial, check that the serial contains
            // the given genre and add to map
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                if (serial.getGenres().contains(action.getGenre())) {
                    double serialAverage = 0;
                    // Calculate and add the season's average rating
                    for (Season season : serial.getSeasons()) {
                        serialAverage += season.calculateAverage();
                    }
                    // Add serial to map
                    searchMap.put(serial.getTitle(), serialAverage / serial.getNumberSeason());
                }
            }

        }

        // Check if the map if empty
        if (!searchMap.isEmpty()) {
            // Sort the map
            searchMap = new MapMethods().sortDoubleMap(searchMap, "asc");
            List<String> namesList = new ArrayList<>();
            // Insert the sorted title in the list
            for (Map.Entry<String, Double> entry : searchMap.entrySet()) {
                namesList.add(entry.getKey());
            }
            return "SearchRecommendation result: " + namesList.toString();
        }

        // The user has seen all the videos
        return "SearchRecommendation cannot be applied!";
    }

}
