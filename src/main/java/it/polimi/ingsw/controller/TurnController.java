package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.PlayerState;


/**
 * This class handles the turn logic in the game, advancing the active player when an End Turn action has been
 * received by the active client.
 */

public class TurnController {

    private final GameTable gameTable;
    private PlayerBoard activePlayer;
    private boolean isLorenzoActive;
    private Integer turnCounter;

    /**
     * Constructor : will be called by Controller once the lobby is full and GameTable has been initialized
     *
     * @param _gameTable the GameTable that hosts the game
     */
    public TurnController(GameTable _gameTable) {

        gameTable = _gameTable;
        activePlayer = gameTable.getActivePlayer();
        isLorenzoActive = false;
        turnCounter = 0;
    }

    /**
     * Getter for isLorenzoActive variable
     *
     * @return - true is it's Lorenzo's turn to move, false otherwise
     */
    public boolean isLorenzoActive() {
        return isLorenzoActive;
    }

    /**
     * Active player getter
     *
     * @return activePlayer
     */
    private PlayerBoard getActivePlayer() {
        return activePlayer;
    }

    /**
     * Active player setter
     *
     * @param _activePlayer value assigned to this.activePlayer
     */
    private void setActivePlayer(PlayerBoard _activePlayer) {
        activePlayer = _activePlayer;
        activePlayer.setPlayerState(PlayerState.PLAYING);
    }

    /**
     * Method to be called whenever an EndTurn request is received by the active player : this method is to be called
     * from the Game class, after having checked that the request was received by the active player.
     */
    public void advanceTurn() {

        if (!gameTable.isSinglePlayer()) {

            PlayerBoard oldActivePlayer = activePlayer;
            PlayerBoard newActivePlayer = gameTable.getNextPlayer(activePlayer);

            oldActivePlayer.setPlayerState(PlayerState.IDLE);
            setActivePlayer(newActivePlayer);
        } else {

            if (isLorenzoActive) {

                isLorenzoActive = false;
                setActivePlayer(gameTable.getPlayerByIndex(0));

            } else {

                isLorenzoActive = true;

                activePlayer.setPlayerState(PlayerState.IDLE);
                activePlayer = null;

                //Eventually I can make a Lorenzo move in here, or in the Controller

            }
        }

        if (gameTable.isFirstRound()) {
            turnCounter++;
            if (turnCounter == gameTable.getNumberOfPlayers()) gameTable.endFirstRound();
        }
    }

    /**
     * Method to check whether the PlayerBoard passed as an argument is the active board or not
     *
     * @param _player PlayerBoard to be checked
     * @return true if _player is activePlayer, false otherwise
     */
    boolean isActivePlayer(PlayerBoard _player) {

        if (activePlayer == null) return false;
        return getActivePlayer().equals(_player);
    }

}
