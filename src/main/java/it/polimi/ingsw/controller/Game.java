package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;

public class Game implements Runnable{

    private ArrayList<VirtualClient> players;
    private GameTable gameTable;
    private Controller controller;

    public Game(){
        //inizializza controller
        gameTable = new GameTable(false);
    }

    @Override
    public void run() {
        System.out.println("Game partito");
        while (true)
            ;
    }

    /**
     * get player's virtual client
     * @param index index of the player, starting by 1
     * @return player's virtual client
     */
    public VirtualClient getClientByIndex(Integer index){
        return players.get(index + 1);
    }

    public void addAllPlayers(ArrayList<VirtualClient> virtualClients){
        this.players = virtualClients;
    }
}
