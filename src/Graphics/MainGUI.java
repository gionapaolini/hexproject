package Graphics;

import javax.swing.*;

/**
 * Created by PabloPSoto on 04/10/2016.
 */
public class MainGUI {
    private JPanel panel1;
    private JButton Save;
    private JButton loadButton;
    private JButton pauseButton;
    private JTextArea textArea1;
    private JButton hideHistoryButton;
    private JButton undoMoveButton;
    private JPanel gridPanel;

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JButton getSave() {
        return Save;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JTextArea getTextArea1() {
        return textArea1;
    }

    public JButton getHideHistoryButton() {
        return hideHistoryButton;
    }

    public JButton getUndoMoveButton() {
        return undoMoveButton;
    }

    public void setGridPanel(JPanel gridPanel) {
        this.gridPanel = gridPanel;
    }
}
