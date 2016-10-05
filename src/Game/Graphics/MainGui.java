package Game.Graphics;

import Game.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by giogio on 10/4/16.
 */
public class MainGui extends JFrame implements Observer {

    public MainGui(SettingsGui gui) {
        setSize(700,500);
        gui.addObserver(this);
        add(gui.getPanel1());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public MainGui(BoardPanel bp) {
        super();
        setSize(745,422);
        add(bp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof SettingsGui){
            SettingsGui settingsGui = (SettingsGui) arg;
            Match match = new Match(settingsGui.getPlayermode(), false, settingsGui.getSwapRule() , settingsGui.getLearningmode(),11);
            BoardPanel bp = new BoardPanel(match);
            this.setSize(bp.getSize());

            Container contain = getContentPane();
            contain.removeAll();
            this.add(bp);
            bp.repaint();
            this.validate();
            this.repaint();
        }

    }


}
