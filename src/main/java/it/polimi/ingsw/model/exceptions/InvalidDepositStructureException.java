package it.polimi.ingsw.model.exceptions;

public class InvalidDepositStructureException extends Throwable {
    public InvalidDepositStructureException() {
        super("After this operation, your Deposit is too full! You need to discard resources.");
    }
}
