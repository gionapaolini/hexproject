package Graphics;

import AIs.AlphaBeta.EvaluationFunction;
import Game.*;
import Game.Enums.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        frame.setResizable(true);
        //frame.setResizable(false);
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
            if (getMatch().history.getList().size()!=0) {
                ColorMode winner = getMatch().history.getList().get(getMatch().history.getList().size() - 1).getPlayer();

                main.getPlayerLabel().setText("<html> GAME ENDED! <br><font color='" + winner + "'>" + winner + "</font> has won!</html>");
            }
        }

        main.getTimeLabel().setText("Time: "+getMatch().getTime().toString());
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
        frame.setTitle("Match Settings");
    }
    public void setMain(){
        if(main.getHistoryPanel().getSize().getWidth()==150){
            frame.setSize(new Dimension(1215,420));
        }else {
            frame.setSize(new Dimension(1090,420));
        }

        frame.remove(settings.getMainPanel());
        frame.add(main.getMainPanel());
        frame.setTitle("Hex Game");

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
                BotType botType;
                BotType botType2 = null;
                if(settings.getPlayermode().getSelectedItem().equals("Singleplayer"))
                    gameType = GameType.Singleplayer;
                else if (settings.getPlayermode().getSelectedItem().equals("Multiplayer"))
                    gameType = GameType.Multiplayer;
                else
                    gameType = GameType.BotFight;


                if(settings.getFirstplayer().getSelectedItem().equals("Blue"))
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
                if(settings.getBot1difficulty().getSelectedItem().equals("PathFinding"))
                    botType = BotType.PathFinding;
                else if(settings.getBot1difficulty().getSelectedItem().equals("MCTS"))
                    botType = BotType.MCTS;
                else if(settings.getBot1difficulty().getSelectedItem().equals("MCTS_alt"))
                    botType = BotType.MCTS_alt;
                else
                    botType = BotType.AlphaBeta;

                if(settings.getBot2difficulty().getSelectedItem().equals("PathFinding"))
                    botType2 = BotType.PathFinding;
                else if(settings.getBot2difficulty().getSelectedItem().equals("MCTS"))
                    botType2 = BotType.MCTS;
                else if(settings.getBot2difficulty().getSelectedItem().equals("MCTS_alt"))
                    botType2 = BotType.MCTS_alt;
                else
                    botType2 = BotType.AlphaBeta;





                match = new Match(gameType,firstPlayer,rule,learningMode, botType,botType2,11, panel);
                match.addObserver(thisInteface);

                setMain();
                frame.revalidate();
                frame.repaint();
                match.startMatch();

            }
        });

        main.getUndoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(match.getGameType()==GameType.Singleplayer) {
                    match.undo();
                    match.undo();
                }else {
                    match.undo();
                }
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
                 System.out.println(EvaluationFunction.get_n_bridges(match.getBoard().getGrid(), ColorMode.Blue));
                System.out.println(EvaluationFunction.get_n_bridges(match.getBoard().getGrid(), ColorMode.Red));

                /*
               match.pause();
               */

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
                        if((match.getBoard().getGrid()[i][j].getStatus() != 0 && ((match.getRule()==SwapRule.NotActive)||(match.getRule()==SwapRule.Active && match.getnTurn()>1)))){
                            panel.setLastSelected(null);
                            break outerloop;
                        }else {
                            int pl;
                            if(match.getCurrentPlayer().color==ColorMode.Blue){
                                pl = 1;
                            }else {
                                pl = 0;
                            }
                            if (match.getGameType() != GameType.BotFight)panel.setLastSelected(new int[]{pl,i, j}); else
                                panel.setLastSelected(null);

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
