package it.polimi.ingsw.client;

/**
 * thrown if !(nickname.length() > 1 && nickname.length() < 30)
 */
public class IllegalNicknameException extends Exception{
    public IllegalNicknameException(){
        super("Nickname too short or too long");
    }
}
