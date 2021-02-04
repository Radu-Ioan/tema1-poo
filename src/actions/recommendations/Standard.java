package actions.recommendations;

import common.Constants;
import entertainment.Video;
import repository.User;
import actions.Action;

import java.util.Map;

public class Standard extends Recommendation implements Action {

    public Standard(final User user, final Map<String, Video> videos) {
        super(user, videos);
    }

    /**
     * Întoarce primul video nevăzut de un utilizator din baza de date.
     */
    @Override
    public String returnMessage() {
        for (Map.Entry<String, Video> videoEntry : videos.entrySet()) {
            if (!user.getViewedVideos().containsKey(videoEntry.getKey())) {
                return Constants.STANDARD_RECOMMENDATION + "result: "
                        + videoEntry.getKey();
            }
        }
        return Constants.STANDARD_RECOMMENDATION + Constants.FAILED_RECOMMENDATION;
    }
}
