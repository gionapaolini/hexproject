package Game;

/**
 * Created by giogio on 9/17/16.
 */
public class Board {
    private NodeCell[][] grid;

    Board(int n){
        //---Initialize the board---
        grid=new NodeCell[n][n];
        for(int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                grid[i][j]=new NodeCell();
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

    public boolean isConnected(boolean player){
        if(player){
            //Check if one of the cell in the first row is connected to one of the last
            for(int i=0;i<grid[0].length;i++){
                for(int j=0;j<grid[0].length;j++){
                    uncheckGrid();
                    if(grid[0][i].isConnectedTo(grid[grid.length-1][j])){
                        return true;
                    }

                }
            }
            return false;
        }else {
            //Check if one of the cell in the first column is connected to one of the last
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid.length;j++){
                    uncheckGrid();
                    if(grid[i][0].isConnectedTo(grid[j][grid[0].length-1])){
                        uncheckGrid();
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


    public NodeCell[][] getGrid() {
        return grid;
    }

   public void placeStone(int x, int y, int value){
       grid[x][y].setStatus((byte) value);
   }
}
