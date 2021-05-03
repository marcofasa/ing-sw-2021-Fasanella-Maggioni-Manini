package it.polimi.ingsw.server;

public class NicknameAlreadyInUseException extends Exception {
    public NicknameAlreadyInUseException(){
        super("This nickname is invalid because it's already in use");
    }
}
