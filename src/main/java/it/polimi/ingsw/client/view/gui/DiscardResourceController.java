package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;

public class DiscardResourceController extends StandardStage {

    //FOR SELECTION
    @FXML
    Label stone_label;
    @FXML
    Label coin_label;
    @FXML
    Label servant_label;
    @FXML
    Label shield_label;

    //FOR VIEW ONLY
    @FXML
    Label stone;
    @FXML
    Label coin;
    @FXML
    Label servant;
    @FXML
    Label shield;

    private HashMap<Resource, Integer> discardList;
    private int nStone=0;
    private int nShield=0;
    private int nServant=0;
    private int nCoin=0;
    private Label getStone_label;
    private Label getShield_label;
    private Label getCoin_label;
    private Label getServant_label;


    //BUTTONS

    public void discard_button(ActionEvent actionEvent) {
        if(nStone==0 && nCoin==0 && nServant==0 & nShield==0){
            setDialogPane("Select at least one resource!",PlayerBoardController.dialog);
        }
        else {
            setDialogPane("Resources discarded",PlayerBoardController.dialog);
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

    public void setDiscardSelection(HashMap<Resource, Integer> discardChoice) {
        for(Resource resource: discardChoice.keySet()){
                switch (resource) {
                    case Coins :
                        coin.setText("x" + discardChoice.get(resource));
                         break;
                    case Servants :
                        servant.setText("x" + discardChoice.get(resource));
                         break;
                    case Shields :
                        shield.setText("x" + discardChoice.get(resource));
                        break;
                    case Stones :
                        stone.setText("x" + discardChoice.get(resource));
                        break;
                    }
                }
        }
    }
