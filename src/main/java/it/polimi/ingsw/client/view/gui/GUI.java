package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ConnectionInfo;
import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.client.view.ViewInterface;
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

    private Client client;
    private Stage primaryStage;
    private FXMLLoader fxmlLoader;
    private Scene scene;
    private LightFaithTrail lightFaithTrail;
    private LogInController logInController;
    private static ConnectionInfo connectionInfo;
    private Semaphore semaphore = new Semaphore(0);

    private Stage Scene(String fxmlPath) {
        setupStage(fxmlPath);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        return stage;
    }

    private void mainScene(String fxmlPath) {
        setupStage(fxmlPath);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
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
        //Parent loader = FXMLLoader.load(getClass().getResource("/fxml/LogIn.fxml"));
        this.primaryStage = stage;


        /*primaryStage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });

         */

        displayWelcome();
        //showScene();

        //TESTS
        /*ArrayList<CardLeader> cardLeaders = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CardLeader cardleaderWhite = new CardLeaderWhiteMarble(Resource.Coins, CardLeaderRequirementsFinder.getRequirements(CardLeaderType.WhiteMarble, Resource.Coins), CardLeaderRequirementsFinder.getVictoryPoints(CardLeaderType.WhiteMarble));
            cardLeaders.add(cardleaderWhite);
        }

        */

        //Card Development Market
        //displayCardDevelopmentMarket();

        //Resource Market
        //displayResourceMarket();

        //Initial Selection
       /*
        ArrayList<CardLeader> list=askForLeaderCardSelection(cardLeaders);
         int m=list.size();
        ArrayList<Resource> resources=askForInitialResourcesSelection(2);
        int i= resources.size();

        */

        //displayTurn
        //displayTurn("io",GamePhase.Initial);

    }

    @Override
    public LightModel getLightModel() {
        return client.getLightModel();
    }

    @Override
    public LightFaithTrail getLightFaithTrail() {
        return lightFaithTrail;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
        lightFaithTrail = new LightFaithTrail(client);
    }

    /*
        public ConnectionInfo displayWelcome() {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        boolean invalid = true;
        while (invalid) {
            Stage stage = Scene("/fxml/LogIn.fxml");
            LogInController logInController = fxmlLoader.getController();
            stage.showAndWait();
            try {
                connectionInfo.setNickname(logInController.getUser_field());
                connectionInfo.setPort(Integer.parseInt(logInController.getPort_field()));
                connectionInfo.setIP(logInController.getIp_field());
                invalid = false;
            } catch (IllegalNicknameException | IllegalAddressException | IllegalPortException ignore) {
            }
        }
        return connectionInfo;
     */

    public void displayWelcome() {
        Platform.runLater(()->{
            mainScene("/fxml/LogIn.fxml");
            LogInController logInController=fxmlLoader.getController();
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

    }

    @Override
    public void displayTimeOut() {

    }

    @Override
    public void displayResourceMarket() {
        //Test Market
        ArrayList<ArrayList<MarbleType>> market = new ArrayList<ArrayList<MarbleType>>();

        ArrayList<MarbleType> row0 = new ArrayList<>();
        row0.add(MarbleType.MarbleWhite);
        row0.add(MarbleType.MarbleBlue);
        row0.add(MarbleType.MarbleRed);
        row0.add(MarbleType.MarblePurple);

        ArrayList<MarbleType> row1 = new ArrayList<>();
        row1.add(MarbleType.MarbleRed);
        row1.add(MarbleType.MarbleRed);
        row1.add(MarbleType.MarblePurple);
        row1.add(MarbleType.MarbleBlue);

        ArrayList<MarbleType> row2 = new ArrayList<>();
        row2.add(MarbleType.MarbleBlue);
        row2.add(MarbleType.MarbleWhite);
        row2.add(MarbleType.MarblePurple);
        row2.add(MarbleType.MarbleBlue);

        market.add(row0);
        market.add(row1);
        market.add(row2);


        //Loading Scene
        mainScene("/fxml/ResourceMarket.fxml");


        ResourceMarketController resourceMarketController = fxmlLoader.getController();
        resourceMarketController.setResourceMarket(market);

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

    }

    @Override
    public void displayWin() {

    }

    @Override
    public void displayLost() {

    }

    @Override
    public void displaySuccess() {

    }

    @Override
    public void displayLeaderRequirementsNotMet() {

    }

    @Override
    public void displayTurn(String currentPlayer, GamePhase gamePhase) {

        mainScene("/fxml/PlayerBoard.fxml");
        PlayerBoardController playerBoardController = fxmlLoader.getController();
        playerBoardController.setModels(getLightModel(), getLightFaithTrail(), gamePhase);

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
        return 0;
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
        Stage stage = Scene("/fxml/InitialSelection.fxml");
        InitialSelectionController initialSelectionController = fxmlLoader.getController();
        initialSelectionController.setPlayerNumber(playerNumber);
        stage.showAndWait();

        return initialSelectionController.getResourceSelection();
    }

    @Override
    public ArrayList<CardLeader> askForLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {

        Stage stage = Scene("/fxml/InitialSelection.fxml");
        InitialSelectionController initialSelectionController = fxmlLoader.getController();
        initialSelectionController.setCardLeaderDeck(cardLeaders);

        stage.showAndWait();

        return initialSelectionController.getCardLeaderSelection();

    }

    @Override
    public HashMap<Resource, Integer> askForResourceToDiscard(HashMap<Resource, Integer> choice) {
        return null;
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
        displayWelcome();

        return connectionInfo;
    }

    public static void setConnectionInfo(ConnectionInfo connectionInfo) {
        System.out.println("setto " + connectionInfo);
        GUI.connectionInfo = connectionInfo;
    }
}
