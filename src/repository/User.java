package repository;

import fileio.UserInputData;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

public class User {
    private final String username;
    private final String subscription;
    private final Map<String, Integer> viewedVideos;
    private final Set<String> favoriteVideos;
    private final Map<String, Double> filmsRated;
    /**
     * Mapeaza fiecare serial cu ratingurile date de utilizator
     * pentru fiecare sezon.
     */
    private final Map<String, Map<Integer, Double>> serialsRated;

    public User(final UserInputData u) {
        username = u.getUsername();
        subscription = u.getSubscriptionType();
        viewedVideos = u.getHistory();
        favoriteVideos = new HashSet<>();
        filmsRated = new HashMap<>();
        serialsRated = new HashMap<>();

        if (u.getFavoriteMovies() != null) {
            favoriteVideos.addAll(u.getFavoriteMovies());
        }
    }

    /**
     * @return numele userului
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return tipul de utilizator
     */
    public String getSubscription() {
        return subscription;
    }

    /**
     * @return numele video-urilor vizualizate impreuna cu numarul de
     * vizualizari
     */
    public Map<String, Integer> getViewedVideos() {
        return viewedVideos;
    }

    /**
     * @return o multime cu numele video-urilor favorite
     */
    public Set<String> getFavoriteVideos() {
        return favoriteVideos;
    }

    /**
     * @return filmele care au primit raitng de la user
     */
    public Map<String, Double> getFilmsRated() {
        return filmsRated;
    }

    /**
     * @return serialele care au primit raitng de la user
     */
    public Map<String, Map<Integer, Double>> getSerialsRated() {
        return serialsRated;
    }
}
