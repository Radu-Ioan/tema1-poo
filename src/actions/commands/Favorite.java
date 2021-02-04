package actions.commands;

import common.Constants;
import repository.DataBase;
import actions.Action;

public class Favorite extends Command implements Action {

    public Favorite(final DataBase repo, final String username,
                    final String videoName) {
        super(repo, username, videoName);
    }

    /**
     * Adauga un video in multimea de favorite a unui utilizator,
     * in caz ca e posibil
     */
    @Override
    public String returnMessage() {
        if (user.getViewedVideos().containsKey(video.getTitle())) {
            if (!user.getFavoriteVideos().contains(video.getTitle())) {
                user.getFavoriteVideos().add(video.getTitle());
            } else {
                return Constants.ERROR + video.getTitle()
                        + " is already in favourite list";
            }
        } else {
            return Constants.ERROR + video.getTitle()
                    + Constants.UNSEEN_MESSAGE;
        }

        return Constants.SUCCESS + video.getTitle()
                + Constants.FAVORITE_MESSAGE;
    }
}
