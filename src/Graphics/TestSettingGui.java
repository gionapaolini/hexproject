package Graphics;

import javax.swing.*;

/**
 * Created by giogio on 10/4/16.
 */
public class TestSettingGui {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        SettingsGui gui = new SettingsGui();
        frame.add(gui.getMainPanel());
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while (true){

        }

    }
}
