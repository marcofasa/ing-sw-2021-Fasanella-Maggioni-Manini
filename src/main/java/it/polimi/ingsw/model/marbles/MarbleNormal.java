package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.enums.MarbleType;
import it.polimi.ingsw.model.enums.Resource;

/**
 * Concrete Marble
 */
public class MarbleNormal extends Marble {

    private final Resource resource;

    /**
     * Constructor of the class
     * @param resource type of resurce represented by the marble
     */
    public MarbleNormal(Resource resource){
        this.resource = resource;
    }

    @Override
    public MarbleType getType() {

        switch (resource) {
            case Coins -> {
                return MarbleType.MarbleYellow;
            }
            case Stones -> {
                return MarbleType.MarbleGrey;
            }
            case Shields -> {
                return MarbleType.MarbleBlue;
            }
            default -> {
                return MarbleType.MarblePurple;
            }
        }
    }

    /**
     * cloner of the class
     * @return a deep clone of Marble
     */
    @Override
    public Marble clone(){
        return new MarbleNormal(resource);
    }

    /**
     * Adds one resource to a player
     * @param playerBoard player where to activate marble
     */
    @Override
    public void activate(PlayerBoard playerBoard) {
        playerBoard.addToTemporaryDeposit(resource);
    }
}
