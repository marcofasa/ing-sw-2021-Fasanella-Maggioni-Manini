package it.polimi.ingsw.client.view.CLI;

public class ParsingCommand {
    private Utils utils;
    private CLI cli;
    public ParsingCommand(Utils utils,CLI cli){
        this.utils=utils;
        this.cli=cli;
    }


    public void readCommand(){
        String command =utils.readString();
       switch (command){
          case "help":
              utils.printHelp();
              break;
           case "resource market":
               cli.askMarketChoice();
               break;
           case "card leader":
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
           case "end turn":
               cli.askEndTurn();
               break;
           default: utils.printCommandError();
       }

    }
}
