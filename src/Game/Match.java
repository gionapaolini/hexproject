package Game;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Date;

/**
 * Created by giogio on 9/17/16.
 */
public class Match {
    private Player[] players;
    private Board board;
    private Player currentPlayer;
    private Date startTime, endTime;
    private boolean paused, rule, gameType, learningMode; //gameType true if singleplayer, false if multiplayer
    public History history;
    private short nTurn;

    public Match(boolean gameType, boolean firstPlayer, boolean rule, boolean learningMode, int sideLenght){
        this.gameType = gameType;
        this.rule = rule;
        this.learningMode = learningMode;
        board = new Board(sideLenght);
        history = new History();
        nTurn = 0;
        players = new Player[2];
        if(gameType){
            players[0] = new Human();
            players[1] = new Bot();
        }else {
            players[0] = new Human();
            players[1] = new Human();
        }

        if(firstPlayer){
            currentPlayer = players[0];
        }else {
            currentPlayer = players[1];
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
            writer.println(gameType+" "+rule+" "+learningMode);
            for (Record record: history.getList()){
                writer.println(record.toString());
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
            while (true){
                line = reader.readLine();
                if(line==null)
                    return;
                String[] currentLine = line.split(" ");
                for(int i=0;i<currentLine.length;i++){
                    System.out.print(currentLine[i]+" ");
                }
                System.out.println();

            }
        }
        notifyObservers();
    }

    public void notifyObservers(){

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
    }


    public void pause(){
        paused = !paused;
    }


    public void switchPlayer(Player player){
        currentPlayer = player;
        if(currentPlayer instanceof Bot){
            currentPlayer.makeMove();
        }
        notifyObservers();
    }

    public void hasWon(boolean player){
        if(board.isConnected(player)){
            if(player)
                System.out.println("Player 1 has won!");
            else
                System.out.println("Player 2 has won!");
            endMatch();
        }

    }

    public void putStone(int x, int y){
        if(currentPlayer.color==true){
            board.getGrid()[x][y].setStatus((byte)1);
            hasWon(true);
            switchPlayer(players[1]);
        }else {
            board.getGrid()[x][y].setStatus((byte)2);
            hasWon(false);
            switchPlayer(players[0]);
        }
        notifyObservers();
    }





}
