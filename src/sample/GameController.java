package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sample.Board.TILE_SIZE;

/**
 * Created by danielwalker on 20/08/17.
 */
public class GameController {

    Scene playScene;
    private MenuController menuCon;
    private SwordsAndShields sas;

    private final int WIDTH = 90;
    private final int HEIGHT = 60;

    //token x y location on board
    public int createGreenX = -45 + WIDTH * 2;
    public int createGreenY = 15 + HEIGHT * 2;
    public int createYellowX = -15 + WIDTH * 5;
    public int createYellowY = 15 + (HEIGHT * 7);
    public int greenTokenID = 1;
    public int yellowTokenID = 0;

    public List listOfPlayerTokens = new ArrayList<GraphicsToken>();
    public Player player1, player2, currentPlayer;

    //for drawing the board
    private Group tileGroup = new Group();


    @FXML // fx:id = "boardCanvas"
    private Canvas boardCanvas;
    @FXML // fx:id = "greenTokenCanvas"
    private Canvas greenTokenCanvas;
    @FXML // fx:id = "yellowTokenCanvas"
    private Canvas yellowTokenCanvas;
    @FXML // fx:id = "cemeteryCanvas"
    private Canvas cemeteryCanvas;

    // the different areas to draw graphics in. Keeps co-ords simple
    public GraphicsContext boardGc;
    public GraphicsContext greenTokGc;
    public GraphicsContext yellowTokGc;
    public GraphicsContext cemeteryGc;


    @FXML
    void initialize() throws Exception {
        menuCon = new MenuController();
        boardGc = boardCanvas.getGraphicsContext2D();
        greenTokGc = greenTokenCanvas.getGraphicsContext2D();
        yellowTokGc = yellowTokenCanvas.getGraphicsContext2D();
        cemeteryGc = cemeteryCanvas.getGraphicsContext2D();

        drawGrTokensBorder(greenTokGc);
        drawYeTokensBorder(yellowTokGc);
        cemeteryBorder(cemeteryGc);

        placeGreenTokens(greenTokGc);
        placeYellowTokens(yellowTokGc);
        showBoard(boardGc);

        player1 = new Player(1);
        player2 = new Player(2);
        currentPlayer = player1;
    }


    /**
     * Method i made to enable the call of a border Rectangle (no solid fill)
     * @param weight
     * @param x
     * @param y
     * @param width
     * @param height
     * @param graphics
     */
    public void drawRect(int weight, int x, int y, int width, int height, GraphicsContext graphics) {
        graphics.fillRect(x, y, width, height);
        graphics.clearRect(x + weight, y + weight, width - weight * 2, height - weight * 2);
    }


    private void drawGrTokensBorder(GraphicsContext graphics) {
        graphics.setFill(Color.BLACK);
        drawRect(5, 10, 10, 270, 520, graphics);
        graphics.fillText("GREEN TOKENS", 130, 0);
        graphics.fillRect(100, 10, 1, 520);
        graphics.fillRect(190, 10, 1, 520);

        for (int i = 1; i < 480; i += 61) {
            graphics.fillRect(10, 10 + i, 270, 1);
        }
    }


    /**
     * Graphical effect to show a green token has been selected and now it is drawn in the creation square
     * @param x
     * @param y
     * @param graphics
     */
    public void drawGreenSelectedToken(int x, int y, GraphicsContext graphics){
        drawGrTokensBorder(graphics);
        //System.out.println("Token ____ selected \n");
        graphics.setFill(Color.RED);
        drawRect(3, x, y, WIDTH-10, HEIGHT, graphics);
        placeGreenTokens(graphics);
        graphics.setFill(Color.WHITE);
        graphics.fillRect(x+3, y+5, 74, 50);
    }


