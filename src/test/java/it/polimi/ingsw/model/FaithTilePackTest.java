package it.polimi.ingsw.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class FaithTilePackTest {


    @Test
    public void FaithTilePackTest(){
       FaithTilePack pack= new FaithTilePack();

        //Victory Points
        Assertions.assertEquals(0,pack.getVictoryPoints());
        Assertions.assertEquals(FaithTileStatus.Not_Reached,pack.getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,pack.getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,pack.getStatus(FaithSection.Three));

        //One reached and the others Discarded
        pack.setReached(FaithSection.One);
        pack.setDiscarded(FaithSection.Two);
        pack.setDiscarded(FaithSection.Three);
        Assertions.assertEquals(FaithTileStatus.Reached,pack.getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Discarded,pack.getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Discarded,pack.getStatus(FaithSection.Three));

        //Victory Points
        Assertions.assertEquals(2,pack.getVictoryPoints());

}
    }