package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.ResponseNicknameUnavailable;
import it.polimi.ingsw.communication.server.ResponseSuccess;
import it.polimi.ingsw.model.CardLeader;

public class ServerCommandDispatcher {

    private final Server server;

    public ServerCommandDispatcher(Server server){
        this.server = server;
    }

    public void setupConnection(String nickname, VirtualClient virtualClient) {
        try {
            server.registerClient(virtualClient, nickname);
        } catch (NicknameAlreadyInUseException e) {
            virtualClient.send(new ResponseNicknameUnavailable());
        }
    }

    public void setLobbySize(VirtualClient virtualClient, Integer size){
        System.out.println("Server size set to " + size);
        server.setCurrentLobbySize(virtualClient, size);
    }

    public void requestActivateCardLeader(CardLeader cardLeader, VirtualClient virtualClient, int timeoutID) {
        System.out.println("Card Leader activation request arrived");
        virtualClient.send(new ResponseSuccess(timeoutID));
    }
}
