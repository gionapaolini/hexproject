package Game;

/**
 * Created by giogio on 9/17/16.
 */
public class Human extends Player {

    public void makeMove(Move lastMove){

    }

    public Human(Match match){
        this.match = match;
    }

    public void makeMove(int x, int y){
        match.putStone(x,y);
    }
}
