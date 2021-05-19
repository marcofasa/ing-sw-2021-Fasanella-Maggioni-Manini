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

    /**
     * Basic constructor, it instantiates all sub controllers and sets gamePhase to 0 [InitialSelection phase]
     * @param _gameTable instance of GameTable used for this match
     */
    public Controller(GameTable _gameTable) {
        gameTable = _gameTable;
        actionController = new ActionController(gameTable);
        initialSelectionController = new InitialSelectionController(gameTable);
        turnController = new TurnController(gameTable);
        isSinglePlayer = gameTable.isSinglePlayer();
        gamePhase = 0;
    }

    /**
     * Getter for InitialSelectionController sub controller
     * @return instance of InitialSelectionController
     */
    public InitialSelectionController getInitialSelectionController() {
        return initialSelectionController;
    }

    /**
     * Getter for TurnController sub controller
     * @return instance of TurnController
     */
    public TurnController getTurnController() {
        return turnController;
    }

    /**
     * Getter for ActionController sub controller
     * @return instance of ActionController
     */
    public ActionController getActionController() {
        return actionController;
    }

    /**
     * Getter for PlayerBoard instances
     * @param _nickname the nickname we are looking for
     * @return Instance of PlayerBoard whose internal nickname attribute is equal to _nickname, false if _nickname
     * is not one of the players' nicknames
     */
    protected PlayerBoard getPlayerBoardByNickname(String _nickname) {

        for (PlayerBoard board : gameTable.getPlayerBoards()) {
            if (_nickname.equals(board.getNickname())) return board;
        }
        // This return statement should never be reached
        return null;
    }

    public int getGamePhase() {
        return gamePhase;
    }

    /**
     * Method to evaluate if a given nickname is associated to the active PlayerBoard.
     * @param _nickname the nickname we are looking for.
     * @return true if the PlayerBoard bound to _nickname is active, false otherwise.
     */
    private boolean isActivePlayer(String _nickname) {

        PlayerBoard player = getPlayerBoardByNickname(_nickname);
        if (player == null) return false;

        return turnController.isActivePlayer(player);

    }

    // Functions to be called by Game

    /**
     * Method to be called after a RequestEndTurn message has been received by the active player.
     * @param _nickname the nickname of the player which sent the request.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to end his turn.
     */
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

    /**
     * Private method to be called every time a player ends his turn. It checks if a player has reached one of the two
     * win conditions.
     * @param _player The PlayerBoard we want to inspect for valid end game conditions
     * @return true if _player has achieved an end game condition, false otherwise
     */
    private boolean checkPlayerEndGameConditions(PlayerBoard _player) {

        if (_player == null) return false;

        if (_player.getAllDevelopmentCards().size() == 7) return true;
        if (gameTable.getFaithTrailInstance().getPosition(_player) == 24) return true;

        return false;
    }

    /**
     * Private method to be called every time a Lorenzo ends his turn. It checks if Lorenzo has reached one of the two
     * win conditions.
     * @return true if Lorenzo has achieved an end game condition, false otherwise
     */
    private boolean checkLorenzoEndGameConditions() {

        if (gameTable.getFaithTrailInstance().getLorenzoPosition() == 24) return true;

        for (int i = 0; i < 4; i++) {

            if (gameTable.getCardDevelopmentMarketInstance().getMarket()[0][i].getCards().size() == 0 &&
                    gameTable.getCardDevelopmentMarketInstance().getMarket()[1][i].getCards().size() == 0 &&
                    gameTable.getCardDevelopmentMarketInstance().getMarket()[2][i].getCards().size() == 0) return true;
        }

        return false;
    }

    /**
     * Method to be called after a ResponseInitialSelection message has been received by the active player.
     * @param _nickname The nickname of the player that has sent the response.
     * @param _cardList The list of selected CardLeaders. Must be _cardList.size() == 2.
     * @param _res1 If the player isn't eligible to obtain one bonus resource this field must be null, otherwise it must
     *              be an element of the Resource enum.
     * @param _res2 If the player isn't eligible to obtain a second bonus resource this field must be null, otherwise it must
     *              be an element of the Resource enum.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     */
    void assignInitialBenefits(
            String _nickname,
            ArrayList<CardLeader> _cardList,
            Resource _res1,
            Resource _res2) throws NotActivePlayerException {

        //Single player game logic
        if (isSinglePlayer && gamePhase == 0) {

            initialSelectionController.assignInitialBenefits(
                    getPlayerBoardByNickname(_nickname),
                    _cardList,
                    _res1,
                    _res2);

            gamePhase = 1;
            gameTable.endFirstRound();
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

    /**
     * Method to be called after a RequestBuyDevelopmentCard message has been received by the active player.
     * @param _nickname The nickname of the player that has sent the request.
     * @param _rowIndex The row index of the desired card within the card market matrix.
     * @param _colIndex The column index of the desired card within the card market matrix.
     * @param _placementIndex The index of the selected slot in which to place the newly bought card.
     * @throws InvalidCardDevelopmentPlacementException : thrown if the selected placement index would not consent for a legal placement.
     * @throws InvalidSlotIndexException : thrown if an invalid index was selected as the placement index.
     * @throws NotEnoughResourcesException : thrown if the player does not hold enough Resource s to buy the desired card.
     * @throws FullSlotException : thrown if the slot at _placementIndex already holds 3 cards.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     */
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

    /**
     * Method to be called after a RequestMarketUse message has been received.
     * @param _nickname The nickname of the player that has sent the request.
     * @param _index The index of the selected line from which to retrieve the Marbles.
     * @param _selection Field must be either "row" or "column"
     * @return null if no resources must be discarded after obtaining them from the market, an instance of HashMap<Resource,Integer>
     *     containing the newly obtained resources if one or more resources must be discarded.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     * @throws IllegalArgumentException : thrown if an invalid _index was selected by the player.
     */
    HashMap<Resource, Integer> useMarket(String _nickname, int _index, String _selection) throws NotActivePlayerException, IllegalArgumentException {

        if (isActivePlayer(_nickname)) {

            if (gamePhase == 1 || gamePhase == 2) {
                return actionController.useMarket(getPlayerBoardByNickname(_nickname), _index, _selection);
            }
        }

        throw new NotActivePlayerException(_nickname);
    }

    /**
     * Method to be called after a ResponseDiscardResourceSelection message has been received.
     * @param _nickname The nickname of the player that has sent the request.
     * @param _discardSelection An instance of HashMap containing the amounts to be discarded for each Resource
     * @return null if _discardSelection allowed for the remaining resources to be added to the player's deposit, an instance of HashMap<Resource,Integer>
     *     containing the previously obtained resources if the selection did not allow for the remaining resources to be added to the deposit.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     */
    HashMap<Resource, Integer> discardResources(String _nickname, HashMap<Resource, Integer> _discardSelection) throws NotActivePlayerException {

        if (isActivePlayer(_nickname)) {

            return actionController.discardResources(
                    Objects.requireNonNull(getPlayerBoardByNickname(_nickname)),
                    _discardSelection
            );

        } else throw new NotActivePlayerException(_nickname);

    }

    /**
     * Method to be called after a RequestActivateProduction message has been received.
     * @param _nickname The nickname of the player that has sent the request.
     * @param _selection An instance of ProductionSelection
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     * @throws InvalidSlotIndexException : thrown if an invalid index for a CardDevelopmentSlot was selected in _selection
     * @throws NotEnoughResourcesException : thrown if the player does not hold enough resources to activate all of the selected production powers.
     */
    void activateProductionPowers(String _nickname, ProductionSelection _selection) throws NotActivePlayerException, InvalidSlotIndexException, NotEnoughResourcesException {

        if (isActivePlayer(_nickname)) {

            if (gamePhase == 1 || gamePhase == 2) {
                actionController.activateProductionPowers(getPlayerBoardByNickname(_nickname), _selection);
            }

        } else throw new NotActivePlayerException(_nickname);

    }

    /**
     * Method to be called after a RequestActivateCardLeader message has been received.
     * @param _nickname The nickname of the player that has sent the request.
     * @param _cardToBeActivated The CardLeader, selected by the player, to be activated.
     * @return true if the CardLeader was activated, false otherwise.
     * @throws NotActivePlayerException : thrown if a player who is not the active player has tried to make this action.
     */
    boolean activateLeaderCard(String _nickname, CardLeader _cardToBeActivated) throws NotActivePlayerException {

        if (isActivePlayer(_nickname)) {

            if (gamePhase == 1 || gamePhase == 2) {
                return actionController.activateLeaderCard(turnController.getActivePlayer(), _cardToBeActivated);
            }

        } else throw new NotActivePlayerException(_nickname);

        return false;
    }

    /**
     * Method to be called when gamePhase == 3.
     * @return An instance of HashMap<String, Integer>, mapping the player nicknames to their total victory points.
     */
    HashMap<String, Integer> calculateScores() {

        HashMap<String, Integer> scores = new HashMap<>();

        for (PlayerBoard board : gameTable.getPlayerBoards()) {
            scores.put(board.getNickname(), board.getVictoryPoints());
        }

        return scores;
    }

    public void discardCardLeader(String nickname, Integer cardLeaderIndex) {
        getActionController().discardCardLeader(getPlayerBoardByNickname(nickname), cardLeaderIndex);
    }
}
