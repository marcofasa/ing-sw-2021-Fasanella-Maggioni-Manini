package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.model.Resource;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CLI implements ViewInterface {

    private Client client;
    private int numberOfPlayers=0;
    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final Scanner in = new Scanner(System.in);
    private LightModel model;
    private Utils utils;

    public CLI(Client client){
        this.client=client;
        this.model=new LightModel();
        this.utils=new Utils(out,in);
    }


    @Override
    public void displayWelcome() {
       /* ASCII ART version
       utils.printArtWelcome();
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

    }

    @Override
    public void displayMarket() {
        // client.send(new GetMarketFromServer());
        /* TODO WAIT FOR SERVER */
    }

    @Override
    public void displayStrongBox() {

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
    public void askNickName() {
        out.println("NickName:");
        //client.send(new ClientMessageNickName(utils.readString(););
    }

    @Override
    public void askPlayerNumber() {
        out.println("How many players?");
        int people= utils.readNumberWithBounds(2,4);
        //client.send(new ClientMessageNumberOfPlayers(people);
    }

    @Override
    public void askResourceToDiscard(HashMap<Resource, Integer> choice, int leftResource) {
        int selection;
        ArrayList<Resource> resources;
         while(leftResource>0) {
             out.println("Choose one resource to discard");
             resources=utils.printListResource(choice);
             selection = utils.readNumber()-1;
             //client.sendResourceSelection(resources.get(selection));
             int currentValue= choice.get(resources.get(selection));
             choice.put(resources.get(selection),currentValue--);
             //Removes Resources with Value=0
             choice.values().removeIf(f -> f == 0);
             leftResource--;
         }


    }

    @Override
    public void askMarketChoice() {
        int rowcolumn=-1;
        int number=-1;
        String s;
        out.println("Choose row or column (type r/c):");
        rowcolumn=utils.chooseRowOrColumn();
        /*
        rowcolumn=1 -> row
        rowcolumn=0 -> column
         */
        if (rowcolumn==1) s= "row";
        else s="column";
        out.println("Now type the "+s+" number:");
        if(rowcolumn==1) number=utils.readNumberWithBounds(1,3);
        else number=utils.readNumberWithBounds(1,4);
        /*
        1 arg: row=1 or col=0
        2 arg: number of row/column
        client.sendMarketChoice(rowcolumn,number);
         */
    }


}
