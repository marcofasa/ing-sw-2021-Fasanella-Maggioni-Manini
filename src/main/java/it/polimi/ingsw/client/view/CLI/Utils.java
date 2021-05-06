package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.model.MarbleType;
import it.polimi.ingsw.model.Resource;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Utils {
    private PrintWriter out;
    private Scanner in;

    //ANSI escape codes for Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GRAY = "\u001b[48;5;240m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //BackGround Colors
    public static final String ANSI_BACKGROUND_GRAY = "\u001b[48;5;240m";
    public static final String ANSI_BACKGROUND_BLACK = "\u001b[40;1m";
    public static final String ANSI_BACKGROUND_RED = "\u001b[41;1m";
    public static final String ANSI_BACKGROUND_GREEN = "\u001b[42;1m";
    public static final String ANSI_BACKGROUND_YELLOW = "\u001b[43;1m";
    public static final String ANSI_BACKGROUND_BLUE = "\u001b[44;1m";
    public static final String ANSI_BACKGROUND_CYAN = "\u001b[46;1m";
    public static final String ANSI_BACKGROUND_WHITE = "\u001b[47;1m";

    public Utils(PrintWriter out,Scanner in){
        this.in=in;
        this.out=out;
    }

    //DEBUG
    public ArrayList<Resource> printListResource(HashMap<Resource,Integer> list){
        int i=1;
        ArrayList<Resource> resources= new ArrayList<>();
        for(Resource resource: list.keySet()){
            String key=resource.toString();
            String value= list.get(resource).toString();
            out.println(i+". x" + value + " resource of " + key);
            resources.add(i--,resource);
            i++;
        }
        return resources;
    }

    //DEBUG
    public void printMarket(ArrayList<ArrayList<MarbleType>> market){
        for (int i = 0; i < market.size(); i++) {
            for (int j = 0; j < market.get(i).size(); j++) {
                printMarble(market.get(i).get(j));
            }
            out.println();
        }
    }

    private void printMarble(MarbleType marble) {
        if (marble==MarbleType.MarbleBlue) out.println(ANSI_BACKGROUND_BLUE+ "   " + ANSI_RESET);
        else if (marble==MarbleType.MarbleGrey) out.println(ANSI_BACKGROUND_GRAY+ "   " + ANSI_RESET);
        else if (marble==MarbleType.MarbleRed) out.println(ANSI_BACKGROUND_RED+ "   " + ANSI_RESET);
        else if (marble==MarbleType.MarbleWhite) out.println(ANSI_BACKGROUND_WHITE+ "   " + ANSI_RESET);
        else if (marble==MarbleType.MarbleYellow) out.println(ANSI_BACKGROUND_YELLOW+ "   " + ANSI_RESET);
    }

    public int readNumber(){
        int selection;
        synchronized (in){
        while(!in.hasNextInt()){
            out.println("Invalid choice! Choose again:");
            in.next();
        }
        selection=in.nextInt();
        return selection;
        }
    }

    public int readNumberWithBounds(int min,int max){
        synchronized (in){
            int number= readNumber();
        while(number< min || number>max){
             out.println("The number to select must be between "+min+ " and "+max+ ". Please retry:");
             number=readNumber();
        }
        return number;}
    }

    public void printArtWelcome(){
        System.out.println(ANSI_BACKGROUND_BLACK+ANSI_BLUE+"    ...     ..      ..                    .x+=:.        s                                                            ..      ...                                             .       .x+=:.      .x+=:.                                                  \n" +
                "  x*8888x.:*8888: -\"888:                 z`    ^%      :8                                             oec :       :~\"8888x :\"%888x                                          @88>    z`    ^%    z`    ^%                                                 \n" +
                " X   48888X `8888H  8888                    .   <k    .88                  .u    .             u.    @88888      8    8888Xf  8888>                u.    u.                 %8P        .   <k      .   <k                u.    u.                        \n" +
                "X8x.  8888X  8888X  !888>        u        .@8Ned8\"   :888ooo      .u     .d88B :@8c      ...ue888b   8\"*88%     X88x. ?8888k  8888X       .u     x@88k u@88c.       u        .       .@8Ned8\"    .@8Ned8\"       u      x@88k u@88c.       .        .u    \n" +
                "X8888 X8888  88888   \"*8%-    us888u.   .@^%8888\"  -*8888888   ud8888.  =\"8888f8888r     888R Y888r  8b.        '8888L'8888X  '%88X    ud8888.  ^\"8888\"\"8888\"    us888u.   .@88u   .@^%8888\"   .@^%8888\"     us888u.  ^\"8888\"\"8888\"  .udR88N    ud8888.  \n" +
                "'*888!X8888> X8888  xH8>   .@88 \"8888\" x88:  `)8b.   8888    :888'8888.   4888>'88\"      888R I888> u888888>     \"888X 8888X:xnHH(`` :888'8888.   8888  888R  .@88 \"8888\" ''888E` x88:  `)8b. x88:  `)8b. .@88 \"8888\"   8888  888R  <888'888k :888'8888. \n" +
                "  `?8 `8888  X888X X888>   9888  9888  8888N=*8888   8888    d888 '88%\"   4888> '        888R I888>  8888R         ?8~ 8888X X8888   d888 '88%\"   8888  888R  9888  9888    888E  8888N=*8888 8888N=*8888 9888  9888    8888  888R  9888 'Y\"  d888 '88%\" \n" +
                "  -^  '888\"  X888  8888>   9888  9888   %8\"    R88   8888    8888.+\"      4888>          888R I888>  8888P       -~`   8888> X8888   8888.+\"      8888  888R  9888  9888    888E   %8\"    R88  %8\"    R88 9888  9888    8888  888R  9888      8888.+\"    \n" +
                "   dx '88~x. !88~  8888>   9888  9888    @8Wou 9%   .8888Lu= 8888L       .d888L .+      u8888cJ888   *888>       :H8x  8888  X8888   8888L        8888  888R  9888  9888    888E    @8Wou 9%    @8Wou 9%  9888  9888    8888  888R  9888      8888L      \n" +
                " .8888Xf.888x:!    X888X.: 9888  9888  .888888P`    ^%888*   '8888c. .+  ^\"8888*\"        \"*888*P\"    4888        8888> 888~  X8888   '8888c. .+  \"*88*\" 8888\" 9888  9888    888&  .888888P`   .888888P`   9888  9888   \"*88*\" 8888\" ?8888u../ '8888c. .+ \n" +
                ":\"\"888\":~\"888\"     `888*\"  \"888*\"\"888\" `   ^\"F        'Y\"     \"88888%       \"Y\"            'Y\"       '888        48\"` '8*~   `8888!`  \"88888%      \"\"   'Y\"   \"888*\"\"888\"   R888\" `   ^\"F     `   ^\"F     \"888*\"\"888\"    \"\"   'Y\"    \"8888P'   \"88888%   \n" +
                "    \"~'    \"~        \"\"     ^Y\"   ^Y'                           \"YP'                                  88R         ^-==\"\"      `\"\"       \"YP'                   ^Y\"   ^Y'     \"\"                            ^Y\"   ^Y'                   \"P'       \"YP'    \n" +
                "                                                                                                      88>                                                                                                                                                \n" +
                "                                                                                                      48                                                                                                                                                 \n" +
                "                                                                                                      '8                                                                                                                                                 \n"+ANSI_RESET);
    }

    public int chooseRowOrColumn() {
        String choice;
        synchronized (in){
            choice= in.nextLine();
            while (choice!="r" || choice!="c"){
                out.println("Invalid choice. Type 'r' for row or 'c' for column:");
                choice=in.nextLine();
            }
            if (choice=="r") return 1;
            else return 0;
        }}

        public String readString(){
            synchronized (in){
            String word;
            word=in.nextLine();
            return word;
            }
        }

    }

