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
    private JComboBox botdifficulty;
    private JCheckBox swaprule;
    private JLabel botlabel;
    private JComboBox firstplayer;
    private JLabel firstLabel;

    public SettingsGui(){
        playermode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(playermode.getSelectedItem().equals("Multiplayer")){
                    botdifficulty.setVisible(false);
                    botlabel.setVisible(false);
                    firstLabel.setVisible(false);
                    firstplayer.setVisible(false);
                }else{
                    botdifficulty.setVisible(true);
                    botlabel.setVisible(true);
                    firstLabel.setVisible(true);
                    firstplayer.setVisible(true);
                }
            }
          });
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

    public JComboBox getBotdifficulty() {
        return botdifficulty;
    }

    public JCheckBox getSwaprule() {
        return swaprule;
    }

    public JComboBox getFirstplayer() {
        return firstplayer;
    }
}
