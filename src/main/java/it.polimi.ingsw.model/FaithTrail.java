package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Map;

public class FaithTrail {
    private int lenght;
    private ArrayList<FaithCell> cells;
    private Map<PlayerBoard,Integer> playerPosition;
    FaithCell getCell(int i){
        return cells.get(i);
    }
    int getVictoryPoints(){
        return 1;
    }

    void movePlayer(PlayerBoard p,int n){
    }
}
