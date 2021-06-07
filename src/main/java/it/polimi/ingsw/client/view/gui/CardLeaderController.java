package it.polimi.ingsw.client.view.gui;
import it.polimi.ingsw.model.CardLeader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CardLeaderController extends StandardStage {

    /*
    IMAGE FORMAT
             Card_Leader_a_b

             (a=type, b=color)

    Discount=1
    Deposit=2
    WhiteMarble=3
    Production=4

    shield=2
    coin=0
    stone=3
    servant=1
     */

    @FXML
    GridPane cardleader_grid;

    private CardLeader[] cardLeaders;
    private ArrayList<CardLeader> cardsLeaderArray;
    private ImageView[] cardLeaderArray;
    private int nRow=3;


    /**
     * Sets the card Leader images into the Grid
     * @param cardsLeaderArray arraylist of current player
     */
    public void setCardLeaderDeck(ArrayList<CardLeader> cardsLeaderArray) {
        this.cardsLeaderArray= cardsLeaderArray;
        cardLeaderArray = new ImageView[3];
        for (int i = 0; i < nRow; i++) {


        if(cardsLeaderArray.get(i)!=null){

            //Generation of image path
            Integer type;
            switch (cardsLeaderArray.get(i).getDescription()){
                case Deposit:
                    type =1;
                    break;
                case Discount:
                    type =0;
                    break;
                case Production:
                    type =3;
                    break;
                default:
                    type =2;
            }

            Integer color;
            switch (cardsLeaderArray.get(i).getResource()){
                case Coins:
                    color=0;
                    break;
                case Servants:
                    color=1;
                    break;
                case Shields:
                    color=2;
                    break;
                case Stones:
                    color=3;
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + cardsLeaderArray.get(i).getResource().toString());
            }

            String path="/images/CardLeader/Card_Leader_"+type.toString()+"-"+ color.toString()+".jpg";


           setImageToArray(i,path,cardLeaderArray,80,120);

            //Mouse Click Event
            int finalI = i;

            cardLeaderArray[i].setOnMouseClicked(mouseEvent -> {
                setClick(finalI);
            });

            //Adding to GridPane
        }
        else {
            //Standard path for empty slot

            String path="/images/CardDevelopment/Card_Development_Empty.png";
            setImageToArray(i,path,cardLeaderArray,80,120);

            //Mouse Click Event
            int finalI = i;

            cardLeaderArray[i].setOnMouseClicked(mouseEvent -> {
                setClick(finalI);
            });

            //Adding to GridPane
        }
            cardleader_grid.add(cardLeaderArray[i],0,i);
        }

        }

    private void setClick(int finalI) {
        if (cardLeaders==null){
            cardLeaders=new CardLeader[2];
        }
        if(cardLeaders[0]!=null){
            cardLeaders[0]=cardsLeaderArray.get(finalI);
        }
        else cardLeaders[1]=cardsLeaderArray.get(finalI);
    }

    //GETTERS

    public CardLeader[] getCardLeaders() {
        return cardLeaders;
    }



    //CLOSING BUTTONS

    public void discardCardLeader(ActionEvent actionEvent) {
        PlayerBoardController.messages= setDialogPane("Card Leader discarded",PlayerBoardController.dialog,PlayerBoardController.messages);
        closeStage(actionEvent);
    }

    public void activateCardLeader(ActionEvent actionEvent) {
        PlayerBoardController.messages= setDialogPane("Card Leader activated",PlayerBoardController.dialog,PlayerBoardController.messages);
        closeStage(actionEvent);
    }
}
