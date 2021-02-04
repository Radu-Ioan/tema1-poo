package utils;

/**
 * Pastreaza o pereche de date
 * @param <K> primul tip de informatie
 * @param <V> valoare asociata cheii
 */
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return cheia perechii
     */
    public K getKey() {
        return key;
    }

    /**
     * @return o valoare asociata cheii
     */
    public V getValue() {
        return value;
    }

    /**
     * Actualizeaza cheia
     */
    public void setKey(final K key) {
        this.key = key;
    }

    /**
     * Actualizeaza valoarea asociata cheii
     */
    public void setValue(final V value) {
        this.value = value;
    }
}
