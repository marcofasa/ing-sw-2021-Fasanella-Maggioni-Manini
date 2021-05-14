package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.model.FaithTileStatus;
import it.polimi.ingsw.model.MarbleType;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UtilsTest {


    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final Scanner in = new Scanner(System.in);

    @Test
    public void MarketTest() {
        ArrayList<ArrayList<MarbleType>> market = new ArrayList<ArrayList<MarbleType>>();
        Utils utils=new Utils(out,in);

        ArrayList<MarbleType> row0 = new ArrayList<>();
        row0.add(MarbleType.MarbleWhite);
        row0.add(MarbleType.MarbleBlue);
        row0.add(MarbleType.MarbleRed);
        row0.add(MarbleType.MarblePurple);

        ArrayList<MarbleType> row1 = new ArrayList<>();
        row1.add(MarbleType.MarbleRed);
        row1.add(MarbleType.MarbleRed);
        row1.add(MarbleType.MarblePurple);
        row1.add(MarbleType.MarbleBlue);

        ArrayList<MarbleType> row2 = new ArrayList<>();
        row2.add(MarbleType.MarbleBlue);
        row2.add(MarbleType.MarbleWhite);
        row2.add(MarbleType.MarblePurple);
        row2.add(MarbleType.MarbleBlue);

        market.add(row0);
        market.add(row1);
        market.add(row2);
        utils.printMarket(market);
    }


    @Test
    public void FaithTrailTest(){
        String nickname="Marco";

        HashMap<String, Integer> playersPosition=new HashMap<>();
        playersPosition.put("Marco",10);
        playersPosition.put("Lucas", 14);
        playersPosition.put("Elia",12);
        playersPosition.put("Mister X", 22);

        ArrayList<FaithTileStatus> tileStatuses=new ArrayList<>();
        tileStatuses.add(FaithTileStatus.Reached);
        tileStatuses.add(FaithTileStatus.Discarded);
        tileStatuses.add(FaithTileStatus.Not_Reached);

        Utils utils=new Utils(out,in);

        utils.printFaithTrail(playersPosition,nickname,tileStatuses);

    }

}