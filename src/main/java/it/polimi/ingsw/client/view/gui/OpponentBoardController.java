package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.BriefModel;
import it.polimi.ingsw.model.CardDevelopment;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class OpponentBoardController extends StandardStage {

    @FXML
    Label nickName_label;
    @FXML
    GridPane opponent_Deposit_grid;
    @FXML
    GridPane opponent_cardDevelop_grid;
    @FXML
    GridPane cardLeader_array;
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
    private ImageView[] cardLeaderArray;

    /**
     * Sets all components of Player (checkout)
     * @param briefModel
     * @param nickName of player to checkout
     */
    public void setBriefModel(BriefModel briefModel,String nickName) {
        ArrayList<CardDevelopment> cardsDevelopment = briefModel.getCardsDevelopment();
        HashMap<Resource, Integer> deposit = briefModel.getDeposit();
        HashMap<Resource, Integer> strongbox = briefModel.getStrongBox();
        ArrayList<CardLeader> cardLeaders= briefModel.getVisibleCardsLeaders();
        nickName_label.setText(nickName);

        //Loading Card development and Card Leader
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
                String path = "/images/CardDevelopment/Card_Development_" + cardsDevelopment.get(i - 1).getVictoryPoints().toString() + "-" + color + ".jpg";
                setImageToArray(i - 1, path, cardDevelopmentArray, 45, 60);
            } else {
                // if there's no card on the deck (printing default image)
                String path = "/images/CardDevelopment/Card_Development_Empty.png";
                setImageToArray(i - 1, path, cardDevelopmentArray, 45, 60);
            }
            //Adding to GridPane
            opponent_cardDevelop_grid.add(cardDevelopmentArray[i - 1], i, 0);
        }

        setCardLeaderDeck(cardLeaders,cardLeaderArray,cardLeader_array);


        //Loading Strongbox and Deposit
        resourceMatrix = new ImageView[3][5];
        depositLevel= utils.initializeDepositLevel(depositLevel);
        loadDepositLevels(depositLevel,deposit,opponent_Deposit_grid,resourceMatrix);
        resourceHandler(strongbox,coin_label,servant_label,shield_label,stone_label);

    }
}

