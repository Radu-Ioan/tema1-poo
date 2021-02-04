package actions.queries.videos;

import common.Constants;
import entertainment.Video;
import actions.Action;
import utils.Pair;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class RatingVideoQuery extends VideoQuery implements Action {
    private final PriorityQueue<Pair<String, Double>> foundVideos;

    public RatingVideoQuery(final int n, final String sortType,
                               final List<List<String>> filters,
                               final Map<String, ? extends Video> videos) {
        super(n, filters, videos, sortType);
        foundVideos = Utils.createDoubleValuesHeap(sortType);
    }

    /**
     * @return lista cu numele video-urilor sortate dupa rating
     */
    @Override
    public String returnMessage() {
        calculateResult();

        if (foundVideos.isEmpty() || n == 0) {
            return Constants.QUERY_RESULT + "[]";
        }

        return Utils.heapStringBuilder(foundVideos, n, Constants.QUERY_RESULT);
    }

    /**
     * Adauga toate videourile care trec de filtre si au primit rating
     * in coada de prioritati
     */
    @Override
    protected void calculateResult() {
        for (Map.Entry<String, ? extends Video> videoEntry
                : videos.entrySet()) {
            if (checkFilters(videoEntry.getValue())
                    && videoEntry.getValue().getRating() != null) {
                foundVideos.add(new Pair<>(videoEntry.getKey(),
                        videoEntry.getValue().getRating()));
            }
        }
    }
}
