package it.polimi.ingsw.controller;

import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;

public class Game implements Runnable{

    private ArrayList<VirtualClient> players;

    @Override
    public void run() {
        System.out.println("Game partito");
        while (true)
            ;
    }

    public void addAllPlayers(ArrayList<VirtualClient> virtualClients){
        this.players = virtualClients;
    }
}
