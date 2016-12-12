package Game;

import Game.Enums.ColorMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giogio on 9/17/16.
 */
public class Board {
    private NodeCell[][] grid;
    int side;

    protected Board(int n){
        side = n;
        //---Initialize the board---
        grid=new NodeCell[n][n];
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                grid[i][j]=new NodeCell(i, j);
            }
        }
        //---Set the connections---
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){

                if(j>0){
                    grid[i][j].setLeft(grid[i][j-1]);
                }
                if(j<n-1){
                    grid[i][j].setRight(grid[i][j+1]);
                }

                if(i>0){
                    grid[i][j].setUpperL(grid[i-1][j]);
                    if(j<n-1){
                        grid[i][j].setUpperR(grid[i-1][j+1]);
                    }
                }
                if(i<n-1){
                    grid[i][j].setLowerR(grid[i+1][j]);
                    if(j>0){
                        grid[i][j].setLowerL(grid[i+1][j-1]);
                    }
                }

            }
        }
    }

    public boolean isConnected(ColorMode colorMode){
        if(colorMode==ColorMode.Blue){
            //Check if one of the cell in the first row is connected to one of the last
            for(int i=0;i<grid[0].length;i++){
                for(int j=0;j<grid[0].length;j++){
                    uncheckGrid();
                    if(grid[0][i].getStatus()==1 && grid[0][i].isConnected(grid[grid.length-1][j])){
                        return true;

                    }

                }
            }
            return false;
        }else{
            //Check if one of the cell in the first column is connected to one of the last
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid.length;j++){
                    uncheckGrid();
                    if(grid[i][0].getStatus()==2 && grid[i][0].isConnected(grid[j][grid[0].length-1])){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void uncheckGrid(){
        for(int i=0;i<grid.length;i++){
            for (int j=0;j<grid[0].length;j++){
                grid[i][j].uncheck();
            }
        }
    }

    public void printGrid(){
        System.out.println();
        System.out.println();

        for(int i=0;i<grid.length;i++){
            System.out.print("|p");
            for(int j=0;j<grid.length;j++){
                if(grid[i][j].getStatus()==0){
                    System.out.print(" X");
                }else if(grid[i][j].getStatus()==1){
                    System.out.print(" O");
                }else {
                    System.out.print(" Q");
                }
                System.out.print(" |");
            }
            System.out.println();
        }

    }
    public NodeCell[][] getGrid() {
        return grid;
    }

   public void placeStone(int x, int y, ColorMode colorMode){
       grid[x][y].setStatus(colorMode);
   }

   public Board getCopy(){
       Board b = new Board(side);
       for (int i = 0;i<b.getGrid().length;i++){
           for (int j = 0;j<b.getGrid().length;j++){
                b.getGrid()[i][j].setStatus(grid[i][j].getStatus());
           }
       }
       return b;
   }

   public ArrayList<Move> getListFreeCell(){
       ArrayList<Move> moves = new ArrayList<Move>();
       for (int i = 0;i<grid.length;i++){
           for (int j = 0;j<grid.length;j++){
               if(grid[i][j].getStatus()==0){
                   moves.add(new Move(i,j));
               }
           }
       }
       return moves;
   }

    public ArrayList<Move> getListColoredCell(ColorMode colorMode){
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 0;i<grid.length;i++){
            for (int j = 0;j<grid.length;j++){
                if(grid[i][j].getColor()!=null && grid[i][j].getColor()==colorMode){
                    moves.add(new Move(i,j));
                }
            }
        }
        return moves;
    }
}
