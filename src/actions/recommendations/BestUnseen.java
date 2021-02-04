package actions.recommendations;

import common.Constants;
import entertainment.Video;
import repository.User;
import actions.Action;

import java.util.Map;
import java.util.PriorityQueue;

public class BestUnseen extends Recommendation implements Action {
    private final PriorityQueue<PositionalPair<String, Double>>
            unseenVideos;

    public BestUnseen(final User user, final Map<String, Video> videos) {
        super(user, videos);
        unseenVideos = createHeap();
    }

    /**
     * Întoarce cel mai bun video nevizualizat de către un utilizator.
     * Toate video-urile sunt ordonate descrescător după rating
     * și se alege primul din ele.
     */
    @Override
    public String returnMessage() {
        insertUnseenVideosInHeap();
        if (unseenVideos.size() == 0) {
            return Constants.BEST_UNSEEN_RECOMMENDATION + Constants.FAILED_RECOMMENDATION;
        }

        return Constants.BEST_UNSEEN_RECOMMENDATION + "result: "
                + unseenVideos.poll().getKey();
    }

    private void insertUnseenVideosInHeap() {
        int i = 0;
        for (Map.Entry<String, Video> videoEntry : videos.entrySet()) {
            if (!user.getViewedVideos().containsKey(videoEntry.getKey())) {
                    unseenVideos.add(new PositionalPair<>(videoEntry.getKey(),
                            videoEntry.getValue().getRating(), i));
            }
            ++i;
        }
    }

    private PriorityQueue<PositionalPair<String, Double>> createHeap() {
        return new PriorityQueue<>((o1, o2) -> {
            if (o1.getValue() == null && o2.getValue() != null) {
                return 1;
            } else if (o2.getValue() == null && o1.getValue() != null) {
                return -1;
            } else if (o1.getValue() == null && o2.getValue() == null) {
                return (o1.getPosition() > o2.getPosition()) ? 1 : -1;
            } else if (Double.compare(o1.getValue(), o2.getValue()) == 0) {
                return (o1.getPosition() > o2.getPosition()) ? 1 : -1;
            }
            return Double.compare(o2.getValue(), o1.getValue());
        });
    }
}
