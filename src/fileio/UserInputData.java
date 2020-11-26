package fileio;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    /**
     * Track number of videos rated
     */
    private int ratingsNumber;

    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;

        this.ratingsNumber = 0;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteVideos() {
        return favoriteMovies;
    }


    /**
     * Add video to favorites
     */
    public void addFavoriteVideo(final String video) {
        this.favoriteMovies.add(video);
    }

    /**
     * Add video to history
     */
    public void addToHistory(final String video) {
        this.history.put(video, 1);
    }

    /**
     * Update the view number
     */
    public void updateHistory(final String video) {
        this.history.put(video, this.history.get(video) + 1);
    }

    /**
     * Increase the number of videos rated
     */
    public void addRatingsNumber() {
        ratingsNumber++;
    }

    public int getRatingsNumber() {
        return ratingsNumber;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
