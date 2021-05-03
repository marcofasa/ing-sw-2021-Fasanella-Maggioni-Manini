package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.SetupConnection;
import it.polimi.ingsw.communication.server.NicknameUnavailable;
import it.polimi.ingsw.communication.server.ServerResponse;

import java.net.Socket;

public class ServerCommandDispatcher {

    private final Server server;

    public ServerCommandDispatcher(Server server){
        this.server = server;
    }

    public void setupConnection(String nickname, VirtualClient virtualClient) {
        try {
            server.registerClient(virtualClient, nickname);
        } catch (NicknameAlreadyInUseException e) {
            virtualClient.send(new NicknameUnavailable());
        }
    }

    public void setLobbySize(VirtualClient virtualClient, Integer size){
        System.out.println("Server size set to " + size);
        server.setCurrentLobbySize(virtualClient, size);
    }
}
