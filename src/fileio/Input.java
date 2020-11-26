package fileio;

import java.util.List;

/**
 * The class contains information about input
 * <p>
 * DO NOT MODIFY
 */
public final class Input {
    /**
     * List of actors
     */
    private final List<ActorInputData> actorsData;
    /**
     * List of users
     */
    private final List<UserInputData> usersData;
    /**
     * List of commands
     */
    private final List<ActionInputData> commandsData;
    /**
     * List of movies
     */
    private final List<MovieInputData> moviesData;
    /**
     * List of serials aka tv shows
     */
    private final List<SerialInputData> serialsData;

    public Input() {
        this.actorsData = null;
        this.usersData = null;
        this.commandsData = null;
        this.moviesData = null;
        this.serialsData = null;
    }

    public Input(final List<ActorInputData> actors, final List<UserInputData> users,
                 final List<ActionInputData> commands,
                 final List<MovieInputData> movies,
                 final List<SerialInputData> serials) {
        this.actorsData = actors;
        this.usersData = users;
        this.commandsData = commands;
        this.moviesData = movies;
        this.serialsData = serials;
    }

    public List<ActorInputData> getActors() {
        return actorsData;
    }

    public List<UserInputData> getUsers() {
        return usersData;
    }

    public List<ActionInputData> getCommands() {
        return commandsData;
    }

    public List<MovieInputData> getMovies() {
        return moviesData;
    }

    public List<SerialInputData> getSerials() {
        return serialsData;
    }

    /**
     * Get the requested movie
     */
    public MovieInputData getMovie(final String title) {
        assert moviesData != null;
        for (MovieInputData movie : moviesData) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Get the requested serial
     */
    public SerialInputData getSerial(final String title) {
        assert serialsData != null;
        for (SerialInputData serial : serialsData) {
            if (serial.getTitle().equals(title)) {
                return serial;

            }
        }
        return null;
    }

    /**
     * Get the requested user
     */
    public UserInputData getUser(final String username) {
        assert usersData != null;
        for (UserInputData user : usersData) {
            if (user.getUsername().equals(username)) {
                return user;

            }
        }
        return null;
    }

}
