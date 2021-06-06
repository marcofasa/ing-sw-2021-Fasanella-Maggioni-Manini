package it.polimi.ingsw.server;

/**
 * Thrown if 2 players with the same nickname are connected to this server
 */
public class NicknameAlreadyInUseException extends Exception {
    public NicknameAlreadyInUseException(){
        super("This nickname is invalid because it's already in use");
    }
}
