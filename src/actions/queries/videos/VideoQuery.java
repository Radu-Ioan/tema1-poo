package actions.queries.videos;

import common.Constants;
import entertainment.Video;

import java.util.List;
import java.util.Map;

public abstract class VideoQuery {
    protected final int n;
    protected String sortType;
    protected final List<List<String>> filters;
    protected final Map<String, ? extends Video> videos;

    public VideoQuery(final int n,
                      final List<List<String>> filters,
                      final Map<String, ? extends Video> videos,
                      final String sortType) {
        this.n = n;
        this.filters = filters;
        this.videos = videos;
        this.sortType = sortType;
    }

    protected abstract void calculateResult();

    /**
     * Verifica daca un video respecta conditiile date in filtre
     * @param video filmul/serialul de verificat
     * @return adevarat daca se potriveste cu filtrele, fals in caz contrar
     */
    protected boolean checkFilters(final Video video) {
        boolean correctYear = true;
        boolean correctGenre = true;

        if (filters.get(Constants.YEAR_IDX).get(0) != null) {
            correctYear = filters.get(Constants.YEAR_IDX).get(0)
                    .equals(Integer.valueOf(video.getReleaseYear())
                            .toString());
        }

        if (filters.get(Constants.GENRE_IDX).get(0) != null) {
            correctGenre = video.getGenres().contains(filters
                    .get(Constants.GENRE_IDX).get(0));
        }

        return correctYear && correctGenre;
    }
}
