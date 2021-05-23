package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Utils {

    private final PrintWriter out;
    private Scanner in;
    private boolean coloredCLI=true;
    private boolean correctNickName=true;

    //ANSI escape codes for Colors

    //Char colors
    public static final String RESET = Colors.RESET.toString();
    public static final String GRAY = Colors.GRAY.toString();
    public static final String BLACK = Colors.BLACK.toString();
    public static final String RED = Colors.RED.toString();
    public static final String GREEN = Colors.GREEN.toString();
    public static final String YELLOW = Colors.YELLOW.toString();
    public static final String BLUE = Colors.BLUE.toString();
    public static final String MAGENTA = Colors.MAGENTA.toString();

    //BackGround Colors
    public static final String BACKGROUND_GRAY = Colors.BLACK_BACKGROUND_BRIGHT.toString();
    public static final String BACKGROUND_BLACK = Colors.BLACK_BACKGROUND_BRIGHT.toString();
    public static final String BACKGROUND_RED = Colors.RED_BACKGROUND_BRIGHT.toString();
    public static final String BACKGROUND_GREEN = Colors.GREEN_BACKGROUND_BRIGHT.toString();
    public static final String BACKGROUND_YELLOW = Colors.YELLOW_BACKGROUND_BRIGHT.toString();
    public static final String BACKGROUND_BLUE = Colors.BLUE_BACKGROUND_BRIGHT.toString();
    public static final String BACKGROUND_WHITE = Colors.WHITE_BACKGROUND_BRIGHT.toString();
    public static final String BACKGROUND_MAGENTA = Colors.MAGENTA_BACKGROUND_BRIGHT.toString();

    /**
     * Constructor
     * @param out
     * @param in
     */
    public Utils(PrintWriter out,Scanner in){
        this.in=in;
        this.out=out;
    }


    /**
     * Print List of given Resources
     * @param list
     */
    public void printListResource(HashMap<Resource,Integer> list){
        for(Resource resource: list.keySet()){
            String key=resource.toString();
            String value= list.get(resource).toString();

            if (coloredCLI) {
                out.printf("- x" + value + " resources of " );
                printResourceColored(resource);
                out.println();
            }
            else out.println("- x" + value + " resources of " + key);

        }
    }

    /**
     * Print a single given Resource in ANSI Colors
     * @param resource
     */
    private void printResourceColored(Resource resource) {
        if(resource==Resource.Coins) out.printf(YELLOW +"Coins"+RESET);
        else if(resource==Resource.Servants) out.printf(MAGENTA +"Servants"+RESET);
        else if(resource==Resource.Stones) out.printf(GRAY +"Stones"+RESET);
        else if(resource==Resource.Shields) out.printf(BLUE +"Shields"+RESET);
    }



    /**
     * Print Marble Market
     * @param market
     * @param spareMarble
     */
    public void printMarket(ArrayList<ArrayList<MarbleType>> market, MarbleType spareMarble){
    out.println("---MARKET---");
        if(coloredCLI){
            printMarbleColored(spareMarble);
            out.println(" 1  2  3  4  ");
        }
        else {
            printMarble(spareMarble);
            out.println(" 1  2  3  4  ");
        }
        for (int i = 0; i < market.size(); i++) {
            out.printf(" "+ (i+1)+" ");
            for (int j = 0; j < market.get(i).size(); j++) {
                if(coloredCLI){
                    printMarbleColored(market.get(i).get(j));
                }
                else printMarble(market.get(i).get(j));
            }
            out.printf("\n");
        }
    }

    /**
     * Print a single given Marble
     * @param marble
     */
    private void printMarble(MarbleType marble){
        if (marble==MarbleType.MarbleBlue) out.printf(" B ");
        else if (marble==MarbleType.MarbleGrey) out.printf(" G ");
        else if (marble==MarbleType.MarbleRed) out.printf(" R ");
        else if (marble==MarbleType.MarbleWhite) out.printf(" W ");
        else if (marble==MarbleType.MarbleYellow) out.printf(" Y ");
        else if (marble==MarbleType.MarblePurple) out.printf(" P ");
    }

    /**
     * Print a single given Marble (ANSI Color)
     * @param marble
     */
    private void printMarbleColored(MarbleType marble) {
        if (marble==MarbleType.MarbleBlue) out.printf(BACKGROUND_BLUE+" B "+RESET);
        else if (marble==MarbleType.MarbleGrey) out.printf(BACKGROUND_GRAY +" G "+RESET);
        else if (marble==MarbleType.MarbleRed) out.printf(BACKGROUND_RED+" R " +RESET);
        else if (marble==MarbleType.MarbleWhite) out.printf(BACKGROUND_WHITE+" W "+RESET);
        else if (marble==MarbleType.MarbleYellow) out.printf(BACKGROUND_YELLOW+" Y "+RESET);
        else if (marble==MarbleType.MarblePurple) out.printf(BACKGROUND_MAGENTA +" P "+RESET);
    }

    /**
     * Reads a number from stdin
     * @return the input number
     */
    public int readNumber(){
        int selection;
        synchronized (in){
            while(!in.hasNextInt()){
                if(coloredCLI) {
                    out.println(RED +"Invalid input ! Choose again:"+ RESET);
                }
                else out.println("Invalid input ! Choose again:");
                in.next();
            }
            selection=in.nextInt();
            return selection;
        }
    }

    /**
     * Read and check a number between
     * @param min minimum bound
     * and
     * @param max maximum value
     * @return
     */
    public int readNumberWithBounds(int min,int max){
        synchronized (in){
            int number= readNumber();
            while(number< min || number>max){
                out.println("The number to select must be between "+min+ " and "+max+ ". Please retry:");
                number=readNumber();
            }
            return number;}
    }


    /**
     * Print Colored Welcome message in ANSI
     */
    public void printANSIWelcome(){
        /*System.out.println(BACKGROUND_BLACK + BLUE +"    ...     ..      ..                    .x+=:.        s                                                            ..      ...                                             .       .x+=:.      .x+=:.                                                  \n" +
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
                "                                                                                                      '8                                                                                                                                                 \n"+ RESET);

         */
        out.println(BACKGROUND_BLACK+BLUE+"███╗   ███╗ █████╗ ███████╗████████╗███████╗██████╗ ███████╗     ██████╗ ███████╗    ██████╗ ███████╗███╗   ██╗ █████╗ ██╗███████╗███████╗ █████╗ ███╗   ██╗ ██████╗███████╗\n" +
                "████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝    ██╔═══██╗██╔════╝    ██╔══██╗██╔════╝████╗  ██║██╔══██╗██║██╔════╝██╔════╝██╔══██╗████╗  ██║██╔════╝██╔════╝\n" +
                "██╔████╔██║███████║███████╗   ██║   █████╗  ██████╔╝███████╗    ██║   ██║█████╗      ██████╔╝█████╗  ██╔██╗ ██║███████║██║███████╗███████╗███████║██╔██╗ ██║██║     █████╗  \n" +
                "██║╚██╔╝██║██╔══██║╚════██║   ██║   ██╔══╝  ██╔══██╗╚════██║    ██║   ██║██╔══╝      ██╔══██╗██╔══╝  ██║╚██╗██║██╔══██║██║╚════██║╚════██║██╔══██║██║╚██╗██║██║     ██╔══╝  \n" +
                "██║ ╚═╝ ██║██║  ██║███████║   ██║   ███████╗██║  ██║███████║    ╚██████╔╝██║         ██║  ██║███████╗██║ ╚████║██║  ██║██║███████║███████║██║  ██║██║ ╚████║╚██████╗███████╗\n" +
                "╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝     ╚═════╝ ╚═╝         ╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚══════╝\n" +
                "                                                                                                                                                                            "+RESET);
    }

    /**
     * Reads if player wants to choose a row or a column
     * @return 1 if row / 0 if column
     */
    public int chooseRowOrColumn() {
        String choice;
        synchronized (in){
            choice= in.nextLine();
            while (!choice.equals("r") && !choice.equals("c")){
                if(coloredCLI) {
                    out.println(RED +"Invalid choice. Type 'r' for row or 'c' for column:"+ RESET);
                }
                else out.println("Invalid choice. Type 'r' for row or 'c' for column:");
                choice=in.nextLine();
            }
            if (choice.equals("r")) return 1;
            else return 0;
        }}

    /**
     * Reads a string from stdin
     * @return
     */
    public String readString(){
        synchronized (in){
            String word;
            word=in.nextLine();
            return word;
        }
    }


    /**
     * Prints FaithTrail with list of other players position
     * @param nickname of this player
     */
    public void printFaithTrail(String nickname, LightFaithTrail lightFaithTrail){
        HashMap<String, Integer> playersPosition= lightFaithTrail.getPlayersPosition();
        if(coloredCLI){ printFaithTrailASCII(lightFaithTrail.getTileStatuses());
        out.println("You are at position "+lightFaithTrail.getPlayersPosition().get(nickname));
        out.println(BACKGROUND_GRAY +"Other players:"+ RESET);
        for (String string: playersPosition.keySet()){
            if(!string.equals(nickname)) out.println(RED +" "+string+ RESET +" is at position "+playersPosition.get(string)+";");
        }}
        else {
            out.println("You are at position "+lightFaithTrail.getPlayersPosition().get(nickname));
            int section=1;
            for(int i=0;i<3;i++){
                out.printf("- Tile of Section "+ section+ " is: ");
                printTile(lightFaithTrail.getTileStatuses().get(i));
                section++;
                out.println();
            }
            out.println("Other players:");
            for (String string: playersPosition.keySet()) {
                if (!string.equals(nickname)) out.println(string + " is at position " + playersPosition.get(string));
            }
        }
    }

    private void printTile(FaithTileStatus tileStatuses) {
        if(tileStatuses==FaithTileStatus.Reached) out.printf("reached;");
        if(tileStatuses==FaithTileStatus.Discarded) out.printf("discarded;");
        else out.printf("not reached;");
    }




    /**
     * Prints Faith Trail in ASCII Art
     * @param tileStatuses
     */
    public void printFaithTrailASCII(ArrayList<FaithTileStatus> tileStatuses){
        int col=18;
        int i =0;
        int row=0;
        while(row<3) {
            while (i <= col) {
                if((row==0 && (i ==0 || i ==1 || i ==8 || i ==11)) || (row==1 && (i ==0 || i ==1 || i ==3 || i ==6 || i ==8 || i ==11|| i ==13 || i ==14|| i ==17 || i ==18)) || (row==2 &&(i ==3 || i ==6 || i ==13 || i ==14|| i ==17 || i ==18)))System.out.print("       ");
                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"┌─────┐"+ RESET);
                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"┌─────┐"+ RESET);

                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"┌─────┐"+ RESET);
                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"┌─────┐"+ RESET);

                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"┌─────┐"+ RESET);
                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"┌─────┐"+ RESET);
                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"┌─────┐"+ RESET);
                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"┌─────┐"+ RESET);

                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"┌─────┐"+ RESET);
                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"┌─────┐"+ RESET);

                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"┌─────┐"+ RESET);
                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"┌─────┐"+ RESET);
                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"┌─────┐"+ RESET);
                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"┌─────┐"+ RESET);

                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"┌─────┐"+ RESET);
                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"┌─────┐"+ RESET);
                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"┌─────┐"+ RESET);

                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"┌─────┐"+ RESET);
                else System.out.print("┌─────┐");
                i++;
            }
            System.out.println();
            i = 0;
            while (i <= col) {
                if((row==0 && !(i != 0 && i != 1 && i != 8 && i != 11)) || !(row != 1 || !(i == 0 || i == 1 || i == 3 || i == 6 || i == 8 || i == 11 || i == 13 || i == 14 || i == 17 || i == 18)) || (row==2 &&(i ==3 || i ==6 || i ==13 || i ==14|| i ==17 || i ==18)))System.out.print("       ");
                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"│     │"+ RESET);
                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"│     │"+ RESET);

                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"│     │"+ RESET);
                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"│     │"+ RESET);

                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"│     │"+ RESET);
                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"│     │"+ RESET);
                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"│     │"+ RESET);
                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"│     │"+ RESET);

                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"│     │"+ RESET);
                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"│     │"+ RESET);

                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"│     │"+ RESET);
                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"│     │"+ RESET);
                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"│     │"+ RESET);
                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"│     │"+ RESET);

                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"│     │"+ RESET);
                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"│     │"+ RESET);
                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"│     │"+ RESET);
                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"│     │"+ RESET);
                else {
                    printFaithTrailCellNumber(row,i);
                }

                i++;
            }
            System.out.println();
            i = 0;
            while (i <= col) {
                if (row == 0 && (i == 0 || i == 1 || i == 8 || i == 11)) {
                    System.out.print("       ");
                } else if ((row == 1 && (i == 0 || i == 1 || i == 3 || i == 6 || i == 8 || i == 11 || i == 13 || i == 14 || i == 17 || i == 18)) || (row == 2 && (i == 3 || i == 6 || i == 13 || i == 14 || i == 17 || i == 18))) {
                    System.out.print("       ");
                }
                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"└─────┘"+ RESET);
                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"└─────┘"+ RESET);

                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"└─────┘"+ RESET);
                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"└─────┘"+ RESET);

                else if(row==1 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"└─────┘"+ RESET);
                else if(row==1 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"└─────┘"+ RESET);
                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"└─────┘"+ RESET);
                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"└─────┘"+ RESET);

                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"└─────┘"+ RESET);
                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"└─────┘"+ RESET);
                else if(row==2 && (i==4||i==5) && (tileStatuses.get(0)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"└─────┘"+ RESET);
                else if(row==2 && (i==15||i==16) && (tileStatuses.get(2)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"└─────┘"+ RESET);
                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"└─────┘"+ RESET);
                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"└─────┘"+ RESET);

                else if(row==1 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"└─────┘"+ RESET);
                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Discarded)) System.out.print(BACKGROUND_RED +"└─────┘"+ RESET);
                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Not_Reached)) System.out.print(BACKGROUND_YELLOW +"└─────┘"+ RESET);

                else if(row==0 && (i==9||i==10) && (tileStatuses.get(1)==FaithTileStatus.Reached)) System.out.print(BACKGROUND_GREEN +"└─────┘"+ RESET);
                else {
                    System.out.print("└─────┘");
                }
                i++;
            }
            System.out.println();
            i = 0;
            row++;
        }
    }

    /**
     * Prints the number of the corresponding cell in printFaithTrailASCII()
     * @param row from 0 to 2
     * @param column from 0 to 18
     */
    private void printFaithTrailCellNumber(int row, int column) {
        int pos;
        if(row==0){
            if(column <8) {
                pos = column + 2;
            }
            else pos= column +6;
        }
        else if (row==1){
            if(column ==2) pos= column +1;
            else if(column ==7) pos= column +3;
            else pos=17;
        }
        else {
            if(column >6) pos= column +4;
            else pos= column;
        }
        if (pos<10) {
            System.out.print("│  "+pos+"  │");
        }
        else {
            System.out.print("│  "+pos+" │");
        }
    }

    /**
     * Prints and asks the Card Leader from the list
     * @param cardLeaders
     * @return arraylist of chosen Card Leader
     */
    public ArrayList<CardLeader> printAndGetCardLeaderFirstSelection(ArrayList<CardLeader> cardLeaders) {
        //Print Card Leader Deck
        printCardLeaderDeck(cardLeaders);

        //Selection
        ArrayList<CardLeader> selection= new ArrayList<>();
        out.println("Choose the first leader card:");
        out.println("Type the corresponding number in the list");
        int one=readNumberWithBounds(1,cardLeaders.size());
        selection.add(cardLeaders.get(one-1));


        out.println("Choose the second leader card:");
        out.println("Type the corresponding number in the list");
        int two=readNumberWithBounds(1,cardLeaders.size());
        while(one==two){
            out.println("Card already picked, please choose another one");
            two=readNumberWithBounds(1,cardLeaders.size());
        }
        selection.add(cardLeaders.get(two-1));
        return selection;
    }

    /**
     *Print and Get a single Card Leader from Deck
     * @param cardLeaders
     * @return
     */
    public CardLeader printAndGetCardLeader(ArrayList<CardLeader> cardLeaders) {
        //Print Card Leader Deck
        printCardLeaderDeck(cardLeaders);

        //Selection
        out.println("Type the corresponding number in the list");
        return cardLeaders.get(readNumberWithBounds(1,cardLeaders.size())-1);
    }

    public Integer printAndGetCardLeaderIndex(ArrayList<CardLeader> cardLeaders) {
        //Print Card Leader Deck
        printCardLeaderDeck(cardLeaders);

        //Selection
        out.println("Type the corresponding number in the list");
        return readNumberWithBounds(1,cardLeaders.size());
    }

    /**
     * Prints a given Card Leader Deck
     * @param cardLeaders
     */
    public void printCardLeaderDeck(ArrayList<CardLeader> cardLeaders){
        int index=1;
        for (int i=0; i<cardLeaders.size();i++){
            out.printf(index+" ");
            printCardLeader(cardLeaders.get(i));
            out.println();
            index++;
        }
    }

    /**
     * Prints a Card Leader with production requirements and victory points
     * @param cardLeader
     */
    private void printCardLeader(CardLeader cardLeader) {
        int victoryPoints = cardLeader.getVictoryPointsValue();
        CardLeaderRequirements cardLeaderRequirements= cardLeader.getRequirements();
        if(coloredCLI) {
            out.printf("Card Leader of type ");
            printCardLeaderType(cardLeader.getDescription()) ;
            printResourceColored(cardLeader.getResource());
            out.println();
            out.printf("(Requirements for activation): ");
        }
        else {
            out.printf("Card Leader of type ");
            printCardLeaderType(cardLeader.getDescription()) ;
            out.printf(cardLeader.getResource().toString());
                    out.println();
                    out.printf("(Requirements for activation): ");
        }

        switch (cardLeaderRequirements.getCardLeaderRequirementsType()){
            case NumberOfDevelopmentCardTypes:
                printCardLeaderDevelopmentNumber(cardLeaderRequirements.getNumberOfDevelopmentCardTypes());
                break;
            case NumberOfDevelopmentCardLevel:
                printCardLeaderDevelopmentLevel(cardLeaderRequirements.getNumberOfDevelopmentCardLevel());
                break;
            case NumberOfResources:
                HashMap<Resource,Integer> list=cardLeaderRequirements.getNumberOfResources();
                for(Resource resource: list.keySet()){
                    String key=resource.toString();
                    String value= list.get(resource).toString();
                    if (coloredCLI) {
                        out.printf(" x" + value + " resource of " );
                        printResourceColored(resource);
                    }
                    else out.printf(" x" + value + " resource of " + key);
                }
                out.printf(",");
                break;
        }
        out.printf(" with "+victoryPoints+" victory points;");
        out.println();
    }

    private void printCardLeaderType(CardLeaderType description) {
        String s=description.toString();
        switch (description){
            case Deposit:
           s=s+ " where you can deposit ";
        break;
            case Discount:
            s=s+" that discounts of one ";
            break;

            case WhiteMarble:
            s=s+" that has effect for ";
            break;

            case Production:
            s=s+" using as input ";
        }
        out.printf(s);
        }


    /**
     * Prints descriptions if CardLeaderRequirementsType==NumberOfDevelopmentCardLevel
     * @param numberOfDevelopmentCardLevel
     */
    private void printCardLeaderDevelopmentLevel(HashMap<CardDevelopmentType, CardDevelopmentLevel> numberOfDevelopmentCardLevel) {
        for(CardDevelopmentType cardDevelopmentType: numberOfDevelopmentCardLevel.keySet()){
            String key=cardDevelopmentType.toString();
            String value=numberOfDevelopmentCardLevel.get(cardDevelopmentType).toString();
            if(coloredCLI){
                out.printf("Card development of type ");
                printCardColor(key);
                out.printf(" at level "+value+",");
            }
            else out.printf("Card development of type "+key+" at level "+value+",");
        }
    }

    private void printCardColor(String key) {
        switch (key){
            case "Yellow":
                out.printf(YELLOW +"Yellow"+ RESET);
                break;
            case "Green":
                out.printf(GREEN +"Green"+ RESET);
                break;
            case "Purple":
                out.printf(MAGENTA +"Purple"+ RESET);
                break;
            case "Blue":
                //TODO Blue color not displayed
                out.printf(BLUE +" Blue"+ RESET);
                break;
            default:printErrorMessage();
        }
    }

    /**
     * Prints descriptions if CardLeaderRequirementsType==NumberOfDevelopmentCardTypes
     * @param numberOfDevelopmentCardTypes
     */
    private void printCardLeaderDevelopmentNumber(HashMap<CardDevelopmentType, Integer> numberOfDevelopmentCardTypes) {
        for(CardDevelopmentType cardDevelopmentType: numberOfDevelopmentCardTypes.keySet()){
            String key=cardDevelopmentType.toString();
            int value=numberOfDevelopmentCardTypes.get(cardDevelopmentType);
            if (coloredCLI){
                out.printf(" x"+value+" cards of type ");
                printCardColor(key);
            out.printf(",");
            }
            else out.printf(" x"+value+" cards of type "+key+",");
        }
    }


    /**
     * Player Error Message
     */
    public void printPlayerCommandError() {
        if (coloredCLI) out.println(RED +"Command not found, please try again! (type \"help\" for command list)"+ RESET);
        else out.println("Command not found, please try again! (type \"help\" for command list)");
    }



    /**
     * Player Help Message Menu
     */
    public void printHelpMenu(boolean active) {
        if(coloredCLI) {
            out.println(BACKGROUND_BLACK + "Help Command List" + RESET);
            out.println("Here's a list of all commands that you can execute:");
            out.println();
            out.println(BACKGROUND_GRAY +"Command list for displays:"+ RESET);
            out.println(GREEN +"colorize"+ RESET +" to remove colors ");
            out.println(GREEN +"faith trail"+ RESET +" to display your position, faith tiles and other players position ");
            out.println(GREEN +"deposit"+ RESET +" to display your deposit ");
            out.println(GREEN +"strongbox"+ RESET +" to display your strongbox ");
            out.println(GREEN +"card leader"+ RESET +" to display your card leader deck ");
            out.println(GREEN +"card development"+ RESET +" to display your card development deck ");
            out.println(GREEN +"resource market"+ RESET +" to display resource market");
            out.println(GREEN +"card development market"+ RESET +" to display card development market");
            out.println(GREEN +"checkout player"+ RESET +" to display a player's brief model");

            out.println();

            if(active){
            out.println(BACKGROUND_GRAY +"Command list for actions"+ RESET);
            out.println(GREEN +"buy resource"+ RESET +" to use market and get new resources");
            out.println(GREEN +"buy card development"+ RESET +" to use market and get new resources");
            out.println(GREEN +"production"+ RESET +" to use various type of productions (basic,card development and card leader)");
            out.println(GREEN +"end turn"+ RESET +" to end your turn ");
            out.println(GREEN +"activate card leader"+ RESET +" to activate a card leader");
            out.println(GREEN +"discard card leader"+ RESET +" to discard a card leader");
            out.println();}
        }
        else {
            out.println("Help Command List:");
            out.println("Here's a list of all commands that you can execute:");
            out.println();
            out.println("Command list for displays:");
            out.println("\"colorize\" to remove colors ");
            out.println("\"faith trail\" to display your position, faith tiles and other players position ");
            out.println("\"deposit\" to display your deposit ");
            out.println("\"strongbox\" to display your strongbox ");
            out.println("\"card leader\" to display your card leader deck ");
            out.println("\"card development\" to display your card development deck ");
            out.println("\"resource market\" to display resource market");
            out.println("\"card development market\" to display card development market");

            out.println();

            if(active){
            out.println("Command list for actions:");
            out.println("\"buy resource\" to use market and get new resources");
            out.println("\"buy card development\" to use market and get new resources");
            out.println("\"production\" to use various type of productions (basic,card development and card leader)");
            out.println("\"end turn\" to end your turn ");
            out.println("\"activate card leader\" to activate a card leader");
            out.println("\"discard card leader\" to discard a card leader");
            out.println();
        }}
    }



    /**
     * Read a number between
     * @param min minimum
     * and
     * @param max maximum
     * @return the number casted as String
     */
    public String readNumberWithBoundsToString(int min, int max) {
        synchronized (in) {
            Integer number = readNumber();
            while (number < min || number > max) {
                out.println("The number to select must be between " + min + " and " + max + ". Please retry:");
                number = readNumber();
            }
            return number.toString();
        }
    }

    /**
     * Prints the Card Development Market
     * @param cardDevelopmentMarket
     */
    public void printDevelopmentCardMarket(ArrayList<ArrayList<CardDevelopment>> cardDevelopmentMarket) {
        for (int i = 0; i < cardDevelopmentMarket.size(); i++) {
            for (int j = 0; j < cardDevelopmentMarket.get(i).size(); j++) {
                printCardDevelopmentForMarket(cardDevelopmentMarket.get(i).get(j),i,j);
            }
            out.println();
        }
    }

    /**
     * Print Card Development Description in Market
     * @param cardDevelopment
     * @param i
     * @param j
     */
    private void printCardDevelopmentForMarket(CardDevelopment cardDevelopment, int i, int j) {
        out.printf("("+i+j+") Card type " + cardDevelopment.getCardType().toString()+
                " of level " + cardDevelopment.getCardLevel().toString() +
                ": "+cardDevelopment.getVictoryPoints()+" victory points");
                printCardResourceCost(cardDevelopment.getCardCosts());
        out.println();
    }

    private void printCardDevelopment(CardDevelopment cardDevelopment){
        out.println(
                "\n\tCard Development type " + cardDevelopment.getCardType().toString() +
                        " of level " + cardDevelopment.getCardLevel().toString() +
                        "\n\tProduction input : " + cardDevelopment.getProductionInput().toString() +
                        "\n\tProduction output: " + cardDevelopment.getProductionOutput().toString() +
                        "\n\tRed Resource produced : " + cardDevelopment.getNumberOfRedResourceProduced() +
                        "\n\tand victory points : " + cardDevelopment.getVictoryPoints());
    }

    /**
     * Prints cost of a Card
     * @param cardCosts HashMap of resources cost
     */
    private void printCardResourceCost(HashMap<Resource, Integer> cardCosts) {
        out.printf("; at a cost of:");
        for (Resource resource: cardCosts.keySet()){
            String key=resource.toString();
            String value= cardCosts.get(resource).toString();
            if (coloredCLI) {
                out.printf(" -x" + value + " resource of ");
                printResourceColored(resource);
            }
            else out.printf(" -x" + value + " resource of " + key);
        }
    }

    /**
     * Reads Basic production Resources
     * @return Array of size()==3 with 2 inputs and 1 output Resource
     *
     */
    public Resource[] getBasicProduction(){
        Resource[] basicProdInfo = new Resource[3];
        int i=0;
        for (;i<2;i++){
            out.println("Type an input resource for basic production:");
            basicProdInfo[i]=readResource(false);
        }
        out.println("Now type the output resource for basic production:");
        basicProdInfo[2]=readResource(false);
        return basicProdInfo;
    }

    /**
     * Reads input of resource selection:
     * Coins, Stones, Servants, Shields
     * @param firstCall
     */
    public Resource readResource(boolean firstCall){
        Resource resource;
        out.println("(Remember that resources are: coin, stone, servant, shield)");
        if(firstCall) in.nextLine();
        String s= readString();
        while (!s.equals("coin") && !s.equals("stone") && !s.equals("servant") && !s.equals("shield") ){
            if(!s.equals("") && !s.equals("\r\n")) {
                if (coloredCLI) {
                    out.println(RED + "Invalid choice. Type the correct name of the resource:");
                    out.println("(Remember that resources are: coin, stone, servant, shield)" + RESET);
                } else {
                    out.println("Invalid choice. Type the correct name of the resource:");
                    out.println("(Remember that resources are: coin, stone, servant, shield)");
                }
            }
            s= in.nextLine();
        }
        switch (s){
            case "coin":
                resource=Resource.Coins;
                break;
            case "stone":
                resource=Resource.Stones;
                break;
            case "servant":
                resource=Resource.Servants;
                break;
            case "shield":
                resource=Resource.Shields;
                break;
            default:
                //This should never be reached
                printResourceError();
                resource=null;
        }
        return resource;
    }

    /**
     * Ask for yes or not answer
     * @return true if "yes"
     * @param firstCall
     */
    public boolean readYesOrNo(boolean firstCall){
        String s;
        out.println("y/n ?");
        if(firstCall) in.nextLine();
        s=readString();
        while (!s.equals("n") && !s.equals("y")){
            if(coloredCLI) {
                out.println(RED +"Invalid input, type y/n :"+ RESET);
            }
            else  out.println("Invalid input, type y/n :");
            s=readString();
        }
        return s.equals("y");
    }

    /**
     * Selection of CardDevelopment to activate
     * @param cardDevelopments that can be activated
     * @return Boolean array in corresponding index
     */
    public Boolean[] getCardDevelopmentActivation(ArrayList<CardDevelopment> cardDevelopments){
        Boolean[] cardDevelopmentSlotActive= new Boolean[3];

        for(int i=0; i<cardDevelopments.size();i++){

            if (cardDevelopments.get(i)!=null) {
                printCardDevelopment(cardDevelopments.get(i));
                cardDevelopmentSlotActive[i]=readYesOrNo(false);
            } else {
                cardDevelopmentSlotActive[i] = false;
            }
        }
        return cardDevelopmentSlotActive;
    }

    /**
     * Error Message
     */
    private void printResourceError() {
        if(coloredCLI){
            out.println(RED +"There has been a problem with resource selection!"+ RESET);
        }
        else out.println("There has been a problem with resource selection!");
    }


    /**
     * Selection of CardLeader to activate
     * @param cardsLeader that can be activated
     * @return array of CardLeader that will be Activated in corresponding index (else NULL)
     */
    public CardLeader[] getCardLeaderActivation(ArrayList<CardLeader> cardsLeader) {
        CardLeader[] cardLeaders=new CardLeader[2];
        for (int i=0;i<cardsLeader.size();i++){
            if(cardsLeader.get(i)!=null){
                printCardLeader(cardsLeader.get(i));
                if (readYesOrNo(false)) cardLeaders[i]=cardsLeader.get(i);
                else cardLeaders[i]=null;
            }
        }
        return cardLeaders;
    }


    /**
     * Read output resource selection for Card Leader production
     * @param cardLeaders that will be activated
     * @return Array of output Resource
     */
    public Resource[] getCardLeaderOutputs(CardLeader[] cardLeaders){
        Resource[] cardLeaderProdOutputs = new Resource[2];
        for (int i=0;i<cardLeaders.length;i++){
            if(cardLeaders[i]!=null){
                out.println("Select an output resource for Card Leader production:");
                printCardLeader(cardLeaders[i]);
                cardLeaderProdOutputs[i]=readResource(false);
            }
            else cardLeaderProdOutputs[i]=null;
        }
        return cardLeaderProdOutputs;
    }

    /**
     * Welcome Message
     */
    public void printWelcomeMessage() {
        if(correctNickName){
        if(coloredCLI) printANSIWelcome();
        else out.println("Welcome to Masters of Renaissance!");
        correctNickName=false;
        }
    }

    /**
     * Waiting Message
     * @param timeoutInSeconds timeout
     * @throws InterruptedException
     * @throws IOException
     */
    public void printWaitingMessage(int timeoutInSeconds) throws InterruptedException, IOException {
        String anim="|/-\\";
        if (coloredCLI){
            for (int x =0 ; x < timeoutInSeconds ; x++) {
                String data = "\r" + anim.charAt(x % anim.length());
                System.out.print(BACKGROUND_BLUE);
                System.out.write(data.getBytes());
                System.out.print(RESET);
                Thread.sleep(1000);
            }}
        else{
            for (int x =0 ; x < timeoutInSeconds ; x++) {
                String data = "\r" + anim.charAt(x % anim.length());
                System.out.write(data.getBytes());
                Thread.sleep(1000);
            }
        }

    }

    /**
     * General Error
     */
    public void printErrorMessage() {
       if(coloredCLI) out.println(RED +"There has been an error in the game. Stack Trace:"+ RESET);
       else out.println("There has been an error in the game. Stack Trace:");
    }

    /**
     * Prints a given Card Development Deck
     * @param cardDevelopment
     */
    public void printCardDevelopmentDeck(ArrayList<CardDevelopment> cardDevelopment) {
        for (int i=0; i<cardDevelopment.size();i++){

            if (cardDevelopment.get(i) != null) {
                System.out.print("Slot number " + i + " holds the following as the top card: ");
                printCardDevelopment(cardDevelopment.get(i));
            } else {
                System.out.println("Slot number " + i + " is empty! ");
            }
        }
    }

    /**
     * Clears Screen
     */
    public void clearScreen(){
        try
        {
            if (!coloredCLI)
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
}

public void setColoredCLI(){
    try
    {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows"))
        {
            coloredCLI=false;
            out.println("Cucumber uses ANSI escape codes to print colored output to the console. This isn’t supported natively in Windows, so you have to install a tool called ANSICON to see colors." +
                    "\n" +
                    "Download and unzip the latest version. Open a command prompt and cd to the folder where you unzipped it. Now, cd into either x86 or x64 (depending on your machine’s processor) and install it globally on your machine:" +
                    "\n" +
                    "\u200B \t\n" +
                    "C:\\somewhere\\ansi140\\x64>\u200B ansicon -i\u200B\n" + "\n"+
                    "Any program that prints ANSI colors will now display properly on your machine." +
                    "\n" +
                    "If you don’t want (or aren’t allowed) to install ANSICON, then you can use the --monochrome option to make the output text only.");
            out.println("To see colors, type \"colors\" during a normal turn.");
        }
        else
        {
           coloredCLI=true;
           out.println("To disable colors, type \"no colors\" during a normal turn.");
        }
    }
    catch (final Exception e)
    {
        e.printStackTrace();
    }
}

    /**
     * Sets variable coloredCLI in
     * @param b boolean
     */
    public void setColors(boolean b) {
        this.coloredCLI=b;
    }

    /**
     * Changes the current boolean coloredCLI into its negation
     */
    public void colorize() {
        coloredCLI= !coloredCLI;
        if(coloredCLI) out.println(Colors.GREEN+"Done!"+RESET);
        else out.println("Done!");
    }

    public Integer checkWinner(HashMap<String, Integer> showScoreBoard) {
        Integer maxValue = 0;
        for (String name: showScoreBoard.keySet()){
            if(showScoreBoard.get(name)>maxValue) maxValue=showScoreBoard.get(name);
        }
        return maxValue;
    }

    public void printScoreBoard(HashMap<String, Integer> showScoreBoard, String nickName) {
        if(coloredCLI){
            out.println(GREEN+"You have scored "+showScoreBoard.get(nickName)+" points!!!"+RESET);
            out.println(BACKGROUND_GRAY +"Other players:"+ RESET);
            for (String string: showScoreBoard.keySet()){
                if(!string.equals(nickName)) out.println(RED+string+ RESET +": "+showScoreBoard.get(string)+";");
            }}
        else {
            out.println("You have scored "+showScoreBoard.get(nickName)+" points!!!");
            out.println("Other players:");
            for (String string: showScoreBoard.keySet()) {
                if (!string.equals(nickName)) out.println(string +": "+showScoreBoard.get(string)+";");
            }
        }
    }

    public void printWinnerMessage() {
        if(coloredCLI){
            out.printf(BACKGROUND_BLACK + BLUE +"██╗    ██╗██╗███╗   ██╗\n" +
                    "██║    ██║██║████╗  ██║\n" +
                    "██║ █╗ ██║██║██╔██╗ ██║\n" +
                    "██║███╗██║██║██║╚██╗██║\n" +
                    "╚███╔███╔╝██║██║ ╚████║\n" +
                    " ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝\n" +
                    "                       \n"+RESET);
            out.println();
        }
        else {
            out.println("You Win!");
        }
    }

    public void printLoserMessage() {
        if(coloredCLI){
            out.printf(BACKGROUND_BLACK + BLUE+"██╗      ██████╗ ███████╗████████╗\n" +
                    "██║     ██╔═══██╗██╔════╝╚══██╔══╝\n" +
                    "██║     ██║   ██║███████╗   ██║   \n" +
                    "██║     ██║   ██║╚════██║   ██║   \n" +
                    "███████╗╚██████╔╝███████║   ██║   \n" +
                    "╚══════╝ ╚═════╝ ╚══════╝   ╚═╝   \n" +
                    "                                  \n"+RESET);
            out.println();
        }else {
            out.println("You've Lost!");
        }
    }

    /**
     * Checks if a given Resource HashMap is empty
     * @param temp
     * @return
     */
    public boolean checkEmptyResourceMap(HashMap<Resource, Integer> temp) {
        for (Resource resource: temp.keySet()){
            if(temp.get(resource)>0) return false;
        }
        return true;
    }

    /**
     * Waiting Player Error Message
     */
    public void printWaitingCommandError() {
        if (coloredCLI) out.println(RED +"Waiting for your turn... it will start soon! (type \"help\" for command list)"+ RESET);
        else out.println("Waiting for your turn... it will start soon!");
    }
}

