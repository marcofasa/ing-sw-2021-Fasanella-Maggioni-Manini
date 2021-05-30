package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.MarbleType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class ResourceMarketController extends StandardScene {


    private int nRow = 3;
    private int nCol = 4;

    private String key;
    private int message;

    private ImageView[][] resourceMatrix;

    @FXML
    GridPane ResourceMarket_grid;

    public void setCol1(ActionEvent actionEvent) {
        key = "column";
        message = 2;
        printClick(key, message);
    }

    public void setCol0(ActionEvent actionEvent) {
        key = "column";
        message = 1;
        printClick(key, message);
    }

    public void setRow2(ActionEvent actionEvent) {
        key = "row";
        message = 3;
        printClick(key, message);
    }

    public void setCol2(ActionEvent actionEvent) {
        key = "column";
        message = 3;
        printClick(key, message);
    }

    public void ResourceMarketPurchase(ActionEvent actionEvent) {
        //Send MarketChoice
    }

    public void setCol3(ActionEvent actionEvent) {
        key = "column";
        message = 4;
        printClick(key, message);
    }

    public void setRow1(ActionEvent actionEvent) {
        key = "row";
        message = 2;
        printClick(key, message);
    }

    public void setRow0(ActionEvent actionEvent) {
        key = "row";
        message = 1;
        printClick(key, message);
    }

    public void printClick(String s, int i) {
        System.out.println("Clicked " + s + " number " + i);
    }


    public void setResourceMarket(ArrayList<ArrayList<MarbleType>> resourceMarket) {
        resourceMatrix = new ImageView[3][4];
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                String color;

                switch (resourceMarket.get(i).get(j)) {
                    case MarbleGrey:
                        color = "grey";
                        break;
                    case MarbleBlue:
                        color = "blue";
                        break;
                    case MarblePurple:
                        color = "purple";
                        break;
                    case MarbleRed:
                        color = "red";
                        break;
                    case MarbleYellow:
                        color = "yellow";
                        break;
                    case MarbleWhite:
                        color = "white";
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value " + resourceMarket.get(i).get(j).toString());
                }


                String path = "/images/Marbles/Marble_" + color + ".png";


                Image image = new Image(GUI.class.getResourceAsStream(path));
                resourceMatrix[i][j] = new ImageView(image);

                //Fitting Image
                resourceMatrix[i][j].setFitWidth(50);
                resourceMatrix[i][j].setFitHeight(50);


                //Adding to GridPane
                ResourceMarket_grid.add(resourceMatrix[i][j], j, i);

            }

        }
    }
}
