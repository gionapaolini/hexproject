package AIs.GenEncoder.Phaenotype;

import Game.NodeCell;

/**
 * Created by Nibbla on 08.12.2016.
 */
public class SliceSet extends AIU {
    //n*n slice sets wich determine the state on the board
    byte[][][] horizontalsStaks; //the first number is the index. from 0 to n-1, the second number is the nummer of indexType
    byte[][][] vertical; //the first number is the row. from 0 to n-1, the second number is the nummer of indexType
    byte[][][] cross;
    byte[][][] inversiBoard;
    private byte[][] horizonatals;
    private byte[][] verticals;

    public static SliceSet createSlicesFromGrid(NodeCell[][] grid) {
        SliceSet messer = new SliceSet();
        //whatever country we might find. 5 colors are enough to sequence it.
        StringBuilder horizontal  = new StringBuilder(grid[0].length);
        StringBuilder vertical  = new StringBuilder(grid[0].length);
        StringBuilder cross  = new StringBuilder(grid[0].length);
        StringBuilder crossInverse  = new StringBuilder(grid[0].length);
        int columnCount = grid.length;
        int rowCount = grid.length;

        int countRow = 0;
        //slice Three Times and everything is good
        //check the forth time and you maybe have something...

        byte[] lastColumnValue = new byte[columnCount];
        lastColumnValue[0] = -1;

        for (int r = 0; r<rowCount;r++){
            byte lastValueRow = -1;


            for (int c = 0; c<columnCount;c++){
               // if (grid[c][r].getStatus()!=lastColumnRow[c]){

                //}
                if (grid[c][r].getStatus()!=lastValueRow){
                   //someHowAddToTheSlice
                    lastValueRow =grid[c][r].getStatus();
                }
            }
        }



        return messer;
    }

    public byte[][] getHorizonatals() {
        return horizonatals;
    }

    public byte[][] getVerticals() {
        return verticals;
    }

    public byte[][][] getCross() {
        return cross;
    }
}
