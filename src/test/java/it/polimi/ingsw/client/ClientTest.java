package it.polimi.ingsw.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


public class ClientTest {


    private void sendAndWait() {
        Client.main(new String[0]);

    }
}
