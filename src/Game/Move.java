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

    public boolean equals(Object m){
        Move move = (Move) m;
        if (move.x == x && move.y == y) return true; else return false;
    }
}
