package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by giogio on 10/4/16.
 */
public class MainGui {
    private JPanel mainPanel;
    private JButton historyButton;
    private JButton pauseButton;
    private JPanel historyPanel;
    private JButton hideHistoryButton;
    private JButton undoButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton menuButton;
    private JPanel gridPanel;
    private JLabel playerLabel;
    private JTextPane historyArea;
    private JPanel rightPanel;
    private JScrollPane scrollPanel;
    private JLabel timeLabel;
    private JButton AnalyseButton;
    private JFrame frame;
    private Analysor analysor;

    public MainGui(JPanel panel, JFrame frame){
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.PAGE_AXIS));
        gridPanel.add(panel);
        this.frame = frame;
        System.out.println(mainPanel.getMaximumSize());
        historyArea.setBounds(0,0,150,400);
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                historyPanel.setMaximumSize(new Dimension(150,(int)historyPanel.getMaximumSize().getHeight()));
                historyPanel.setPreferredSize(new Dimension(150,(int)historyPanel.getMaximumSize().getHeight()));
                hideHistoryButton.setVisible(true);
                historyArea.setVisible(true);
                scrollPanel.setVisible(true);
                historyButton.setVisible(false);
                frame.setSize(new Dimension(1215,420));

            }
        });
        hideHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                historyPanel.setMaximumSize(new Dimension(25,(int)historyPanel.getMaximumSize().getHeight()));
                historyPanel.setPreferredSize(new Dimension(25,(int)historyPanel.getMaximumSize().getHeight()));
                hideHistoryButton.setVisible(false);
                scrollPanel.setVisible(false);
                historyArea.setVisible(false);
                historyButton.setVisible(true);
                frame.setSize(new Dimension(1090,420));

            }
        });
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(frame.getSize());
            }
        });
        MainGui mg = this;
        AnalyseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (analysor == null) {
                    analysor = new Analysor(mg);
                }
                    analysor.setVisible(!analysor.isVisible());
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getHistoryButton() {
        return historyButton;
    }

    public JTextPane getHistoryArea() {
        return historyArea;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getMenuButton() {
        return menuButton;
    }

    public JButton getHideHistoryButton() {
        return hideHistoryButton;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JLabel getPlayerLabel() {
        return playerLabel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }
    public JPanel getHistoryPanel(){
        return historyPanel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }
}
