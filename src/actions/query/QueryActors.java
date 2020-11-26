package actions.query;

import actor.ActorsAwards;
import common.MapMethods;
import entertainment.Season;
import fileio.*;

import java.util.*;

import static utils.Utils.stringToAwards;

public class QueryActors {
    /**
     * Do actor query by action criteria
     */
    public String doQuery(final Input input, final ActionInputData action) {
        String message = "";

        // Check criteria for the actor query
        switch (action.getCriteria()) {
            case "average":
                message = getAverage(input, action);
                break;
            case "awards":
                message = getAwards(input, action);
                break;
            case "filter_description":
                message = getFilter(input, action);
                break;
            default:
                break;
        }

        return message;
    }

    /**
     * Return a list of actors sorted by average rating of filmography
     */
    public String getAverage(final Input input, final ActionInputData action) {
        // Check if query limit is larger than the list's size
        int queryNumber = action.getNumber();
        if (queryNumber > input.getActors().size()) {
            queryNumber = input.getActors().size();
        }

        // Create map to insert actors and average grade
        Map<String, Double> actorMap = new HashMap<>();

        // Traverse actor list to calculate average grade
        for (ActorInputData actor : input.getActors()) {
            // Retain average grade of movies and shows
            double average = 0;
            // Retain number of movies and shows with nonzero grade
            int i = 0;

            // Traverse actor filmography
            for (String title : actor.getFilmography()) {

                // Check if video is a movie
                if (input.getMovies().contains(input.getMovie(title))) {
                    MovieInputData movie = input.getMovie(title);
                    assert movie != null;

                    //If the ratings map is not empty, calculate the average rating
                    if (!movie.getMovieRatings().isEmpty()) {
                        average += movie.calculateAverage();
                        i++;
                    }

                // Check if video is a show
                } else if (input.getSerials().contains(input.getSerial(title))) {
                    SerialInputData serial = input.getSerial(title);

                    //Retain serial average
                    double serialAverage = 0;

                    assert serial != null;
                    // Traverse season list for serial
                    for (Season season : serial.getSeasons()) {
                        // Calculate each season's average rating
                        serialAverage += season.calculateAverage();
                    }

                    // If the serial average is not zero, at least one season has been rated
                    if (serialAverage != 0) {
                        average += serialAverage / serial.getNumberSeason();
                        i++;
                    }
                }
            }
            if (i != 0) {
                actorMap.put(actor.getName(), average / i);
            }

        }
        // Create a sorted list with actor names
        List<String> nameList = new MapMethods().getListDouble(actorMap, queryNumber,
                action.getSortType());

        return  "Query result: " + nameList.toString();
    }

    /**
     * Return a list of actors that won the awards given in the query,
     * sorted by the total number of awards
     */
    public String getAwards(final Input input, final ActionInputData action) {
        // Create map to insert actors and number of awards
        Map<String, Integer> actorMap = new HashMap<>();

        // Traverse actor list
        for (ActorInputData actor : input.getActors()) {
            // Calculate if actor has all the query awards
            int i = 0;
            // Calculate total number of awards
            int awardsNumber = 0;

            // Traverse query awards list
            for (String awards : action.getFilters().get(3)) {
                // Increment if actor has won the award
                if (actor.getAwards().containsKey(stringToAwards(awards))) {
                    i++;
                }

            }
            // If the actor has all the query awards, insert actor and number of awards in map
            if (i == action.getFilters().get(3).size()) {
                for (Map.Entry<ActorsAwards, Integer> awards : actor.getAwards().entrySet()) {
                    awardsNumber += awards.getValue();
                }
                actorMap.put(actor.getName(), awardsNumber);
            }
        }

        // Sort the map
        actorMap = new MapMethods().sortIntegerMap(actorMap, action.getSortType());

        // Add actors' names to list
        List<String> nameList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : actorMap.entrySet()) {
            nameList.add(entry.getKey());
        }
        return  "Query result: " + nameList.toString();
    }

    /**
     * Return a list of actors that have in career description all the words
     * given by the query, sorted alphabetically
     */
    public String getFilter(final Input input, final ActionInputData action) {
        // Create actor list
        List<String> actorList = new ArrayList<>();

        // Traverse actor list
        for (ActorInputData actor : input.getActors()) {
            // Retain number of filter words that the description contains
            int i = 0;
            // Convert career description to array to check names
            String[] words = actor.getCareerDescription().split("\\W+");
            for (String keyword : action.getFilters().get(2)) {
                for (String word : words) {
                    // Make first character uppercase
                    if (word.toLowerCase().equals(keyword)) {
                        i++;
                        break;
                    }
                }
            }
            // If the actor has all the words in description
            if (i == action.getFilters().get(2).size()) {
                actorList.add(actor.getName());
            }

        }
        // Sort list alphabetically
        if (action.getSortType().equals("asc")) {
            actorList.sort(String::compareToIgnoreCase);
        } else {
            actorList.sort((d1, d2)
                    -> d2.compareToIgnoreCase(d1));
        }

        return  "Query result: " + actorList.toString();
    }
}
