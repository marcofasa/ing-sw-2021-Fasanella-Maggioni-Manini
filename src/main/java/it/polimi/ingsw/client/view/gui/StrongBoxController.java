package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.HashMap;

public class StrongBoxController extends StandardStage {

    @FXML
    Label stone_label;
    @FXML
    Label coin_label;
    @FXML
    Label shield_label;
    @FXML
    Label servant_label;

    /**
     * Sets current Player deposit
     * @param deposit hashmap of resources
     */
    public void setStrongBox(HashMap<Resource, Integer> deposit){
        for(Resource resource: deposit.keySet()){
            switch (resource){
                case Coins:
                    coin_label.setText("x"+deposit.get(resource));
                    break;
                case Servants:
                    servant_label.setText("x"+deposit.get(resource));
                    break;
                case Shields:
                    shield_label.setText("x"+deposit.get(resource));
                    break;
                case Stones:
                    stone_label.setText("x"+deposit.get(resource));
                    break;
            }
        }
    }

    /**
     * Closing this Stage
     * @param actionEvent button
     */
    public void close(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }
}
