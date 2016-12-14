package Graphics;

import Game.BoardPanel;
import Game.Enums.FirstPlayer;
import Game.Enums.GameType;
import Game.Enums.LearningMode;
import Game.Enums.SwapRule;
import Game.Match;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PabloPSoto on 04/10/2016.
 */
public class TestMainGui {
    public static void main(String[] args){
        BoardPanel gamePanel = new BoardPanel(null);
        Match match = new Match(GameType.Singleplayer, FirstPlayer.Yes, SwapRule.NotActive, LearningMode.NotActive,null,null,11, gamePanel);
        JFrame frame = new JFrame("GameHex");
        frame.setSize(1055,410);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainGui mainGUI = new MainGui(gamePanel,frame);
frame.setResizable(false);
        frame.add(mainGUI.getMainPanel());
        frame.setVisible(true);
        match.startMatch();
        while (true){

        }

    }
}
