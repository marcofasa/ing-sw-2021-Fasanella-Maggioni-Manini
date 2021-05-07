package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.PlayerBoard;

/**
 * This class handles the initial phase of the game where players, starting from the first player, select their
 * leader cards from those that were drawn and, if eligible, also select one or two bonus resources.
 */

public class InitialSelectionController {

    private final GameTable gameTable;


    public InitialSelectionController(GameTable _gameTable) {
        gameTable = _gameTable;
    }

    /**
     * Method to distribute the initial benefits to a specific player
     * @param _player PlayerBoard whose turn it is to be assigned the initial benefits
     */

    /*
    TODO: this method must also receive the Resource selection the corresponding client has made, in order to be able
        to call gameTable.setupHelper()!
     */
    public void assignInitialBenefits(PlayerBoard _player) {

        int index = gameTable.getIndexFromPlayer(_player);


    }

}
