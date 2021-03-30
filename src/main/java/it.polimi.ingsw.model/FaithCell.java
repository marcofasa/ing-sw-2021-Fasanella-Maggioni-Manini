package it.polimi.ingsw.model;

public abstract class FaithCell {
    private int victoryPoints;
    private FaithSection section;
    private FaithCellType type;

    FaithSection getSection(){
        return section;
    }

    /**
    *Returns 1 if the type of FaithCell is Pope
     */
    public int isPope(){
      if(this.type==FaithCellType.Pope) return 1;
      else return 0;
    }

    public FaithCellType getType() {
        return type;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
