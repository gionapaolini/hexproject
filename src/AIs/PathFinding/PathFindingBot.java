package AIs.PathFinding;

import Game.Enums.ColorMode;
import Game.Match;
import Game.Move;
import Game.NodeCell;

import java.util.ArrayList;

/**
 * Created by giogio on 11/29/16.
 */
public class PathFindingBot {
    Match match;
    ColorMode colorMode;
    public PathFindingBot(Match match, ColorMode colorMode){
        this.match =match;
        this.colorMode = colorMode;
    }
    public Move start(){
        if(colorMode==ColorMode.Blue)
            return bluePath();
        else
            return redPath();


    }
    public Move redPath(){

        NodeCell[][] grid = match.getBoard().getGrid();
        ArrayList<Move> best = new ArrayList<Move>();
        int bestMovesSize = 10000;
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid.length;j++){
                if(grid[i][0].getColor()==null && grid[j][10].getColor()==null) {
                    PathFindingAlgorithm path = new PathFindingAlgorithm(grid[i][0], grid[j][10], colorMode);
                    ArrayList<Move> moves = path.start();
                    if (moves != null && moves.size() < bestMovesSize) {
                        bestMovesSize = moves.size();
                        best = moves;
                    }
                }

            }
        }
        boolean firstToPut = true;
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid.length;j++){
                if(grid[i][j].getColor()!= null && grid[i][j].getColor()==colorMode){
                    for (int k=0;k<grid.length;k++){
                        PathFindingAlgorithm path = new PathFindingAlgorithm(grid[i][j],grid[k][10],colorMode);
                        ArrayList<Move> moves = path.start();
                        if(moves!=null && moves.size()<=bestMovesSize){
                            bestMovesSize=moves.size();
                            best = moves;
                            firstToPut = false;
                        }
                    }

                }

            }
        }
        if(!firstToPut)
            best.remove(best.size()-1);

        return best.get(best.size()-1);




    }

    public Move bluePath(){

        NodeCell[][] grid = match.getBoard().getGrid();
        ArrayList<Move> best = new ArrayList<Move>();
        int bestMovesSize = 10000;
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid.length;j++){
                PathFindingAlgorithm path = new PathFindingAlgorithm(grid[0][i],grid[10][j],colorMode);
                ArrayList<Move> moves = path.start();
                if(moves!=null && moves.size()<bestMovesSize){
                    bestMovesSize=moves.size();
                    best = moves;
                }

            }
        }
        boolean firstToPut = true;

        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid.length;j++){
                if(grid[i][j].getColor()!=null && grid[i][j].getColor()==colorMode){

                    for (int k=0;k<grid.length;k++){
                        PathFindingAlgorithm path = new PathFindingAlgorithm(grid[i][j],grid[10][k],colorMode);
                        ArrayList<Move> moves = path.start();
                        if(moves!=null && moves.size()<bestMovesSize){

                            bestMovesSize=moves.size();
                            best = moves;
                            firstToPut = false;
                        }
                    }

                }

            }
        }

        if(!firstToPut)
            best.remove(best.size()-1);
        return best.get(best.size()-1);




    }

}






