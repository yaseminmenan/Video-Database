package entertainment;

//import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {
    /**
     * Number of current season
     */
    private final int currentSeason;
    /**
     * Duration in minutes of a season
     */
    private int duration;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;
    /**
     * Map that tracks users and their rating
     */
    private final Map<String, Double> userRatings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();

        this.userRatings = new HashMap<>();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(final List<Double> ratings) {
        this.ratings = ratings;
    }

    public int getCurrentSeasonNumber() {
        return currentSeason;
    }

    public Map<String, Double> getUserRatings() {
        return userRatings;
    }

    /**
     * Calculate the average rating of a season
     */
    public double calculateAverage() {
        double average = 0;
        int i = 0;
        // Add all ratings
        for (Map.Entry<String, Double> rating : userRatings.entrySet()) {
            average = average + rating.getValue();
            i++;
        }
        // If the season has been rated at least once, calculate average
        if (i != 0) {
            average = average / i;
        }
        return average;
    }
    @Override
    public String toString() {
        return "Episode{"
                + "currentSeason="
                + currentSeason
                + ", duration="
                + duration
                + '}';
    }
}

