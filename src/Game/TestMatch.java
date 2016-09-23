package Game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by giogio on 9/17/16.
 */
public class TestMatch {
    public static void main(String[] args) throws InterruptedException, IOException {
        Match match = new Match(false,false,false,false,11);
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
            int x = in.nextInt();
            int y = in.nextInt();
            Human play = (Human) match.getCurrentPlayer();
            play.makeMove(x,y);
        }

    }
}
