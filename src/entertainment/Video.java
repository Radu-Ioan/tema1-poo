package entertainment;

import fileio.ShowInput;

import java.util.ArrayList;

public abstract class Video {
    protected final String title;
    protected final int releaseYear;
    /**
     * Actorii care joaca in video
     */
    protected final ArrayList<String> cast;
    /**
     * Categoriile in care se incadreaza videoul
     */
    protected final ArrayList<String> genres;
    protected int noViews;
    protected int duration;

    public Video(final ShowInput show) {
        title = show.getTitle();
        releaseYear = show.getYear();
        cast = show.getCast();
        genres = show.getGenres();
        noViews = 0;
    }

    /**
     * Metoda trebuie implementata diferit de filme si seriale
     * @return ratingul filmului
     */
    public abstract Double getRating();

    /**
     * @return numarul de vizualizari
     */
    public int getNoViews() {
        return noViews;
    }

    /**
     * Actualizeaza numarul de vizualizari
     */
    public void setNoViews(final int noViews) {
        this.noViews = noViews;
    }

    /**
     * @return numele videoului
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return anul de lansare
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * @return lista cu actorii care joaca in video
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * @return Categoriile video-ului
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * @return duarata unui video.
     */
    public abstract int getDuration();
}
