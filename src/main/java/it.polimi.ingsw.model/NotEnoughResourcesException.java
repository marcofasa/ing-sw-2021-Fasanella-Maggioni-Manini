package it.polimi.ingsw.model;

public class NotEnoughResourcesException extends Throwable {

    public NotEnoughResourcesException(String playerNickname, int rowIndex, int colIndex) {
        super("Player " + playerNickname + " does not have enough resources to buy the card at row : "
                + rowIndex + " and col : " + colIndex);
    }

}
