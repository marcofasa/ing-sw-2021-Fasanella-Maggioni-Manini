package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {


    /**
     * Fixes the Resource selection for being elaborated from controller
     */
    public void fixResourceSelection(ArrayList<Resource> resourceList){
       if(resourceList.size()==0){
           resourceList.add(null);
           resourceList.add(null);
       }
       else if(resourceList.size()==1){
           resourceList.add(null);
       }
    }


    public Boolean[] setStrongboxLevel(Boolean[] strongboxLevel) {
        strongboxLevel=new Boolean[3];
        strongboxLevel[0]=false;
        strongboxLevel[1]=false;
        strongboxLevel[2]=false;
        return strongboxLevel;
    }

    public String getResourcePath(Resource resource) {
        String stone = "/images/Resources/stone.png";
        String shield = "/images/Resources/shield.png";
        String servant = "/images/Resources/servant.png";
        String coin = "/images/Resources/coin.png";
        return switch (resource) {
            case Servants -> servant;
            case Coins -> coin;
            case Shields -> shield;
            case Stones -> stone;
        };
    }
}
