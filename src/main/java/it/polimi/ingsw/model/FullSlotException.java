package it.polimi.ingsw.model;

public class FullSlotException extends Exception {

    public FullSlotException(CardDevelopmentSlotID id) {
        super("The slot with id " + id + " is already full! You can't place a development card here.");
    }
}
