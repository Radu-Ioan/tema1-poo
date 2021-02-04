package actions.commands;

import entertainment.Video;
import repository.DataBase;
import repository.User;

/**
 * Contine referinte comune pentru cele 3
 * comenzi concrete care o vor extinde: View, Favorite si Rating
 */
public class Command {
    /**
     * Utilizatorul care comanda
     */
    protected final User user;
    /**
     * Filmul sau serialul care sunt prelucrate
     */
    protected Video video;

    protected Command(final DataBase repo, final String username,
                      final String videoName) {
        this.user = repo.getUsers().get(username);
        this.video = repo.getVideos().get(videoName);
    }
}
