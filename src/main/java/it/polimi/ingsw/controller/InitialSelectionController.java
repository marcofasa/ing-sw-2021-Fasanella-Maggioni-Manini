package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.enums.Resource;
import it.polimi.ingsw.model.exceptions.CardLeaderWrongOwnerException;

import java.util.ArrayList;

/**
 * This class handles the initial phase of the game where players, starting from the first player, select their
 * leader cards from those that were drawn and, if eligible, also select one or two bonus resources.
 */

public class InitialSelectionController {

    private final GameTable gameTable;

    /**
     * Basic constructor to set internal GameTable reference.
     * @param _gameTable Instance of GameTable associated with the current match.
     */
    public InitialSelectionController(GameTable _gameTable) {
        gameTable = _gameTable;
    }

    /**
     * Method to distribute the initial benefits to a specific player
     * @param _player PlayerBoard whose turn it is to be assigned the initial benefits
     * @param cardList a list of exactly 2 CardLeader, which are the player's selected leader cards
     * @param res1 the first bonus resource to be assigned to an eligible player
     * @param res2 the second bonus resource to be assigned to an eligible player
     */
    public void assignInitialBenefits(
            PlayerBoard _player,
            ArrayList<CardLeader> cardList,
            Resource res1,
            Resource res2) throws IllegalArgumentException, CardLeaderWrongOwnerException {

        // This method throws an IllegalArgumentException : it is caught and handled in Game class
        gameTable.setupHelper(getPlayerIndex(_player), res1, res2);

        // This method throws a CardLeaderWrongOwnerException : it is caught and handled in Game class
        _player.selectCardsLeader(cardList.get(0), cardList.get(1));
    }


    /**
     * Private method to retrieve a player's index in the array that dictates the play order, based on a PlayerBoard reference.
     * @param _player The PlayerBoard's whose index we want to fetch.
     * @return _player's index.
     */
    private int getPlayerIndex(PlayerBoard _player) {
        return gameTable.getIndexFromPlayer(_player);
    }

}
