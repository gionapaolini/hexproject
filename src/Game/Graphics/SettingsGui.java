package Game.Graphics;


import Game.LearningMode;
import Game.PlayerMode;
import Game.SwapRule;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

/**
 * Created by giogio on 10/4/16.
 */
public class SettingsGui extends Observable {
    private JPanel mainPanel;
    private JComboBox playermode;
    private JCheckBox learningmode;
    private JButton playbutton;
    private JComboBox botdifficulty;
    private JCheckBox swaprule;
    private JLabel botlabel;

    public SettingsGui(){


        ComboBoxModel<String> cbm = new DefaultComboBoxModel(PlayerMode.values());
        playermode.setModel(cbm);
        playermode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(playermode.getSelectedItem().equals(PlayerMode.HumanVsHuman.toString())){
                    botdifficulty.setVisible(false);
                    botlabel.setVisible(false);
                }else{
                    botdifficulty.setVisible(true);
                    botlabel.setVisible(true);
                }

            }
          });
        SettingsGui thisClass = this;
        playbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(thisClass);
            }
        });
    }

    public JPanel getPanel1() {
        return mainPanel;
    }

    public PlayerMode getPlayermode() {

        return PlayerMode.valueOf(playermode.getSelectedItem().toString());
    }

    public LearningMode getLearningmode() {
        if (learningmode.isSelected()) return LearningMode.JA; else return LearningMode.NEIN;

    }

    public SwapRule getSwapRule(){
        if (swaprule.isSelected()) return SwapRule.JA; else return SwapRule.NEIN;
    }

    public JButton getPlaybutton() {
        return playbutton;
    }

    public JComboBox getBotdifficulty() {
        return botdifficulty;
    }


}
