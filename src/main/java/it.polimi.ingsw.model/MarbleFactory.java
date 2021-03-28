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
        switch (marbleType) {
            case MarbleRed:
                new MarbleRed(gameTable.getFaithTrail());
            case MarbleWhite:
                return new MarbleWhite();
            case MarbleBlue:
                return new MarbleNormal(Resource.Shields);
            case MarblePurple:
                return new MarbleNormal(Resource.Servants);
            case MarbleGrey:
                return new MarbleNormal(Resource.Stone);
            case MarbleYellow:
                return new MarbleNormal(Resource.Coins);
            default:
                throw new IllegalArgumentException();
        }
    }
}
