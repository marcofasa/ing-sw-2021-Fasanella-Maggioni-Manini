package it.polimi.ingsw.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VirtualClientCommandDispatcherTest {

    @Test
    void requestBuyAndPlaceDevelopmentCard() {
        VirtualClientCommandDispatcher virtualClientCommandDispatcher =
                new VirtualClientCommandDispatcher(new VirtualClient(null, null, null));
        virtualClientCommandDispatcher.requestBuyAndPlaceDevelopmentCard
                (0,0,0,-1);

    }
}