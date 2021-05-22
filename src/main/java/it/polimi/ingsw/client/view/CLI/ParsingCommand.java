package it.polimi.ingsw.client.view.CLI;
import java.io.PrintWriter;
import java.util.Scanner;

public class ParsingCommand {


    private Utils utils;
    private CLI cli;
    private final PrintWriter out ;
    private final Scanner in;
    private boolean haveMove=false;
    
    /**
     * Constructor of Parsing Command
     * @param utils
     * @param cli
     * @param out
     * @param in
     */
    public ParsingCommand(Utils utils, CLI cli, PrintWriter out, Scanner in){
        this.utils=utils;
        this.cli=cli;
        this.out=out;
        this.in=in;
    }

    /**
     * PlayerMenu displayed
     */
    public void PlayerMenu(){
        haveMove=true;
        printMenu();
        while(readPlayerCommand());
    }

    private void printMenu() {
        out.println("Is your Turn!");
        out.println("Choose a move:");
        out.println("(Type help to read commands)");
    }

    /**
     * Reads a user command during the turn
     * @return
     */
    private boolean readPlayerCommand(){
        String command = utils.readString();
        switch (command){
            case"":
            case"\n":
                break;
            case "help":
                utils.printHelpMenu(true);
                break;
            case "colorize":
                cli.colorize();
                break;
            case "buy resource":
                    cli.askMarketChoice(); //1 chance
                return false;
            case "resource market":
                    cli.displayResourceMarket();
                break;
            case "activate card leader":
                cli.askCardLeaderActivation();
                break;
            case "card development market":
                    cli.displayCardDevelopmentMarket();
                break;
            case "buy card development":
                cli.askDevelopmentCardChoice(); //1 chance
                break;
            case "production":
                    cli.askProductionActivation(); //1 chance
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

            default: utils.printPlayerCommandError();
        }
        return true;
    }

    /**
     * Prints an invalid move message
     */
    private void printInvalidMove() {
        out.println("Invalid move, you have already done a one-chance action!");
    }

    public void WaitingMenu() {
        while(readWaitingCommand());
    }

    private boolean readWaitingCommand() {
        String command = utils.readString();
        switch (command){
            case"":
            case"\n":
                break;
            case "help":
                utils.printHelpMenu(false);
                break;
            case "colorize":
                cli.colorize();
                break;
            case "resource market":
                cli.displayResourceMarket();
                break;
            case "card development market":
                cli.displayCardDevelopmentMarket();
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
            default: utils.printWaitingCommandError();
        }
        return true;
    }
}
