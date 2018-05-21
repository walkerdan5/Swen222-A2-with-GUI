package sample;

/**
 * Created by danielwalker on 2/08/17.
 */
public class Token implements BoardGame {
    private char top, left, right, bottom = 'W';
    int tokenID;

    public Player whosToken;


    public Token(int id, Player player) {
        tokenID = id;
        whosToken = player;
    }


    public void drawToken(){
        System.out.println(" " + top + "\n" + left + tokenID + right+ "\n" + " " + bottom);
    }


    public void rotate90(){
        char t = top;
        char l = left;
        char r = right;
        char b = bottom;

        //rotation for a sword
        if (t == '|')
            right = '-';

        else if (l == '-')
            top = '|';

        else if (r == '-')
            bottom = '|';

        else if (b == '|')
            left = '-';

        //rotation for a shield
        if (t == '#')
            right = '#';

        else if (l == '#')
            top = '#';

        else if (r == '#')
            bottom = '#';

        else if (b == '#')
            left = '#';

        //rotation for nothing
        if (t == ' ')
            right = ' ';

        else if (l == ' ')
            top = ' ';

        else if (r == ' ')
            bottom = ' ';

        else if (b == ' ')
            left = ' ';
    }


    public void rotate180(){
        rotate90();
        rotate90();
    }


    public void rotate270(){
        rotate90();
        rotate90();
        rotate90();
    }

    public char getLeft(){
        return left;
    }

    public char getRight() {
        return right;
    }

    public char getTop() {
        return top;
    }

    public char getBottom() {
        return bottom;
    }

    public void setLeft(char left){
        this.left = left;
    }

    public void setRight(char right){
        this.right = right;
    }

    public void setTop(char top){
        this.top = top;
    }

    public void setBottom(char bottom){
        this.bottom = bottom;
    }

    public String toString(){
        return " " + top + "\n" + left + tokenID + right+ "\n" + " " + bottom;
    }

}
