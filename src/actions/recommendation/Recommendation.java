package actions.recommendation;

import common.MapMethods;
import entertainment.Season;
import fileio.*;

import java.util.*;

public class Recommendation {

    /**
     * Javadoc Comment
     */
    public String doRecommendation(final Input input, final ActionInputData action) {
        String message = "";
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
     * Javadoc Comment
     */
    public String getStandard(final Input input, final ActionInputData action) {
        String message = "StandardRecommendation cannot be applied!";
        for (MovieInputData movie : input.getMovies()) {
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                message = "StandardRecommendation result: " + movie.getTitle();
                return message;
            }
        }
        for (SerialInputData serial : input.getSerials()) {
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                message = "StandardRecommendation result: " + serial.getTitle();
                return message;
            }
        }

        return message;
    }

    /**
     * Javadoc Comment
     */
    public String getBestUnseen(final Input input, final ActionInputData action) {
        String message = "BestRatedUnseenRecommendation cannot be applied!";

        LinkedHashMap<String, Double> movieMap = new LinkedHashMap<>();
        for (MovieInputData movie : input.getMovies()) {
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                movieMap.put(movie.getTitle(), movie.calculateAverage());
            }
        }

        if (!movieMap.isEmpty()) {
            String title = movieMap.entrySet().iterator().next().getKey();
            double value = movieMap.entrySet().iterator().next().getValue();
            for (Map.Entry<String, Double> entry : movieMap.entrySet()) {
                if (entry.getValue() > value) {
                    title = entry.getKey();
                    value = entry.getValue();
                }
            }
            return "BestRatedUnseenRecommendation result: " + title;
        }

        LinkedHashMap<String, Double> serialMap = new LinkedHashMap<>();
        for (SerialInputData serial : input.getSerials()) {
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                double serialAverage = 0;
                for (Season season : serial.getSeasons()) {
                    serialAverage += season.calculateAverage();
                }
                if (serialAverage != 0) {
                    serialMap.put(serial.getTitle(), serialAverage / serial.getNumberSeason());
                }
            }
        }

        if (!serialMap.isEmpty()) {
            String title = serialMap.entrySet().iterator().next().getKey();
            double value = serialMap.entrySet().iterator().next().getValue();
            for (Map.Entry<String, Double> entry : serialMap.entrySet()) {
                if (entry.getValue() > value) {
                    title = entry.getKey();
                    value = entry.getValue();
                }
            }
            return "BestRatedUnseenRecommendation result: " + title;
        }

        return message;
    }

    /**
     * Javadoc Comment
     */
    public String getPopular(final Input input, final ActionInputData action) {
        String message = "PopularRecommendation cannot be applied!";

        if (input.getUser(action.getUsername()).getSubscriptionType().equals("BASIC")) {
            message = "PopularRecommendation cannot be applied!";
            return message;
        }
        Map<String, Integer> genreMap = new MapMethods().getGenreMap();

        for (UserInputData user : input.getUsers()) {
            for (Map.Entry<String, Integer> entry : user.getHistory().entrySet()) {
                if (input.getMovies().contains(input.getMovie(entry.getKey()))) {
                    for (String genre : input.getMovie(entry.getKey()).getGenres()) {
                        genreMap.put(genre, genreMap.get(genre) + entry.getValue());
                    }

                } else {
                    for (String genre : input.getSerial(entry.getKey()).getGenres()) {
                        genreMap.put(genre, genreMap.get(genre) + entry.getValue());
                    }
                }

            }
        }
        genreMap = new MapMethods().sortIntegerMap(genreMap, "desc");

        for (Map.Entry<String, Integer> genres : genreMap.entrySet()) {
            for (MovieInputData movie : input.getMovies()) {
                if (movie.getGenres().contains(genres.getKey())) {
                    if (!input.getUser(action.getUsername()).getHistory().
                            containsKey(movie.getTitle())) {
                        message = "PopularRecommendation result: " + movie.getTitle();
                        return message;
                    }
                }
            }
            for (SerialInputData serial : input.getSerials()) {
                if (serial.getGenres().contains(genres.getKey())) {
                    if (!input.getUser(action.getUsername()).getHistory().
                            containsKey(serial.getTitle())) {
                        message = "PopularRecommendation result: " + serial.getTitle();
                        return message;
                    }
                }

            }
        }
        return message;
    }

    /**
     * Javadoc Comment
     */
    public String getFavorite(final Input input, final ActionInputData action) {
        String message = "FavoriteRecommendation cannot be applied!";
        if (input.getUser(action.getUsername()).getSubscriptionType().equals("BASIC")) {
            message = "FavoriteRecommendation cannot be applied!";
            return message;
        }

        LinkedHashMap<String, Integer> videoMap = new LinkedHashMap<>();
        for (MovieInputData movie : input.getMovies()) {
            int isFavorite = 0;
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                for (UserInputData user : input.getUsers()) {
                    if (!user.getUsername().equals(action.getUsername())) {
                        if (user.getFavoriteVideos().contains(movie.getTitle())) {
                            isFavorite++;
                        }
                    }
                }
                if (isFavorite != 0) {
                    videoMap.put(movie.getTitle(), isFavorite);
                }

            }
        }
        for (SerialInputData serial : input.getSerials()) {
            int isFavorite = 0;
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                for (UserInputData user : input.getUsers()) {
                    if (!user.getUsername().equals(action.getUsername())) {
                        if (user.getFavoriteVideos().contains(serial.getTitle())) {
                            isFavorite++;
                        }
                    }
                }
                if (isFavorite != 0) {
                    videoMap.put(serial.getTitle(), isFavorite);
                }

            }
        }

        if (!videoMap.isEmpty()) {
            String title = videoMap.entrySet().iterator().next().getKey();
            int value = videoMap.entrySet().iterator().next().getValue();
            for (Map.Entry<String, Integer> entry : videoMap.entrySet()) {
                if (entry.getValue() > value) {
                    title = entry.getKey();
                    value = entry.getValue();
                }
            }
            message = "FavoriteRecommendation result: " + title;
            return message;
        }

        return message;
    }

    /**
     * Javadoc Comment
     */
    public String getSearch(final Input input, final ActionInputData action) {
        String message = "SearchRecommendation cannot be applied!";
        if (input.getUser(action.getUsername()).getSubscriptionType().equals("BASIC")) {
            message = "SearchRecommendation cannot be applied!";
            return message;
        }
        Map<String, Double> searchMap = new HashMap<>();

        for (MovieInputData movie : input.getMovies()) {
            if (!input.getUser(action.getUsername()).getHistory().containsKey(movie.getTitle())) {
                if (movie.getGenres().contains(action.getGenre())) {
                    searchMap.put(movie.getTitle(), movie.calculateAverage());
                }
            }

        }
        for (SerialInputData serial : input.getSerials()) {
            if (!input.getUser(action.getUsername()).getHistory().containsKey(serial.getTitle())) {
                if (serial.getGenres().contains(action.getGenre())) {
                    double serialAverage = 0;
                    for (Season season : serial.getSeasons()) {
                        serialAverage += season.calculateAverage();
                    }
                    searchMap.put(serial.getTitle(), serialAverage / serial.getNumberSeason());
                }
            }

        }
        if (!searchMap.isEmpty()) {
            searchMap = new MapMethods().sortDoubleMap(searchMap, "asc");
            List<String> namesList = new ArrayList<>();
            for (Map.Entry<String, Double> entry : searchMap.entrySet()) {
                namesList.add(entry.getKey());
            }
            message = "SearchRecommendation result: " + namesList.toString();
        }

        return message;
    }

}
