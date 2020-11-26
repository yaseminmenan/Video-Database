package fileio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    /**
     * Map that retains the user that rated the movie and the rating grade
     */
    private final Map<String, Double> movieRatings;

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;

        this.movieRatings = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    /**
     * Insert rating to movie map
     */
    public void addToMovieRatings(final String username, final double grade) {
        this.movieRatings.put(username, grade);
    }

    public Map<String, Double> getMovieRatings() {
        return movieRatings;
    }

    /**
     * Calculate the ratings average for movie
     */
    public double calculateAverage() {
        double average = 0;
        int i = 0;
        for (Map.Entry<String, Double> rating : movieRatings.entrySet()) {
            average += rating.getValue();
            i++;
        }
        if (i != 0) {
            average = average / i;
        }
        return average;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
