package it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.model.*;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UtilsTest {


    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final Scanner in = new Scanner(System.in);
    private static final Utils utils=new Utils(out,in);

    @Test
    public void WelcomeTest(){
        utils.printANSIWelcome();
    }

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
        utils.printMarket(market,MarbleType.MarbleBlue);
    }


    @Test
    public void FaithTrailTest(){
        Client client=new Client(true);
        String nickname="Marco";

        HashMap<String, Integer> playersPosition=new HashMap<>();
        playersPosition.put("Marco",10);
        playersPosition.put("Lucas", 14);
        playersPosition.put("Elia",12);
        playersPosition.put("Mister X", 22);

        ArrayList<FaithTileStatus> tileStatuses=new ArrayList<>();
        tileStatuses.add(FaithTileStatus.Reached);
        tileStatuses.add(FaithTileStatus.Not_Reached);
        tileStatuses.add(FaithTileStatus.Discarded);
        LightFaithTrail lightFaithTrail=new LightFaithTrail(client);
        lightFaithTrail.setFaithTrail(playersPosition,tileStatuses);

        utils.printFaithTrail("Marco",lightFaithTrail);
       utils.clearScreen();

        utils.printFaithTrail("Elia",lightFaithTrail);

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
    public void CardDevelopmentTest(){

    }


    @Test
    public void MessageTests(){
        utils.printErrorMessage();
        utils.printHelpMenu(true);
        utils.printWelcomeMessage();
    }




    @Test
    public void printScoreBoard() {
        String nickname="Marco";

        HashMap<String, Integer> showScoreBoard =new HashMap<>();
        showScoreBoard.put("Marco",2);
        showScoreBoard.put("Lucas", 14);
        showScoreBoard.put("Elia",12);
        showScoreBoard.put("Mister X", 22);
        int maxPoints=utils.checkWinner(showScoreBoard);
        if(showScoreBoard.get(nickname)==maxPoints){
            utils.printWinnerMessage();
        }
        else{
            utils.printLoserMessage();
        }
        utils.printScoreBoard(showScoreBoard,nickname);
    }
}