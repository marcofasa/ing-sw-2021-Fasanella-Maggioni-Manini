package it.polimi.ingsw.model;

public abstract class FaithCell {
    private int victoryPoints;
    private FaithSection section;
    FaithSection getSection(){
        return section;
    }
    public void activate() {
    }
}
