package it.polimi.ingsw.model;

public abstract class FaithTile {
    private FaithSection section;
    private FaithTileStatus status;
    int getVictoryPoints(){
        return 1;
    }
}
