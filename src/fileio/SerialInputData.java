package fileio;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;
    /**
     * List of ratings for each season
     */
    private List<Double> ratings;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * Set the rating for the requested season
     */
    public void setRating(final ActionInputData action) {
        for (Season season : seasons) {
            if (season.getCurrentSeasonNumber() == action.getSeasonNumber()) {
                season.getUserRatings().put(action.getUsername(), action.getGrade());
            }
        }
    }

    /**
     * Get the requested season
     */
    public Season getSeason(final int seasonNumber) {
        for (Season season : seasons) {
            if (season.getCurrentSeasonNumber() == seasonNumber) {
                return season;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
