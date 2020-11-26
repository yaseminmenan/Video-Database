package fileio;

import java.util.ArrayList;

import static common.Constants.GENRE_INDEX;
import static common.Constants.YEAR_INDEX;
import static common.Constants.FIRST_INDEX;

/**
 * General information about show (video), retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public abstract class ShowInput {
    /**
     * Show's title
     */
    private final String title;
    /**
     * The year the show was released
     */
    private final int year;
    /**
     * Show casting
     */
    private final ArrayList<String> cast;
    /**
     * Show genres
     */
    private final ArrayList<String> genres;

    public ShowInput(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * Check if the video contains the specified year and genre
     */
    public final boolean checkVideoFilters(final ActionInputData action) {
        boolean yearCheck = true;
        boolean genreCheck = true;

        // Check if we are given a year in filter
        if (action.getFilters().get(YEAR_INDEX).get(FIRST_INDEX) != null) {
            // If the video's year is different, set to false
            if (!action.getFilters().get(0).contains(Integer.toString(this.getYear()))) {
                yearCheck = false;
            }
        }
        // Check if we are given a genre in filter
        if (action.getFilters().get(GENRE_INDEX).get(FIRST_INDEX) != null) {
            // If the video's genre is different, set to false
            for (String genre : action.getFilters().get(1)) {
                if (!this.getGenres().contains(genre)) {
                    genreCheck = false;
                    break;
                }
            }
        }
        return yearCheck && genreCheck;
    }
}
