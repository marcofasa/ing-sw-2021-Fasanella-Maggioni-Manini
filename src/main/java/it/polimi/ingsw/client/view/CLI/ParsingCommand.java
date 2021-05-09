package it.polimi.ingsw.client.view.CLI;

public class ParsingCommand {
    private Utils utils;
    private CLI cli;
    public ParsingCommand(Utils utils,CLI cli){
        this.utils=utils;
        this.cli=cli;
    }

    public void readCommand(){
        System.out.println("");
        String command =utils.readString();
       switch (command){
          case "help": utils.printHelp();
           case "market": cli.askMarketChoice();
           case "card leader":
           case "end turn":cli.askEndTurn();
           default: utils.printCommandError();

       }

    }
}
