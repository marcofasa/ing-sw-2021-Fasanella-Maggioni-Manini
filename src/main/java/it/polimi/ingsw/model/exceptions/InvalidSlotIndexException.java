package it.polimi.ingsw.model.exceptions;

public class InvalidSlotIndexException extends Throwable {

    public InvalidSlotIndexException() {
        super("Invalid index for a Card Slot");
    }
}
