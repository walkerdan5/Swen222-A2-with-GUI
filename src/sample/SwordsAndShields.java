package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by danielwalker on 8/08/17.
 */
public class SwordsAndShields {

    private Player player1, player2, currentPlayer;
    private A1Board board;
    private Token token;


    public SwordsAndShields(){
        player1 = new Player(1);
        player2 = new Player(2);
        currentPlayer = player1;
        board = new A1Board(player1, player2);
    }


    /**
     * This method can be used for testing purposes
     * I used it in the earlier stages of the program to make sure the tokens were working correctly
     * @param currentPlayer
     */
    public void printPlayerTokenIDs(Player currentPlayer) {
        int count = 1; //for keeping track of number of tokens

        if (currentPlayer.getPlayerID() == '1') {
            System.out.println("----------------" +
                    "\nGreen (Player 1's) Tokens: ");
            for (Token token : currentPlayer.getThisPlayersTokens()) {
                System.out.println(count++ + ". " + token.toString());
            }
        }
        else {
            System.out.println();
            System.out.println("----------------" +
                    "\nYellow (Player 2's) Tokens: ");
            for (Token token : currentPlayer.getThisPlayersTokens()) {
                System.out.println(count++ + ". " + token.toString());
            }
        }
    }

    /**
     * Who ever is now playing, show their tokens so they can choose one
     * @param currentPlayer
     */
    public void printPlayerTokens(Player currentPlayer) {

        if (currentPlayer.getPlayerID() == '1') {
            System.out.println("----------------" +
                    "\nGreen (Player 1's) Tokens: ");


            for (Token token : currentPlayer.getThisPlayersTokens()) {
                char ID = currentPlayer.getTokenID();
                token.drawToken();
            }
        }
        else {
            System.out.println();
            System.out.println("----------------" +
                    "\nYellow (Player 2's) Tokens: ");

            for (Token token : currentPlayer.getThisPlayersTokens()) {
                char ID = currentPlayer.getTokenID();
                token.drawToken();
            }
        }
    }

