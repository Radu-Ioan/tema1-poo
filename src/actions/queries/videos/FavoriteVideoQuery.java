package actions.queries.videos;

import common.Constants;
import entertainment.Video;
import repository.User;
import actions.Action;
import utils.Pair;
import utils.Utils;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.PriorityQueue;

public class FavoriteVideoQuery extends VideoQuery implements Action {
    /**
     * Numarul de aparitii ale filmelor in lista de
     * favorite ale utilizatorilor.
     * Stringul reprezinta numele unui video mapat la un numar de aparitii.
     */
    private final Map<String, Integer> noFavorites;
    /**
     * Multimea utilizatorilor
     */
    private final Map<String, User> users;

    public FavoriteVideoQuery(final int n,
                              final List<List<String>> filters,
                              final Map<String, ? extends Video> videos,
                              final Map<String, User> users,
                              final String sortType) {

        super(n, filters, videos, sortType);
        this.users = users;
        noFavorites = new HashMap<>();
    }

    /**
     * @return primele n video-uri sortate descrescător după numărul
     * de apariții în listele de video-uri favorite ale utilizatorilor.
     */
    public String returnMessage() {
        calculateResult();
        PriorityQueue<Pair<String, Integer>> pq
                = Utils.createIntValuesHeap(sortType);

        for (Map.Entry<String, Integer> entry : noFavorites.entrySet()) {
            pq.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        if (pq.isEmpty() || n == 0) {
            return Constants.QUERY_RESULT + "[]";
        }

        return Utils.heapStringBuilder(pq, n, Constants.QUERY_RESULT);
    }

    /**
     * Obtine posibilele filme care pot fi returnate ca cele mai des
     * intalnite in listele de favorite ale utilizatorilor
     */
    @Override
    protected void calculateResult() {
        for (Map.Entry<String, User> userEntry : users.entrySet()) {
            for (String s : userEntry.getValue().getFavoriteVideos()) {
                if (videos.containsKey(s) && checkFilters(videos.get(s))) {
                    if (!noFavorites.containsKey(s)) {
                        noFavorites.put(s, 1);
                    } else {
                        int x = noFavorites.get(s);
                        noFavorites.replace(s, ++x);
                    }
                }
            }
        }
    }
}
