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

        /*
        Conditions for InvalidCardDevelopmentPlacementException, InvalidSlotIndexException and FullSlotException
        must be checked before buying the card! Otherwise the card will be picked up by GC and lost.
         */

        checkBuyAndPlaceParams(_player, _rowIndex, _colIndex, _placementIndex);

        // Buy the development card from the market
        CardDevelopment boughtCard = _player.buyCardDevelopmentCardFromMarket(_rowIndex, _colIndex);

        // Place the card on the desired slot
        _player.placeCardDevelopmentCardOnBoard(boughtCard, _placementIndex);
    }

    public boolean useMarket(PlayerBoard _player, int _index, String _selection) throws IllegalArgumentException {

        ArrayList<Marble> marbles = new ArrayList<>();

        // Row was selected
        if (_selection.equalsIgnoreCase("row")) {

            //Throws IllegalArgumentException if index < 1 || index > 3
            marbles = _player.getMarketRow(_index);

        // Column was selected
        } else if (_selection.equalsIgnoreCase("column")) {

            //Throws IllegalArgumentException if index < 1 || index > 4
            marbles = _player.getMarketCol(_index);

        // An illegal key was set for RequestMarketUse
        } else throw new IllegalArgumentException();

        return _player.tryAddMarbles(marbles);
    }

    public void activateProductionPowers(
            PlayerBoard _player,
            ProductionSelection _productionSelection)
                throws
                InvalidSlotIndexException,
                NotEnoughResourcesException {

        if (!_player.tryActivateProductions(_productionSelection)) {
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

    /**
     * This private method is to be called to sanitize the input given to buyAndPlaceDevCard function.
     * The method assures that the second statement of the method will not throw an exception, as if this were to occur
     * the bought card would be picked up by GC and lost.
     * @param _player _player param in buyAndPlaceDevCard
     * @param _rowIndex _rowIndex param in buyAndPlaceDevCard
     * @param _colIndex _colIndex param in buyAndPlaceDevCard
     * @param _placementIndex _placementIndex param in buyAndPlaceDevCard
     * @throws InvalidSlotIndexException - thrown if an invalid slot index is passed
     * @throws FullSlotException - thrown if selected slot already holds 3 cards
     * @throws InvalidCardDevelopmentPlacementException - thrown if a placement in the selected slot would break the game rules
     */
    private void checkBuyAndPlaceParams(
            PlayerBoard _player,
            int _rowIndex,
            int _colIndex,
            int _placementIndex)
                throws
                InvalidSlotIndexException,
                FullSlotException,
                InvalidCardDevelopmentPlacementException {

        //Check for InvalidSlotIndexException
        if (_placementIndex < 0 || _placementIndex > 2)
            throw new InvalidSlotIndexException();

        //Check for FullSlotException
        if (_player.getCardDevelopmentSlotByIndex(_placementIndex).getSize() > 2)
            throw new FullSlotException(CardDevelopmentSlotID.values()[_placementIndex]);

        //Check for InvalidCardDevelopmentPlacementException

        CardDevelopment desiredCard = gameTable.getCardDevelopmentMarketInstance().getMarket()[_rowIndex][_colIndex].getCards().peek();

        CardDevelopmentSlot targetSlot = _player.getCardDevelopmentSlotByIndex(_placementIndex);

        switch (desiredCard.getCardLevel()) {
            case One:

                if (targetSlot.getSize() != 0)
                    throw new InvalidCardDevelopmentPlacementException(null);
                break;

            case Two:
                if (targetSlot.getSize() != 1 || targetSlot.getTop().getCardLevel() != CardDevelopmentLevel.One)
                    throw new InvalidCardDevelopmentPlacementException(null);
                break;

            case Three:
                if (targetSlot.getSize() != 2 || targetSlot.getTop().getCardLevel() != CardDevelopmentLevel.Two)
                    throw new InvalidCardDevelopmentPlacementException(null);

                break;

        }

    }
}
