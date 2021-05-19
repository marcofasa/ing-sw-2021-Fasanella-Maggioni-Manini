package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FaithCellTest {

    @Test
    public void FaithCell(){
        //Initializing
        ArrayList<FaithCell> cells= new ArrayList<FaithCell>();
        int l=25;
        for (int i = 0; i < l; i++) {
            cells.add(new FaithCell(i));
        }
        //Victory Points test
        assertEquals(0,cells.get(2).getVictoryPoints());
        assertEquals(1,cells.get(4).getVictoryPoints());
        assertEquals(2,cells.get(7).getVictoryPoints());
        assertEquals(4,cells.get(9).getVictoryPoints());
        assertEquals(4,cells.get(11).getVictoryPoints());
        assertEquals(6,cells.get(14).getVictoryPoints());
        assertEquals(9,cells.get(17).getVictoryPoints());
        assertEquals(12,cells.get(20).getVictoryPoints());
        assertEquals(16,cells.get(23).getVictoryPoints());
        assertEquals(20,cells.get(24).getVictoryPoints());

        //Cell Type test
        assertEquals(FaithCellType.Pope,cells.get(8).getType());
        assertEquals(FaithCellType.Pope,cells.get(16).getType());
        assertEquals(FaithCellType.Pope,cells.get(24).getType());
        assertEquals(FaithCellType.Not_Report,cells.get(1).getType());
        assertEquals(FaithCellType.Not_Report,cells.get(10).getType());
        assertEquals(FaithCellType.Not_Report,cells.get(18).getType());
        assertEquals(FaithCellType.Report,cells.get(5).getType());
        assertEquals(FaithCellType.Report,cells.get(23).getType());
    }
}