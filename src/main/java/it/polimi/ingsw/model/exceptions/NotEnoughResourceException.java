package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.enums.Resource;

public class NotEnoughResourceException extends Throwable {
    public NotEnoughResourceException(Resource resourceToBeUsed) {
        super("You do not have enough" + resourceToBeUsed.toString() + "!");
    }
}