    /**
     * Called from the main()
     * Is passed user input, Passes turns between players, reprints board after moves
     */
    public void playGame() {
        System.out.println("Game has started");
        board.showBoard();
        InputStreamReader in = new InputStreamReader(System.in);

        while (true) { //while there is user input
            printPlayerTokens(currentPlayer);
            System.out.println("\n -> Player: " + currentPlayer.getPlayerID() + " Turn!");
            try{
                String input = playPass(in);
                if(in.equals("pass")){
                    // No creation
                }
                else if(input.equals("play")){
                    // play by adding a piece to the board

                }
                else if(input.equals("spotTaken")){
                    System.out.println("Cant place token, creation spot currently has a token");

                } else if(input.equals("undo")){
                    System.exit(1);
                } else if (input.equals("next")){
                    System.out.println("Next players turn");

                } else if (input.equals("invalidInput")){
                    playPass(in);
                } else {
                    //
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            board.showBoard();
            System.out.println("Next players turn");
            if(currentPlayer.equals(player1)){
                currentPlayer = player2;
            } else{
                currentPlayer = player1;
            }
            System.out.println("Current player is now: " + currentPlayer.getPlayerID());

        }
    }

    /**
     * Checks weather the player wants to play, pass or undo
     * @param isr
     * @return
     * @throws IOException
     */
    private String playPass(InputStreamReader isr) throws IOException {

        isr = new InputStreamReader(System.in);  //This takes in user input.
        BufferedReader br = new BufferedReader(isr);
        String input = "";
        System.out.println(" -> play, pass or undo?");
        input = br.readLine();
        if (input.equals("Pass") || input.equals("pass") || input.equals("PASS")){
            return "pass";
        }
        if(!board.canWeAddAPiece (currentPlayer)) {
                System.out.println("spotTaken");
                moveTokenOut(isr);

        }

        else if (input.equals("Play") || input.equals("play") || input.equals("PLAY")) {
            if (board.canWeAddAPiece(currentPlayer)) {
                create(isr);
                return "next";
            } else {
                System.out.println("creation spot occupied");
                return "move";
            }
        } else if (input.equals("Undo") || input.equals("undo") || input.equals("UNDO")) {
            return "undo";
        } else {
            return "invalidInput";
        }
        throw new IOException();
        }


    /**
     * Method is called if there is already a token in the creation square
     * the user is required to "moveTokenOut" of the square before he can play
     * @param isr
     * @return
     * @throws IOException
     */
    private Token moveTokenOut(InputStreamReader isr) throws IOException{
        isr = new InputStreamReader(System.in);  //This takes in user input.
        BufferedReader br = new BufferedReader(isr);
        String input = "";
        System.out.println("-> Which token would you like to move?");
        input = br.readLine();
        char chosenTokenID = input.charAt(0);
        Token chooseToken = currentPlayer.chooseToken(chosenTokenID);
        System.out.println("-> Which direction would you like to move the token?");
        input = br.readLine();
        String chosenDirection = input;
        mover(chosenTokenID, chosenDirection);
        return chooseToken;
    }

    /**
     * Asks the user which token to play and asks if wanting to move the token
     * @param isr
     * @return
     * @throws IOException
     */
    private Token create(InputStreamReader isr) throws IOException {
        isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String input = "";
        System.out.println("-> Which token would you like to play? (type in token id i.e 'a', 'b'");
        input = br.readLine();
        char chosenTokenID = input.charAt(0);
        Token chooseToken = currentPlayer.chooseToken(chosenTokenID);
        board.placeToken(currentPlayer, chooseToken);
        currentPlayer.removePlayerToken(chooseToken);
        board.showBoard();
        System.out.println("-> Would you like to move the token? (yes/no)");
        input = br.readLine();
        if (input.equals("Yes") || input.equals("yes") || input.equals("YES")) {
            System.out.println("-> Rotate the token, type 0 for no rotation, 90 for 1/4 turn, 180 for 1/2 turn, 270 for 3/4 turn");
            input = br.readLine();
            String rotation = input;
            System.out.println("-> Which direction would you like to move the token? (up, left, down, right)");
            input = br.readLine();
            String chosenDirection = input;
            moverAndRotator(chosenTokenID, chosenDirection, rotation);
        }
        return chooseToken;
    }

    /**
     * Helper method for the create method above. Checks which direction the player wants to move
     * Calls the required pushToken method
     * @param tokenID
     * @param option
     */
    private void mover(char tokenID,String option) {
        Token selectedToken = board.getToken(tokenID);
        //apply push
        if (option.equals("up")) {
            board.pushTokenUp(selectedToken, tokenID);
        } else if (option.equals("down")) {
            board.pushTokenDown(selectedToken, tokenID);
        } else if (option.equals("left")) {
            board.pushTokenLeft(selectedToken, tokenID);
        } else if (option.equals("right")) {
            board.pushTokenRight(selectedToken, tokenID);
        }
    }


    /**
     * Helper method for the create method above. Checks which amount of rotation and which direction the player wants
     * to move
     * Calls the required roatate method and pushToken method
     * @param tokenID
     * @param option
     */
    private void moverAndRotator(char tokenID,String option, String rotate) {
        Token selectedToken = board.getToken(tokenID);
        //apply rotation
        if (rotate.equals("0")){
            //no rotation
        } else if (rotate.equals("90")){
            board.rotateQuarter(selectedToken, tokenID);
        } else if (rotate.equals("180")){
            board.rotateHalf(selectedToken, tokenID);
        } else if (rotate.equals("270")) {
            board.rotateThreeQuarter(selectedToken, tokenID);
        }
        //apply push
        if (option.equals("up")) {
            board.pushTokenUp(selectedToken, tokenID);
        } else if (option.equals("down")) {
            board.pushTokenDown(selectedToken, tokenID);
        } else if (option.equals("left")) {
            board.pushTokenLeft(selectedToken, tokenID);
        } else if (option.equals("right")) {
            board.pushTokenRight(selectedToken, tokenID);
        }
    }


}
