package Game;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by giogio on 9/17/16.
 */
public class TestMatch {
    public static void main(String[] args) throws InterruptedException, IOException {

        BoardPanel gamePanel = new BoardPanel(null);
        Match match = new Match(false,false,false,false,11, gamePanel);
        JFrame frame = new JFrame("GameHex");
        frame.setSize(700,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gamePanel);
        frame.setVisible(true);


        match.startMatch();
        while (match.getCurrentPlayer()!=null){
            int pl;
            if(match.getCurrentPlayer().color){
                pl = 1;
            }else {
                pl = 2;
            }
            System.out.print("Player "+pl+" make your move: ");
            Scanner in = new Scanner(System.in);
            String choice = in.next();
            int x, y;
            if(choice.equals("l")){
                match.loadMatch();
            }else if(choice.equals("s")){
                match.saveMatch();
            }else if(choice.equals("undo")){
                match.undo();
            }else{
                try{
                    x = Integer.parseInt(choice);
                    if(x>=0 && x<=10){
                        try{
                            y = Integer.parseInt(in.next());
                            if(y>=0 && y<=10){
                                Human play = (Human) match.getCurrentPlayer();
                                play.makeMove(x,y);
                            }else {
                                System.out.println("Y coord not in the range, try again!");
                            }
                        }catch (NumberFormatException e){
                            System.out.println("Error, try again. 2");
                        }

                    }else{
                        System.out.println("X coord not in the range, try again!");
                    }
                }catch(NumberFormatException e){
                    System.out.println("Error, try again. 1");
                }




            }

        }

    }
}
