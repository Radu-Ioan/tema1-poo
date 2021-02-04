package actions.commands;

import common.Constants;
import entertainment.Season;
import entertainment.Serial;
import repository.DataBase;
import actions.Action;

import java.util.HashMap;
import java.util.Map;

public class SerialRating extends Command implements Action {
    private final Serial serial;
    private final Season season;
    private final int seasonNumber;
    private Double rating;

    public SerialRating(final DataBase repo, final String username,
                        final String serialName, final Double rating,
                        final int seasonNumber) {
        super(repo, username, serialName);
        serial = (Serial) super.video;
        season = serial.getSeasons().get(seasonNumber - 1);
        this.seasonNumber = seasonNumber;
        this.rating = rating;
    }

    /**
     * Adauga un rating pentru un serial daca este posibil
     * @return un mesaj de eroare sau de succes
     */
    @Override
    public String returnMessage() {
        if (user.getViewedVideos().containsKey(serial.getTitle())) {
            Map<Integer, Map<String, Double>> seasonsRatings = serial.getRatingsByUsers();

            /*
             * Cazul in care a vazut serialul, dar nu a dat niciun rating inca,
             * deci nu a dat rating la niciun sezon.
             */
            if (!user.getSerialsRated().containsKey(serial.getTitle())) {

                user.getSerialsRated().put(serial.getTitle(), new HashMap<>());
                user.getSerialsRated().get(serial.getTitle())
                        .put(seasonNumber, rating);

                season.getRatings().add(rating);

                if (!seasonsRatings.containsKey(seasonNumber)) {
                    seasonsRatings.put(seasonNumber, new HashMap<>());
                }
                seasonsRatings.get(seasonNumber).put(user.getUsername(), rating);

                return Constants.SUCCESS + serial.getTitle()
                        + Constants.RATED_MESSAGE + rating
                        + " by " + user.getUsername();
            } else {

                /*
                 * Se verfica daca sezonul pentru care se da rating nu a primit deja unul
                 * de la acelasi utilizator.
                 */
                if (seasonsRatings.containsKey(seasonNumber)
                            && seasonsRatings.get(seasonNumber)
                                .containsKey(user.getUsername())) {
                    return Constants.ERROR + serial.getTitle()
                            + Constants.ALREADY_RATED;
                } else {

                    if (!seasonsRatings.containsKey(seasonNumber)) {
                        seasonsRatings.put(seasonNumber, new HashMap<>());
                    }

                    season.getRatings().add(rating);
                    seasonsRatings.get(seasonNumber).put(user.getUsername(), rating);

                    user.getSerialsRated().get(serial.getTitle())
                            .put(seasonNumber, rating);

                    return Constants.SUCCESS + serial.getTitle()
                            + Constants.RATED_MESSAGE + rating
                            + " by " + user.getUsername();
                }
            }
        } else {
            return Constants.ERROR + serial.getTitle() + Constants.UNSEEN_MESSAGE;
        }
    }
}
