package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.HashMap;

public class DepositController extends StandardScene{
    @FXML
    Label stone_label;
    @FXML
    Label coin_label;
    @FXML
    Label shield_label;
    @FXML
    Label servant_label;


    public void setDeposit(HashMap<Resource, Integer> deposit){
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

    public void close(ActionEvent actionEvent) {
        printClick("Close button");
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
