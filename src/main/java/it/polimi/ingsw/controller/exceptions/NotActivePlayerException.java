package it.polimi.ingsw.controller.exceptions;

public class NotActivePlayerException extends Throwable {

    public NotActivePlayerException(String _nickname) {
        super("Player " + _nickname + " is not the active player!");
    }
}
