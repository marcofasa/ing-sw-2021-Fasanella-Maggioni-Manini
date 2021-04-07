package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class GameTable {
    private ArrayList<PlayerBoard> players;

    private Lorenzo lorenzo;

    private FaithTrail faithTrail;

    private CardLeaderDeck cardLeaderDeck;

    private Market market;



    public CardLeader getCardLeader(PlayerBoard playerBoard) {
        return cardLeaderDeck.getCardLeader(playerBoard);
    }


    /**
     * discard development card from PlayerBoard
     *
     * @param discardType type of development card
     * @param i           number of dev. cards to discard
     */
    public void discardDevelopmentCardFromSlot(CardDevelopmentType discardType, int i) {
        try {
            throw new ExecutionControl.NotImplementedException("discardDevCard has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        //THIS FUNCTION SHOULD ACTIVATE ENDGAME
    }

    public Lorenzo getLorenzoInstance() {
        if (lorenzo == null) {
            lorenzo = new Lorenzo(this);
        }
        return lorenzo;
    }

    public ArrayList<PlayerBoard> getPlayerBoards() {
        return new ArrayList<>(players);
    }

    public FaithTrail getFaithTrail() {
        return faithTrail;
    }

    public Market getMarketInstance() {
        if (market == null) {
            market = new Market(this);
        }
        return market;
    }

    public void activateEndGame(PlayerBoard p) {
        try {
            throw new ExecutionControl.NotImplementedException("Activate Endgame has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }
}
