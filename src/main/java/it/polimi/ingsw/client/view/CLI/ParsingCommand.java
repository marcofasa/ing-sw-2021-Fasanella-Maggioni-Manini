package it.polimi.ingsw.client.view.CLI;

import java.util.Scanner;

public class ParsingCommand {
    private Scanner in;
    public ParsingCommand(Scanner in){
        this.in=in;
    }

    public void readCommand(){
        String command =in.nextLine();
       switch (command){
           //TODO
          // case "help":
       }

    }
}
