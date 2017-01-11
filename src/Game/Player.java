package Game;

import Game.Enums.ColorMode;

/**
 * Created by giogio on 9/17/16.
 */
public abstract class Player {
    public ColorMode color;
    Match match;
    public abstract void makeMove(Move lastMove);

    public void setColor(ColorMode color){
        this.color = color;
    }
}
