package it.polimi.ingsw.model;

import it.polimi.ingsw.model.marbles.Marble;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MarketTest {

    @Test
    public void getCol() {
        GameTable gameTable = new GameTable(new ArrayList<>(Arrays.asList("1", "2", "3")));
        Market market = new Market(gameTable);
        var marketclone = market.getMarket();
        var spareMarbleClone = market.getSpareMarble();
        int i=0;
        var colonna = market.getCol(1);
        for (int j = 0; j < 3; j++) {
            assertEquals(marketclone.get(j).get(0).getClass(), colonna.get(j).getClass());
        }
        assertEquals(market.getSpareMarble().getClass(), marketclone.get(2).get(0).getClass());
    }

    @Test
    public void getRow() {
        GameTable gameTable = new GameTable(new ArrayList<>(Arrays.asList("1", "2", "3")));
        Market market = new Market(gameTable);
        for (int i = 0; i < 6; i++) {
            var marketclone = market.getMarket();
            var spareMarbleClone = market.getSpareMarble();
            getRowTester(market, marketclone, spareMarbleClone);
        }
    }

    private void getRowTester(Market market, ArrayList<ArrayList<Marble>> marketclone, Marble sparemarble) {
        var riga = market.getRow(1);
        for (int j = 0; j < 4; j++) {
            assertEquals(marketclone.get(0).get(j).getClass(), riga.get(j).getClass());
        }
        assertEquals(market.getSpareMarble().getClass(), marketclone.get(0).get(3).getClass());
    }
}