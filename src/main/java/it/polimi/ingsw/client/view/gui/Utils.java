package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class Utils {
    public void fixResourceSelection(ArrayList<Resource> resourceList){
       if(resourceList.size()==0){
           resourceList.add(null);
           resourceList.add(null);
       }
       else if(resourceList.size()==1){
           resourceList.add(null);
       }
    }
}
