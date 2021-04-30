package it.polimi.ingsw.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class ClientDummyTest {

    @Test
    public void givenClient1_whenServerResponds_thenCorrect() {
        ClientDummy clientDummy1 = new ClientDummy();
        clientDummy1.startConnection("127.0.0.1", 25580);
        String msg1 = clientDummy1.sendMessage("hello");
        String msg2 = clientDummy1.sendMessage("world");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() {
        ClientDummy clientDummy2 = new ClientDummy();
        clientDummy2.startConnection("127.0.0.1", 25580);
        ClientDummy clientDummy3 = new ClientDummy();
        clientDummy3.startConnection("127.0.0.1", 25580);
        String msg1 = clientDummy2.sendMessage("hello");
        String msg2 = clientDummy2.sendMessage("world");
        String msg21 = clientDummy3.sendMessage("hello");
        String msg22 = clientDummy3.sendMessage("world");
        String terminate2 = clientDummy3.sendMessage(".");
        String terminate = clientDummy2.sendMessage(".");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
        assertEquals(msg21, "hello");
        assertEquals(msg22, "world");
        assertEquals(terminate2, "bye");
    }
}