package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.enums.MarbleType;

/**
 * Concrete Marble
 */
public class MarbleRed extends Marble {

    /**
     * Constructor of the class
     */
    MarbleRed(){
    }

    /**
     * cloner of the class
     * @return a deep clone of Marble
     */
    @Override
    public Marble clone(){
        return new MarbleRed();
    }

    /**
     * Moves player forward by 1 faith cell
     * @param playerBoard player where to activate marble
     */
    @Override
    public void activate(PlayerBoard playerBoard) {
        playerBoard.moveFaith(1);
    }

    @Override
    public MarbleType getType() {
        return MarbleType.MarbleRed;
    }
}
