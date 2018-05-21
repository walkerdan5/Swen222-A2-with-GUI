package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by danielwalker on 2/08/17.
 */
public class Player implements BoardGame {

    public int playersID;

    public List<Token> currentPlayerTokens;

    public boolean topSword;
    public boolean topShield;
    public boolean topNothing;



    private char tokenID = 'a';

    /**
     * player1 represents green
     * player2 represents yellow
     * @param playerID
     */
    public Player(int playerID) {
        assert playerID == 1 || playerID == 2; // only two players in game. No other id should be allowed
        currentPlayerTokens = new ArrayList<Token>();
        playersID = playerID;
        loadTokens();
    }


    public char getTokenID(){
        return tokenID;
    }


    /**
     * Method used in sample.A1Board Class to draw the players respective ID on the board
     * @return
     */
    public char getPlayerID(){
        if(playersID == 2)
            return '2';
        else
            return '1';

    }


    public List<Token> getThisPlayersTokens(){
        return this.currentPlayerTokens;
    }


    public Token chooseToken(char ID){
        for (Token token : currentPlayerTokens) {
            if (token.tokenID == ID) return token;
        }
        return null;
    }


    /**
     * Each player is generated 24 tokens
     *
     */
    private void loadTokens() {
        while (currentPlayerTokens.size() <24) {
            generateToken();
        }
    }


    /**
     * Creates tokens for each player which consists of a random permutation of a combo of swords/shields/nothing
     * Assigns a random int between 1 and 3 for each side of the token
     */
    private void generateToken(){
        Token newToken = new Token(tokenID++, this);
        Random random = new Random();

        //generates a random number for each side for assigning weapon
        int top = random.nextInt(3) + 1;
        int left = random.nextInt(3) + 1;
        int bottom = random.nextInt(3) + 1;
        int right = random.nextInt(3) + 1;

        // add an option to each side of the new token
        tokenOptions("top", top, newToken);
        tokenOptions("left", left, newToken);
        tokenOptions("bottom", bottom, newToken);
        tokenOptions("right", right, newToken);

        currentPlayerTokens.add(newToken);



    }


    /**
     * Considers the three possible options for each token (Sword, Shield, Nothing)
     * Checks which side to assign the symbol
     * 1 for Sword, 2 for Shield, 3 for nothing
     */
    private void tokenOptions(String pos, int value, Token t){

        if(pos.equals("top")){
            char weapon = 'W';
            if(value == 1){
                weapon = '|';
            } else if (value == 2){
                weapon = '#';
            } else{
                weapon = ' ';
            }
            t.setTop(weapon);
        }

        if (pos.equals("left")){
            char weapon = 'W';
            if(value == 1){
                weapon = '-';
            } else if (value == 2){
                weapon = '#';
            } else{
                weapon = ' ';
            }
            t.setLeft(weapon);

        }

        if (pos.equals("bottom")){
            char weapon = 'W';
            if(value == 1){
                weapon = '|';
            } else if (value == 2){
                weapon = '#';
            } else{
                weapon = ' ';
            }
            t.setBottom(weapon);
        }

        if (pos.equals("right")){
            char weapon = 'W';
            if(value == 1){
                weapon = '-';
            } else if (value == 2){
                weapon = '#';
            } else{
                weapon = ' ';
            }
            t.setRight(weapon);
        }
    }



    /**
     * When a player places a token on the board, this will be called and
     * the token played will be removed from the players inventory
     * @param remove
     * @return
     */
    public boolean removePlayerToken(Token remove){
        return currentPlayerTokens.remove(remove);
    }
}
