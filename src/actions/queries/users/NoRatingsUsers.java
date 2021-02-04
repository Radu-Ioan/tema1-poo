package actions.queries.users;

import common.Constants;
import repository.User;
import actions.Action;
import utils.Pair;
import utils.Utils;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * Se ocupa de query-ul pentru utilizatori
 */
public class NoRatingsUsers implements Action {
    private final int n;
    private final String sortType;
    private final Map<String, User> users;

    public NoRatingsUsers(final Map<String, User> users,
                          final int n, final String sortType) {
        this.n = n;
        this.sortType = sortType;
        this.users = users;
    }

    /**
     * @return lista cu numele celor mai activi utilizatori
     */
    @Override
    public String returnMessage() {
        PriorityQueue<Pair<String, Integer>> pq = createHeap();
        insertUsersInHeap(pq);

        if (pq.isEmpty() || n == 0) {
            return Constants.QUERY_RESULT + "[]";
        }

        return Utils.heapStringBuilder(pq, n, Constants.QUERY_RESULT);
    }

    private PriorityQueue<Pair<String, Integer>> createHeap() {
        return new PriorityQueue<>((o1, o2)
                -> Utils.stringNumberPairComparator(o1, o2, sortType));
    }

    private void insertUsersInHeap(final PriorityQueue<Pair<String, Integer>> pq) {
        for (Map.Entry<String, User> userEntry : users.entrySet()) {
            User currentUser = userEntry.getValue();

            int totalRatings = currentUser.getFilmsRated().size();
            for (Map.Entry<String, Map<Integer, Double>> serialRatingEntry
                    : currentUser.getSerialsRated().entrySet()) {

                totalRatings += serialRatingEntry.getValue().size();
            }

            if (totalRatings > 0) {
                pq.add(new Pair<>(currentUser.getUsername(), totalRatings));
            }
        }
    }
}
