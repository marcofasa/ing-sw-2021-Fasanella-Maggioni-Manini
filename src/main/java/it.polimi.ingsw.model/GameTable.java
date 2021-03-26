package it.polimi.ingsw.model;

import java.util.ArrayList;

public class GameTable {
    private ArrayList<PlayerBoard> players;

    private Lorenzo lorenzo;

    public Lorenzo getLorenzoInstance() {
        return lorenzo;
    }

    public ArrayList<PlayerBoard> getPlayerBoards() {
        return new ArrayList<>(players);
    }
}
