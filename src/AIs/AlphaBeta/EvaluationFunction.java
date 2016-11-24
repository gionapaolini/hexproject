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

    public static int uselessCount(Board testBoard){
        int count=0;
        for (int i=0;i<testBoard.getGrid().length;i++){
            for (int j=0;j<testBoard.getGrid().length;j++) {
                if(testBoard.getGrid()[i][j].getColor()==ColorMode.Blue)
                    if(useless(testBoard, i,j))
                        count++;
            }
        }
        return count;
    }

    public static boolean useless(Board testBoard, int h, int l){

        NodeTree child = new NodeTree(null);
        child.x = h;
        child.y = l;
        int count=0;
        int oppositeCol=0;

        for(int i=child.y-1;i<child.y+1;i++){
            for (int j=child.x-1;j<child.x+1;j++){
                if((i==child.y-1 && j==child.x-1)||(i==child.y+1 && j==child.x+1)||(i==child.y && j==child.x))
                    continue;
                if(testBoard.getGrid()[j][i].getStatus()==0)
                    count++;
                else
                    if(testBoard.getGrid()[j][i].getColor()!=ColorMode.Blue)
                        oppositeCol++;
            }
        }
        if (count==2 && oppositeCol==4){
            int[][] coords = {{-1,0},{-1,+1},{0,+1},{+1,0},{+1,-1},{0,-1},{-1,0}};
            boolean checks = false;
            for(int k = 0;k<7;k++){
                int currentX = child.x+coords[k][0];
                int currentY = child.y+coords[k][0];
                if(checks==true){
                    if(testBoard.getGrid()[currentX][k].getStatus()==0){
                        return true;
                    }
                    return false;
                }
                if(testBoard.getGrid()[currentY][k].getStatus()==0){
                    checks=true;
                }

            }


        }
        return false;




    }
}
