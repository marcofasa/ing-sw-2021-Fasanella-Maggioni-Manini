package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.model.CardDevelopment;
import it.polimi.ingsw.model.FaithTileStatus;
import it.polimi.ingsw.model.MarbleType;
import it.polimi.ingsw.model.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UtilsTest {


    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final Scanner in = new Scanner(System.in);
    private static final Utils utils=new Utils(out,in);

    @Test
    public void MarketTest() {
        ArrayList<ArrayList<MarbleType>> market = new ArrayList<ArrayList<MarbleType>>();

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


    }

    @Test
    public void ListTest(){
        HashMap<Resource, Integer> map=new HashMap<>();
        map.put(Resource.Coins,2);
        map.put(Resource.Shields,4);
        map.put(Resource.Stones,1);
        map.put(Resource.Servants,3);
        utils.printListResource(map);
    }

    @Test
    public void WaitingTest() throws IOException, InterruptedException {
     utils.printWaitingMessage(10);
    }

    @Test
    public void CardDevelopmentTest(){

    }


    @Test
    public void MessageTests(){
        utils.printErrorMessage();
        utils.printHelp();
        utils.printWelcomeMessage();
    }


    @Test
    public void printWaitingMessage() throws IOException, InterruptedException {
        utils.printWaitingMessage(10);
    }
}