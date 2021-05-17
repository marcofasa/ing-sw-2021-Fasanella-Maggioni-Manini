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
     * Menu displayed
     */
    public void Menu(){
        haveMove=true;
        printMenu();
        /*if(in.hasNext("") || in.hasNext("\r")) {
            in.nextLine();
        }

         */
        //in.nextLine();
        while(readCommand());
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
    private boolean readCommand(){
        String command = utils.readString();
        switch (command){
            case"":
                break;
            case"\n":
                break;
            case "help":
                utils.printHelp();
                break;
            case "resource market":
                    cli.askMarketChoice(); //1 chance

                break;
            case "activate card leader":
                cli.askCardLeaderActivation();
                break;
            case "card development market":
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
            case "colors":
                cli.colorize(true);
                break;
            case "no colors":
                cli.colorize(false);
            case "end turn":
                cli.askEndTurn();
                return false;
            default: utils.printCommandError();
        }
        return true;
    }

    /**
     * Prints an invalid move message
     */
    private void printInvalidMove() {
        out.println("Invalid move, you have already done a one-chance action!");
    }
}
