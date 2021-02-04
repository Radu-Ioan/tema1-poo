package actions;

public interface Action {
    /**
     * Fiecare actiune asupra repository-ului va returna un mesaj ce
     * va pune in evidenta efectul.
     * @return pentru query-uri si recomandari, rezultatul actiunii,
     *         pentru comenzi, un mesaj cu sens
     */
    String returnMessage();
}
