package Game;

import Game.Enums.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by giogio on 9/17/16.
 */
public class Match {
    private Player[] players;
    private List<Observer> observers;
    private Board board;
    private Player currentPlayer;
    private int currentInt;
    private Date startTime, endTime;
    private GameType gameType;
    private LearningMode learningMode;
    private SwapRule rule;
    private boolean paused;
    public History history;
    private short nTurn,sideLength;

    public Match(GameType gameType, FirstPlayer firstPlayer, SwapRule rule, LearningMode learningMode, int sideLenght, BoardPanel gamePanel){
        this.gameType = gameType;
        this.rule = rule;
        this.learningMode = learningMode;
        this.sideLength = (short) sideLenght;
        board = new Board(sideLenght);
        observers = new ArrayList<Observer>();
        gamePanel.match = this;
        observers.add(gamePanel);


        history = new History();
        nTurn = 0;
        players = new Player[2];
        if(gameType==gameType.Singleplayer){
            if(firstPlayer== FirstPlayer.Yes) {
                players[0] = new Human(this);
                players[1] = new Bot(this);
                players[0].setColor(ColorMode.Blue);
                players[1].setColor(ColorMode.Red);
            }else {
                players[1] = new Human(this);
                players[0] = new Bot(this);
                players[0].setColor(ColorMode.Blue);
                players[1].setColor(ColorMode.Red);
            }
        }else {
            players[0] = new Human(this);
            players[1] = new Human(this);
            players[0].setColor(ColorMode.Blue);
            players[1].setColor(ColorMode.Red);
        }
        notifyObservers();
    }

    public void saveMatch() throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser("./Saved");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("HEX GAME FILE", "hex", "Hex Dump");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if(!file.getName().substring(file.getName().length() - 4).equals(".hex")){
                file = new File(fileChooser.getSelectedFile()+".hex");
            }
            PrintWriter writer = new PrintWriter(file);
            writer.println("settings "+gameType+" "+rule+" "+learningMode);
            for (Record record: history.getList()){
                writer.println("rec "+record.toString());
            }
            writer.close();
            System.out.println(file);
        }
    }

    public void loadMatch() throws IOException {
        JFileChooser fileChooser = new JFileChooser("./Saved");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("HEX GAME FILE", "hex", "Hex Dump");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            FileReader fr = null;
            fr = new FileReader(file);
            String line;
            BufferedReader reader = new BufferedReader(fr);
            history = new History();
            while (true){
                line = reader.readLine();
                if(line==null) {
                    for (Record rec: history.getList()){
                        rec.printRec();
                    }
                    loadBoard();
                    return;
                }
                String[] currentLine = line.split(" ");
                if(currentLine[0].equals("settings")){
                    System.out.print("Loading settings..");
                    if(currentLine[1].equals("Singleplayer")){
                        gameType = GameType.Singleplayer;
                    }else {
                        gameType = GameType.Multiplayer;
                    }

                    if(currentLine[2].equals("Active")){
                        rule = SwapRule.Active;
                    }else {
                        rule = SwapRule.NotActive;
                    }
                    if(currentLine[3].equals("Active")){
                        learningMode = LearningMode.Active;
                    }else {
                        learningMode = LearningMode.NotActive;
                    }
                }else {
                    boolean status;
                    ColorMode player;
                    byte row, column;
                    Date date;
                    try {
                        if(currentLine[1].equals("Blue")){
                            player = ColorMode.Blue;
                        }else {
                            player = ColorMode.Red;
                        }
                        row = (byte)Integer.parseInt(currentLine[2]);
                        column = (byte)Integer.parseInt(currentLine[3]);
                        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                        date = format.parse(currentLine[4]);
                        if(currentLine[5].equals("false")){
                            status = false;
                        }else {
                            status = true;
                        }
                        history.addRecord(new Record(player,row,column,date,status));

                    }catch (ParseException e){
                        e.printStackTrace();
                    }


                }



            }

        }
        notifyObservers();
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void notifyObservers(){
        System.out.println("Notified!");
        for(Observer obs: observers){
            obs.update();
        }
    }

    public void startMatch(){
        paused = false;
        currentPlayer = players[0];
        if(currentPlayer instanceof Bot){
            currentPlayer.makeMove();
        }
        notifyObservers();

    }

    public void endMatch(){
        paused=true;
        currentPlayer = null;
        notifyObservers();
        System.out.println("ENDED");
    }


    public void pause(){
        paused = !paused;
    }


    public void switchPlayer(){
        if(currentPlayer == players[0]){
            currentPlayer=players[1];
        }else {
            currentPlayer=players[0];
        }
        if(currentPlayer instanceof Bot){
            currentPlayer.makeMove();
        }
        notifyObservers();
    }

    public void hasWon(ColorMode colorMode){
        if(board.isConnected(colorMode)){
            System.out.println("Player "+colorMode+" has won!");
            endMatch();
        }

    }



    public void putStone(int x, int y){
        if(!paused) {
            if (x >= board.getGrid().length || y >= board.getGrid().length || x < 0 || y < 0 || board.getGrid()[x][y].getStatus() != 0) {
                System.out.println("On the same cell, again!");
                return;
            }
            board.placeStone(x, y, currentPlayer.color);
            history.addRecord(new Record(currentPlayer.color, (byte) x, (byte) y));
            hasWon(currentPlayer.color);
            if (currentPlayer != null) {
                switchPlayer();
            }

            notifyObservers();
        }
    }

    public void printGrid(){
        board.printGrid();
    }

    public Board getBoard(){
        return board;
    }

    public void undo(){
        if(!paused) {
            int count = 1;
            while (count <= history.getList().size() && !history.getList().get(history.getList().size() - count).isStatus()) {
                count++;
            }
            if (!(count > history.getList().size()))
                history.getList().get(history.getList().size() - count).setStatus(false);
            loadBoard();
        }
    }

    private void loadBoard(){
        board = new Board(sideLength);
        for(Record rec: history.getList()){
            if(rec.isStatus()){
                board.placeStone(rec.getRow(),rec.getColumn(),rec.getPlayer());
            }
        }
        if(currentPlayer!=null){
            int count = 1;
            while(count<=history.getList().size() && !history.getList().get(history.getList().size()-count).isStatus()){
                count++;
            }
            if(count>history.getList().size()){
                currentPlayer = players[0];
            }else if(history.getList().get(history.getList().size()-count).getPlayer()==ColorMode.Blue){
                currentPlayer = players[1];
            }else{
                currentPlayer = players[0];
            }
        }
        notifyObservers();

    }

    public void addObserver(Observer obs){
        observers.add(obs);
        System.out.println("Observer added!");
    }



}
