package it.polimi.ingsw.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


public class ClientTest {

    @Test
    private void singleClient() throws InterruptedException {
        Client client = new Client(true);
        System.out.println("Client has started");
        int port = 25556;
        String ip = "127.0.0.1";
        client.startConnectionAndListen(ip,port, "nickname");
    }

}
