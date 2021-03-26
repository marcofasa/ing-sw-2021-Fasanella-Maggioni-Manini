package it.polimi.ingsw.model;

public abstract class FaithCell {
    private int victoryPoints;
    private FaithSection section;
    int isPope(){
        return 1;
    }
    FaithSection getSection(){
        return section;
    }
    public void activate() {
    }
}
