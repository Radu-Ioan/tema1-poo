package actor;

import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final ActorInputData actor) {
        name = actor.getName();
        careerDescription = actor.getCareerDescription();
        filmography = actor.getFilmography();
        awards = actor.getAwards();
    }

    /**
     * @return numele actorului
     */
    public String getName() {
        return name;
    }

    /**
     * @return descrierea carierei
     */
    public String getCareerDescription() {
        return careerDescription;
    }

    /**
     * @return numele filmelor in care actorul a jucat
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }

    /**
     * Premiile obtinute de actor
     * @return o multime in care fiecarui premiu al actorului
     * i se asociaza de cate ori a fost dat
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
}
