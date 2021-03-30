package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class GameTable {
    private ArrayList<PlayerBoard> players;

    private Lorenzo lorenzo;

    private FaithTrail faithTrail;

    private CardLeaderDeck cardLeaderDeck;

    public CardLeader getCardLeader(PlayerBoard playerBoard) {
        return cardLeaderDeck.getCardLeader(playerBoard);
    }


    /**
     * discard development card from PlayerBoard
     * @param discardType type of development card
     * @param i number of dev. cards to discard
     */
    public void discardDevelopmentCard(CardDevelopmentType discardType, int i) {
        try {
            throw new ExecutionControl.NotImplementedException("discardDevCard has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    public Lorenzo getLorenzoInstance() {
        return lorenzo;
    }

    public ArrayList<PlayerBoard> getPlayerBoards() {
        return new ArrayList<>(players);
    }

    public FaithTrail getFaithTrail() {
        return faithTrail;
    }


}
