package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.PlayerState;

import java.util.LinkedHashMap;


/**
 * This class handles the turn logic in the game, advancing the active player when an End Turn action has been
 * received by the active client.
 *
 * Note that this class also keeps track of the connection statuses of all players: this is because the game
 * skips a player's turn when the player's client is disconnected from the server.
 */

public class TurnController {

    private final GameTable gameTable;
    private PlayerBoard activePlayer;
    private boolean isLorenzoActive;
    private Integer turnCounter;
    private final LinkedHashMap<String, Boolean> connectionStatuses;

    /**
     * Basic constructor to set internal GameTable reference.
     * @param _gameTable Instance of GameTable associated with the current match.
     */
    public TurnController(GameTable _gameTable) {

        gameTable = _gameTable;
        activePlayer = gameTable.getActivePlayer();
        isLorenzoActive = false;
        turnCounter = 0;

        connectionStatuses = new LinkedHashMap<>();
        for(PlayerBoard board : gameTable.getPlayerBoards()) {
            connectionStatuses.put(
                    board.getNickname(),
                    true
            );
        }
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
    public PlayerBoard getActivePlayer() {
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
     * This class lives for this method
     *
     * Method to be called whenever an EndTurn request is received by the active player : this method is to be called
     * from the Game class, after having checked that the request was received by the active player.
     */
    public void advanceTurn() {

        if (!gameTable.isSinglePlayer()) {

            PlayerBoard oldActivePlayer = activePlayer;
            PlayerBoard newActivePlayer = gameTable.getNextPlayer(activePlayer);

            while (!isPlayerConnected(newActivePlayer)) {
                newActivePlayer = gameTable.getNextPlayer(newActivePlayer);
            }

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
            }
        }

        if (gameTable.isFirstRound()) {
            turnCounter++;
            if (turnCounter == gameTable.getNumberOfPlayers()) gameTable.endFirstRound();
        }
    }

    /**
     * Method to force a turn advance when the active player gets disconnected
     * @param _nickname the nickname of the disconnected player. Must be activePlayer's nickname to trigger advanceTurn()
     */
    public boolean forceAdvanceTurn(String _nickname) {

        if (_nickname.equals(activePlayer.getNickname())) {
            advanceTurn();
            return true;
        }

        return false;

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

    /**
     * Overloaded method
     * Getter for a specified PlayerBoard's connection status.
     * @param _board PlayerBoard whose status is of interest.
     * @return true if the corresponding client is connected, false otherwise.
     */
    boolean isPlayerConnected(PlayerBoard _board) {
        return connectionStatuses.get(_board.getNickname());
    }

    /**
     * Overloaded method
     * Getter for a specified player's connection status.
     * @param _nickname Player's nickname whose status is of interest.
     * @return true if the corresponding client is connected, false otherwise.
     */
    boolean isPlayerConnected(String _nickname) {
        return connectionStatuses.get(_nickname);
    }

    /**
     * Overloaded method
     * Setter for a specified PlayerBoard's connection status.
     * @param _board PlayerBoard whose status is to be set.
     * @param _newStatus New connection status, either true or false.
     */
    void setPlayerConnection(PlayerBoard _board, Boolean _newStatus) {
        connectionStatuses.put(_board.getNickname(), _newStatus);
    }

    /**
     * Overloaded method
     * Setter for a specified player's connection status.
     * @param _nickname Player's nickname whose status is to be set.
     * @param _newStatus New connection status, either true or false.
     */
    public void setPlayerConnection(String _nickname, Boolean _newStatus) {
        connectionStatuses.put(_nickname, _newStatus);
    }

}
