package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FaithTilePackTest {


    @Test
    public void FaithTilePackTest(){
       FaithTilePack pack= new FaithTilePack();

        //Victory Points
        assertEquals(0,pack.getVictoryPoints());
        assertEquals(FaithTileStatus.Not_Reached,pack.getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Not_Reached,pack.getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Not_Reached,pack.getStatus(FaithSection.Three));

        //One reached and the others Discarded
        pack.setReached(FaithSection.One);
        pack.setDiscarded(FaithSection.Two);
        pack.setDiscarded(FaithSection.Three);
        assertEquals(FaithTileStatus.Reached,pack.getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Discarded,pack.getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Discarded,pack.getStatus(FaithSection.Three));

        //Victory Points
        assertEquals(2,pack.getVictoryPoints());

}
    }