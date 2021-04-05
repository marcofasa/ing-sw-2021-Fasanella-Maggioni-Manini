package it.polimi.ingsw.model;

/**
 * Concrete Marble
 */
public class MarbleRed extends Marble {

    private final FaithTrail faithTrail;

    /**
     * Constructor of the class
     * @param faithTrail instance of FaithTrail where to move players
     */
    MarbleRed(FaithTrail faithTrail){
        this.faithTrail = faithTrail;
    }

    /**
     * cloner of the class
     * @return a deep clone of Marble
     */
    @Override
    public Marble clone(){
        return new MarbleRed(faithTrail);
    }

    /**
     * Moves player forward by 1 faith cell
     * @param playerBoard player where to activate marble
     */
    @Override
    public void activate(PlayerBoard playerBoard) {
        playerBoard.moveFaith(1);
    }
}
