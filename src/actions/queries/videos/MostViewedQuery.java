package actions.queries.videos;

import common.Constants;
import entertainment.Video;
import actions.Action;
import utils.Pair;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MostViewedQuery extends VideoQuery implements Action {
    private final PriorityQueue<Pair<String, Integer>> videosNoViews;

    public MostViewedQuery(final int n, final List<List<String>> filters,
                           final String sortType,
                           final Map<String, ? extends Video> videos) {
        super(n, filters, videos, sortType);
        videosNoViews = Utils.createIntValuesHeap(super.sortType);
    }

    /**
     * Calculeaza cele mai vizionate filme
     */
    @Override
    protected void calculateResult() {
        for (Map.Entry<String, ? extends Video> videoEntry
                : videos.entrySet()) {
            if (checkFilters(videoEntry.getValue())
                    && videoEntry.getValue().getNoViews() > 0) {
                videosNoViews.add(new Pair<>(videoEntry.getKey(),
                        videoEntry.getValue().getNoViews()));
            }
        }
    }

    /**
     * @return primele N video-uri sortate după numărul de vizualizări.
     */
    @Override
    public String returnMessage() {
        calculateResult();
        if (videosNoViews.isEmpty() || n == 0) {
            return Constants.QUERY_RESULT + "[]";
        }

        return Utils.heapStringBuilder(videosNoViews, n, Constants.QUERY_RESULT);
    }
}
