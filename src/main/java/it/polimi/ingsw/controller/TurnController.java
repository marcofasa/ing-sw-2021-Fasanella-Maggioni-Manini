package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.PlayerBoard;
import it.polimi.ingsw.model.PlayerState;

import java.util.ArrayList;


/**
 * This class handles the turn logic in the game, advancing the active player when an End Turn action has been
 * received by the active client.
 */

public class TurnController {

    private final GameTable gameTable;
    private final Controller controller;
    private PlayerBoard activePlayer;
    private ArrayList<PlayerBoard> players;

    /**
     * Constructor : will be called by Controller once the lobby is full and GameTable has been initialized
     * @param _gameTable the GameTable that hosts the game
     * @param _controller the Controller that will dispatch user commands
     */

    public TurnController(GameTable _gameTable, Controller _controller) {

        gameTable = _gameTable;
        controller = _controller;
        activePlayer = gameTable.getActivePlayer();
        players = gameTable.getPlayerBoards();
    }

    private PlayerBoard getActivePlayer() {
        return activePlayer;
    }

    private void setActivePlayer(PlayerBoard activePlayer) {
        this.activePlayer = activePlayer;
    }

    public PlayerBoard advanceTurn() {

        activePlayer.setPlayerState(PlayerState.IDLE);

        gameTable.getNextPlayer(activePlayer).setPlayerState(PlayerState.PLAYING);
        setActivePlayer(gameTable.getActivePlayer());

        return activePlayer;
    }

    public boolean isActivePlayer(PlayerBoard player) {
        return getActivePlayer().equals(player);
    }
}
