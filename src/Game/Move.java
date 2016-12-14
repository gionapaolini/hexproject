package Game;

/**
 * Created by giogio on 10/13/16.
 */
public class Move {
    public int x;
    public int y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return ("x: " + x + " y: " + y);
    }
}
