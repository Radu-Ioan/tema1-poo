package actions.recommendations;

import entertainment.Video;
import repository.User;

import java.util.Map;

/**
 * Contine informatii comune (nu pe toate) pentru fiecare tip
 * de recomandare
 */
public class Recommendation {
    protected User user;
    protected Map<String, Video> videos;

    protected Recommendation(final User user,
                          final Map<String, Video> videos) {
        this.user = user;
        this.videos = videos;
    }
}
