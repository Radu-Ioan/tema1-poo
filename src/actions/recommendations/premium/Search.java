package actions.recommendations.premium;

import common.Constants;
import entertainment.Video;
import repository.User;
import actions.Action;
import actions.recommendations.Recommendation;
import utils.Pair;
import utils.Utils;

import java.util.Map;
import java.util.PriorityQueue;

public class Search extends Recommendation implements Action {
    private String filter;
    private PriorityQueue<Pair<String, Double>> unseenVideos;

    public Search(final User user, final Map<String, Video> videos,
                  final String genre) {
        super(user, videos);
        filter = genre;
        unseenVideos = Utils.createDoubleValuesHeap(Constants.ASCENDANT);
    }

    /**
     * @return toate filmele nevăzute de user dintr-un anumit gen,
     *      dat ca filtru în input, sortate după rating crescător
     */
    @Override
    public String returnMessage() {
        if (!user.getSubscription().equals(Constants.PREMIUM)) {
            return Constants.SEARCH_RECOMMENDATION + Constants.FAILED_RECOMMENDATION;
        }

        for (Map.Entry<String, Video> videoEntry : videos.entrySet()) {

            if (!user.getViewedVideos().containsKey(videoEntry.getKey())
                    && videoEntry.getValue().getGenres().contains(filter)) {

                Double rating = videoEntry.getValue().getRating();
                unseenVideos.add(new Pair<>(videoEntry.getKey(),
                        rating != null ? rating : 0d));
            }
        }
        if (unseenVideos.size() == 0) {
            return Constants.SEARCH_RECOMMENDATION
                    + Constants.FAILED_RECOMMENDATION;
        }

        return Utils.heapStringBuilder(unseenVideos, unseenVideos.size(),
                Constants.SEARCH_RECOMMENDATION + "result: ");
    }
}
