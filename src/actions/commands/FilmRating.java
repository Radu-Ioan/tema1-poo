package actions.commands;

import common.Constants;
import entertainment.Film;
import repository.DataBase;
import actions.Action;

public class FilmRating extends Command implements Action {
    private Double rating;

    public FilmRating(final DataBase repo, final String username,
                      final String filmName, final Double rating) {
        super(repo, username, filmName);
        this.rating = rating;
    }

    /**
     * Adauga un grad de rating unui film daca este posibil
     * @return un mesaj care descrie efectul
     */
    @Override
    public String returnMessage() {
        if (user.getViewedVideos().containsKey(video.getTitle())) {
            if (user.getFilmsRated().containsKey(video.getTitle())) {
                return Constants.ERROR + video.getTitle()
                        + Constants.ALREADY_RATED;
            } else {
                user.getFilmsRated().put(video.getTitle(), rating);
                ((Film) video).getRatings().add(rating);
                return Constants.SUCCESS + video.getTitle()
                        + Constants.RATED_MESSAGE + rating
                        + " by " + user.getUsername();
            }
        } else {
            return Constants.ERROR + video.getTitle() + Constants.UNSEEN_MESSAGE;
        }
    }
}
