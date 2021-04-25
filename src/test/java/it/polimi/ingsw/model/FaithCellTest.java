package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

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
        Assertions.assertEquals(0,cells.get(2).getVictoryPoints());
        Assertions.assertEquals(1,cells.get(4).getVictoryPoints());
        Assertions.assertEquals(2,cells.get(7).getVictoryPoints());
        Assertions.assertEquals(4,cells.get(9).getVictoryPoints());
        Assertions.assertEquals(4,cells.get(11).getVictoryPoints());
        Assertions.assertEquals(6,cells.get(14).getVictoryPoints());
        Assertions.assertEquals(9,cells.get(17).getVictoryPoints());
        Assertions.assertEquals(12,cells.get(20).getVictoryPoints());
        Assertions.assertEquals(16,cells.get(23).getVictoryPoints());
        Assertions.assertEquals(20,cells.get(24).getVictoryPoints());

        //Cell Type test
        Assertions.assertEquals(FaithCellType.Pope,cells.get(8).getType());
        Assertions.assertEquals(FaithCellType.Pope,cells.get(16).getType());
        Assertions.assertEquals(FaithCellType.Pope,cells.get(24).getType());
        Assertions.assertEquals(FaithCellType.Not_Report,cells.get(1).getType());
        Assertions.assertEquals(FaithCellType.Not_Report,cells.get(10).getType());
        Assertions.assertEquals(FaithCellType.Not_Report,cells.get(18).getType());
        Assertions.assertEquals(FaithCellType.Report,cells.get(5).getType());
        Assertions.assertEquals(FaithCellType.Report,cells.get(23).getType());
    }
}