package it.polimi.ingsw.model;

public class InvalidSlotIndexException extends Throwable {

    public InvalidSlotIndexException() {
        super("Invalid index for a Card Slot");
    }
}
