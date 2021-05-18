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

    /**
     * Basic constructor to set internal GameTable reference.
     * @param _gameTable Instance of GameTable associated with the current match.
     */
    public ActionController(GameTable _gameTable) {
        gameTable = _gameTable;
    }


    /* *** Standard user actions *** */

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

    /**
     * Central method to grab Resources from the Marble market
     * @param _player the player that wishes to grab resources
     * @param _index the index of the row or column selected by the player
     * @param _selection "row" or "column"
     * @return Null if the player doesn't need to discard any resources, instance of Hashmap if the player
     * has to discard some resources.
     * @throws IllegalArgumentException - when an invalid index is given for the corresponding _selection
     */
    public HashMap<Resource, Integer> useMarket(PlayerBoard _player, int _index, String _selection) throws IllegalArgumentException {

        ArrayList<Marble> marbles;

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

        HashMap<Resource, Integer> resources = _player.consumeMarbles(marbles);

        return _player.tryAddResources(resources);
    }

    /**
     * Method used to activate a selection of production powers.
     * @param _player The PlayerBoard requesting for his powers to be activated.
     * @param _productionSelection Instance of ProductionSelection containing the powers selected by the player.
     * @throws InvalidSlotIndexException : thrown if an invalid index was selected for the CardDevelopmentSlot(s).
     * @throws NotEnoughResourcesException : thrown if the player does not hold enough resources to activate all selected powers.
     */
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

    /**
     * Method used to try and activate a CardLeader.
     * @param _leaderToBeActivated CardLeader to be activated.
     * @return true if the CardLeader was successfully activated, false if the card could not be activated.
     */
    public boolean activateLeaderCard(PlayerBoard _board, CardLeader _leaderToBeActivated) {

        if (_leaderToBeActivated.canActivate(_board)) {
            _leaderToBeActivated.activate(_board);
            return true;
        } else
            return false;
    }

    /**
     * This method discards the resources selected by a player, after he has used the market and it was found
     * that he cannot place all of the collected resources in his deposit
     * @param _player Player who is discarding the resources
     * @param _discardSelection A HashMap<Resource, Integer> that represents the resources to be discarded
     * @return null if the selection has generated a consistent state for the deposit,
     * a map with the obtained resources from which the selection must be made, if otherwise.
     */
    public HashMap<Resource, Integer> discardResources(PlayerBoard _player, HashMap<Resource, Integer> _discardSelection) {

       //Grab a clone of the previously obtained resources
       HashMap<Resource, Integer> tempDepositClone = new HashMap<>(_player.getTempDeposit());

       for (Resource res : Resource.values()) {

           //Check that the player has not discarded more Resources that he can discard

           if (_discardSelection.containsKey(res)) {
               tempDepositClone.put(res, tempDepositClone.get(res) - _discardSelection.get(res));
           }

       }

       //Try adding the obtained resources
       if (_player.tryAddResources(tempDepositClone) == null) {

           //Selection was successful: move all other players faith pawns and return true

           int numDiscarded = 0;
           for (Integer i : _discardSelection.values()) numDiscarded += i;

           for (int i = 0; i < numDiscarded; i++)  {

               if (gameTable.isSinglePlayer()) {
                   gameTable.moveFaithTrailLorenzo();
               } else {
                   gameTable.moveOthersFaithTrail(_player);
               }

           }
           return null;

       } else {

           //Selection was not effective: return false and force another selection
           return tempDepositClone;
       }
    }

    public void discardCardLeader(PlayerBoard playerBoard, CardLeader cardLeader){
        playerBoard.discardCardLeader(cardLeader);
    }

    /**
     * Method invoked after a player has ended his turn in a single player match, forcing Lorenzo to make a move.
     */
    public void makeLorenzoMove() {

        Lorenzo lorenzo = gameTable.getLorenzoInstance();
        lorenzo.getActionCardDeck().getCard().activate();
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
