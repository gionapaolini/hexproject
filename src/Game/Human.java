package Game;

import Game.Enums.ColorMode;
import Game.Enums.GameType;

/**
 * Created by giogio on 9/17/16.
 */
public class Human extends Player {

    public void makeMove(){

    }

    public Human(Match match){
        this.match = match;
    }

    public void makeMove(int x, int y){
        match.putStone(x,y);
    }
}
