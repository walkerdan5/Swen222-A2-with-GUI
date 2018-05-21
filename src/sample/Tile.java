package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * Created by danielwalker on 20/08/17.
 * Used to create the layout of the 10x10 board
 * Specifies location of the players and their creation squares
 */
public class Tile extends Rectangle{


    public Tile(GraphicsContext gc, boolean light, int x, int y){

        int row;   // Row number, from 0 to 7
        int col;   // Column number, from 0 to 7

        for (row =1; row<11; row++) {
            for (col = 1; col < 11; col++) {
                //relocate(x * sample.A1Board.TILE_SIZE, y * sample.A1Board.TILE_SIZE);
                if (x == 0 && y == 0 || x == 1 && y == 0 || x == 0 && y == 1 || x == 9 && y == 9 || x == 9 && y == 8 ||
                        x == 8 && y == 9) {
                    gc.setFill(Color.valueOf("#000000"));
                    // gameCon.fillRect(row * sample.A1Board.TILE_SIZE, col * sample.A1Board.TILE_SIZE, sample.A1Board.TILE_SIZE * 4, sample.A1Board.TILE_SIZE * 4);
                } else if (x == 2 && y == 2) {
                    gc.setFill(Color.valueOf("#17d406"));
                    gc.setStroke(Color.valueOf("000000"));
                    // gameCon.fillRect(row * sample.A1Board.TILE_SIZE, col * sample.A1Board.TILE_SIZE, sample.A1Board.TILE_SIZE * 4, sample.A1Board.TILE_SIZE * 4);

                } else if (x == 7 && y == 7) {
                    gc.setFill(Color.valueOf("#F6EC07"));
                    gc.setStroke(Color.valueOf("000000"));
                    // gameCon.fillRect(row * sample.A1Board.TILE_SIZE, col * sample.A1Board.TILE_SIZE, sample.A1Board.TILE_SIZE * 4, sample.A1Board.TILE_SIZE * 4);

                } else {
                    gc.setFill(light ? Color.valueOf("#d8d8d8") : Color.valueOf("#838383"));
                    gc.setStroke(Color.valueOf("000000"));
                    // gameCon.fillRect(row * sample.A1Board.TILE_SIZE, col * sample.A1Board.TILE_SIZE, sample.A1Board.TILE_SIZE * 4, sample.A1Board.TILE_SIZE * 4);

                }
            }
        }

    }

}
