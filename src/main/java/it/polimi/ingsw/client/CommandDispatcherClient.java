package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.server.*;
import it.polimi.ingsw.communication.client.*;

public class CommandDispatcherClient {
    public static void parseInput(ServerMessage inputClass, Client client) {
        if(inputClass instanceof RequestPlayersNumber){
            client.sendPlayersNumber();
        }
        if(inputClass instanceof CurrentPlayersAndSlotLeft){
            client.notifyConnected();
        }
    }
}
