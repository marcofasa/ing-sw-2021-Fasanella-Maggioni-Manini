package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ConnectionInfo;
import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.requests.RequestActivateProduction;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class GUI extends Application implements ViewInterface {

    public static Semaphore semaphoreRequest = new Semaphore(0);
    private static Client client;
    private static LightFaithTrail lightFaithTrail;
    private final Utils utils = new Utils();
    public static Stage primaryStage;
    public static FXMLLoader fxmlLoader;
    public static Scene scene;
    private LogInController logInController;
    private static ConnectionInfo connectionInfo;
    private static int playerNumber;
    public static ArrayList<CardLeader> cardLeaderList;
    public static ArrayList<Resource> resourceList;
    public ArrayList<String> messages = new ArrayList<>();
    public static HashMap<Resource, Integer> discardList;

    public static void setPlayerNumber(int i) {
        playerNumber=i;
    }

    public static void sendMessage(ClientMessage clientMessage) {
        client.send(clientMessage);
    }


    private Stage Scene(String fxmlPath) {
        setupStage(fxmlPath);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        primaryStage=stage;
        return stage;
    }

    private void mainScene(String fxmlPath) {
        Platform.runLater(() -> {
            setupStage(fxmlPath);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });
    }

    private void setupStage(String fxmlPath) {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxmlPath));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            scene = new Scene(new Label("Error during FXML Loading"));
        }
        ((StandardScene) fxmlLoader.getController()).init();
    }

    public void show(Stage stage) {
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = Scene("/fxml/Logo.fxml");
        primaryStage.showAndWait();

    }

    @Override
    public LightModel getLightModel() {
        return client.getLightModel();
    }

    @Override
    public LightFaithTrail getLightFaithTrail() {
        return lightFaithTrail;
    }


    public void setClient(Client client) {
        GUI.client = client;
        GUI.lightFaithTrail = new LightFaithTrail(client);
    }


    public void displayWelcome() {
        Platform.runLater(() -> {
            mainScene("/fxml/LogIn.fxml");
        });
    }

    /**
     * Runs the specified {@link Runnable} on the
     * JavaFX application thread and waits for completion.
     *
     * @param action the {@link Runnable} to run
     * @throws NullPointerException if {@code action} is {@code null}
     */
    public static void runAndWait(Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        // run synchronously on JavaFX thread
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        // queue on JavaFX thread and wait for completion
        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            // ignore exception
        }
    }

    @Override
    public void displayStartingGame() {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void displayPosition() {
        Platform.runLater(() -> {
            mainScene("/fxml/FaithTrail.fxml");
        });
    }

    @Override
    public void displayTimeOut() {
    }

    @Override
    public void displayResourceMarket() {

        //Loading Scene
        mainScene("/fxml/ResourceMarket.fxml");

Platform.runLater(()->{
    ResourceMarketController resourceMarketController = fxmlLoader.getController();
    resourceMarketController.setResourceMarket(getLightModel().getMarket());
});


    }

    @Override
    public void displayStrongBox() {

    }

    @Override
    public void displayNotActivePlayerError() {

    }

    @Override
    public void displayNotEnoughResource() {

    }

    @Override
    public void displayDisconnection() {
        mainScene("/fxml/CardDevelopmentSelection.fxml");
    }

    @Override
    public void displayConnection() {
        this.primaryStage = Scene("/fxml/Logo.fxml");
        //primaryStage.showAndWait();
    }

    @Override
    public void displayWin() {

    }

    @Override
    public void displayLost() {

    }

    @Override
    public void displaySuccess() {
        System.out.println("Action executed successfully");
        messages.add("Action executed successfully");
    }

    @Override
    public void displayLeaderRequirementsNotMet() {

    }

    @Override
    public void displayTurn(String currentPlayer, GamePhase gamePhase) {
        mainScene("/fxml/PlayerBoard.fxml");
        Platform.runLater(() -> {
            PlayerBoardController playerBoardController = fxmlLoader.getController();
            playerBoardController.setModels(getLightModel(), getLightFaithTrail(), gamePhase);
        });
    }

    @Override
    public void displayWaitingOpponent(String currentPlayer) {

    }

    @Override
    public void displayDeposit() {

    }

    @Override
    public void displayCardLeader() {

    }

    @Override
    public void displayCardDevelopment() {

    }

    @Override
    public String askNickName() {
        return null;
    }

    @Override
    public int askPlayerNumber() {

        Platform.runLater(()->{
            LogInController logInController= fxmlLoader.getController();
            logInController.askPlayerNumber();

        });
        try {
            GUI.semaphoreRequest.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        /*
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/fxml/PlayerNumber.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);
   GenericRequestController genericRequestController=loader.getController();
        newWindow.showAndWait();

         */
      return   playerNumber;
    }

    @Override
    public void askMarketChoice() {

    }

    @Override
    public void askDevelopmentCardChoice() {

    }

    @Override
    public void askProductionActivation() {
        Stage stage = Scene("/fxml/Production.fxml");
        ProductionController productionController = fxmlLoader.getController();
        productionController.setProduction(getLightModel().getCardsDevelopment(), getLightModel().getCardsLeader());
        stage.showAndWait();


        //Sends Request to Client
        client.send(new RequestActivateProduction(productionController.getProductionSelection()));
        //
    }

    @Override
    public void askCardLeaderActivation() {

    }

    @Override
    public void askEndTurn() {

    }

    @Override
    public ArrayList<Resource> askForInitialResourcesSelection(int playerNumber) {
        //Remove
        Platform.runLater(()->{
            InitialSelectionController initialSelectionController = fxmlLoader.getController();
            initialSelectionController.setPlayerNumber(playerNumber);
        });

        try {
            semaphoreRequest.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        utils.fixResourceSelection(resourceList);
        return resourceList;
    }

    @Override
    public ArrayList<CardLeader> askForLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        mainScene("/fxml/InitialSelection.fxml");
        Platform.runLater(()->{
            InitialSelectionController initialSelectionController = fxmlLoader.getController();
            initialSelectionController.setCardLeaderDeck(cardLeaders);
        });

        try {
            semaphoreRequest.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return cardLeaderList ;

    }

    @Override
    public HashMap<Resource, Integer> askForResourceToDiscard(HashMap<Resource, Integer> choice) {
        Platform.runLater(()->{
            PlayerBoardController playerBoardController=fxmlLoader.getController();
            playerBoardController.setDiscardRequest();
        });

        if (discardList==null){
            discardList=new HashMap<>();
        }
        else {
            discardList.clear();
        }

        try {
            semaphoreRequest.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return discardList;
    }

    @Override
    public void displayWaiting(int timeoutInSeconds) {

    }

    @Override
    public void askCardLeaderDiscard() {

    }

    @Override
    public void displayCardDevelopmentMarket() {

        //cardDevelop Market Array
        ArrayList<ArrayList<CardDevelopment>> cardDevelopments = new ArrayList<ArrayList<CardDevelopment>>();
        for (int i = 0; i < 3; i++) {
            cardDevelopments.add(new ArrayList<CardDevelopment>());
            for (int j = 0; j < 4; j++) {
                cardDevelopments.get(i).add(new CardDevelopment(i, j, 1));
            }
        }

        //Personal Card test
        ArrayList<CardDevelopment> card = new ArrayList<CardDevelopment>();
        for (int i = 0; i < 3; i++) {
            card.add(null);
        }


        mainScene("/fxml/CardDevelopmentMarket.fxml");


        CardDevelopmentMarketController cardDevelopmentMarketController = fxmlLoader.getController();

        //REAL LINE
        //cardDevelopmentMarketController.setDevelopmentMarket(client.getLightModel().getCardDevelopmentMarket());

        //TEST LINE
        cardDevelopmentMarketController.setDevelopmentMarket(cardDevelopments, card);


    }

    @Override
    public void displayStartingEndGame(String payload) {

    }

    @Override
    public void displayScoreBoard(HashMap<String, Integer> showScoreBoard) {

    }

    @Override
    public void displayMainMoveAlreadyMade() {

    }

    @Override
    public void displayConnectionError() {

    }

    @Override
    public void displayTimeoutError() {

    }

    @Override
    public void displayLorenzoActivation(ActionCardEnum actionCardType) {

    }

    @Override
    public void displayInvalidPlacementSelection() {

    }

    @Override
    public ConnectionInfo getConnectionInfo() {
        System.out.println("leggo " + connectionInfo);
       // displayWelcome();

        return connectionInfo;
    }

    @Override
    public void displayNickNameUnavailable() {
        LogInController logInController=fxmlLoader.getController();
        Platform.runLater(()-> {
            logInController.status_label.setText("STATUS: NickName Unavailable");
        });
    }

    @Override
    public void displayServerUnreachable() {
        LogInController logInController=fxmlLoader.getController();
        Platform.runLater(()-> {
            logInController.status_label.setText("STATUS: Server Unreachable");
        });
    }

    @Override
    public void gameHasStarted() {
        LogInController logInController=fxmlLoader.getController();
        Platform.runLater(()-> {
            logInController.status_label.setText("STATUS: Game Started, hold on...");
        });
    }

    @Override
    public void displayClientAccepted() {
        System.out.println("Connected to server");
        LogInController logInController=fxmlLoader.getController();
        Platform.runLater(()-> {
            logInController.status_label.setText("STATUS: Server connected, waiting players");
        });
    }

    @Override
    public void notifyDisconnectionOf(String nickname) {

    }

    @Override
    public void notifyReconnection(String nickname) {

    }


    public static void setConnectionInfo(ConnectionInfo connectionInfo) {
        System.out.println("setto " + connectionInfo);
        GUI.connectionInfo = connectionInfo;
    }
}
