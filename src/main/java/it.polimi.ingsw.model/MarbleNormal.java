package it.polimi.ingsw.model;

/**
 * Concrete Marble
 */
public class MarbleNormal extends Marble {

    private final Resource resource;

    /**
     * Constructor of the class
     * @param resource type of resurce represented by the marble
     */
    MarbleNormal(Resource resource){
        this.resource = resource;
    }

    public Marble clone(){
        return new MarbleNormal(resource);
    };

    /**
     * Adds one resource to a player
     * @param playerBoard player where to activate marble
     */
    @Override
    public void activate(PlayerBoard playerBoard) {

    }
}
