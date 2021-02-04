package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Serial extends Video {
    private final ArrayList<Season> seasons;

    /**
     * Fiecare sezon are o multime de utilizatori care au dat rating.
     * Integer: numarul sezonului
     * Perechea <String, Double> reprezinta un nume de utilizator impreuna cu ratingul
     * dat de acesta.
     */
    private final Map<Integer, Map<String, Double>> ratingsByUsers;

    public Serial(final SerialInputData serial) {
        super(serial);
        seasons = serial.getSeasons();
        ratingsByUsers = new HashMap<>();

    }

    /**
     * Calculeaza media ratingurilor tuturor sezoanelor
     * @return ratingul serialului, sau null daca serialul nu a primit rating
     * pentru niciun sezon
     */
    @Override
    public Double getRating() {
        Double sum = 0d;
        int noRatedSeasons = 0;

        for (int i = 0; i < seasons.size(); i++) {
            if (getRating(i) != 0d) {
                sum += getRating(i);
                noRatedSeasons++;
            }
        }

        return (noRatedSeasons != 0) ? (sum / seasons.size()) : null;
    }

    /**
     * @param seasonId specifica despre care sezon e vorba
     * @return ratingul pentru un sezon al serialului
     */
    public Double getRating(final int seasonId) {
        Season s = seasons.get(seasonId);

        if (s.getRatings().size() == 0) {
            return 0d;
        }

        Double sum = 0d;

        for (Double d : s.getRatings()) {
            sum += d;
        }

        return sum / s.getRatings().size();
    }

    /**
     * @return o lista cu sezoanele serialului
     */
    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     * @return ratingurile date de utilizatori pentru fiecare sezon
     */
    public Map<Integer, Map<String, Double>> getRatingsByUsers() {
        return ratingsByUsers;
    }

    /**
     * @return durata unui serial
     */
    @Override
    public int getDuration() {
        int sum = 0;

        for (Season s : seasons) {
            sum += s.getDuration();
        }

        return sum;
    }
}
