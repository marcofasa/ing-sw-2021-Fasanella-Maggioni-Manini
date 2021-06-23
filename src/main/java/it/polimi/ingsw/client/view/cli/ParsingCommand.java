package it.polimi.ingsw.client.view.cli;
import it.polimi.ingsw.communication.server.requests.GamePhase;

import java.io.PrintWriter;
import java.util.Scanner;

public class ParsingCommand {


    private final Boolean debug;
    private final Utils utils;
    private final CLI cli;
    private final PrintWriter out ;
    private final Scanner in;
    private boolean haveMove=false;
    
    /**
     * Constructor of Parsing Command
     * @param utils
     * @param cli
     * @param out
     * @param in
     * @param debug
     */
    public ParsingCommand(Utils utils, CLI cli, PrintWriter out, Scanner in, Boolean debug){
        this.utils=utils;
        this.cli=cli;
        this.out=out;
        this.in=in;
        this.debug = debug;
    }

    /**
     * PlayerMenu displayed
     */
    public void PlayerMenu(GamePhase gamePhase){
        haveMove=true;
        printMenu();
        while(readPlayerCommand(gamePhase));
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
    private boolean readPlayerCommand(GamePhase gamePhase){
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
                if(gamePhase != GamePhase.Final || debug) {
                    cli.askMarketChoice(); //1 chance
                    return false;
                }
                else {
                    printInvalidMove();
                }
                break;
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
                if(gamePhase != GamePhase.Final || debug) {
                    cli.askDevelopmentCardChoice(); //1 chance
                    return false;
                }
                else
                    printInvalidMove();
                break;
            case "production":
                if(gamePhase != GamePhase.Final || debug) {
                    cli.askProductionActivation(); //1 chance
                    return false;
                }
                else
                    printInvalidMove();
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
            case "checkout player":
                cli.checkoutPlayer();
                break;
            case "end turn":
                if(gamePhase == GamePhase.Final) {
                    cli.askEndTurn();
                    return false;
                }
                else {
                    printInvalidMovePass();
                    break;
                }
            default: utils.printPlayerCommandError();
        }
        return true;
    }

    private void printInvalidMovePass() {
        out.println("Invalid move, you need to make a primary action before passing");
    }

    /**
     * Prints an invalid move message
     */
    private void printInvalidMove() {
        out.println("Invalid move, you have already done a one-chance action!");
        out.println("You can make a secondary action or write 'end turn' to pass");
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
