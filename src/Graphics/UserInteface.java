package Graphics;

import Game.*;
import Game.Enums.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by giogio on 10/5/16.
 */
public class UserInteface implements Observer{
    private JFrame frame;
    private SettingsGui settings;
    private MainGui main;
    private BoardPanel panel;
    private Match match;
    private UserInteface thisInteface;
    public UserInteface() {
        frame = new JFrame("Hex - Match Settings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        settings = new SettingsGui();
        panel = new BoardPanel(null);
        main = new MainGui(panel,frame);
        setSettings();
        setActions();
        frame.setVisible(true);
        thisInteface = this;

    }
    public Match getMatch(){
        return match;
    }
    public void update(){

        if(match.getCurrentPlayer()!=null) {

            main.getPlayerLabel().setText("<html>Player <font color='"+match.getCurrentPlayer().color+"'>"+match.getCurrentPlayer().color+"</font> is your turn!</html>");

        }else {
            main.getPlayerLabel().setText("GAME ENDED!");

        }
        main.getHistoryArea().setText("");
        for(Record rec: match.history.getList()){
            int x = rec.getRow();
            int y = rec.getColumn();
            StyledDocument document = (StyledDocument) main.getHistoryArea().getDocument();
            String s;
            StyleContext sc = new StyleContext();
            Style style = sc.addStyle("strikethru", null);
            StyleConstants.setStrikeThrough (style,true);
            s = rec.getPlayer() + ": (" + x + "," + y + ")\n";

            try {
                if(rec.isStatus())
                    document.insertString (document.getLength(), s, null);
                else
                    document.insertString (document.getLength(), s, style);
            }catch (BadLocationException e){
                System.out.println("Error: "+e);
            }

        }
        frame.repaint();

    }





    public void setSettings(){
        frame.setSize(new Dimension(400,300));
        frame.remove(main.getMainPanel());
        frame.add(settings.getMainPanel());
    }
    public void setMain(){
        frame.setSize(new Dimension(1090,420));
        frame.remove(settings.getMainPanel());
        frame.add(main.getMainPanel());
    }

    private void setActions(){
        main.getMenuButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                match.endMatch();
                setSettings();


            }
        });
        settings.getPlaybutton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameType gameType;
                FirstPlayer firstPlayer;
                SwapRule rule;
                LearningMode learningMode;
                if(settings.getPlayermode().equals("Singleplayer"))
                    gameType = GameType.Singleplayer;
                else
                    gameType = GameType.Multiplayer;
                if(settings.getFirstplayer().equals("Blue"))
                    firstPlayer = FirstPlayer.Yes;
                else
                    firstPlayer = FirstPlayer.No;
                if(settings.getSwaprule().isSelected())
                    rule = SwapRule.Active;
                else
                    rule = SwapRule.NotActive;
                if(settings.getLearningmode().isSelected())
                    learningMode = LearningMode.Active;
                else
                    learningMode = LearningMode.NotActive;



                match = new Match(gameType,firstPlayer,rule,learningMode,11, panel);
                match.addObserver(thisInteface);
                match.startMatch();
                setMain();
                frame.repaint();

            }
        });

        main.getUndoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                match.undo();
            }
        });
        main.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    match.saveMatch();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        main.getLoadButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    match.loadMatch();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        main.getPauseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(main.getRightPanel().getSize());
            }
        });


        panel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (panel.getLastSelected()!=null) {
                    match.putStone(panel.getLastSelected()[1],panel.getLastSelected()[2]);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (match.getCurrentPlayer()==null)return;
                int y = e.getY();
                int x = e.getX();

                int startX = 60;
                int startY = 30;
                NodeCell[][] grid = match.getBoard().getGrid();
                outerloop:for(int i=0;i<grid.length;i++){
                    for(int j=0;j<grid[0].length;j++){
                        Polygon p = panel.getPolygon(startX,startY);
                        startX = startX + 20;
                        startY = startY + 30;
                        if (!p.contains(x,y)) continue;
                        if(!(grid[i][j].getStatus()==0)){
                            panel.setLastSelected(null);
                            break outerloop;
                        }else {
                            int pl;
                            if(match.getCurrentPlayer().color==ColorMode.Blue){
                                pl = 1;
                            }else {
                                pl = 0;
                            }
                            panel.setLastSelected(new int[]{pl,i, j});
                            panel.repaint();
                            break outerloop;
                        }

                    }
                    startY=30;
                    startX=60+((1+i)*(2*20));
                }
            }
        });


    }

}
