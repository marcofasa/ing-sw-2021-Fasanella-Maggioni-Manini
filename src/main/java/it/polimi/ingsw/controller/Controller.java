package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.exceptions.NotActivePlayerException;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class is the main class in the controller package. It is responsible for implementing the state machine that
 * governs the game's logic.
 *
 * Game phase state : InitialSelection (0), MainLoop (1), EndGame (2), CalculateLeaderboard (3), Done (4)
 *
 * This class thus is responsible also for forwarding game actions to the corresponding controller and propagating
 * their response, checking if an end game condition was met after a player has ended his turn and generating the end game
 * leaderboard.
 *
 * This class also validates that the player that has requested an action to be made, is in fact the active player whose
 * turn it is to play, using the turnController.
 * If a different player from activePlayer has sent a request, the Controller throws a NotActivePlayerException
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

    public InitialSelectionController getInitialSelectionController() {
        return initialSelectionController;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public ActionController getActionController() {
        return actionController;
    }

    protected PlayerBoard getPlayerBoardByNickname(String _nickname) {

        for (PlayerBoard board : gameTable.getPlayerBoards()) {
            if (_nickname.equals(board.getNickname())) return board;
        }
        // This return statement should never be reached
        return null;
    }

    private boolean isActivePlayer(String _nickname) {

        PlayerBoard player = getPlayerBoardByNickname(_nickname);
        if (player == null) return false;

        return turnController.isActivePlayer(player);

    }

    // Functions to be called by Game

    void advanceTurn(String _nickname) throws NotActivePlayerException {

        if (isActivePlayer(_nickname)) {
            turnController.advanceTurn();

            // Single player match logic
            if (isSinglePlayer) {

                if (checkPlayerEndGameConditions(getPlayerBoardByNickname(_nickname))) {
                    //Player has won!
                    gamePhase = 3;
                    return;
                }

                //Time for Lorenzo to make a move
                actionController.makeLorenzoMove();

                if (checkLorenzoEndGameConditions()) {
                    gamePhase = 4; //Lorenzo has won. Player has lost.
                }
                else {
                    turnController.advanceTurn(); //Player's turn once again, if the gamePhase was set to 3 this statement is useless
                }
            }

            // Multi player match logic
            else {

                if (gamePhase == 2 && gameTable.getActivePlayer().isFirst()) {
                    //The last player has made his move in the end game : display the leaderboards and terminate the game
                    gamePhase = 3;
                }
                else if (gamePhase == 1 && checkPlayerEndGameConditions(getPlayerBoardByNickname(_nickname))) {
                    //The player that last played has reached an end game condition! Advance game state to endGame
                    gamePhase = 2;
                }
            }
        }
        else throw new NotActivePlayerException(_nickname);
    }

    private boolean checkPlayerEndGameConditions(PlayerBoard _player) {

        if (_player == null) return false;

        if (_player.getAllDevelopmentCards().size() == 7) return true;
        if (gameTable.getFaithTrailInstance().getPosition(_player) == 24) return true;

        return false;
    }

    private boolean checkLorenzoEndGameConditions() {

        if (gameTable.getFaithTrailInstance().getLorenzoPosition() == 24) return true;

        for (int i = 0; i < 4; i++) {

            if (gameTable.getCardDevelopmentMarketInstance().getMarket()[0][i].getCards().size() == 0 &&
                    gameTable.getCardDevelopmentMarketInstance().getMarket()[1][i].getCards().size() == 0 &&
                    gameTable.getCardDevelopmentMarketInstance().getMarket()[2][i].getCards().size() == 0) return true;
        }

        return false;
    }

    void assignInitialBenefits(
            String _nickname,
            ArrayList<CardLeader> _cardList,
            Resource _res1,
            Resource _res2) throws NotActivePlayerException {

        //Single player games do not have an initial selection phase
        if (isSinglePlayer && gamePhase == 0) {
            gamePhase = 1;
            return;
        }

        //Check if game is InitialSelectionPhase and if the current round is still the first
        if (gamePhase == 0 && gameTable.isFirstRound()) {

            //Check if the player that sent the request is the active player
            if (isActivePlayer(_nickname)) {

                //Assign initial benefits
                initialSelectionController.assignInitialBenefits(
                        getPlayerBoardByNickname(_nickname),
                        _cardList,
                        _res1,
                        _res2);

                //Advance game phase if the last player has just made his selection
                if (gameTable.getNextPlayer(getPlayerBoardByNickname(_nickname)).isFirst()) {
                    gamePhase++;
                }

                //Advance turn
                turnController.advanceTurn();

            } else throw new NotActivePlayerException(_nickname);
        }
    }

    void buyAndPlaceDevCard(
            String _nickname,
            int _rowIndex,
            int _colIndex,
            int _placementIndex)
            throws
            InvalidCardDevelopmentPlacementException,
            InvalidSlotIndexException,
            NotEnoughResourcesException,
            FullSlotException,
            NotActivePlayerException {

        if (isActivePlayer(_nickname)) {

            if (gamePhase == 1 || gamePhase == 2) {
                actionController.buyAndPlaceDevCard(getPlayerBoardByNickname(_nickname), _rowIndex, _colIndex, _placementIndex);
            }
        } else throw new NotActivePlayerException(_nickname);

    }

    HashMap<Resource, Integer> useMarket(String _nickname, int _index, String _selection) throws NotActivePlayerException {

        if (isActivePlayer(_nickname)) {

            if (gamePhase == 1 || gamePhase == 2) {
                return actionController.useMarket(getPlayerBoardByNickname(_nickname), _index, _selection);
            }
        }

        throw new NotActivePlayerException(_nickname);
    }

    HashMap<Resource, Integer> discardResources(String _nickname, HashMap<Resource, Integer> _discardSelection) throws NotActivePlayerException {

        if (isActivePlayer(_nickname)) {

            return actionController.discardResources(
                    Objects.requireNonNull(getPlayerBoardByNickname(_nickname)),
                    _discardSelection
            );

        } else throw new NotActivePlayerException(_nickname);

    }

    void activateProductionPowers(String _nickname, ProductionSelection _selection) throws NotActivePlayerException, InvalidSlotIndexException, NotEnoughResourcesException {

        if (isActivePlayer(_nickname)) {

            if (gamePhase == 1 || gamePhase == 2) {
                actionController.activateProductionPowers(getPlayerBoardByNickname(_nickname), _selection);
            }

        } else throw new NotActivePlayerException(_nickname);

    }

    boolean activateLeaderCard(String _nickname, CardLeader _cardToBeActivated) throws NotActivePlayerException {

        if (isActivePlayer(_nickname)) {

            if (gamePhase == 1 || gamePhase == 2) {
                return actionController.activateLeaderCard(_cardToBeActivated);
            }

        } else throw new NotActivePlayerException(_nickname);

        return false;
    }

    HashMap<String, Integer> calculateScores() {

        HashMap<String, Integer> scores = new HashMap<>();

        for (PlayerBoard board : gameTable.getPlayerBoards()) {
            scores.put(board.getNickname(), board.getVictoryPoints());
        }

        return scores;
    }
}
