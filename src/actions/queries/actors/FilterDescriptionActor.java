package actions.queries.actors;

import actor.Actor;
import common.Constants;
import actions.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterDescriptionActor extends ActorQuery implements Action {
    private final List<String> words;
    private final List<String> foundActors;

    public FilterDescriptionActor(final Map<String, Actor> actors,
                                  final List<String> words, final String sortType) {
        super(actors, sortType);
        this.words = words;
        foundActors = new ArrayList<>();
    }

    /**
     * @return numele actorilor gasiti
     */
    @Override
    public String returnMessage() {
        searchThroughActorsDescription();
        return Constants.QUERY_RESULT + foundActors;
    }

    /**
     * Cauta in descrierea unui actor includerea fiecarui cuvant din
     * filtru cu conditia sa nu fie concatenat cu alte siruri care
     * incep (pentru concatenare la dreapta) sau se termina
     * (pentru concatenare la stanga) cu caractere alfabetice.
     */
    private void searchThroughActorsDescription() {
        for (Map.Entry<String, Actor> actorEntry : actors.entrySet()) {
            Actor actor = actorEntry.getValue();
            int noWordsContained = 0;

            for (String word : words) {
                String regex = "\\b" + word.toLowerCase() + "\\b";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(actor.getCareerDescription()
                        .toLowerCase());

                if (matcher.find()) {
                    ++noWordsContained;
                }
            }

            if (noWordsContained == words.size()) {
                foundActors.add(actor.getName());
            }
        }


        Collections.sort(foundActors, (o1, o2) -> {
            if (sortType.equals(Constants.ASCENDANT)) {
                return CharSequence.compare(o1, o2);
            } else {
                return CharSequence.compare(o2, o1);
            }
        });
    }
}
