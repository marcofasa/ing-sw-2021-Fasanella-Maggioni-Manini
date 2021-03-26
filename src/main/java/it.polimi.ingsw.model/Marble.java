package it.polimi.ingsw.model;

/**
 * Abstract Marble
 */
public abstract class Marble {

    /**
     * @param playerBoard player where to activate marble
     */
    public abstract void activate(PlayerBoard playerBoard); /*TODO in every child class*/
}
