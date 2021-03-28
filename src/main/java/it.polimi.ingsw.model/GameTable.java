package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GameTable {
    private ArrayList<PlayerBoard> players;

    private Lorenzo lorenzo;

    private FaithTrail faithTrail;

    private CardLeaderDeck cardLeaderDeck;

    public CardLeader getCardLeader(PlayerBoard playerBoard){
        return cardLeaderDeck.getCardLeader(playerBoard);
    };

    public Lorenzo getLorenzoInstance() {
        return lorenzo;
    }

    public ArrayList<PlayerBoard> getPlayerBoards() {
        return new ArrayList<>(players);
    }

    public FaithTrail getFaithTrail() {
        return faithTrail;
    };


}
