package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;

/**
 * This class is the main class in the controller package. It is responsible for implementing the state machine that
 * governs the game's logic.
 *
 * Game phase state : ResourceSelection, LeaderSelection, MainLoop, EndGame, Done
 *
 * This class thus is responsible also for forwarding game actions to the corresponding controller and managing
 * their response, checking if an end game condition was met after a player's valid move and generating the end game
 * leaderboard.
 *
 */
public class Controller {

    private final GameTable gameTable;
    private final boolean isSinglePlayer;

    public Controller(GameTable _gameTable) {
        gameTable = _gameTable;
        isSinglePlayer = gameTable.isSinglePlayer();
    }
}
