package AIs.AlphaBeta;

import Game.Board;
import Game.Enums.ColorMode;
import Game.NodeCell;

/**
 * Created by giogio on 11/16/16.
 */
public class EvaluationFunction {
    public static int EvaluationFunction(NodeCell[][] grid, ColorMode colorMode){
        int point = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid.length;j++){
                if(grid[i][j].getColor()==colorMode && grid[i][j].getColor()==ColorMode.Blue){

                    if(j!=10 && i==1 && grid[0][j].getColor()==null && grid[0][j+1].getColor()==null){
                        point++;

                    }
                    if(j!=0 && i==9 && grid[10][j].getColor()==null && grid[10][j-1].getColor()==null){
                        point++;
                        System.out.println("fsf");
                    }







                    //check the left node
                    if((j-1)>=0 && (i+1)<11 && grid[i][j-1]!=null && grid[i][j-1].getColor()==null && grid[i+1][j-1]!=null && grid[i+1][j-1].getColor()==null){
                        if((j-2)>=0 && grid[i+1][j-2]!=null && grid[i+1][j-2].getColor()==colorMode){

                            point++;
                        }


                    }

                    if((j-1)>=0 && (i+1)<11 && grid[i+1][j-1]!=null && grid[i+1][j-1].getColor()==null && grid[i+1][j]!=null && grid[i+1][j].getColor()==null){
                        if((i+2)<11 && grid[i+2][j-1]!=null && grid[i+2][j-1].getColor()==colorMode){
                            point++;
                        }

                    }

                    if((j+1)<11 && (i+1)<11 && grid[i+1][j]!=null && grid[i+1][j].getColor()==null && grid[i][j+1]!=null && grid[i][j+1].getColor()==null){
                        if(grid[i+1][j+1]!=null && grid[i+1][j+1].getColor()==colorMode){
                            point++;
                        }

                    }



                }

            }
        }
        return point;
    }
}
