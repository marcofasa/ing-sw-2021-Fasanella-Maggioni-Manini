package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.client.*;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.ProductionSelection;
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
    private ParsingCommand parsingCommand;

    public CLI(Client client){
        this.client=client;
        this.lightModel =new LightModel(client);
        this.utils=new Utils(out,in);
        this.parsingCommand=new ParsingCommand(utils,this);
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
    public void displayPosition() {
        utils.printFaithTrailASCII(lightModel.getTileStatuses());
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
    public String askNickName() {
        out.println("NickName:");
        return utils.readString();
    }

    @Override
    public int askPlayerNumber() {
        out.println("How many players?");
        return utils.readNumberWithBounds(1,4);
    }

    @Override
    public HashMap<Resource, Integer> askForResourceToDiscard(HashMap<Resource, Integer> choice) {
        HashMap<Resource, Integer> selection = new HashMap<>();

        utils.printListResource(choice);
        out.println("Do you want to discard a resource?");
        if (utils.readYesOrNo()) {
            do {
                Resource resource = utils.readResource();
                if (selection.containsKey(resource)) {
                    int i = selection.get(resource);
                    selection.replace(resource, i + 1);
                } else {
                    selection.put(resource, 1);
                }
                out.println("Discard another resource?");
            } while (utils.readYesOrNo());
        }
   return selection;
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
        ProductionSelection productionSelection= new ProductionSelection();

        //Ask for basic production
        out.println("Do you want to activate basic production?");
        productionSelection.setBasicProduction(utils.readYesOrNo());
        if (productionSelection.getBasicProduction()){
            productionSelection.setBasicProdInfo(utils.getBasicProduction());
        }

        //Ask for Card Development production
        out.println("Do you want to activate Card Development production?");
        if (utils.readYesOrNo()) productionSelection.setCardDevelopmentSlotActive(utils.getCardDevelopmentActivation(lightModel.getCardDevelopment()));
        else {
            Boolean[] falseArray =new Boolean[3];
            falseArray[0]=false;
            falseArray[1]=false;
            falseArray[2]=false;
            productionSelection.setCardDevelopmentSlotActive(falseArray);
        }


        //Ask for Card Leader production
        out.println("Do you want to activate Card Leader production?");
        if (utils.readYesOrNo()) {
            CardLeader[] cardLeaders;
            cardLeaders=utils.getCardLeaderActivation(lightModel.getCardsLeader());
            productionSelection.setCardLeadersToActivate(cardLeaders);
        }
        else {
            CardLeader[] cardLeaders=new CardLeader[2];
            cardLeaders[0]=null;
            cardLeaders[1]=null;
            productionSelection.setCardLeadersToActivate(cardLeaders);
            Resource[] cardLeaderProdOutputs = new Resource[2];
            cardLeaderProdOutputs[0]=null;
            cardLeaderProdOutputs[1]=null;
            productionSelection.setCardLeaderProdOutputs(cardLeaderProdOutputs);
        }

        //Sending request
        try {
            client.sendAndWait(new RequestActivateProduction(productionSelection),-1);
        } catch (RequestTimeoutException e) {
            displayTimeOut();
            e.printStackTrace();
        }


    }

    @Override
    public void askCardLeaderActivation() {
        out.println("Choose with card leader to activate:");
        try {
            client.sendAndWait(new RequestActivateCardLeader(utils.printAndGetCardLeader(lightModel.getCardsLeader())),-1);
        } catch (RequestTimeoutException e) {
            displayTimeOut();
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<Resource> askForInitialResourcesSelection(int playerNumber) {
        ArrayList<Resource> resources =new ArrayList<>();
        switch (playerNumber){
            case 1:
                resources.add(null);
                resources.add(null);
                break;
            case 2: case 3:
                resources.add(utils.readResource());
                resources.add(null);
                break;
            case 4:
                resources.add(utils.readResource());
                resources.add(utils.readResource());
                break;
        }

        return resources;
    }

    @Override
    public void askEndTurn() {
        out.println("Turn is finished, wait for other players...");
        try {
            client.sendAndWait(new RequestEndTurn(),-1);
        } catch (RequestTimeoutException e) {
            displayTimeOut();
            e.printStackTrace();
        }

    }

    @Override
    public HashMap<Resource,Integer> askForResourceSelection(HashMap<Resource, Integer> resources) {
        //USE askForResourceToDiscard()
        return null;
    }

    @Override
    public ArrayList<CardLeader> askForLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        return utils.printAndGetCardLeaderList(cardLeaders);
    }


}
