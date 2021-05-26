package it.polimi.ingsw.model;

import java.io.Serializable;

public class FaithCell implements Serializable {
    private int victoryPoints;
    private FaithSection section;
    private FaithCellType type;

    FaithSection getSection(){

        return this.section;
    }

    public FaithCellType getType() {
        return this.type;
    }

    public int getVictoryPoints() {
        return this.victoryPoints;
    }

    /**
     * Constructor of each cell, the values of points and type of FaithSection depend on index
     * @param index of Arraylist cells in FaithTrail
     */
    public FaithCell(int index){
        switch(index){
            case 0:case 1:case 2:
                this.type=FaithCellType.Not_Report;
                this.section=FaithSection.One;
                this.victoryPoints=0;
                break;
            case 3:case 4:
                this.type=FaithCellType.Not_Report;
                this.section=FaithSection.One;
                this.victoryPoints=1;
                break;
            case 5:
                this.type=FaithCellType.Report;
                this.section=FaithSection.One;
                this.victoryPoints=1;
                break;
            case 6:case 7:
                this.type=FaithCellType.Report;
                this.section=FaithSection.One;
                this.victoryPoints=2;
                break;
            case 8:
                this.type=FaithCellType.Pope;
                this.section=FaithSection.One;
                this.victoryPoints=2;
                break;
            case 9:case 10:case 11:
                this.type=FaithCellType.Not_Report;
                this.section=FaithSection.Two;
                this.victoryPoints=4;
                break;
            case 12:case 13:case 14:
                this.type=FaithCellType.Report;
                this.section=FaithSection.Two;
                this.victoryPoints=6;
                break;
            case 15:
                this.type=FaithCellType.Report;
                this.section=FaithSection.Two;
                this.victoryPoints=9;
                break;
            case 16:
                this.type=FaithCellType.Pope;
                this.section=FaithSection.Two;
                this.victoryPoints=9;
                break;
            case 17:
                this.type=FaithCellType.Not_Report;
                this.section=FaithSection.Three;
                this.victoryPoints=9;
                break;
            case 18:
                this.type=FaithCellType.Not_Report;
                this.section=FaithSection.Three;
                this.victoryPoints=12;
                break;
            case 19: case 20:
                this.type=FaithCellType.Report;
                this.section=FaithSection.Three;
                this.victoryPoints=12;
                break;
            case 21: case 22: case 23:
                this.type=FaithCellType.Report;
                this.section=FaithSection.Three;
                this.victoryPoints=16;
                break;
            case 24:
                this.type=FaithCellType.Pope;
                this.section=FaithSection.Three;
                this.victoryPoints=20;
                break;

        }

    }

    /**
     *Returns 1 if the type of FaithCell is Pope
     */
    public int isPope(){
        if(this.type==FaithCellType.Pope) return 1;
        else return 0;
    }

    /**
     *Returns 1 if the type of FaithCell is Normal
     */
    public int isNotReport(){
        if(this.type==FaithCellType.Not_Report) return 1;
        else return 0;
    }

    /**
     *Returns 1 if the type of FaithCell is Report
     */
    public int isReport(){
        if(this.type==FaithCellType.Report) return 1;
        else return 0;
    }

}
