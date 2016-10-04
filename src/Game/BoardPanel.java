package Game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PabloPSoto and Lukas on 28/09/2016.
 */

public class BoardPanel extends JPanel implements Observer{

    Match match;

    public BoardPanel(Match m){
        match = m;
    }
    /*
     *
     */
    public void update(){
        repaint();
    }
    private Polygon getPolygon(int midX, int midY){
        int length = 20;

        int[] x = new int[6];
        int[] y = new int[6];

        x[0] = midX;
        y[0] = midY - length;

        x[1] = midX + length;
        y[1] = midY - (int) (0.5*length);

        x[2] = midX + length;
        y[2] = midY + (int) (0.5*length);

        x[3] = midX;
        y[3] = midY + length;

        x[4] = midX - length;
        y[4] = midY + (int) (0.5*length);

        x[5] = midX - length;
        y[5] = midY - (int) (0.5*length);

        return new Polygon(x, y, 6);
    }

    public void paint(Graphics g){

        //Color borders
        int[] t1x = new int[3];
        t1x[0] = 40; t1x[1] = 520; t1x[2] = 400;
        int[] t1y = new int[3];
        t1y[0] = 70; t1y[1] = 70; t1y[2] = 250;
        int[] t2x = new int[3];
        t2x[0] = 520; t2x[1] = 760; t2x[2] = 400;
        int[] t2y = new int[3];
        t2y[0] = 70; t2y[1] = 430; t2y[2] = 250;
        int[] t3x = new int[3];
        t3x[0] = 280; t3x[1] = 760; t3x[2] = 400;
        int[] t3y = new int[3];
        t3y[0] = 430; t3y[1] = 430; t3y[2] = 250;
        int[] t4x = new int[3];
        t4x[0] = 280; t4x[1] = 40; t4x[2] = 400;
        int[] t4y = new int[3];
        t4y[0] = 430; t4y[1] = 70; t4y[2] = 250;

        g.setColor(Color.red);
        g.fillPolygon(t1x,t1y,3);
        g.fillPolygon(t3x,t3y,3);
        g.setColor(Color.blue);
        g.fillPolygon(t2x,t2y,3);
        g.fillPolygon(t4x,t4y,3);


        //cells
        NodeCell[][] grid = match.getBoard().getGrid();

        int startX = 100;
        int startY = 100;


        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j].getStatus()==0){
                    g.setColor(Color.lightGray);
                }else if(grid[i][j].getStatus()==1){
                    g.setColor(Color.red);
                }else {
                    g.setColor(Color.blue);
                }
                g.fillPolygon(getPolygon(startX,startY));
                g.setColor(Color.black);
                g.drawPolygon(getPolygon(startX,startY));
                startX = startX + 20;
                startY = startY + 30;


            }
            startY=100;
            startX=100+((1+i)*(2*20));
        }
        //labels
        g.setColor(Color.white);
        g.drawString("A        B        C        D        E        F        G        H        I         J        K",76,85);
        g.drawString("A        B        C        D        E        F        G        H        I         J        K",315,425);

        int startLX=65;
        int startLY=105;
        for(int i = 1;i<grid.length+1;i++){
            g.drawString(Integer.toString(i),startLX,startLY);
            startLX=startLX+20;
            startLY=startLY+30;
        }

        int startRX=521;
        int startRY=105;
        for(int j = 1;j<grid.length+1;j++){
            g.drawString(Integer.toString(j),startRX,startRY);
            startRX=startRX+20;
            startRY=startRY+30;
        }
    }
}
