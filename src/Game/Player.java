package Game;

/**
 * Created by giogio on 9/17/16.
 */
public abstract class Player {
    public boolean color; //true is first player(blue), false is second player(red)
    Match match;
    public abstract void makeMove();
}