    /**
     * Graphical effect to show a yellow token has been selected and now it is drawn in the creation square
     * @param x
     * @param y
     * @param graphics
     */
    public void drawYellowSelectedToken(int x, int y, GraphicsContext graphics){
        drawYeTokensBorder(graphics);
        //System.out.println("Token ____ selected \n");
        graphics.setFill(Color.RED);
        drawRect(3, x, y, WIDTH-10, HEIGHT, graphics);
        placeYellowTokens(graphics);
    }


    /**
     * Draws all 24 of green players tokens on the board
     *
     * @param graphics
     */
    private void placeGreenTokens(GraphicsContext graphics) {
        int row = 60;
        int col = 90;

        for (int i = 20; i<480; i+=row){
            for (int j = 20; j<210; j+=col){
                drawAToken(j, i, graphics, Color.GREEN, greenTokenID);
                //System.out.println(graTok.getThisPlayersTokens()+ "Green token placed");
                System.out.println("Green token ID " + greenTokenID);
                greenTokenID++;
            }
        }
        greenTokenID = 1;
    }

    /**
     * Draws all 24 of yellow players tokens on the board
     *
     * @param graphics
     */
    private void placeYellowTokens(GraphicsContext graphics) {
        int row = 60;
        int col = 90;

        for (int i = 20; i<480; i+=row){
            for (int j = 20; j<210; j+=col){
                drawAToken(j, i, graphics, Color.YELLOW, yellowTokenID);
                System.out.println("Yellow token ID " + yellowTokenID);
                yellowTokenID++;
            }
        }
    }


    /**
     * Randomly allocates a weapon to each side of the token
     * @param x
     * @param y
     * @param graphics
     * @param col
     */
    public void drawAToken(int x, int y, GraphicsContext graphics, Color col, int tokenID) {
            Random random = new Random();

            int width = 45;
            int height = 45;

            //generates a random number for each side for assigning weapon
            int top = random.nextInt(3) + 1;
            int left = random.nextInt(3) + 1;
            int bottom = random.nextInt(3) + 1;
            int right = random.nextInt(3) + 1;

            GraphicsToken newGraphicsToken = new GraphicsToken(x, y, tokenID, top, left, bottom, right);

            graphics.setFill(Color.BLACK);
            graphics.fillRect(x, y, WIDTH - 40, HEIGHT - 8);
            graphics.setFill(col);
            graphics.fillOval(x + 10, y + 10, width - 10, height - 10);

            // add an option to each side of the new token
            graphicsTokenOptions(newGraphicsToken, "top", top, graphics);
            graphicsTokenOptions(newGraphicsToken, "left", left, graphics);
            graphicsTokenOptions(newGraphicsToken, "bottom", bottom, graphics);
            graphicsTokenOptions(newGraphicsToken, "right", right, graphics);

            listOfPlayerTokens.add(newGraphicsToken);
        }


    public void getThisPlayersTokens(){
        System.out.println(listOfPlayerTokens);
    }

