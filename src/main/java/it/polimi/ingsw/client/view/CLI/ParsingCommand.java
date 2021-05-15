package it.polimi.ingsw.client.view.CLI;

import java.io.PrintWriter;

public class ParsingCommand {
    private Utils utils;
    private CLI cli;
    private final PrintWriter out ;


    public ParsingCommand(Utils utils, CLI cli, PrintWriter out){
        this.utils=utils;
        this.cli=cli;
        this.out=out;
    }

    public void Menu(){
        printMenu();
        while(readCommand());
    }

    private void printMenu() {
       out.println("Is your Turn!");
       out.println("Choose a move:");
       out.println("(Type help to read commands)");
    }


    private boolean readCommand(){
        String command =utils.readString();
       switch (command){
          case "help":
              utils.printHelp();
              break;
           case "resource market":
               cli.askMarketChoice();
               break;
           case "activate card leader":
               cli.askCardLeaderActivation();
               break;
           case "card development market":
               cli.askDevelopmentCardChoice();
               break;
           case "production":
               cli.askProductionActivation();
               break;
           case "discard card leader":
               cli.askCardLeaderDiscard();
               break;
           case "faith trail":
               cli.displayPosition();
               break;
           case "deposit":
               cli.displayDeposit();
               break;
           case "strongbox":
               cli.displayStrongBox();
               break;
           case "card leader":
               cli.displayCardLeader();
               break;
           case "card development":
               cli.displayCardDevelopment();
               break;
           case "end turn":
               cli.askEndTurn();
               return false;
           default: utils.printCommandError();
       }
      return true;
    }
}
