package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by PabloPSoto and Lukas on 28/09/2016.
 */

public class BoardPanel extends JPanel implements Observer{

    Match match;
    private int[] lastSelected;

    public BoardPanel(Match m){
        match = m;
        setSize(745,422);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (lastSelected!=null) {
                    m.putStone(lastSelected[1],lastSelected[2]);
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
        addMouseMotionListener(new MouseMotionListener() {
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
                        Polygon p = getPolygon(startX,startY);
                        startX = startX + 20;
                        startY = startY + 30;
                        if (!p.contains(x,y)) continue;
                        if(!(grid[i][j].getStatus()==0)){
                            lastSelected=null;
                            break outerloop;
                        }else {
                            lastSelected= new int[]{match.getCurrentPlayer().getplayerID(),i, j};
                            repaint();
                            break outerloop;
                        }

                    }
                    startY=30;
                    startX=60+((1+i)*(2*20));
                }
            }
        });
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

        int startXT=0;
        int startYT=0;
        //Color borders
        int[] t1x = new int[3];
        t1x[0] = startXT+0; t1x[1] = startXT+480; t1x[2] = startXT+360;
        int[] t1y = new int[3];
        t1y[0] = startYT+0; t1y[1] = startYT+0; t1y[2] = startYT+180;
        int[] t2x = new int[3];
        t2x[0] = startXT+480; t2x[1] = startXT+720; t2x[2] = startXT+360;
        int[] t2y = new int[3];
        t2y[0] = startYT+0; t2y[1] = startYT+360; t2y[2] = startYT+180;
        int[] t3x = new int[3];
        t3x[0] = startXT+240; t3x[1] = startXT+720; t3x[2] = startXT+360;
        int[] t3y = new int[3];
        t3y[0] = startYT+360; t3y[1] = startYT+360; t3y[2] = startYT+180;
        int[] t4x = new int[3];
        t4x[0] = startXT+240; t4x[1] = startXT+0; t4x[2] = startXT+360;
        int[] t4y = new int[3];
        t4y[0] = startYT+360; t4y[1] = startYT+0; t4y[2] = startYT+180;

        g.setColor(Color.red);
        g.fillPolygon(t1x,t1y,3);
        g.fillPolygon(t3x,t3y,3);
        g.setColor(Color.blue);
        g.fillPolygon(t2x,t2y,3);
        g.fillPolygon(t4x,t4y,3);


        //cells
        NodeCell[][] grid = match.getBoard().getGrid();

        int startX = 60;
        int startY = 30;


        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j].getStatus()==0){
                    g.setColor(Color.lightGray);
                    if (lastSelected!=null){
                    if (lastSelected[1]==i&&lastSelected[2]==j) {
                        if(lastSelected[0]==1){
                            g.setColor(Color.blue);
                        }else {
                            g.setColor(Color.red);
                        }
                    }}

                }else if(grid[i][j].getStatus()==1){
                    g.setColor(Color.blue);
                }else {
                    g.setColor(Color.red);
                }


                g.fillPolygon(getPolygon(startX,startY));
                g.setColor(Color.black);
                g.drawPolygon(getPolygon(startX,startY));
                startX = startX + 20;
                startY = startY + 30;


            }
            startY=30;
            startX=60+((1+i)*(2*20));
        }


        String LetterString = "ABCDEFGHIJK";
        int startLX=36;
        int startLY=15;
        for(int i = 0;i<LetterString.length();i++){
            g.drawString(String.valueOf(LetterString.charAt(i)),startLX,startLY);
            startLX=startLX+40;

        }

        LetterString = "ABCDEFGHIJK";
        startLX=275;
        startLY=355;
        for(int i = 0;i<LetterString.length();i++){
            g.drawString(String.valueOf(LetterString.charAt(i)),startLX,startLY);
           startLX=startLX+40;

        }



        //labels
        g.setColor(Color.white);
        startLX=25;
        startLY=35;
        for(int i = 1;i<grid.length+1;i++){
            g.drawString(Integer.toString(i),startLX,startLY);
            startLX=startLX+20;
            startLY=startLY+30;
        }


        int startRX=481;
        int startRY=35;
        for(int j = 1;j<grid.length+1;j++){
            g.drawString(Integer.toString(j),startRX,startRY);
            startRX=startRX+20;
            startRY=startRY+30;
        }
    }
}
