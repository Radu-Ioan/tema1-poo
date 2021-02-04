package actions.recommendations;

import utils.Pair;

/**
 * Aceasta clasa pastreaza in plus si pozitia din baza de date a unui film.
 * @param <K> numele unui film/serial
 * @param <V> valoarea asociata videoului
 */
public class PositionalPair<K, V> extends Pair<K, V> {
    private int position;

    public PositionalPair(final K key, final V value, final int position) {
        super(key, value);
        this.position = position;
    }

    /**
     * @return pozitia videoului din baza de date
     */
    public int getPosition() {
        return position;
    }

    /**
     * Actualizeaza pozitia unui film cautat
     */
    public void setPosition(final int position) {
        this.position = position;
    }
}
