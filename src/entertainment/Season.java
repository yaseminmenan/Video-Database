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

    private final Map<String, Double> userRatings;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
        this.ratings = new ArrayList<>();

        //MINE
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
     * Javadoc Comment
     */
    public double calculateAverage() {
        double average = 0;
        int i = 0;
        for (Map.Entry<String, Double> rating : userRatings.entrySet()) {
            average = average + rating.getValue();
            i++;
        }
        if (i != 0) {
            average = average / i;
        } else {
            average = 0;
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

