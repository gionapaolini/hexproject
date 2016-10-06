package Game.Graphics;

import Game.*;

/**
 * Created by PabloPSoto on 04/10/2016.
 */
public class TestMainGui {
    public static void main(String[] args){
        boolean FirstPlayer = false;
        Match match = new Match(PlayerMode.HumanVsHuman, (boolean)FirstPlayer, SwapRule.NEIN, LearningMode.NEIN,11);
        BoardPanel bp = new BoardPanel(match);
        MainGui frame = new MainGui(bp);

    }
}
