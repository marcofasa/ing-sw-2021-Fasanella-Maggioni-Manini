package it.polimi.ingsw.client;

public class IllegalNicknameException extends Exception{
    public IllegalNicknameException(){
        super("Nickname too short or too long");
    }
}
