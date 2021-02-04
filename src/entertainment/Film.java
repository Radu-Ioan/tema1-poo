package entertainment;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class Film extends Video {
    private final List<Double> ratings;

    public Film(final MovieInputData movie) {
        super(movie);
        super.duration = movie.getDuration();
        ratings = new ArrayList<>();
    }

    /**
     * @return durata filmului
     */
    @Override
    public int getDuration() {
        return super.duration;
    }

    /**
     * Calculeaza media aritmetica a ratingurilor primite.
     * @return gradul de rating; daca e null inseamna ca filmul nu a primit rating
     */
    @Override
    public Double getRating() {
        if (ratings.size() == 0) {
            return null;
        }
        Double sum = 0d;

        for (Double d : ratings) {
            sum += d;
        }

        return sum / ratings.size();
    }

    /**
     * @return lista de ratinguri
     */
    public List<Double> getRatings() {
        return ratings;
    }
}
