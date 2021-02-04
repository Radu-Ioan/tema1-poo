package repository;

import actor.Actor;
import entertainment.Film;
import entertainment.Serial;
import entertainment.Video;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public final class DataBase {
    private final Map<String, User> users;
    private final LinkedHashMap<String, Video> allShows;
    private final LinkedHashMap<String, Film> films;
    private final LinkedHashMap<String, Serial> serials;
    private final Map<String, Actor> actors;

    private DataBase() {
        users = new HashMap<>();
        allShows = new LinkedHashMap<>();
        films = new LinkedHashMap<>();
        serials = new LinkedHashMap<>();
        actors = new HashMap<>();
    }

    public DataBase(final List<UserInputData> users, final List<MovieInputData> films,
                    final List<SerialInputData> serials,
                    final List<ActorInputData> actors) {
        this();

        for (UserInputData u : users) {
            this.users.put(u.getUsername(), new User(u));
        }

        for (ActorInputData a : actors) {
            this.actors.put(a.getName(), new Actor(a));
        }

        for (MovieInputData m : films) {
            this.films.put(m.getTitle(), new Film(m));
        }

        for (SerialInputData s : serials) {
            this.serials.put(s.getTitle(), new Serial(s));
        }

        allShows.putAll(this.films);
        allShows.putAll(this.serials);
        initializeNoViews(allShows, this.users);
    }

    private void initializeNoViews(final Map<String, Video> videos,
                                   final Map<String, User> usersSet) {

        for (Map.Entry<String, Video> videoEntry : videos.entrySet()) {
            for (Map.Entry<String, User> userEntry : usersSet.entrySet()) {

                int currentNoViews = videoEntry.getValue().getNoViews();
                Integer userNoViews = userEntry.getValue().getViewedVideos()
                        .get(videoEntry.getKey());
                if (userNoViews != null) {
                    videoEntry.getValue().setNoViews(currentNoViews + userNoViews);
                }
            }
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Film> getFilms() {
        return films;
    }

    public Map<String, Serial> getSerials() {
        return serials;
    }

    public LinkedHashMap<String, Video> getVideos() {
        return allShows;
    }

    public Map<String, Actor> getActors() {
        return actors;
    }
}
