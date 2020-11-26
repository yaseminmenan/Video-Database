package actions.command;

import fileio.*;

import java.util.Map;

public final class Command {
    public Command() {
    }

    /**
     * Do command by action type
     */
    public String doCommand(final Input input, final ActionInputData action) {
        String message = "";

        // Check action type
        switch (action.getType()) {
            case "favorite":
                message = addFavorite(input, action);
                break;
            case "view":
                message = addView(input, action);
                break;
            case "rating":
                message = addRating(input, action);
                break;
            default:
                break;
        }
        return message;
    }
    /**
     * Add video to favorite
     */
    public String addFavorite(final Input input, final ActionInputData action) {
        // Get video title
        String title = action.getTitle();
        // Get user
        UserInputData user = input.getUser(action.getUsername());

        // If the video isn't in the user's history, it cannot be added to favorites
        if (!user.getHistory().containsKey(title)) {
            return "error -> " + title + " is not seen";
        }

        // Check if the video has already been added to favorites
        if (user.getFavoriteVideos().contains(title)) {
            return "error -> " + title + " is already in favourite list";
        }

        // Add video to favorites
        user.addFavoriteVideo(title);
        return "success -> " + title + " was added as favourite";
    }

    /**
     * Add video to history
     */
    public String addView(final Input input, final ActionInputData action) {
        // Get video title
        String title = action.getTitle();
        // Get user
        UserInputData user = input.getUser(action.getUsername());

        // If the video is already in history, update the number of views
        if (user.getHistory().containsKey(title)) {
            user.updateHistory(action.getTitle());
            return "success -> " + title + " was viewed with total views of "
                    + user.getHistory().get(title);
        }

        // Add video to history
        user.addToHistory(title);
        return "success -> " + title + " was viewed with total views of 1";
    }

    /**
     * Add video rating
     */
    public String addRating(final Input input, final ActionInputData action) {
        String message;
        String title = action.getTitle();
        UserInputData user = input.getUser(action.getUsername());

        // If the video is a movie
        if (input.getMovies().contains(input.getMovie(title))) {
            MovieInputData movie = input.getMovie(title);

            // Check if the serial has already been rated
            if (!movie.getMovieRatings().isEmpty()) {
                if (movie.getMovieRatings().containsKey(action.getUsername())) {
                    message = "error -> " + title + " has been already rated";
                    return message;
                }
            }
          // If the video is a serial
        } else if (input.getSerials().contains(input.getSerial(title))) {
                SerialInputData serial = input.getSerial(title);

                // Check if the serial has already been rated
                if (serial.getSeason(action.getSeasonNumber()).
                        getUserRatings().containsKey(action.getUsername())) {
                    message = "error -> " + title + " has been already rated";
                    return message;
                }
            }

        // Check if video is in user's history
        if(user.getHistory().containsKey(title)){
            user.addRatingsNumber();
            // Add user's rating grade
             if (input.getMovies().contains(input.getMovie(title))) {
                input.getMovie(title).addToMovieRatings(user.getUsername(), action.getGrade());

             } else if (input.getSerials().contains(input.getSerial(title))) {
                    input.getSerial(title).setRating(action);
             }
             return "success -> " + title + " was rated with " + action.getGrade()
                        + " by " + action.getUsername();
        }

        // Video is not in user's history, cannot add rating
        return "error -> " + title + " is not seen";

    }

}
