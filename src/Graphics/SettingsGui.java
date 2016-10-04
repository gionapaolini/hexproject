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

    public SettingsGui(){
        playermode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(playermode.getSelectedItem().equals("Multiplayer")){
                    botdifficulty.setVisible(false);
                    botlabel.setVisible(false);
                }else{
                    botdifficulty.setVisible(true);
                    botlabel.setVisible(true);
                }
            }
          });
    }

    public JPanel getPanel1() {
        return mainPanel;
    }

    public JComboBox getComboBox1() {
        return playermode;
    }

    public JCheckBox getCheckBox1() {
        return learningmode;
    }

    public JButton getPlaybutton() {
        return playbutton;
    }

    public JComboBox getBotdifficulty() {
        return botdifficulty;
    }


}
