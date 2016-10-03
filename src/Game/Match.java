package Game;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private short nTurn,sideLength;
    private BoardPanel gamePanel;

    public Match(boolean gameType, boolean firstPlayer, boolean rule, boolean learningMode, int sideLenght){
        this.gameType = gameType;
        this.rule = rule;
        this.learningMode = learningMode;
        this.sideLength = (short) sideLenght;
        board = new Board(sideLenght);


        history = new History();
        nTurn = 0;
        players = new Player[2];
        if(gameType){
            players[0] = new Human(this, true);
            players[1] = new Bot(this, false);
        }else {
            players[0] = new Human(this, true);
            players[1] = new Human(this, false);
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
                    if(currentLine[1].equals("false")){
                        gameType = false;
                    }else {
                        gameType = true;
                    }

                    if(currentLine[2].equals("false")){
                        rule = false;
                    }else {
                        rule = true;
                    }
                    if(currentLine[3].equals("false")){
                        learningMode = false;
                    }else {
                        learningMode = true;
                    }
                    System.out.println("... Done");
                }else {
                    System.out.print("Loading a record..");
                    boolean player, status;
                    byte row, column;
                    Date date;
                    try {
                        if(currentLine[1].equals("false")){
                            player = false;
                        }else {
                            player = true;
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

                    System.out.println("... Done");

                }



            }

        }
        notifyObservers();
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void notifyObservers(){

    }

    public void startMatch(){
        paused = false;
        currentPlayer = players[0];
        if(currentPlayer instanceof Bot){
            currentPlayer.makeMove();
        }
        board.printGrid();
        notifyObservers();
    }

    public void endMatch(){
        paused=true;
        currentPlayer = null;
        notifyObservers();
        System.out.println("ENDED");
    }

    public void setPanel(BoardPanel board){
        gamePanel = board;
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
        if(x>=board.getGrid().length || y>=board.getGrid().length || x<0 || y<0 || board.getGrid()[x][y].getStatus()!=0){
            return;
        }
        if(currentPlayer.color==true){
            board.getGrid()[x][y].setStatus((byte)1);
            history.addRecord(new Record(true,(byte)x,(byte)y));
            gamePanel.repaint();
            hasWon(true);
            if(currentPlayer!=null) {
                switchPlayer(players[1]);
            }
        }else{
            board.getGrid()[x][y].setStatus((byte)2);
            history.addRecord(new Record(false,(byte)x,(byte)y));
            gamePanel.repaint();
            hasWon(false);
            if(currentPlayer!=null) {
                switchPlayer(players[0]);
            }
        }
        notifyObservers();
    }

    public void printGrid(){
        board.printGrid();
    }

    public Board getBoard(){
        return board;
    }

    public void undo(){
        int count = 1;
        while(count<=history.getList().size() && !history.getList().get(history.getList().size()-count).isStatus()){
            count++;
        }
        if(!(count>history.getList().size()))
            history.getList().get(history.getList().size()-count).setStatus(false);
        loadBoard();
    }

    private void loadBoard(){
        board = new Board(sideLength);
        for(Record rec: history.getList()){
            if(rec.isStatus()){
                byte stat;
                if(rec.isPlayer()){
                    stat = 1;
                }else {
                    stat = 2;
                }
                board.getGrid()[rec.getRow()][rec.getColumn()].setStatus(stat);
            }
        }
        if(currentPlayer!=null){
            int count = 1;
            while(count<=history.getList().size() && !history.getList().get(history.getList().size()-count).isStatus()){
                count++;
            }
            if(count>history.getList().size()){
                if(history.getList().get(history.getList().size()-(count-1)).isPlayer()){
                    currentPlayer = players[0];
                }else {
                    currentPlayer = players[1];
                }
            }else if(history.getList().get(history.getList().size()-count).isPlayer()){
                currentPlayer = players[1];
            }else{
                currentPlayer = players[0];
            }
        }
        gamePanel.repaint();

    }

}
