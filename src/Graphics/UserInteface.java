package Graphics;

import Game.BoardPanel;
import Game.Human;
import Game.Match;
import Game.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            if (match.getCurrentPlayer().color) {
                System.out.println("changed to blue");
                main.getPlayerLabel().setText("<html>Player <font color='blue'>BLUE</font> is your turn!</html>");
            }else{
                System.out.println("changed to red");
                main.getPlayerLabel().setText("<html>Player <font color='red'>RED</font> is your turn!</html>");
            }
        }else {
            System.out.println("NULL"+ match.getCurrentPlayer());
        }
        System.out.println("here");
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
                boolean gameType, firstPlayer,rule, learningMode;
                if(settings.getPlayermode().equals("Singleplayer"))
                    gameType = true;
                else
                    gameType = false;
                if(settings.getFirstplayer().equals("Blue"))
                    firstPlayer = true;
                else
                    firstPlayer = false;
                if(settings.getSwaprule().isSelected())
                    rule = true;
                else
                    rule = false;
                if(settings.getLearningmode().isSelected())
                    learningMode = true;
                else
                    learningMode = false;



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
                if (match.getCurrentPlayer() != null) {
                    int pl;
                    if (getMatch().getCurrentPlayer().color) {
                        pl = 1;
                    } else {
                        pl = 2;
                    }
                    System.out.print("Player " + pl + " make your move: ");
                    Scanner in = new Scanner(System.in);
                    String choice = in.next();
                    int x, y;
                    try {
                        x = Integer.parseInt(choice);
                        if (x >= 0 && x <= 10) {
                            try {
                                y = Integer.parseInt(in.next());
                                if (y >= 0 && y <= 10) {
                                    Human play = (Human) getMatch().getCurrentPlayer();
                                    play.makeMove(x, y);
                                } else {
                                    System.out.println("Y coord not in the range, try again!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error, try again. 2");
                            }

                        } else {
                            System.out.println("X coord not in the range, try again!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error, try again. 1");
                    }

                }
            }
        });

    }

}
