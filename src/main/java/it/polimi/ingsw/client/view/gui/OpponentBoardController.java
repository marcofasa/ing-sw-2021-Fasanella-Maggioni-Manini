package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.BriefModel;
import it.polimi.ingsw.model.CardDevelopment;
import it.polimi.ingsw.model.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class OpponentBoardController extends StandardStage {


    @FXML
    GridPane opponent_StrongBox_grid;

    @FXML
    GridPane opponent_Deposit_grid;

    @FXML
    GridPane opponent_cardDevelop_grid;

    @FXML
    Label stone_label;
    @FXML
    Label coin_label;
    @FXML
    Label servant_label;
    @FXML
    Label shield_label;


    private ImageView[] cardDevelopmentArray;
    private ImageView[][] resourceMatrix;
    private Boolean[] depositLevel;
    private final Utils utils = new Utils();


    public void setBriefModel(BriefModel briefModel) {
        ArrayList<CardDevelopment> cardsDevelopment = briefModel.getCardsDevelopment();
        HashMap<Resource, Integer> deposit = briefModel.getDeposit();
        HashMap<Resource, Integer> strongbox = briefModel.getStrongBox();
        //Loading card development
        cardDevelopmentArray = new ImageView[3];
        for (int i = 1; i < 4; i++) {
            if (cardsDevelopment.size() >= i && cardsDevelopment.get(i - 1) != null) {
                Integer color = switch (cardsDevelopment.get(i - 1).getCardType()) {
                    case Green -> 0;
                    case Blue -> 2;
                    case Purple -> 1;
                    case Yellow -> 3;
                };
                //image final path
                String path = "/images/CardDevelopment/Card_Development_" + cardsDevelopment.get(i - 1).getVictoryPoints().toString() + "-" + color.toString() + ".jpg";
                setImageToArray(i - 1, path, cardDevelopmentArray, 80, 120);
            } else {
                // if there's no card on the deck (printing default image)
                String path = "/images/CardDevelopment/Card_Development_Empty.png";
                setImageToArray(i - 1, path, cardDevelopmentArray, 80, 120);
            }
            //Adding to GridPane
            opponent_cardDevelop_grid.add(cardDevelopmentArray[i - 1], i, 0);
        }


        //Loading Strongbox
        resourceMatrix = new ImageView[3][5];

        depositLevel = utils.setStrongboxLevel(depositLevel);
        for (Resource resource : deposit.keySet()) {
            if (deposit.get(resource) == 3) {
                loadStrongboxLevel(resource, resourceMatrix, 2, 1, 3, opponent_StrongBox_grid);
                depositLevel[2] = true;
            } else if (deposit.get(resource) == 2) {
                if (!depositLevel[1]) {
                    loadStrongboxLevel(resource, resourceMatrix, 1, 1, 2, opponent_StrongBox_grid);
                    depositLevel[1] = true;
                } else {
                    loadStrongboxLevel(resource, resourceMatrix, 2, 1, 2, opponent_StrongBox_grid);
                    depositLevel[2] = true;
                }
            } else if (deposit.get(resource) == 1) {
                if (!depositLevel[0]) {
                    loadStrongboxLevel(resource, resourceMatrix, 0, 2, 1, opponent_StrongBox_grid);
                    depositLevel[0] = true;
                } else if (!depositLevel[1]) {
                    loadStrongboxLevel(resource, resourceMatrix, 1, 1, 1, opponent_StrongBox_grid);
                    depositLevel[1] = true;
                } else {
                    loadStrongboxLevel(resource, resourceMatrix, 2, 1, 1, opponent_StrongBox_grid);
                    depositLevel[2] = true;
                }
            }
        }
        setDeposit(strongbox);
    }

    private void loadStrongboxLevel(Resource resource, ImageView[][] resourceMatrix, int row, int startingColumn, int nResources, GridPane gridPane) {
        String path = utils.getResourcePath(resource);
        while (nResources > 0) {
            setImageToMatrix(row, startingColumn, resourceMatrix, path, 20, 20, gridPane);
            startingColumn++;
            nResources--;
        }
    }

    public void setDeposit(HashMap<Resource, Integer> deposit) {
        ResourceHandler(deposit, coin_label, servant_label, shield_label, stone_label);
    }
}

