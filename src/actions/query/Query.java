package actions.query;

import fileio.ActionInputData;
import fileio.Input;

public final class Query {

    /**
     * Do query by action object type
     */
    public String doQuery(final Input input, final ActionInputData action) {
        String message = "";

        // Check the object type required for the query
        switch (action.getObjectType()) {
            case "actors":
                message = new QueryActors().doQuery(input, action);
                break;
            case "movies":
                message = new QueryMovies().doQuery(input, action);
                break;
            case "shows":
                message = new QueryShows().doQuery(input, action);
                break;
            case "users":
                message = new QueryUsers().doQuery(input, action);
                break;
            default:
                break;
        }
        return message;
    }

}
