package actions.recommendations.premium;

import common.Constants;
import entertainment.Video;
import repository.User;
import actions.Action;
import actions.recommendations.PositionalPair;
import actions.recommendations.Recommendation;

import java.util.HashMap;
import java.util.Map;

public class MostSavedVideo extends Recommendation implements Action {
    private Map<String, User> users;
    private Map<String, Integer> appearances;
    private PositionalPair<String, Integer> max;

    public MostSavedVideo(final User user, final Map<String, Video> videos,
                          final Map<String, User> users) {
        super(user, videos);
        this.users = users;
        appearances = new HashMap<>();
        max = new PositionalPair<>(null, -1, -1);
    }

    /**
     * @return video-ul cel mai des intalnit in lista de favorite a
     * utilizatorilor
     */
    @Override
    public String returnMessage() {
        if (!user.getSubscription().equals(Constants.PREMIUM)) {
            return Constants.FAVORITE_RECOMMENDATION + Constants.FAILED_RECOMMENDATION;
        }

        int i = 0;

        for (Map.Entry<String, Video> videoEntry : videos.entrySet()) {
            if (user.getViewedVideos().containsKey(videoEntry.getKey())) {
                continue;
            }

            for (Map.Entry<String, User> userEntry : users.entrySet()) {
                if (userEntry.getValue().getFavoriteVideos()
                        .contains(videoEntry.getKey())) {
                    if (!appearances.containsKey(videoEntry.getKey())) {
                        appearances.put(videoEntry.getKey(), 1);
                    } else {
                        appearances.replace(videoEntry.getKey(),
                                appearances.get(videoEntry.getKey()) + 1);
                    }

                    int comp = appearances.get(videoEntry.getKey()) - max.getValue();
                    if (comp > 0) {
                        max.setKey(videoEntry.getKey());
                        max.setValue(appearances.get(videoEntry.getKey()));
                        max.setPosition(i);
                    }
                }
            }
            ++i;
        }

        if (max.getKey() == null) {
            return Constants.FAVORITE_RECOMMENDATION + Constants.FAILED_RECOMMENDATION;
        }

        return Constants.FAVORITE_RECOMMENDATION + "result: " + max.getKey();
    }
}
