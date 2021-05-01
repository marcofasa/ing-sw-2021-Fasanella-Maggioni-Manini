package it.polimi.ingsw.server;

public class NicknameAlreadyInUseException extends IllegalArgumentException {
    public NicknameAlreadyInUseException(){
        super("This nickname is invalid because it's already in use");
    }
}
