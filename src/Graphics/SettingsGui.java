package Graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by giogio on 10/4/16.
 */
public class SettingsGui {
    private JPanel mainPanel;
    private JComboBox playermode;
    private JCheckBox learningmode;
    private JButton playbutton;
    private JComboBox bot1difficulty;
    private JCheckBox swaprule;
    private JLabel bot1label;
    private JComboBox firstplayer;
    private JLabel firstLabel;
    private JComboBox bot2Difficulty;
    private JLabel bot2label;
    private JLabel learningModeLabel;

    public SettingsGui(){

        playermode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(playermode.getSelectedItem().equals("Multiplayer")){
                    learningmode.setVisible(false);

                    bot1difficulty.setVisible(false);
                    bot1label.setVisible(false);
                    firstLabel.setVisible(false);
                    firstplayer.setVisible(false);
                }else if (playermode.getSelectedItem().equals("Botfight")){
                    bot1difficulty.setVisible(true);
                    bot1label.setVisible(true);
                    bot2Difficulty.setVisible(true);
                    bot2label.setVisible(true);
                    firstLabel.setVisible(true);
                    firstplayer.setVisible(true);
                }else{
                    bot1difficulty.setVisible(true);
                    bot1label.setVisible(true);
                    bot2Difficulty.setVisible(false);
                    bot2label.setVisible(false);
                    firstLabel.setVisible(true);
                    firstplayer.setVisible(true);
                }
            }

        }
        );
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JComboBox getPlayermode() {
        return playermode;
    }

    public JCheckBox getLearningmode() {
        return learningmode;
    }

    public JButton getPlaybutton() {
        return playbutton;
    }

    public JComboBox getBot1difficulty() {
        return bot1difficulty;
    }

    public JCheckBox getSwaprule() {
        return swaprule;
    }

    public JComboBox getFirstplayer() {
        return firstplayer;
    }
}
