package actions.queries.actors;

import actor.Actor;
import java.util.Map;

/**
 * Contine informatii comune necesare query-urilor pentru actori
 */
public abstract class ActorQuery {
    protected Map<String, Actor> actors;
    protected final String sortType;

    protected ActorQuery(final Map<String, Actor> actors,
                         final String sortType) {
        this.actors = actors;
        this.sortType = sortType;
    }
}
