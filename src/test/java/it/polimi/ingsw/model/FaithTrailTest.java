package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FaithTrailTest {
    @Test
    public void FaithTrailLorenzo(){
        //Single Player Test
        GameTable gameTable = new GameTable(new ArrayList<>(Collections.singletonList("Single Player")));
        FaithTrail f= gameTable.getFaithTrailInstance();
        ArrayList<PlayerBoard> p = gameTable.getPlayerBoards();
        assertEquals(1,p.size());

        //Lorenzo Position test
        assertEquals(0,f.getLorenzoPosition());
        f.moveLorenzo();
        assertEquals(1,f.getLorenzoPosition());
        //Move to the first Pope Cell
        for(int i=0;i<7;i++) {
        f.moveLorenzo();
        }
        assertEquals(8,f.getLorenzoPosition());
        assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
    }

    @Test
    public void FaithTrail(){
        //Single Player Test
        GameTable gameTable = new GameTable(new ArrayList<>(Arrays.asList("P1", "P2", "P3")));
        FaithTrail f= gameTable.getFaithTrailInstance();
        ArrayList<PlayerBoard> p = gameTable.getPlayerBoards();
        assertEquals(0,f.getPosition(p.get(0)));
        assertEquals(0,f.getPosition(p.get(1)));
        assertEquals(0,f.getPosition(p.get(2)));


        //P1 moves 1 step
        f.movePlayer(p.get(0),1);
        assertEquals(1,f.getPosition(p.get(0)));
        assertEquals(0,f.getPosition(p.get(1)));
        assertEquals(0,f.getPosition(p.get(2)));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.Three));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.Three));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Three));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.One));

        //P2 moves 7 steps but DOESN'T reach Pope cell
        f.movePlayer(p.get(1),7);

        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.One));

        //Pope Cell Reached by P1
        f.movePlayer(p.get(0),7);
        assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.One));
        assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(2)).getStatus(FaithSection.One));

        //P3 arrives at last Cell TODO ActivateEndgame
        f.movePlayer(p.get(2),24);
        assertTrue(f.checkEndGame(f.getPosition(p.get(2))));
        assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(0)).getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(1)).getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Two));
        assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(0)).getStatus(FaithSection.Three));
        assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(1)).getStatus(FaithSection.Three));
        assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Three));

        //Getting VictoryPoints
        assertEquals(4,f.getVictoryPoints(p.get(0))); //2+(2)
        assertEquals(4,f.getVictoryPoints(p.get(1))); //2+(2)
        assertEquals(27,f.getVictoryPoints(p.get(2))); //20+(3+4)
    }



}