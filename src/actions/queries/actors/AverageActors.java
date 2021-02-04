package actions.queries.actors;

import actor.Actor;
import common.Constants;
import entertainment.Video;
import actions.Action;
import utils.Pair;
import utils.Utils;

import java.util.Map;
import java.util.PriorityQueue;

public class AverageActors extends ActorQuery implements Action {
    private final int n;
    private final Map<String, Video> videos;

    public AverageActors(final Map<String, Actor> actors, final int n,
                         final Map<String, Video> videos,
                         final String sortType) {
        super(actors, sortType);
        this.n = n;
        this.videos = videos;
    }

    /**
     * @return rezultatul query-ului
     */
    @Override
    public String returnMessage() {
        PriorityQueue<Pair<String, Double>> pq = createHeap();

        Double sum;
        int noRatings;

        for (Map.Entry<String, Actor> actorsIterator : actors.entrySet()) {
            Actor actor = actorsIterator.getValue();
            sum = 0d;
            noRatings = 0;

            for (String videoName : actor.getFilmography()) {
                Video video = videos.get(videoName);

                if (video != null && video.getRating() != null) {
                    sum += video.getRating();
                    noRatings++;
                }
            }

            if (noRatings != 0) {
                sum /= noRatings;
                pq.add(new Pair<>(actor.getName(), sum));
            }
        }

        return Utils.heapStringBuilder(pq, n, Constants.QUERY_RESULT);
    }

    /**
     * Creeaza un heap in care se pastreaza actorii cu ratingurile
     * corespunzatoare.
     * @return structura de date care foloseste ordinea data de
     * campul sortType al clasei.
     */
    private PriorityQueue<Pair<String, Double>> createHeap() {
        return new PriorityQueue<>(
            (o1, o2) -> Utils.stringNumberPairComparator(o1, o2, sortType));
    }
}
