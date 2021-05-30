package it.polimi.ingsw.client.view.cli;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.Scanner;

class CLITest {
    private static final PrintWriter out = new PrintWriter(System.out, true);
    private static final Scanner in = new Scanner(System.in);
    private static final Utils utils=new Utils(out,in);

    @Test
    void displayScoreBoard() {
    }
}