package actions.queries.actors;

import actor.Actor;
import actor.ActorsAwards;
import common.Constants;
import actions.Action;
import utils.Pair;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class AwardsActors extends ActorQuery implements Action {
    private final List<String> awards;
    private final List<Pair<String, Integer>> foundActors;

    public AwardsActors(final Map<String, Actor> actors, final List<String> awards,
                        final String sortType) {
        super(actors, sortType);
        this.awards = awards;
        foundActors = new ArrayList<>();
    }

    /**
     * @return rezulatul query-ului
     */
    @Override
    public String returnMessage() {
        calculateActors();
        StringBuilder result = new StringBuilder(Constants.QUERY_RESULT + "[");
        int lastIndex = foundActors.size() - 1;

        for (int k = 0; k < lastIndex; ++k) {
            result.append(foundActors.get(k).getKey() + Constants.SEPARATOR);
        }

        if (foundActors.size() > 0) {
            result.append(foundActors.get(lastIndex).getKey());
        }
        return result.append("]").toString();
    }

    private void calculateActors() {
        for (Map.Entry<String, Actor> actorsIterator : actors.entrySet()) {
            Actor actor = actorsIterator.getValue();
            Map<ActorsAwards, Integer> copyOfActorAwards
                    = new HashMap<>(actor.getAwards());
            /*
             * Numarul de premii ale actorului care se regasesc si in query
             */
            int noAwardsFromQuery = 0;
            int noAwards = 0;

            for (String s : awards) {
                if (actor.getAwards().containsKey(Utils.stringToAwards(s))) {
                    noAwards += copyOfActorAwards.remove(Utils.stringToAwards(s));
                    noAwardsFromQuery++;
                } else {
                    break;
                }
            }

            if (noAwardsFromQuery == awards.size()) {
                for (Map.Entry<ActorsAwards, Integer> entry
                        : copyOfActorAwards.entrySet()) {
                    noAwards += entry.getValue();
                }
                foundActors.add(new Pair<>(actor.getName(), noAwards));
            }
        }

        Collections.sort(foundActors, (o1, o2)
                -> Utils.stringNumberPairComparator(o1, o2, sortType));
    }
}
