package Graphics;

import Game.BoardPanel;
import Game.EvaluationFunction;
import Game.LearningMode;
import Game.Match;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PabloPSoto on 04/10/2016.
 */
public class TestMainGui {
    public static void main(String[] args){
        BoardPanel gamePanel = new BoardPanel(null);
        boolean FirstPlayer = false;
        Match match = new Match(GameType.HumanVsHuman, (boolean)FirstPlayer,EvaluationFunction.NEIN, LearningMode.NEIN,11, gamePanel);
        BoardPanel bp = new BoardPanel(match);
        MainGui frame = new MainGui();
        frame.setSize(720,382);
        frame.add(bp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setVisible(true);
        while (true){

        }

    }
}
