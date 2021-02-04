package utils;

import actions.queries.videos.FavoriteVideoQuery;
import actions.queries.videos.LongestVideosQuery;
import actions.queries.videos.MostViewedQuery;
import actions.queries.videos.RatingVideoQuery;

import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import repository.DataBase;
import actions.Action;
import actions.commands.Favorite;
import actions.commands.FilmRating;
import actions.commands.SerialRating;
import actions.commands.View;
import actions.queries.actors.AverageActors;
import actions.queries.actors.AwardsActors;
import actions.queries.actors.FilterDescriptionActor;
import actions.queries.users.NoRatingsUsers;
import actions.recommendations.BestUnseen;
import actions.recommendations.Standard;
import actions.recommendations.premium.MostSavedVideo;
import actions.recommendations.premium.Popular;
import actions.recommendations.premium.Search;

import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.PriorityQueue;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> Genre.ACTION;
            case "adventure" -> Genre.ADVENTURE;
            case "drama" -> Genre.DRAMA;
            case "comedy" -> Genre.COMEDY;
            case "crime" -> Genre.CRIME;
            case "romance" -> Genre.ROMANCE;
            case "war" -> Genre.WAR;
            case "history" -> Genre.HISTORY;
            case "thriller" -> Genre.THRILLER;
            case "mystery" -> Genre.MYSTERY;
            case "family" -> Genre.FAMILY;
            case "horror" -> Genre.HORROR;
            case "fantasy" -> Genre.FANTASY;
            case "science fiction" -> Genre.SCIENCE_FICTION;
            case "action & adventure" -> Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> Genre.SCI_FI_FANTASY;
            case "animation" -> Genre.ANIMATION;
            case "kids" -> Genre.KIDS;
            case "western" -> Genre.WESTERN;
            case "tv movie" -> Genre.TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Transforms a string into an enum
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }


    private static void parseCommand(final DataBase repository,
                                     final ActionInputData action,
                                     final Pair<Action, String> option) {

        switch (action.getType()) {
            case Constants.FAVORITE_COMMAND:
                option.setKey(new Favorite(repository,
                        action.getUsername(),
                        action.getTitle()));
                break;

            case Constants.VIEW_COMMAND:
                option.setKey(new View(repository,
                        action.getUsername(),
                        action.getTitle()));
                break;

            case Constants.RATING_COMMAND:
                if (action.getSeasonNumber() == 0) {
                    option.setKey(new FilmRating(repository,
                            action.getUsername(),
                            action.getTitle(),
                            action.getGrade()));
                } else {
                    option.setKey(new SerialRating(repository,
                            action.getUsername(),
                            action.getTitle(),
                            action.getGrade(),
                            action.getSeasonNumber()));
                }
                break;

            default:
                break;
        }
        option.setValue(option.getKey().returnMessage());
    }

    private static void parseQuery(final DataBase repository,
                                   final ActionInputData action,
                                   final Pair<Action, String> option) {
        switch (action.getObjectType()) {

            case Constants.ACTORS:
                switch (action.getCriteria()) {

                    case Constants.AVERAGE:
                        option.setKey(new AverageActors(
                                repository.getActors(),
                                action.getNumber(),
                                repository.getVideos(),
                                action.getSortType()));
                        break;

                    case Constants.AWARDS:
                        option.setKey(new AwardsActors(
                                repository.getActors(),
                                action.getFilters()
                                        .get(Constants.AWARDS_IDX),
                                action.getSortType()));
                        break;

                    case Constants.FILTER_DESCRIPTIONS:
                        option.setKey(new FilterDescriptionActor(
                                repository.getActors(),
                                action.getFilters()
                                        .get(Constants.WORDS_IDX),
                                action.getSortType()));
                        break;

                    default:
                        break;
                }
                break;

            case Constants.USERS:
                option.setKey(new NoRatingsUsers(repository.getUsers(),
                        action.getNumber(), action.getSortType()));
                break;

            case Constants.MOVIES:
                switch (action.getCriteria()) {

                    case Constants.RATINGS:
                        option.setKey(new RatingVideoQuery(
                                action.getNumber(),
                                action.getSortType(),
                                action.getFilters(),
                                repository.getFilms()));
                        break;

                    case Constants.FAVORITE_CRITERIA:
                        option.setKey(new FavoriteVideoQuery(
                                action.getNumber(),
                                action.getFilters(),
                                repository.getFilms(),
                                repository.getUsers(),
                                action.getSortType()));
                        break;

                    case Constants.LONGEST:
                        option.setKey(new LongestVideosQuery(
                                action.getNumber(),
                                action.getSortType(),
                                action.getFilters(),
                                repository.getFilms()));
                        break;

                    case Constants.MOST_VIEWED:
                        option.setKey(new MostViewedQuery(
                                action.getNumber(),
                                action.getFilters(),
                                action.getSortType(),
                                repository.getFilms()));
                        break;

                    default:
                        break;
                }
                break;

            case Constants.SHOWS:
                switch (action.getCriteria()) {

                    case Constants.RATINGS:
                        option.setKey(new RatingVideoQuery(
                                action.getNumber(),
                                action.getSortType(),
                                action.getFilters(),
                                repository.getSerials()));
                        break;

                    case Constants.FAVORITE_CRITERIA:
                        option.setKey(new FavoriteVideoQuery(
                                action.getNumber(),
                                action.getFilters(),
                                repository.getSerials(),
                                repository.getUsers(),
                                action.getSortType()));
                        break;

                    case Constants.LONGEST:
                        option.setKey(new LongestVideosQuery(
                                action.getNumber(),
                                action.getSortType(),
                                action.getFilters(),
                                repository.getSerials()));
                        break;

                    case Constants.MOST_VIEWED:
                        option.setKey(new MostViewedQuery(
                                action.getNumber(),
                                action.getFilters(),
                                action.getSortType(),
                                repository.getSerials()));
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
        option.setValue(option.getKey().returnMessage());
    }

    private static void parseRecommendation(final DataBase repository,
                                            final ActionInputData action,
                                            final Pair<Action, String> option) {
        switch (action.getType()) {

            case Constants.STANDARD:
                option.setKey(new Standard(repository.getUsers()
                        .get(action.getUsername()),
                        repository.getVideos()));
                break;

            case Constants.BEST_UNSEEN:
                option.setKey(new BestUnseen(repository.getUsers()
                        .get(action.getUsername()),
                        repository.getVideos()));
                break;

            case Constants.POPULAR:
                option.setKey(new Popular(repository.getUsers()
                        .get(action.getUsername()),
                        repository.getVideos()));
                break;

            case Constants.FAVORITE_CRITERIA:
                option.setKey(new MostSavedVideo(repository
                        .getUsers().get(action.getUsername()),
                        repository.getVideos(), repository.getUsers()));
                break;

            case Constants.SEARCH:
                option.setKey(new Search(repository.getUsers()
                        .get(action.getUsername()),
                        repository.getVideos(),
                        action.getGenre()));
                break;

            default:
                break;
        }
        option.setValue(option.getKey().returnMessage());
    }

    /**
     * Pune in functiune executia comenzilor asupra bazei de date
     * @param repository baza de date
     * @param commands instructiunile de executie
     * @param results variabila unde sunt stocate rezultatele
     * @param outFile fisierul de output
     * @throws IOException in caz de eroare
     */
    public static void turnOnActions(final DataBase repository,
                                     final List<ActionInputData> commands,
                                     final JSONArray results,
                                     final Writer outFile) throws IOException {

        Pair<Action, String> option = new Pair<>(null, null);
        int id = 1;

        for (ActionInputData action : commands) {

            switch (action.getActionType()) {
                case Constants.COMMAND:
                    parseCommand(repository, action, option);
                    break;

                case Constants.QUERY:
                    parseQuery(repository, action, option);
                    break;

                case Constants.RECOMMENDATION:
                    parseRecommendation(repository, action, option);
                    break;

                default:
                    break;
            }
            results.add(outFile.writeFile(id++, null, option.getValue()));
        }
    }

    /**
     * Functie comparator ce specifica tipul de sortare dintr o structura
     * de date.
     * @param sortType ascendent sau descendent
     * @return un rezultat care stabileste cum sa fie sortate obiectele
     */
    public static <String, V extends Comparable<V>>
            int stringNumberPairComparator(
                    final Pair<String, V> o1, final Pair<String, V> o2,
                    final String sortType) {
        if (sortType.equals(Constants.ASCENDANT)) {
            if (o1.getValue().compareTo(o2.getValue()) == 0) {
                return CharSequence.compare(o1.getKey().toString(),
                        o2.getKey().toString());
            }

            return o1.getValue().compareTo(o2.getValue());
        } else {
            if (o1.getValue().compareTo(o2.getValue()) == 0) {
                return CharSequence.compare(o2.getKey().toString(),
                        o1.getKey().toString());
            }

            return o2.getValue().compareTo(o1.getValue());
        }
    }

    /**
     * Creeaza o coada de prioritate cu valorile intregi si cheile siruri de
     * caractere
     */
    public static PriorityQueue<Pair<String, Integer>> createIntValuesHeap(
                final String sortType) {
        return new PriorityQueue<>((o1, o2)
                -> stringNumberPairComparator(o1, o2, sortType));
    }

    /**
     * Creeaza o coada de prioritate libera cu numere de tip double si
     * cheile siruri de caractere
     */
    public static PriorityQueue<Pair<String, Double>> createDoubleValuesHeap(
            final String sortType) {
        return new PriorityQueue<>((o1, o2)
                -> Utils.stringNumberPairComparator(o1, o2, sortType));
    }

    /**
     * O metoda a carei logica se intalneste de mai multe ori in queriuri.
     * @param pq o coada de prioritati din care se extrag elemente sortate
     * @param n numarul maxim de elemente ce pot fi scoase din structura
     * @return un mesaj ce pune in evidenta rezultatul unui query
     */
    public static <K, V> String heapStringBuilder(
            final PriorityQueue<Pair<K, V>> pq,
            final int n, final String intro) {

        int i = 0;
        StringBuilder result = new StringBuilder(intro + "[");

        while (true) {
            result.append(pq.poll().getKey());
            ++i;
            if (pq.isEmpty() || i == n) {
                break;
            }
            result.append(", ");
        }

        return result.append("]").toString();
    }
}
