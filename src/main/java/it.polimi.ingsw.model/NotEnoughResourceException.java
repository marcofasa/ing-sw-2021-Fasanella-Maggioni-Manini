package it.polimi.ingsw.model;

public class NotEnoughResourceException extends Throwable {
    public NotEnoughResourceException(Resource resourceToBeUsed) {
        super("You do not have enough" + resourceToBeUsed.toString() + "!");
    }
}
