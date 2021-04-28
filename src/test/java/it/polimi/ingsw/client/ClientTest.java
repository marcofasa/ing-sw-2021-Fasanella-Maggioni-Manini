package it.polimi.ingsw.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class ClientTest {

    @Test
    public void givenClient1_whenServerResponds_thenCorrect() {
        Client client1 = new Client();
        client1.startConnection("127.0.0.1", 25580);
        String msg1 = client1.sendMessage("hello");
        String msg2 = client1.sendMessage("world");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
    }

    @Test
    public void givenClient2_whenServerResponds_thenCorrect() {
        Client client2 = new Client();
        client2.startConnection("127.0.0.1", 25580);
        Client client3 = new Client();
        client3.startConnection("127.0.0.1", 25580);
        String msg1 = client2.sendMessage("hello");
        String msg2 = client2.sendMessage("world");
        String msg21 = client3.sendMessage("hello");
        String msg22 = client3.sendMessage("world");
        String terminate2 = client3.sendMessage(".");
        String terminate = client2.sendMessage(".");

        assertEquals(msg1, "hello");
        assertEquals(msg2, "world");
        assertEquals(terminate, "bye");
        assertEquals(msg21, "hello");
        assertEquals(msg22, "world");
        assertEquals(terminate2, "bye");
    }
}