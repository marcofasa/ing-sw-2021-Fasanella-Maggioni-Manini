package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.FaithTileStatus;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class FaithTrailController extends StandardScene{
    private String pathPlayer="/images/Resources/redcross.png";
    private String pathEnemy = "/images/punchboard/croce.png";
    private String tileDiscarded="/images/punchboard/quadrato rosso.png";
    private String tileNotReached="/images/punchboard/quadrato arancione.png";
    private String tileReached="/images/punchboard/quadrato giallo.png";
    @FXML
    GridPane faithtrail_grid;

    public void setFaithTrail(ArrayList<FaithTileStatus> tileStatuses, HashMap<String, Integer> playersPosition,String nickName) {
        ImageView[][] faithtrailMatrix = new ImageView[3][19];
        //Set Position into the corresponding cell in the grid.
        for (String string : playersPosition.keySet()) {
            boolean player=false;
            if (string.equals(nickName)) {
              player=true;
            }
            setPlayerPosition(playersPosition.get(string), faithtrailMatrix, player);
        }

        for(int i=0; i<3; i++){
            setTiles(tileStatuses.get(i),faithtrailMatrix,i);
        }
    }

    private void setTiles(FaithTileStatus faithTileStatus, ImageView[][] faithtrailMatrix,int section) {
        int row;
        int col;
        if(section==0){
         row=1;
         col=4;
        }
        else if (section==1){
row=0;
col=9;
        }
        else {
row=1;
col=15;
        }


        if(faithTileStatus==FaithTileStatus.Discarded){
            Image image = new Image(GUI.class.getResourceAsStream(tileDiscarded));

            faithtrailMatrix[row][col] = new ImageView(image);

            //Fitting Image
            faithtrailMatrix[row][col].setFitWidth(50);
            faithtrailMatrix[row][col].setFitHeight(50);


            //Adding to GridPane
            faithtrail_grid.add(faithtrailMatrix[row][col], col, row);
        }
        else if(faithTileStatus==FaithTileStatus.Not_Reached){
            Image image = new Image(GUI.class.getResourceAsStream(tileNotReached));

            faithtrailMatrix[row][col] = new ImageView(image);

            //Fitting Image
            faithtrailMatrix[row][col].setFitWidth(50);
            faithtrailMatrix[row][col].setFitHeight(50);


            //Adding to GridPane
            faithtrail_grid.add(faithtrailMatrix[row][col], col, row);
        }
        else{
            Image image = new Image(GUI.class.getResourceAsStream(tileReached));

            faithtrailMatrix[row][col] = new ImageView(image);

            //Fitting Image
            faithtrailMatrix[row][col].setFitWidth(50);
            faithtrailMatrix[row][col].setFitHeight(50);


            //Adding to GridPane
            faithtrail_grid.add(faithtrailMatrix[row][col], col, row);
        }
    }

    private void setPlayerPosition(int position,ImageView[][] faithtrailMatrix,boolean player){
        int col;
        int row;
        switch (position){
            case 0,1,2:
                row=2;
                col=position;
                break;
            case 3:
                row=1;
                col=2;
                break;
            case 4,5,6,7,8,9:
                row=0;
                col=position-2;
                break;
            case 10:
                row=1;
                col=7;
                break;
            case 11,12,13,14,15,16:
                row=2;
                col=position-5;
                break;
            case 17:
                row=1;
                col=12;
                break;
            case 18,19,20,21,22,23,24:
                row=0;
                col=position-6;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
       if(player){
           Image image = new Image(GUI.class.getResourceAsStream(pathPlayer));

           faithtrailMatrix[row][col] = new ImageView(image);

           //Fitting Image
           faithtrailMatrix[row][col].setFitWidth(10);
           faithtrailMatrix[row][col].setFitHeight(10);


           //Adding to GridPane
           faithtrail_grid.add(faithtrailMatrix[row][col], col, row);
       }
       else {
           Image image = new Image(GUI.class.getResourceAsStream(pathEnemy));

           faithtrailMatrix[row][col] = new ImageView(image);

           //Fitting Image
           faithtrailMatrix[row][col].setFitWidth(10);
           faithtrailMatrix[row][col].setFitHeight(10);


           //Adding to GridPane
           faithtrail_grid.add(faithtrailMatrix[row][col], col, row);

       }

    }
}
