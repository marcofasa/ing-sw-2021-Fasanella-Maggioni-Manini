package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.client.requests.*;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements ViewInterface {

    // Attributes
    private final Client client;
    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final Scanner in = new Scanner(System.in);
    private final LightModel lightModel;
    private final LightFaithTrail lightFaithTrail;
    private final Utils utils;
    private final ParsingCommand parsingCommand;

    /**
     * Constructor of CLI
     * @param client
     */
    public CLI(Client client){
        this.client=client;
        this.lightModel =new LightModel(client);
        this.lightFaithTrail = new LightFaithTrail(client);
        this.utils=new Utils(out,in);
        this.parsingCommand=new ParsingCommand(utils,this,out,in);
    }

    public LightModel getLightModel() {
        return lightModel;
    }

    @Override
    public LightFaithTrail getLightFaithTrail() {
        return lightFaithTrail;
    }

    @Override
    public void displayWelcome() {
        utils.printWelcomeMessage();
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
        String nickname = lightModel.getNickname();
        utils.printFaithTrail(nickname, lightFaithTrail.getFaithTrail());
    }

    @Override
    public void displayTimeOut() {
        out.println("Timeout error! Your connection to server may have been lost");
    }

    @Override
    public void displayMarket() {
        ArrayList<ArrayList<MarbleType>> marketClone = lightModel.getMarket();

        utils.printMarket(marketClone);
    }

    @Override
    public void displayStrongBox() {
        out.println("---StrongBox---");
        HashMap<Resource, Integer> strongboxClone = lightModel.getStrongbox();

        //TODO Fix with semaphore the second call

        /*

        // If first call ever fails for some reason, grab a strongbox clone again
        if (strongboxClone.size() == 0) {
            strongboxClone = lightModel.getStrongbox();
        }

         */

        utils.printListResource(strongboxClone);
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
        utils.printWinnerMessage();
    }

    @Override
    public void displayLost() {
        utils.printLoserMessage();
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
    public void displayTurn(String currentPlayer) {
        //utils.clearScreen();
        if (currentPlayer.equals(lightModel.getNickname())){
            parsingCommand.Menu();
        }
        else displayWaitingOpponent(currentPlayer);
    }

    @Override
    public void displayWaitingOpponent(String currentPlayer) {
        out.println("Wait for your move, "+currentPlayer+" is now playing...");
    }

    @Override
    public void displayDeposit() {
        out.println("---Deposit---");
        HashMap<Resource, Integer> cloneDeposit = lightModel.getDeposit();

        /*
        // If first call ever fails, grab deposit again
        if (cloneDeposit.size() == 0) {
            cloneDeposit = lightModel.getDeposit();
        }
         */

        utils.printListResource(cloneDeposit);

    }

    @Override
    public void displayCardLeader() {
        out.println("---Card Leader---");
        utils.printCardLeaderDeck(lightModel.getCardsLeader());
    }

    @Override
    public void displayCardDevelopment() {
        out.println("---Card Development---");
        utils.printCardDevelopmentDeck(lightModel.getCardsDevelopment());
    }

    @Override
    public String askNickName() {
        //utils.setColoredCLI();
        //Welcome Message
        displayWelcome();

        //Reads the nickname
        String input;
        out.println("NickName:");
        input = utils.readString();
        lightModel.setNickname(input);

        return input;
    }

    @Override
    public int askPlayerNumber() {
        out.println("How many players?");
        return utils.readNumberWithBounds(1,4);
    }

    /**
     * Let player select which resources to discard
     * @param choice HashMap of available resources
     * @return HashMap of selected resources
     */
    @Override
    public HashMap<Resource, Integer> askForResourceToDiscard(HashMap<Resource, Integer> choice) {
        HashMap<Resource, Integer> temp = new HashMap<>(choice);
        HashMap<Resource, Integer> selection = new HashMap<>();
        boolean firstCall= true;
        boolean loop=false;

        //Read resources
        out.println("Ops, you must discard at least one resource:");
        utils.printListResource(choice);
        out.println("Here's a list of available resources to discard:");
            do {
                Resource resource = utils.readResource(loop);
                if (temp.get(resource)>0) {
                    int i = temp.get(resource);
                    if(!selection.containsKey(resource)){
                        selection.put(resource,1);
                    }
                    else {
                        int r=selection.get(resource);
                        selection.replace(resource, r + 1);
                    }
                    temp.replace(resource,i-1);
                    out.println("Discard another resource?");
                    loop=utils.readYesOrNo(false);
                }
                else{
                    out.println("You can't discard this resource, try with another.");
                    out.println("Here's a list of available resources to discard:");
                    utils.printListResource(temp);
                }
            } while (loop);

        return selection;
    }



    @Override
    public void displayWaiting(int timeoutInSeconds) {
        try {
            utils.printWaitingMessage(timeoutInSeconds);
        } catch (InterruptedException | IOException e) {
            utils.printErrorMessage();
            e.printStackTrace();
        }
    }

    @Override
    public void askCardLeaderDiscard() {
        out.println("Choose a card leader to discard:");
        try{
            client.sendAndWait(new RequestDiscardCardLeader(utils.printAndGetCardLeader(lightModel.getCardsLeader())),-1);
        }
        catch (RequestTimeoutException e){
            e.printStackTrace();
        }
    }


    @Override
    public void displayCardDevelopmentMarket() {
        utils.printDevelopmentCardMarket(lightModel.getCardDevelopmentMarket());
    }

    @Override
    public void displayStartingEndGame(String NickName) {
        out.println(NickName + " has activated endgame!");
    }

    @Override
    public void displayScoreBoard(HashMap<String, Integer> showScoreBoard) {
       int maxPoints=utils.checkWinner(showScoreBoard);
       String nickName=lightModel.getNickname();
       if(showScoreBoard.get(nickName)==maxPoints){
           displayWin();
       }
       else{
           displayLost();
       }
       utils.printScoreBoard(showScoreBoard,nickName);
    }


    @Override
    public void askMarketChoice() {
        displayMarket();
        int rowcolumn;
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
        try {
            client.sendAndWait(new RequestMarketUse(message,key), -1);
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void askDevelopmentCardChoice() {
        String s;
        utils.printDevelopmentCardMarket(lightModel.getCardDevelopmentMarket());

        //Reads the card to buy
        out.println("Type the number in the round brackets of the corresponding card that you want to buy");
        s = utils.readString();
        char[] array = s.toCharArray();
        while (array.length!=2 || (!Character.isDigit(s.charAt(0)) || !Character.isDigit(s.charAt(1))) || (0>Integer.parseInt(String.valueOf(array[0])) || Integer.parseInt(String.valueOf(array[0]))>2) || (0>Integer.parseInt(String.valueOf(array[1])) || Integer.parseInt(String.valueOf(array[1]))>3)){
            out.println("Invalid input. Type only the the two digits in the round brackets!");
            s = utils.readString();
            array = s.toCharArray();
        }
        Integer rowIndex= Integer.parseInt(String.valueOf(array[0]));
        Integer columnIndex=Integer.parseInt(String.valueOf(array[1]));

        out.println("In which slot would you like to place your new card? Possible selection are {0 ,1 ,2}");
        s = utils.readNumberWithBoundsToString(0, 2);

        Integer placementIndex = Integer.parseInt(s);

        try {
            client.sendAndWait(new RequestBuyDevelopmentCard(rowIndex,columnIndex,placementIndex),-1);
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
        productionSelection.setBasicProduction(utils.readYesOrNo(false));
        if (productionSelection.getBasicProduction()){
            productionSelection.setBasicProdInfo(utils.getBasicProduction());
        }

        //Ask for Card Development production
        out.println("Do you want to activate Card Development production?");
        if (utils.readYesOrNo(false)) {
            productionSelection.setCardDevelopmentSlotActive(utils.getCardDevelopmentActivation(lightModel.getCardsDevelopment()));
        }
        else {
            Boolean[] falseArray =new Boolean[3];
            falseArray[0]=false;
            falseArray[1]=false;
            falseArray[2]=false;
            productionSelection.setCardDevelopmentSlotActive(falseArray);
        }


        //Ask for Card Leader production and output
        out.println("Do you want to activate Card Leader production?");
        if (utils.readYesOrNo(false)) {
            CardLeader[] cardLeaders;
            cardLeaders=utils.getCardLeaderActivation(lightModel.getCardsLeader());
            productionSelection.setCardLeadersToActivate(cardLeaders);
            Resource[] resourcesOutput;
            resourcesOutput=utils.getCardLeaderOutputs(productionSelection.getCardLeadersToActivate());
            productionSelection.setCardLeaderProdOutputs(resourcesOutput);
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

        //Sending request to Server
        try {
            client.sendAndWait(new RequestActivateProduction(productionSelection),-1);
        } catch (RequestTimeoutException e) {
            displayTimeOut();
            e.printStackTrace();
        }
    }



    @Override
    public void askCardLeaderActivation() {
        out.println("Choose which card leader to activate:");
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
            case 0:
                out.println("You have no resources to choose because you're the first player!");
                out.println();
                resources.add(null);
                resources.add(null);
                break;
            case 1: case 2:
                out.println("Choose one resource : ");
                resources.add(utils.readResource(true));
                resources.add(null);
                break;
            case 3:
                out.println("Choose two resources : ");
                resources.add(utils.readResource(true));
                resources.add(utils.readResource(false));
                break;
        }

        return resources;
    }


    /**
     * Ask to end turn
     */
    @Override
    public void askEndTurn() {
        out.println("Turn is finished, wait for other players...");
        client.send(new RequestEndTurn());
    }


    /**
     * Ask to select two cards leader at the beginning of the game
     * @param cardLeaders deck of card
     * @return
     */
    @Override
    public ArrayList<CardLeader> askForLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        return utils.printAndGetCardLeaderFirstSelection(cardLeaders);
    }


    public void colorize() {
        utils.colorize();
    }


}
