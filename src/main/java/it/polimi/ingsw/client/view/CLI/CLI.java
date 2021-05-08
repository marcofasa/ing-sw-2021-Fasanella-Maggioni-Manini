package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.client.*;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements ViewInterface {

    private Client client;
    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final Scanner in = new Scanner(System.in);
    private LightModel lightModel;
    private Utils utils;

    public CLI(Client client){
        this.client=client;
        this.lightModel =new LightModel(client);
        this.utils=new Utils(out,in);

    }


    @Override
    public void displayWelcome() {
       /* ASCII ART version
       utils.printASCIIWelcome();
        */
      out.println("Welcome to Masters of Renaissance!");
    }

    @Override
    public void displayStartingGame() {
        out.println("Game is starting!!!");
    }

    @Override
    public void displayMessage(String message) {
      out.println(message);
    }

    @Override
    public void displayLogin() {

    }

    @Override
    public void displayPosition() {
        utils.printFaithTrailASCII(lightModel.getTileStatuses());
        //utils.printFaithTrailASCII(new RequestTileStatuses());
        utils.printFaithTrail(lightModel.getPlayersPosition(), lightModel.getNickname());


    }

    @Override
    public void displayTimeOut() {
        out.println("Timeout error! Your connection to server may have been lost");
    }

    @Override
    public void displayMarket() {
        utils.printMarket(lightModel.getMarket());
    }

    @Override
    public void displayStrongBox() {

    }

    @Override
    public void displayNotActivePlayerError() {
      out.println("Ops, you are no more active!");
    }

    @Override
    public void displayNotEnoughResource() {
      out.println("There are not enough resources to perform this action.");
    }

    @Override
    public void displayDisconnection() {
        out.println("Oops, connection to server was lost!");
    }

    @Override
    public void displayWin() {
        out.println("You win!");
    }

    @Override
    public void displayLost() {
      out.println("You've lost!");
    }

    @Override
    public void displaySuccess() {
       out.println("Action executed successfully");
    }

    @Override
    public void displayLeaderRequirementsNotMet() {
       out.println("Ops, requirements for this Card Leader are not met! ");
    }

    @Override
    public void askNickName() {
        out.println("NickName:");
        //client.sendNickName(utils.readString());
    }

    @Override
    public void askPlayerNumber() {
        out.println("How many players?");
        utils.readNumberWithBounds(1,4);
        //client.send(utils.readNumberWithBounds(1,4));
    }

    @Override
    public void askResourceToDiscard(HashMap<Resource, Integer> choice) {
        int selection;
        ArrayList<Resource> resources;

             out.println("Choose one resource to discard");
             resources=utils.printListResource(choice);
             selection = utils.readNumber()-1;
             //client.sendResourceSelection(resources.get(selection));
             int currentValue= choice.get(resources.get(selection));
             choice.put(resources.get(selection),currentValue--);
             //Removes Resources with Value=0
             choice.values().removeIf(f -> f == 0);


    }

    @Override
    public void askMarketChoice() {
        int rowcolumn=-1;
        String key;
        String message;
        out.println("Choose row or column (type r/c):");
        rowcolumn=utils.chooseRowOrColumn();
        /*
        rowcolumn=1 -> row
        rowcolumn=0 -> column
         */
        if (rowcolumn==1) key= "row";
        else key="column";
        out.println("Now type the "+key+" number:");
        if(rowcolumn==1) message=utils.readNumberWithBoundsToString(1,3);
        else message=utils.readNumberWithBoundsToString(1,4);
        /*
        1 arg: row=1 or col=0
        2 arg: number of row/column

         */
        try{
            client.sendAndWait( new RequestMarketUse(message,key),-1);
        }
        catch (RequestTimeoutException e){
            displayTimeOut();
            e.printStackTrace();
        }

    }

    @Override
    public void askDevelopmentCardChoice() {
        String s;
        utils.printDevelopmentCardMarket(lightModel.getCardDevelopmentMarket());
        out.println("Type the number in the round brackets of the corresponding card that you want to buy");
        s = utils.readString();
        char[] array = s.toCharArray();
        while (array.length!=2){
            out.println("Invalid input. Type only the the two digits in the round brackets!");
            s = utils.readString();
            array = s.toCharArray();
        }
        Integer rowIndex= Integer.parseInt(String.valueOf(array[0]));
        Integer columnIndex=Integer.parseInt(String.valueOf(array[1]));
        try {
            client.sendAndWait(new RequestBuyDevelopmentCard(rowIndex,columnIndex),-1);
        } catch (RequestTimeoutException e) {
            displayTimeOut();
            e.printStackTrace();
        }
    }

    @Override
    public void askProductionActivation() {
        //TODO
        /*
        client.sendAndWait(new RequestActivateProduction(),-1);
         */
    }

    @Override
    public void askCardLeaderActivation() {
        out.println("Choose with card leader to activate:");
        try {
            client.sendAndWait(new RequestActivateCardLeader(utils.printandgetCardLeaderList(lightModel.getCardsLeader())),-1);
        } catch (RequestTimeoutException e) {
            displayTimeOut();
            e.printStackTrace();
        }
    }

    @Override
    public void askForResourceSelection(ArrayList<Marble> marbles) {
         utils.printMarbleList(marbles);
        //client.sendMarbleSelection(utils.readNumberWithBounds(1,marbles.size())-1);
    }

    @Override
    public ArrayList<Resource> askForInitialResourcesSelection() {
        return null;
    }

    @Override
    public void askEndTurn() {
        out.println("Turn if finished, wait for other players...");
        try {
            client.sendAndWait(new RequestEndTurn(),-1);
        } catch (RequestTimeoutException e) {
            displayTimeOut();
            e.printStackTrace();
        }

    }

    @Override
    public ArrayList<CardLeader> askLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        return null;
    }


}
