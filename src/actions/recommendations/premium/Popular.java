package actions.recommendations.premium;

import common.Constants;
import entertainment.Video;
import repository.User;
import actions.Action;
import actions.recommendations.Recommendation;
import utils.Pair;
import utils.Utils;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Popular extends Recommendation implements Action {
    private Map<String, LinkedList<String>> videosGenres;
    private PriorityQueue<Pair<String, Integer>> orderedVideos;

    public Popular(final User user, final Map<String, Video> videos) {
        super(user, videos);
        videosGenres = new HashMap<>();
        orderedVideos = Utils.createIntValuesHeap(Constants.DESCENDANT);
    }

    /**
     * Întoarce primul video nevizualizat din cel mai popular gen
     * @return numele unui video in caz de succes sau eroare in caz
     * contrar
     */
    @Override
    public String returnMessage() {
        if (!user.getSubscription().equals(Constants.PREMIUM)) {
            return Constants.POPULAR_RECOMMENDATION + Constants.FAILED_RECOMMENDATION;
        }

        calculateMostPopularVideos();

        for (Map.Entry<String, LinkedList<String>> entry : videosGenres.entrySet()) {
            orderedVideos.add(new Pair<>(entry.getKey(), entry.getValue().size()));
        }

        while (!orderedVideos.isEmpty()) {
            LinkedList<String> currentVideos
                    = videosGenres.get(orderedVideos.poll().getKey());

            for (String name : currentVideos) {
                if (!user.getViewedVideos().containsKey(name)) {
                    return Constants.POPULAR_RECOMMENDATION
                            + "result: " + name;
                }
            }
        }
        return Constants.POPULAR_RECOMMENDATION + Constants.FAILED_RECOMMENDATION;
    }

    private void calculateMostPopularVideos() {
        for (Map.Entry<String, Video> videoEntry : videos.entrySet()) {
            for (String genre : videoEntry.getValue().getGenres()) {
                if (!videosGenres.containsKey(genre)) {
                    videosGenres.put(genre, new LinkedList<>());
                }
                videosGenres.get(genre).addLast(videoEntry.getKey());
            }
        }
    }
}
