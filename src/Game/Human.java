package Game;

/**
 * Created by giogio on 9/17/16.
 */
public class Human extends Player {

    public void makeMove(){

    }

    public Human(Match match, boolean color){
        this.match = match;
        this.color = color;
    }

    public void makeMove(int x, int y){
        match.putStone(x,y);
    }
}
