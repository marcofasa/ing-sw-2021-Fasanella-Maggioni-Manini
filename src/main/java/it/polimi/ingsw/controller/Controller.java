package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.PlayerBoard;

/**
 * This class is the main class in the controller package. It is responsible for implementing the state machine that
 * governs the game's logic.
 *
 * Game phase state : InitialSelection (0), MainLoop (1), EndGame (2), CalculateLeaderboard (3), Done (4)
 *
 * This class thus is responsible also for forwarding game actions to the corresponding controller and managing
 * their response, checking if an end game condition was met after a player's valid move and generating the end game
 * leaderboard.
 *
 * This class also validates that the player that has requested an action to be made, is in fact the active player whose
 * turn it is to play, using the turnController.
 * If a different player from activePlayer has sent a request, the Controller responds with a NotActivePlayerError
 */
public class Controller {

    private final GameTable gameTable;
    private final ActionController actionController;
    private final InitialSelectionController initialSelectionController;
    private final TurnController turnController;
    private final boolean isSinglePlayer;
    private int gamePhase;

    public Controller(GameTable _gameTable) {
        gameTable = _gameTable;
        actionController = new ActionController(gameTable);
        initialSelectionController = new InitialSelectionController(gameTable);
        turnController = new TurnController(gameTable);
        isSinglePlayer = gameTable.isSinglePlayer();
        gamePhase = 0;
    }

    private PlayerBoard getPlayerBoardByNickname(String nickname) {

        for (PlayerBoard board : gameTable.getPlayerBoards()) {
            if (nickname.equals(board.getNickname())) return board;
        }
        // This return statement should never be reached
        return null;
    }

    public InitialSelectionController getInitialSelectionController() {
        return initialSelectionController;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public ActionController getActionController() {
        return actionController;
    }
}
