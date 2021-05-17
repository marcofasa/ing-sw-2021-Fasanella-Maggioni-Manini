package it.polimi.ingsw.model;

/**
 * Factory pattern implementation, returns a Concrete Marble
 */
public class MarbleFactory {

    /**
     *
     * @param marbleType MarbleType to use for the creation of the new Marble
     * @param gameTable Instance of gameTable for Marble activation
     * @return Concrete Marble
     */
    public Marble produce(MarbleType marbleType, GameTable gameTable){
        return switch (marbleType) {
            case MarbleRed -> new MarbleRed(gameTable.getFaithTrailInstance());
            case MarbleWhite -> new MarbleWhite();
            case MarbleBlue -> new MarbleNormal(Resource.Shields);
            case MarblePurple -> new MarbleNormal(Resource.Servants);
            case MarbleGrey -> new MarbleNormal(Resource.Stones);
            case MarbleYellow -> new MarbleNormal(Resource.Coins);
            default -> throw new IllegalArgumentException();
        };
    }
}
