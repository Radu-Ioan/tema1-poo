package actions.queries.videos;

import common.Constants;
import entertainment.Video;
import actions.Action;
import utils.Pair;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class LongestVideosQuery extends VideoQuery implements Action {
    private final PriorityQueue<Pair<String, Integer>> videosTimes;

    public LongestVideosQuery(final int n, final String sortType,
                              final List<List<String>> filters,
                              final Map<String, ? extends Video> videos) {
        super(n, filters, videos, sortType);
        videosTimes = Utils.createIntValuesHeap(sortType);
    }

    /**
     * @return lista cu numele celor mai lungi video-uri
     */
    @Override
    public String returnMessage() {
        calculateResult();

        if (videosTimes.isEmpty() || n == 0) {
            return Constants.QUERY_RESULT + "[]";
        }

        return Utils.heapStringBuilder(videosTimes, n, Constants.QUERY_RESULT);
    }

    /**
     * Adauga in coada de prioritati toate videourile care sunt
     * conforme cu filtrele
     */
    @Override
    protected void calculateResult() {
        for (Map.Entry<String, ? extends Video> videoEntry : videos.entrySet()) {
            if (checkFilters(videoEntry.getValue())) {
                videosTimes.add(new Pair<>(videoEntry.getKey(),
                        videoEntry.getValue().getDuration()));
            }
        }
    }
}
