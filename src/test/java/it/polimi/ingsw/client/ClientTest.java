package it.polimi.ingsw.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class ClientTest {
    @Test
    public void multipleClientsTests() throws InterruptedException {
        Client.main(null);
        Client.main(null);
        Client.main(null);
        Client.main(null);
    }
}