    /**
     * For deciding which weapon to allocate
     * @param token
     * @param pos
     * @param value
     * @param graphics
     */
    private void graphicsTokenOptions(GraphicsToken token, String pos, int value, GraphicsContext graphics) {
        int width = 45;
        int height = 45;

        if (pos.equals("top")) {
            if (value == 1) { //draw sword
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x + (width / 2)+5, token.y, 5, (height / 2)+10);
        } else if (value == 2) { //draw shield
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x, token.y, width+5, 5);
            } else {
                //do nothing
            }

        } if (pos.equals("left")) {
            if (value == 1) { //draw sword
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x, token.y+(width/2)+5, width/2+5, 5);
            } else if (value == 2) { //draw shield
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x, token.y, 5, height+5);
            }  else {
                //do nothing
            }

        } if (pos.equals("bottom")) {
            if (value == 1) { //draw sword
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x + (width / 2)+5, token.y + (height / 2)+5, 5, height / 2);
            } else if (value == 2) { //draw shield
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x, token.y + height, width+5, 5);
            } else {
                //DO NOTHING
            }

        }  if (pos.equals("right")) {
            if (value == 1) { //draw sword
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x + (width / 2)+5, token.y + (width / 2)+5, width / 2, 5);
            } else if (value == 2) { //draw shield
                graphics.setFill(Color.BLUE);
                graphics.fillRect(token.x + width, token.y, 5, height+5);
            } else {
                //draw nothing
            }
        }
    }


    private void drawYeTokensBorder(GraphicsContext graphics) {
        graphics.setFill(Color.BLACK);
        drawRect(5, 15, 10, 270, 520, graphics);
        graphics.fillText("YELLOW TOKENS", 50, 0);
        graphics.fillRect(100, 10, 1, 520);
        graphics.fillRect(190, 10, 1, 520);

        for (int i = 1; i < 480; i += 61) {
            graphics.fillRect(15, 10 + i, 270, 1);
        }
    }


    private void cemeteryBorder(GraphicsContext graphics) {
        graphics.setFill(Color.BLACK);
        drawRect(3, 0, 0, 1131, 121, graphics);
        graphics.fillRect(0, 520, 600, 130);
        graphics.clearRect(5, 525, 590, 70);
        graphics.fillText("CEMETERY", 270, 0);


    }


    public void showBoard(GraphicsContext graphics) throws Exception {
        createContent(graphics);
    }


    private Parent createContent(GraphicsContext graphics) {
        for (int y = 0; y < TILE_SIZE; y++) {
            for (int x = 0; x < TILE_SIZE; x++) {
                Tile tile = new Tile(graphics, (x + y) % 2 == 0, x, y);
                tileGroup.getChildren().add(tile);
                graphics.fillRect(x * Board.WIDTH + 10, y * HEIGHT + 10, Board.WIDTH, HEIGHT);
            }
        }
        return tileGroup;
    }


    public void findGreenSelectedTokenOnBoard(int ID) {
        drawGreenSelectedTokenOnBoard(ID, boardGc);
    }


    public void findYellowSelectedTokenOnBoard(int ID) {
        drawYellowSelectedTokenOnBoard(ID, boardGc);
    }


    public void drawGreenSelectedTokenOnBoard(int ID, GraphicsContext graphics){
        drawAToken(createGreenX, createGreenY, graphics, Color.GREEN, ID);

    }


    public void drawYellowSelectedTokenOnBoard(int ID, GraphicsContext graphics){
        drawAToken( createYellowX, createYellowY, graphics, Color.YELLOW, ID);
    }


    public void moveGreenTokenUp(int ID){
        moveGreenUp(ID,boardGc);
    }


    public void moveGreenTokenLeft(int ID) {
        moveGreenLeft(ID, boardGc);
    }


    public void moveGreenTokenDown(int ID) {
        moveGreenDown(ID, boardGc);
    }


    public void moveGreenTokenRight(int ID) {
        moveGreenRight(ID, boardGc);
    }


    public void moveYellowTokenUp(int ID){
        moveYellowUp(ID,boardGc);
    }


    public void moveYellowTokenLeft(int ID) {
        moveYellowLeft(ID, boardGc);
    }


    public void moveYellowTokenDown(int ID) {
        moveYellowDown(ID, boardGc);
    }


    public void moveYellowTokenRight(int ID) {
        moveYellowRight(ID, boardGc);
    }


    public void moveYellowSelectedTokenOnBoard(int ID) {
        drawYellowSelectedTokenOnBoard(ID, boardGc);
    }


    public void moveGreenRight(int ID, GraphicsContext graphics){
        try {
            showBoard(boardGc);
            drawYellowSelectedTokenOnBoard(yellowTokenID, boardGc);

        }catch (Exception e){}

        drawAToken(createGreenX+60, createGreenY, graphics, Color.GREEN, ID);
        createGreenX = createGreenX+60;
    }


    public void moveGreenLeft(int ID, GraphicsContext graphics){
        try {
            showBoard(boardGc);
            drawYellowSelectedTokenOnBoard(yellowTokenID, boardGc);

        }catch (Exception e){}

        drawAToken(createGreenX-60, createGreenY, graphics, Color.GREEN, ID);
        createGreenX = createGreenX-60;
    }


    public void moveGreenUp(int ID, GraphicsContext graphics){

        try {
            showBoard(boardGc);
            drawYellowSelectedTokenOnBoard(yellowTokenID, boardGc);

        }catch (Exception e){}

        drawAToken(createGreenX, createGreenY-60, graphics, Color.GREEN, ID);
        createGreenY = createGreenY-60;
    }


    public void moveGreenDown(int ID, GraphicsContext graphics){
        try {
            showBoard(boardGc);
            drawYellowSelectedTokenOnBoard(yellowTokenID, boardGc);

        }catch (Exception e){}

        drawAToken(createGreenX, createGreenY+60, graphics, Color.GREEN, ID);
        createGreenY = createGreenY+60;
    }


    public void moveYellowRight(int ID, GraphicsContext graphics){
        try {
            showBoard(boardGc);
            drawGreenSelectedTokenOnBoard(greenTokenID, boardGc);

        }catch (Exception e){}

        drawAToken(createYellowX+60, createYellowY, graphics, Color.YELLOW, ID);
        createYellowX = createYellowX+60;
    }


    public void moveYellowLeft(int ID, GraphicsContext graphics){
        try {
            showBoard(boardGc);
            drawGreenSelectedTokenOnBoard(greenTokenID, boardGc);

        }catch (Exception e){}

        drawAToken(createYellowX-60, createYellowY, graphics, Color.YELLOW, ID);
        createYellowX = createYellowX-60;
    }


    public void moveYellowUp(int ID, GraphicsContext graphics){

        try {
            showBoard(boardGc);
            drawGreenSelectedTokenOnBoard(greenTokenID, boardGc);
        }catch (Exception e){}

        drawAToken(createYellowX, createYellowY-60, graphics, Color.YELLOW, ID);
        createYellowY = createYellowY-60;
    }


    public void moveYellowDown(int ID, GraphicsContext graphics){
        try {
            showBoard(boardGc);
            drawGreenSelectedTokenOnBoard(greenTokenID, boardGc);
        }catch (Exception e){}

        drawAToken(createYellowX, createYellowY+60, graphics, Color.YELLOW, ID);
        createYellowY = createYellowY+60;
    }


    @FXML
    private void pass(){
        if (currentPlayer==player1) {
            currentPlayer = player2;
        }
        else {
            currentPlayer = player1;
        }
        System.out.println("Current player is now player " + currentPlayer.getPlayerID());
        drawYeTokensBorder(yellowTokGc);
        drawGrTokensBorder(greenTokGc);
        placeYellowTokens(yellowTokGc);
        placeGreenTokens(greenTokGc);
        drawYellowSelectedTokenOnBoard(yellowTokenID, boardGc);
        drawGreenSelectedTokenOnBoard(greenTokenID, boardGc);
    }


    @FXML
    private void quitGameAction(ActionEvent event) throws IOException {
        // get a handle to the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // do what you have to do
        stage.close();
    }


    @FXML
    public void menuClicked(ActionEvent event) throws IOException {
        Parent playGameParent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        playScene = new Scene(playGameParent, 800, 500);
        Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        gameStage.hide();
        gameStage.setScene(playScene);
        gameStage.show();
    }


    @FXML
    public void surrenderP1Clicked (ActionEvent event) throws IOException {
        if (currentPlayer == player1) {
            Parent playGameParent = FXMLLoader.load(getClass().getResource("surrenderP1.fxml"));
            playScene = new Scene(playGameParent, 800, 500);
            Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            gameStage.hide();
            gameStage.setScene(playScene);
            gameStage.show();
        } else {
            Parent playGameParent = FXMLLoader.load(getClass().getResource("surrenderP2.fxml"));
            playScene = new Scene(playGameParent, 800, 500);
            Stage gameStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            gameStage.hide();
            gameStage.setScene(playScene);
            gameStage.show();
        }
    }


}
