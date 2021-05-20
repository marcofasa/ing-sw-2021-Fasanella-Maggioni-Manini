package it.polimi.ingsw.controller.exceptions;

public class MainMoveAlreadyMadeException extends Throwable {

    public MainMoveAlreadyMadeException() {
        super("Main move was already made!");
    }
}
