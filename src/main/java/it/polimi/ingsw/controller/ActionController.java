package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This class handles any action that can be requested by a player.
 *
 * It assumes that Controller has already checked that the request has been received by the active player, thus
 * the only responsibility this class has is to make the requested action and return whether the operation was
 * successful or not.
 */
public class ActionController {

    private final GameTable gameTable;

    public ActionController(GameTable _gameTable) {
        gameTable = _gameTable;
    }

    /**
     * Method to be called when a ClientRequest : RequestBuyCardDevelopment is received
     * @param _player the active player that wishes to buy a development card
     * @param _rowIndex the row index inside the market matrix of the desired card
     * @param _colIndex the column index inside the market matrix of the desired card
     * @param _placementIndex the index of the slot in which the player wants to place the bought card
     * @throws NotEnoughResourcesException - player does not have enough resource to buy the card
     * @throws InvalidCardDevelopmentPlacementException - card can't be placed because of placement rules
     * @throws InvalidSlotIndexException - _placementIndex is such that : _placementIndex < 0 || _placementIndex > 2
     * @throws FullSlotException - the slot at _placementIndex is full
     */
    public void buyAndPlaceDevCard(
            PlayerBoard _player,
            int _rowIndex,
            int _colIndex,
            int _placementIndex)
                throws
                NotEnoughResourcesException,
                InvalidCardDevelopmentPlacementException,
                InvalidSlotIndexException,
                FullSlotException {

        // Buy the development card from the market
        CardDevelopment boughtCard = _player.buyCardDevelopmentCardFromMarket(_rowIndex, _colIndex);

        // Place the card on the desired slot
        _player.placeCardDevelopmentCardOnBoard(boughtCard, _placementIndex);
    }

    public boolean useMarket(PlayerBoard _player, int index, String selection) throws IllegalArgumentException {

        ArrayList<Marble> marbles = new ArrayList<>();

        // Row was selected
        if (selection.equalsIgnoreCase("row")) {

            //Throws IllegalArgumentException if index < 1 || index > 3
            marbles = _player.getMarketRow(index);

        // Column was selected
        } else if (selection.equalsIgnoreCase("column")) {

            //Throws IllegalArgumentException if index < 1 || index > 4
            marbles = _player.getMarketCol(index);

        // An illegal key was set for RequestMarketUse
        } else throw new IllegalArgumentException();

        return _player.tryAddMarbles(marbles);
    }

    public void activateProductionPowers(PlayerBoard _player, ProductionSelection productionSelection) throws InvalidSlotIndexException, NotEnoughResourcesException {

        if (!_player.tryActivateProductions(productionSelection)) {
            throw new NotEnoughResourcesException(_player.getNickname());
        }
    }

    public boolean activateLeaderCard(CardLeader _leaderToBeActivated) {

        if (_leaderToBeActivated.canActivate()) {
            _leaderToBeActivated.activate();
            return true;
        } else
            return false;
    }


}
