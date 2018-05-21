package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;


public class MenuController {

    Scene playScene;
    GameController gameCon;
    public int mouseX = 0;
    public int mouseY = 0;
    private int greenID = 0;
    private int yellowID = 0;

    /**
     * Allows access to GameController methods
     */
    public MenuController() {
        gameCon = new GameController();
    }


    /**
     * Launches the boardgame scene once the "play game" button is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    public void playGameClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        loader.setController(gameCon);
        Parent root = loader.load();
        playScene = new Scene(root, 1200, 800);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.hide();
        gameStage.setScene(playScene);
        gameStage.setTitle("New Swords and Shields Game");
        gameStage.show();
        onClick();
        playScene.setOnKeyPressed(keyListener);
    }


    /**
     * Looking for input from a mouse click
     * Considers "current player" to know which player is currently clicking
     */
    public void onClick() {
        playScene.setOnMouseClicked(click -> {
            mouseX = (int) click.getSceneX();
            mouseY = (int) click.getSceneY();

            int col;
            int row;
            int boardRow;
            int boardCol;

            if (gameCon.currentPlayer == gameCon.player1) {
                System.out.println("Player 1's Turn");
                for (row = 110; row < 560; row += 60) {
                    for (col = 10; col < 280; col += 90) {
                        greenID += 1;
                        clickForGreenID(col, row, greenID, gameCon.greenTokGc);
                        gameCon.findGreenSelectedTokenOnBoard(greenID);
                    }
                }
            } else {
                System.out.println("Player 2's Turn");
                for (row = 110; row < 560; row += 60) {
                    for (col = 910; col < 1180; col += 90) {
                        yellowID += 1;
                        clickForYellowID(col, row, yellowID);
                        gameCon.findYellowSelectedTokenOnBoard(yellowID);
                    }
                }
            }

            if (gameCon.currentPlayer == gameCon.player1) {
                for (boardRow = 0; boardRow < 60; boardRow += 60) {
                    for (boardCol = 0; boardCol < 60; boardCol += 60) {
                        clickForMoveGreenID(boardCol,boardRow,greenID, gameCon.boardGc);
                    }
                }
            }
        });
    }


    /**
     * Looking for key input for when a player wants to move a token in a particular direction
     * Listening for either UP,LEFT,DOWN,RIGHT arrow keys
     */
    private EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.UP) {
                if(gameCon.currentPlayer == gameCon.player1) {
                    System.out.println("You made me go up");
                    gameCon.moveGreenTokenUp(greenID);
                } else if (gameCon.currentPlayer == gameCon.player2){
                    System.out.println("You made me go up");
                    gameCon.moveYellowTokenUp(yellowID);
                }
            }
            if (event.getCode() == KeyCode.DOWN) {
                if(gameCon.currentPlayer == gameCon.player1) {
                    System.out.println("You made me go down");
                    gameCon.moveGreenTokenDown(greenID);
                } else if (gameCon.currentPlayer == gameCon.player2){
                    System.out.println("You made me go down");
                    gameCon.moveYellowTokenDown(yellowID);
                }
            }
            if (event.getCode() == KeyCode.RIGHT) {
                if(gameCon.currentPlayer == gameCon.player1) {
                    System.out.println("You made me go right");
                    gameCon.moveGreenTokenRight(greenID);
                } else if (gameCon.currentPlayer == gameCon.player2){
                    System.out.println("You made me go right");
                    gameCon.moveYellowTokenRight(yellowID);
                }
            }
            if (event.getCode() == KeyCode.LEFT) {
                if(gameCon.currentPlayer == gameCon.player1) {
                    System.out.println("You made me go left");
                    gameCon.moveGreenTokenLeft(greenID);
                } else if (gameCon.currentPlayer == gameCon.player2){
                    System.out.println("You made me go left");
                    gameCon.moveYellowTokenLeft(yellowID);
                }
            }

            event.consume();
        }
    };


    /**
     * if a green player clicks, this determines which token was clicked on via mouse co-ords
     * @param col
     * @param row
     * @param id
     * @param graphics
     */
    public void clickForGreenID(int col, int row, int id, GraphicsContext graphics) {
        if (mouseX > col && mouseX < col + 90 && mouseY > row && mouseY < row + 60) {
            gameCon.drawGreenSelectedToken(col + 3, row + 3 - 100, gameCon.greenTokGc);
            System.out.println("You clicked on the Square with greenID: " + id);
            //System.out.println("TOKENCLICKEDID" + gameCon.greenTokenID);
        }
    }


    /**
     * if a green yellow clicks, this determines which token was clicked on via mouse co-ords
     * @param col
     * @param row
     * @param id
     * @param graphics
     */
    public void clickForYellowID(int col, int row, int id) {
        if (mouseX > col && mouseX < col + 90 && mouseY > row && mouseY < row + 60) {
            gameCon.drawYellowSelectedToken(col + 3 - 895, row + 3 - 100, gameCon.yellowTokGc);
            System.out.println("You clicked on the Square with yellowID: " + id);
        }
    }


    public void clickForMoveGreenID(int col, int row, int id, GraphicsContext graphics) {
        if (mouseX > col && mouseX < col + 60 && mouseY > row && mouseY < row + 60) {
            graphics.fillRect(col, row, 20,20);
        }
    }


    public void clickForMoveYellowID(int col, int row, int id) {
        if (mouseX > col && mouseX < col + 90 && mouseY > row && mouseY < row + 60) {
            gameCon.drawYellowSelectedToken(col + 3 - 895, row + 3 - 100, gameCon.yellowTokGc);
            System.out.println("You clicked on the Square with yellowID: " + id);
        }
    }


    /**
     * if the info button is clicked, move to INFO scene
     * @param event
     * @throws IOException
     */
    @FXML
    public void infoClicked(ActionEvent event) throws IOException {
        Parent playGameParent = FXMLLoader.load(getClass().getResource("info.fxml"));
        playScene = new Scene(playGameParent, 800, 500);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.hide();
        gameStage.setScene(playScene);
        gameStage.show();
    }


    /**
     * if quit button is clicked, close the application
     * @param event
     * @throws IOException
     */
    @FXML
    private void quitButtonAction(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
