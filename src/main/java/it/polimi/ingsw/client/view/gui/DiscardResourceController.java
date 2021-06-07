package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;

public class DiscardResourceController extends StandardStage {

    @FXML
    Label stone_label;

    @FXML
    Label coin_label;

    @FXML
    Label servant_label;

    @FXML
    Label shield_label;

    private HashMap<Resource, Integer> discardList;
    private int nStone=0;
    private int nShield=0;
    private int nServant=0;
    private int nCoin=0;


    //BUTTONS

    public void discard_button(ActionEvent actionEvent) {
        if(nStone==0 && nCoin==0 && nServant==0 & nShield==0){
            PlayerBoardController.messages=setDialogPane("Select at least one resource!",PlayerBoardController.dialog,PlayerBoardController.messages);
        }
        else {
            PlayerBoardController.messages=setDialogPane("Resources discarded",PlayerBoardController.dialog,PlayerBoardController.messages);
            if (discardList == null) {
                discardList = new HashMap<>();
            }
            if (nCoin > 0) {
                discardList.put(Resource.Coins, nCoin);
            }
            if (nServant > 0) {
                discardList.put(Resource.Servants, nServant);
            }
            if (nShield > 0) {
                discardList.put(Resource.Shields, nShield);
            }
            if (nStone > 0) {
                discardList.put(Resource.Stones, nStone);
            }
           GUI.discardList=discardList;
            GUI.semaphoreRequest.release();
            closeStage(actionEvent);
        }
    }


    public void coin_click(MouseEvent mouseEvent) {
       nCoin++;
       coin_label.setText("x"+nCoin);
    }

    public void servant_click(MouseEvent mouseEvent) {
        nServant++;
        servant_label.setText("x"+nServant);
    }

    public void stone_click(MouseEvent mouseEvent) {
        nStone++;
        stone_label.setText("x"+nStone);
    }

    public void shield_click(MouseEvent mouseEvent) {
        nShield++;
        shield_label.setText("x"+nShield);
    }
}
