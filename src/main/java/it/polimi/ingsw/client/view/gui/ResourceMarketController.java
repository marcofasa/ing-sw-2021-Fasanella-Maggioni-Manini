package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.communication.client.requests.RequestMarketUse;
import it.polimi.ingsw.model.MarbleType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class ResourceMarketController extends StandardStage {


    private int nRow = 3;
    private int nCol = 4;

    private String key = "row";
    private Integer message = 2;
    private boolean viewOnly=false;

    private ImageView[][] resourceMatrix;

    @FXML
    GridPane ResourceMarket_grid;

    public void setCol1(ActionEvent actionEvent) {
        key = "column";
        message = 2;
        sendSelection(actionEvent);
    }

    public void setCol0(ActionEvent actionEvent) {
        key = "column";
        message = 1;
        sendSelection(actionEvent);
    }

    public void setRow2(ActionEvent actionEvent) {
        key = "row";
        message = 3;
        sendSelection(actionEvent);
    }

    public void setCol2(ActionEvent actionEvent) {
        key = "column";
        message = 3;
        sendSelection(actionEvent);
    }

    public void ResourceMarketPurchase(ActionEvent actionEvent) {
        if (!viewOnly) {
            String s="Resources purchased at "+key+" "+message.toString();
            PlayerBoardController.messages= setDialogPane(s,PlayerBoardController.dialog,PlayerBoardController.messages);
            GUI.sendMessage(new RequestMarketUse(message, key));
        }
        closeStage(actionEvent);
    }

    public void setCol3(ActionEvent actionEvent) {
        key = "column";
        message = 4;
        sendSelection(actionEvent);
    }

    public void setRow1(ActionEvent actionEvent) {
        key = "row";
        message = 2;
        sendSelection(actionEvent);
    }

    public void setRow0(ActionEvent actionEvent) {
        key = "row";
        message = 1;
        sendSelection(actionEvent);
    }

    public void sendSelection(ActionEvent actionEvent) {
        System.out.println("Clicked " + key + " number " + message);
    }


    public void setResourceMarket(ArrayList<ArrayList<MarbleType>> resourceMarket) {
        resourceMatrix = new ImageView[3][4];
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                String color = switch (resourceMarket.get(i).get(j)) {
                    case MarbleGrey -> "grey";
                    case MarbleBlue -> "blue";
                    case MarblePurple -> "purple";
                    case MarbleRed -> "red";
                    case MarbleYellow -> "yellow";
                    case MarbleWhite -> "white";
                };


                String path = "/images/Marbles/Marble_" + color + ".png";

                setImageToMatrix(i,j,resourceMatrix,path,50,50);

                //Adding to GridPane
                ResourceMarket_grid.add(resourceMatrix[i][j], j, i);

            }

        }
    }

    public void setViewOnly() {
        viewOnly=true;
    }
}
