package it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.communication.server.requests.GamePhase;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class ParsingCommand {


    private final Utils utils;
    private final CLI cli;
    private final PrintWriter out ;
    private final Scanner in;
    private boolean haveMove=false;
    private boolean waitingMenu;
    private boolean reading = false;

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

    public void setWaitingMenu(Boolean waitingMenu){
        this.waitingMenu = waitingMenu;
    }

    /**
     * PlayerMenu displayed
     */
    public void PlayerMenu(GamePhase gamePhase){
        if (gamePhase == GamePhase.Ended)
            waitingMenu = true;
        if (reading)
            return;
        reading = true;
        Boolean returnValue = true;
        while(returnValue) {
            String command = utils.readString();
            haveMove = true;
            printMenu();
            if (gamePhase != GamePhase.Ended && !waitingMenu) {
                returnValue = readPlayerCommand(gamePhase, command);
            } else {
                returnValue = readWaitingCommand(command);
            }
        }
        reading = false;Z
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
    private boolean readPlayerCommand(GamePhase gamePhase, String command){
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
                    if(gamePhase != GamePhase.Final)
                        cli.askMarketChoice(); //1 chance
                    else {
                        printInvalidMove();
                        break;
                    }
                return false;
            case "resource market":
                if(gamePhase != GamePhase.Final)
                    cli.displayResourceMarket();
                else
                    printInvalidMove();
                break;
            case "activate card leader":
                cli.askCardLeaderActivation();
                break;
            case "card development market":
                    cli.displayCardDevelopmentMarket();
                break;
            case "buy card development":
                if(gamePhase != GamePhase.Final)
                    cli.askDevelopmentCardChoice(); //1 chance
                else
                    printInvalidMove();
                break;
            case "production":
                if(gamePhase != GamePhase.Final)
                    cli.askProductionActivation(); //1 chance
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
            case "end turn":
                if(gamePhase == GamePhase.Final) {
                    cli.askEndTurn();
                    return false;
                }
                else {
                    printInvalidEndTurn();
                    break;
                }
            default: utils.printPlayerCommandError();
        }
        return true;
    }

    private void printInvalidEndTurn() {
        out.println("Invalid move, you need to make a move in order to end your turn!");
    }

    /**
     * Prints an invalid move message
     */
    private void printInvalidMove() {
        out.println("Invalid move, you have already done a one-chance action!");
        out.println("You can make a secondary action or write 'end turn' to pass");
    }

    private boolean readWaitingCommand(String command) {
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
