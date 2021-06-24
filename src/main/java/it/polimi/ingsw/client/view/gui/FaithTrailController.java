package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.model.BriefModel;
import it.polimi.ingsw.model.FaithTileStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class FaithTrailController extends StandardStage {
    @FXML
    GridPane faithtrail_grid;
    @FXML
    Label players_label;
    @FXML
    TextField checkout_text;

    public void setFaithTrail(LightFaithTrail faithTrail, String nickName) {
        ImageView[][] faithTrailMatrix = new ImageView[3][19];
        HashMap<String, Integer> playersPosition = faithTrail.getPlayersPosition();
        ArrayList<FaithTileStatus> tileStatuses = faithTrail.getTileStatuses();

        printPlayers(faithTrail.getPlayersPosition(),nickName);
        //Set Position into the corresponding cell in the grid.

        for (String string : playersPosition.keySet()) {
            boolean player = string.equals(nickName);
            setPlayerPosition(playersPosition.get(string), faithTrailMatrix, player);
        }

        for (int i = 0; i < 3; i++) {
            setTiles(tileStatuses.get(i), faithTrailMatrix, i);
        }
    }

    private void printPlayers(HashMap<String, Integer> playersPosition, String nickName) {
        String s="";
        for (String players: playersPosition.keySet()){
            if(!players.equals(nickName)){
                s=s+players+" ("+playersPosition.get(players)+")  ";
            }
        }
        players_label.setText(s);
    }

    private void setTiles(FaithTileStatus faithTileStatus, ImageView[][] faithTrailMatrix, int section) {
        int row;
        int col;
        if (section == 0) {
            row = 2;
            col = 4;
        } else if (section == 1) {
            row = 0;
            col = 9;
        } else {
            row = 2;
            col = 15;
        }


        if (faithTileStatus == FaithTileStatus.Discarded) {
            String tileDiscarded1 = "/images/punchboard/tileDiscarded1.png";
            String tileDiscarded2 = "/images/punchboard/tileDiscarded2.png";
            String tileDiscarded3 = "/images/punchboard/tileDiscarded3.png";
            switch (section) {
                case 0 -> setImageToMatrix(row, col, faithTrailMatrix, tileDiscarded1, 80, 80);
                case 1 -> setImageToMatrix(row, col, faithTrailMatrix, tileDiscarded2, 80, 80);
                case 2 -> setImageToMatrix(row, col, faithTrailMatrix, tileDiscarded3, 80, 80);
            }
        //Adding to GridPane
            faithtrail_grid.add(faithTrailMatrix[row][col], col, row);
        } else if (faithTileStatus == FaithTileStatus.Not_Reached) {
            //Void
        } else {
            String tileReached1 = "/images/punchboard/tileReached1.png";
            String tileReached2 = "/images/punchboard/tileReached2.png";
            String tileReached3 = "/images/punchboard/tileReached3.png";
            switch (section) {
                case 0 -> setImageToMatrix(row, col, faithTrailMatrix, tileReached1, 80, 80);
                case 1 -> setImageToMatrix(row, col, faithTrailMatrix, tileReached2, 80, 80);
                case 2 -> setImageToMatrix(row, col, faithTrailMatrix, tileReached3, 80, 80);
            }
            //Adding to GridPane
            faithtrail_grid.add(faithTrailMatrix[row][col], col, row);
        }
    }

    private void setPlayerPosition(int position, ImageView[][] faithtrailMatrix, boolean player) {
        int col;
        int row;
        switch (position) {
            case 0, 1, 2 -> {
                row = 2;
                col = position;
            }
            case 3 -> {
                row = 1;
                col = 2;
            }
            case 4, 5, 6, 7, 8, 9 -> {
                row = 0;
                col = position - 2;
            }
            case 10 -> {
                row = 1;
                col = 7;
            }
            case 11, 12, 13, 14, 15, 16 -> {
                row = 2;
                col = position - 4;
            }
            case 17 -> {
                row = 1;
                col = 12;
            }
            case 18, 19, 20, 21, 22, 23, 24 -> {
                row = 0;
                col = position - 6;
            }
            default -> throw new IllegalStateException("Unexpected value: " + position);
        }
        if (player) {
            String pathPlayer = "/images/punchboard/croce.png";
            setImageToMatrix(row, col, faithtrailMatrix, pathPlayer, 35, 35);
        } else {
            String pathEnemy = "/images/Resources/redcross.png";
            setImageToMatrix(row, col, faithtrailMatrix, pathEnemy, 35, 35);
        }
        faithtrail_grid.add(faithtrailMatrix[row][col], col, row);

    }

    public void checkout(ActionEvent actionEvent) {
        String nickName = checkout_text.getText();
        if (GUI.checkoutValidator(nickName)) {
            BriefModel briefModel = GUI.getClient().getModelByNickname(nickName);
            FXMLLoader fxmlLoader = load("/fxml/OpponentBoard.fxml");
            Scene secondScene = setScene(fxmlLoader);
            OpponentBoardController opponentBoardController = fxmlLoader.getController();
            opponentBoardController.setBriefModel(briefModel,nickName);
            showStage(secondScene);
        }
    }
}
