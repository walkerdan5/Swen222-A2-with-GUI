package sample;


/**
 * Created by danielwalker on 2/09/17.
 */
public class GraphicsToken {

    public int graphicsTokenID;
    public int x;
    public int y;
    public int top;
    public int left;
    public int bottom;
    public int right;


    public GraphicsToken(int x, int y, int id, int top, int left, int bottom, int right){
        this.graphicsTokenID = id;
        this.x = x;
        this.y = y;
        this.top = top;
        this.left = left;
        this.right = right;
        this.bottom = bottom;
    }

}
