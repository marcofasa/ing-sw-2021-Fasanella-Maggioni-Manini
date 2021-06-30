package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.enums.CardDevelopmentSlotID;

public class FullSlotException extends Exception {

    public FullSlotException(CardDevelopmentSlotID id) {
        super("The slot with id " + id + " is already full! You can't place a development card here.");
    }
}
