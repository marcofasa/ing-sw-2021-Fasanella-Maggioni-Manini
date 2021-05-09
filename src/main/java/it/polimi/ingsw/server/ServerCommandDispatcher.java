package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.ResponseNicknameUnavailable;
import it.polimi.ingsw.communication.server.ResponseSuccess;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.ProductionSelection;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

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

    public void requestActivateCardLeader(VirtualClient virtualClient, CardLeader cardLeader, int timeoutID) {
        System.out.println("Card Leader activation request arrived");
        virtualClient.send(new ResponseSuccess(timeoutID));
    }

    public void initialSelection(VirtualClient virtualClient, ArrayList<CardLeader> cardLeader, Resource resource1, Resource resource2) {
        System.out.println("initialSelection");

        //Ottiene il nickname del giocatore dalla mappa in server.clientsNickname

        /*
        Chiama Game passando nickname, la lista di 2 carte leader selezionate, e le risorse bonus selezionate.
        Game validera` la richiesta, confermando che il giocatore che ha mandato la richiesa e` quello attivo,
        se si chiama Controller.do
         */
    }

    public void requestActivateProduction(VirtualClient virtualClient, ProductionSelection productionSelection) {
        //<- tornare indietro una server response chiamata controller
        //SEND al player server response
    }

    public void requestBuyDevelopmentCard(VirtualClient virtualClient, int rowIndex, int columnIndex) {
        
    }

    public void requestEndTurn(VirtualClient virtualClient) {

        //Ottiene il nickname del giocatore dalla mappa in server.clientsNickname

        /*
        Chiama Game passando nickname e una new RequestEndTurn() : game validera` se il giocatore che ha mandato la
        richiesta e` quello attivo, se si chiama Controller.advanceTurn()
         */

        //Fa virtualClient.send(ResponseSuccess) oppure virtualClient.send(ResponseNotActivePlayerError)
    }
}
