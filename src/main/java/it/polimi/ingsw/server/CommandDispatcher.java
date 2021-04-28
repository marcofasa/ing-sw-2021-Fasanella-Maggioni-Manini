package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.SetupConnection;

import java.net.Socket;

public class CommandDispatcher {
    public static void parseInput(ClientMessage inputClass, VirtualClient virtualClient) {
        if(inputClass instanceof SetupConnection){
            virtualClient.setupConnection(inputClass.getPayload());
        }
    }
}
