package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.model.FaithTileStatus;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

public class FaithTrailController extends StandardStage {
    @FXML
    GridPane faithtrail_grid;

    public void setFaithTrail(LightFaithTrail faithTrail,String nickName) {
        ImageView[][] faithTrailMatrix = new ImageView[3][19];
        HashMap<String,Integer> playersPosition=faithTrail.getPlayersPosition();
        ArrayList<FaithTileStatus> tileStatuses=faithTrail.getTileStatuses();

        //Set Position into the corresponding cell in the grid.

        for (String string : playersPosition.keySet()) {
            boolean player=false;
            if (string.equals(nickName)) {
              player=true;
            }
            setPlayerPosition(playersPosition.get(string), faithTrailMatrix, player);
        }

        for(int i=0; i<3; i++){
            setTiles(tileStatuses.get(i), faithTrailMatrix,i);
        }
    }

    private void setTiles(FaithTileStatus faithTileStatus, ImageView[][] faithTrailMatrix, int section) {
        int row;
        int col;
        if(section==0){
         row=2;
         col=4;
        }
        else if (section==1){
row=0;
col=9;
        }
        else {
row=2;
col=15;
        }


        if(faithTileStatus==FaithTileStatus.Discarded){
            String tileDiscarded1 = "/images/punchboard/tileDiscarded1.png";
            String tileDiscarded2 = "/images/punchboard/tileDiscarded2.png";
            String tileDiscarded3 = "/images/punchboard/tileDiscarded3.png";
            switch (section){
                case 0:
                    setImageToMatrix(row,col, faithTrailMatrix, tileDiscarded1,80,80);
break;
                case 1:
                    setImageToMatrix(row,col, faithTrailMatrix, tileDiscarded2,80,80);
                    break;
                case 2:
                    setImageToMatrix(row,col, faithTrailMatrix, tileDiscarded3,80,80);
                    break;

            }
//Adding to GridPane
            faithtrail_grid.add(faithTrailMatrix[row][col], col, row);
        }
        else if(faithTileStatus==FaithTileStatus.Not_Reached){
           //Void
        }
        else{
            String tileReached1 = "/images/punchboard/tileReached1.png";
            String tileReached2 = "/images/punchboard/tileReached2.png";
            String tileReached3 = "/images/punchboard/tileReached3.png";
            switch (section){
                case 0:
                    setImageToMatrix(row,col, faithTrailMatrix, tileReached1,80,80);
                    break;
                case 1:
                    setImageToMatrix(row,col, faithTrailMatrix, tileReached2,80,80);
                    break;
                case 2:
                    setImageToMatrix(row,col, faithTrailMatrix, tileReached3,80,80);
                    break;

            }
            //Adding to GridPane
            faithtrail_grid.add(faithTrailMatrix[row][col], col, row);
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
           String pathPlayer = "/images/Resources/redcross.png";
           setImageToMatrix(row,col,faithtrailMatrix, pathPlayer,35,35);
       }
       else {
           String pathEnemy = "/images/punchboard/croce.png";
           setImageToMatrix(row,col,faithtrailMatrix, pathEnemy,35,35);
       }
        faithtrail_grid.add(faithtrailMatrix[row][col], col, row);

    }

}
