package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

    private final String coin="/images/Resources/coin.png";
    private final String servant="/images/Resources/servant.png";
    private final String shield="/images/Resources/shield.png";
    private final String stone="/images/Resources/stone.png";


    /**
     * Fixes the Resource selection for being elaborated from controller
     * @param resourceList
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
        switch (resource){
            case Servants:
                return servant;
            case Coins:
                return coin;
            case Shields:
                return shield;
            case Stones:
                return stone;
        }
        return null;
    }
}
