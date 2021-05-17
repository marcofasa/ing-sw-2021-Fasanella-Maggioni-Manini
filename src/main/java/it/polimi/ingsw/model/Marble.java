package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Abstract Marble
 */
public abstract class Marble implements Serializable {

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

    public abstract MarbleType getType();
}
