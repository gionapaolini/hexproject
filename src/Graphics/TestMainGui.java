package Graphics;

import Game.BoardPanel;
import Game.Match;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PabloPSoto on 04/10/2016.
 */
public class TestMainGui {
    public static void main(String[] args){
        BoardPanel gamePanel = new BoardPanel(null);
        Match match = new Match(false,false,false,false,11, gamePanel);
        JFrame frame = new JFrame("GameHex");
        frame.setSize(720,382);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainGUI mainGUI = new MainGUI();
        mainGUI.getGridPanel().add(gamePanel);
        frame.add(mainGUI.getPanel1());
        frame.setVisible(true);
        while (true){

        }

    }
}
