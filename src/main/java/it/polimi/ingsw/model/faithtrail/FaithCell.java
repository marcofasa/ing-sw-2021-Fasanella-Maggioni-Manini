package it.polimi.ingsw.model.faithtrail;

import it.polimi.ingsw.model.enums.FaithCellType;
import it.polimi.ingsw.model.enums.FaithSection;

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
        switch (index) {
            case 0, 1, 2 -> {
                this.type = FaithCellType.Not_Report;
                this.section = FaithSection.One;
                this.victoryPoints = 0;
            }
            case 3, 4 -> {
                this.type = FaithCellType.Not_Report;
                this.section = FaithSection.One;
                this.victoryPoints = 1;
            }
            case 5 -> {
                this.type = FaithCellType.Report;
                this.section = FaithSection.One;
                this.victoryPoints = 1;
            }
            case 6, 7 -> {
                this.type = FaithCellType.Report;
                this.section = FaithSection.One;
                this.victoryPoints = 2;
            }
            case 8 -> {
                this.type = FaithCellType.Pope;
                this.section = FaithSection.One;
                this.victoryPoints = 2;
            }
            case 9, 10, 11 -> {
                this.type = FaithCellType.Not_Report;
                this.section = FaithSection.Two;
                this.victoryPoints = 4;
            }
            case 12, 13, 14 -> {
                this.type = FaithCellType.Report;
                this.section = FaithSection.Two;
                this.victoryPoints = 6;
            }
            case 15 -> {
                this.type = FaithCellType.Report;
                this.section = FaithSection.Two;
                this.victoryPoints = 9;
            }
            case 16 -> {
                this.type = FaithCellType.Pope;
                this.section = FaithSection.Two;
                this.victoryPoints = 9;
            }
            case 17 -> {
                this.type = FaithCellType.Not_Report;
                this.section = FaithSection.Three;
                this.victoryPoints = 9;
            }
            case 18 -> {
                this.type = FaithCellType.Not_Report;
                this.section = FaithSection.Three;
                this.victoryPoints = 12;
            }
            case 19, 20 -> {
                this.type = FaithCellType.Report;
                this.section = FaithSection.Three;
                this.victoryPoints = 12;
            }
            case 21, 22, 23 -> {
                this.type = FaithCellType.Report;
                this.section = FaithSection.Three;
                this.victoryPoints = 16;
            }
            case 24 -> {
                this.type = FaithCellType.Pope;
                this.section = FaithSection.Three;
                this.victoryPoints = 20;
            }
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
