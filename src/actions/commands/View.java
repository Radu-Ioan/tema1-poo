package actions.commands;

import common.Constants;
import repository.DataBase;
import actions.Action;

import java.util.Map;

public class View extends Command implements Action {
    private final Map<String, Integer> userVideos;

    public View(final DataBase repo, final String username,
                final String videoName) {
        super(repo, username, videoName);
        this.userVideos = super.user.getViewedVideos();
    }

    /**
     * Incrementeaza numarul de vizualizari ale videoului,
     * atat ca atribut al filmului/serialului, cat si pentru
     * istoricul utilizatorului
     */
    @Override
    public String returnMessage() {
        if (userVideos.containsKey(video.getTitle())) {
            Integer n = userVideos.get(video.getTitle());
            userVideos.replace(video.getTitle(), ++n);
        } else {
            userVideos.put(video.getTitle(), 1);
        }

        video.setNoViews(video.getNoViews() + 1);
        return Constants.SUCCESS + video.getTitle()
                + Constants.VIEW_MESSAGE
                + userVideos.get(video.getTitle());
    }
}
