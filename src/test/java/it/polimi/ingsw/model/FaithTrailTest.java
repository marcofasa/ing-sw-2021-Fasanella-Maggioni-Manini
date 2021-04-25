package it.polimi.ingsw.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Arrays;

public class FaithTrailTest {
    @Test
    public void FaithTrailLorenzo(){
        //Single Player Test
        GameTable gameTable = new GameTable(new ArrayList<String>(Arrays.asList("Single Player")));
        FaithTrail f= gameTable.getFaithTrailInstance();
        ArrayList<PlayerBoard> p = gameTable.getPlayerBoards();
        Assertions.assertEquals(1,p.size());

        //Lorenzo Position test
        Assertions.assertEquals(0,f.getLorenzoPosition());
        f.moveLorenzo();
        Assertions.assertEquals(1,f.getLorenzoPosition());
        //Move to the first Pope Cell
        for(int i=0;i<7;i++) {
        f.moveLorenzo();
        }
        Assertions.assertEquals(8,f.getLorenzoPosition());
        Assertions.assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
    }

    @Test
    public void FaithTrail(){
        //Single Player Test
        GameTable gameTable = new GameTable(new ArrayList<String>(Arrays.asList("P1","P2","P3")));
        FaithTrail f= gameTable.getFaithTrailInstance();
        ArrayList<PlayerBoard> p = gameTable.getPlayerBoards();
        Assertions.assertEquals(0,f.getPosition(p.get(0)));
        Assertions.assertEquals(0,f.getPosition(p.get(1)));
        Assertions.assertEquals(0,f.getPosition(p.get(2)));


        //P1 moves 1 step
        f.movePlayer(p.get(0),1);
        Assertions.assertEquals(1,f.getPosition(p.get(0)));
        Assertions.assertEquals(0,f.getPosition(p.get(1)));
        Assertions.assertEquals(0,f.getPosition(p.get(2)));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.Three));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.Three));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Three));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.One));

        //P2 moves 7 steps but DOESN'T reach Pope cell
        f.movePlayer(p.get(1),7);

        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Not_Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.One));

        //Pope Cell Reached by P1
        f.movePlayer(p.get(0),7);
        Assertions.assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(0)).getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(1)).getStatus(FaithSection.One));
        Assertions.assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(2)).getStatus(FaithSection.One));

        //P3 arrives at last Cell TODO ActivateEndgame
        f.movePlayer(p.get(2),24);
        Assertions.assertEquals(true,f.checkEndGame(f.getPosition(p.get(2))));
        Assertions.assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(0)).getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(1)).getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Two));
        Assertions.assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(0)).getStatus(FaithSection.Three));
        Assertions.assertEquals(FaithTileStatus.Discarded,f.getTilePack(p.get(1)).getStatus(FaithSection.Three));
        Assertions.assertEquals(FaithTileStatus.Reached,f.getTilePack(p.get(2)).getStatus(FaithSection.Three));

        //Getting VictoryPoints
        Assertions.assertEquals(4,f.getVictoryPoints(p.get(0))); //2+(2)
        Assertions.assertEquals(4,f.getVictoryPoints(p.get(1))); //2+(2)
        Assertions.assertEquals(27,f.getVictoryPoints(p.get(2))); //20+(3+4)
    }



}