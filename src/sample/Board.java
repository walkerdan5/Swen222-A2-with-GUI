package sample;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

/**
 * Created by danielwalker on 20/08/17.
 */
public class Board extends JPanel implements MouseListener{

    public static final int TILE_SIZE = 10;
    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;

    int selectedRow; // Row and column of selected square.  If no
    int selectedCol; //      square is selected, selectedRow is -1.

    Board() {
        // Constructor.  Set selectedRow to -1 to indicate that
        // no square is selected.  And set the board object
        // to listen for mouse events on itself.
        selectedRow = -1;
        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        // Draw the checkerboard and highlight selected square, if any.
        // (Note: paintComponent() is not necessary, since this
        // method already paints the entire surface of the object.
        // This assumes that the object is exactly 160-by-160 pixels.

        int row;   // Row number, from 0 to 7
        int col;   // Column number, from 0 to 7
        int x,y;   // Top-left corner of square

        for ( row = 0;  row < 8;  row++ ) {

            for ( col = 0;  col < 8;  col++) {
                x = col * 20;
                y = row * 20;
                if ( (row % 2) == (col % 2) )
                    g.setColor(Color.red);
                else
                    g.setColor(Color.black);
                g.fillRect(x, y, 20, 20);
            }

        } // end for row

        if (selectedRow >= 0) {
            // Since there is a selected square, draw a cyan
            // border around it.  (If selectedRow < 0, then
            // no square is selected and no border is drawn.)
            g.setColor(Color.cyan);
            y = selectedRow * 20;
            x = selectedCol * 20;
            g.drawRect(x, y, 19, 19);
            g.drawRect(x+1, y+1, 17, 17);
        }

    }  // end paint()


    public void mousePressed(MouseEvent evt) {
        // When the user clicks on the applet, figure out which
        // row and column the click was in and change the
        // selected square accordingly.

        int col = evt.getX() / 20;   // Column where user clicked.
        int row = evt.getY() / 20;   // Row where user clicked.

        if (selectedRow == row && selectedCol == col) {
            // User clicked on the currently selected square.
            // Turn off the selection by setting selectedRow to -1.
            selectedRow = -1;
        }
        else {
            // Change the selection to the square the user clicked on.
            selectedRow = row;
            selectedCol = col;
        }
        repaint();

    }  // end mouseDown()



    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
