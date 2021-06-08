package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.FaithTileStatus;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class FaithTrailController extends StandardScene{
    private String path = "/images/punchboard/croce.png";
    @FXML
    GridPane faithtrail_grid;

    public void setFaithTrail(ArrayList<FaithTileStatus> tileStatuses, HashMap<String, Integer> playersPosition){
        ImageView[][] faithtrailMatrix=new ImageView[3][19];
        //Set Position into the corresponding cell in the grid.


/*
        Image image = new Image(GUI.class.getResourceAsStream(path));
        faithtrailMatrix[i][j] = new ImageView(image);

        //Fitting Image
        faithtrailMatrix[i][j].setFitWidth(50);
        faithtrailMatrix[i][j].setFitHeight(50);


        //Adding to GridPane
        ResourceMarket_grid.add(faithtrailMatrix[i][j], j, i);

 */
    }
}
