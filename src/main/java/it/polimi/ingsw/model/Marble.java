package it.polimi.ingsw.model;

/**
 * Abstract Marble
 */
public abstract class Marble {

    /**
     * @param playerBoard player where to activate marble
     */
    public abstract void activate(PlayerBoard playerBoard);

    /**
     * cloner of the class
     * @return a deep clone of Marble
     */
    @Override
    public abstract Marble clone();
}
