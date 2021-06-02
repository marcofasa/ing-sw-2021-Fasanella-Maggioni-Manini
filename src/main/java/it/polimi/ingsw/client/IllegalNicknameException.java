package it.polimi.ingsw.client;

public class IllegalNicknameException extends Exception{
    public IllegalNicknameException(){
        super("Nickname must be less than 255 characters and more than 1");
    }
}
