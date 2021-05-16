package it.polimi.ingsw.client.view.CLI;
import java.io.PrintWriter;
import java.util.Scanner;

public class ParsingCommand {


    private Utils utils;
    private CLI cli;
    private final PrintWriter out ;
    private final Scanner in;
    private boolean haveMove=true;

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
        in.nextLine();
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
            case "help":
                utils.printHelp();
                break;
            case "resource market":
                if (haveMove) {
                    cli.askMarketChoice();
                    haveMove=false;
                }
                else printInvalidMove();
                break;
            case "activate card leader":
                cli.askCardLeaderActivation();
                break;
            case "card development market":
                if(haveMove) {
                    cli.askDevelopmentCardChoice();
                    haveMove=false;
                }
                else printInvalidMove();
                break;
            case "production":
                if(haveMove) {
                    cli.askProductionActivation();
                    haveMove=false;
                }
                else printInvalidMove();
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

    private void printInvalidMove() {
        out.println("Invalid Move, you have already done a one-chance action!");
    }
}
