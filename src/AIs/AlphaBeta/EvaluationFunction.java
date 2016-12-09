package AIs.AlphaBeta;

import AIs.PathFinding.PathFindingAlgorithm;
import Game.Board;
import Game.Enums.ColorMode;
import Game.Move;
import Game.NodeCell;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by giogio on 11/16/16.
 */
public class EvaluationFunction {

    public static int get_n_bridges(NodeCell[][] grid, ColorMode colorMode){
        int point = 0;
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid.length;j++){
                if(grid[i][j].getColor()==colorMode){

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

    public static int uselessCount(Board testBoard,ColorMode color){
        int count=0;
        for (int i=0;i<testBoard.getGrid().length;i++){
            for (int j=0;j<testBoard.getGrid().length;j++) {
                if(testBoard.getGrid()[i][j].getColor()==color)
                    if(useless(testBoard, i,j,color))
                        count++;
            }
        }
        return count;
    }

    public static boolean useless(Board testBoard, int x, int y, ColorMode color){

        int count=0;
        int oppositeCol=0;
        for(int i=y-1;i<y+2;i++){
            for (int j=x-1;j<x+2;j++){
                if(i<0 || i>10 || j<0 || j>10 || (i==y-1 && j==x-1)||(i==y+1 && j==x+1)||(i==y && j==x)){
                    continue;
                }

                if(testBoard.getGrid()[j][i].getStatus()==0) {
                    count++;
                }else if(testBoard.getGrid()[j][i].getColor()!=color) {
                    oppositeCol++;
                }
            }
        }


        if (count==2 && oppositeCol==4){
            int[][] coords = {{-1,0},{-1,+1},{0,+1},{+1,0},{+1,-1},{0,-1},{-1,0}};
            boolean checks = false;
            for(int k = 0;k<7;k++){
                int currentX = x+coords[k][0];
                int currentY = y+coords[k][1];
                if(checks==true){
                    if(testBoard.getGrid()[currentX][currentY].getStatus()==0){
                        return true;
                    }
                    return false;
                }
                if(testBoard.getGrid()[currentX][currentY].getStatus()==0){
                    checks=true;
                }

            }


        }
        return false;
    }


    public static NodeCell getBestBridge(NodeCell nodeCell, NodeCell[][] grid){
        if(nodeCell.getColor()==ColorMode.Blue) {
            int leftDistance = getLeftDistance(nodeCell,grid);
            int rightDistance = getRightDistance(nodeCell,grid);
            int best = 1000;
            NodeCell bestCell = null;
            for (NodeCell cell : nodeCell.getPossibleBridgesList()) {
                int cellLeftDistance = getLeftDistance(cell,grid);
                int cellRightDistance = getRightDistance(cell,grid);
                if(leftDistance+cellRightDistance<best){
                    bestCell = cell;
                    best = leftDistance+cellRightDistance;
                }
                if(rightDistance+cellLeftDistance<best){
                    bestCell = cell;
                    best = rightDistance+cellLeftDistance;
                }

            }
            return bestCell;
        }else {
            int topDistance = getTopDistance(nodeCell,grid);
            int bottomDistance = getBottomDistance(nodeCell,grid);
            int best = 1000;
            NodeCell bestCell = null;
            for (NodeCell cell : nodeCell.getPossibleBridgesList()) {

                int cellTopDistance = getTopDistance(cell,grid);
                int cellBottomDistance = getBottomDistance(cell,grid);
                if(topDistance+cellBottomDistance<best){
                    bestCell = cell;
                    best = topDistance+cellBottomDistance;
                }
                if(bottomDistance+cellTopDistance<best){
                    bestCell = cell;
                    best = bottomDistance+cellTopDistance;
                }

            }
            return bestCell;

        }

    }

    public static int getLeftDistance(NodeCell nodeCell, NodeCell[][] grid){
        int best = 100;
        for (int i=0;i<grid.length;i++){


            PathFindingAlgorithm pathFindingAlgorithm = new PathFindingAlgorithm(nodeCell,grid[0][i],ColorMode.Blue);
            ArrayList<Move> path = pathFindingAlgorithm.start();
            int dist;
            if(path!=null)
                dist = path.size();
            else
                dist = 100;
            if(dist<best){
                best = dist;
            }
        }
        return best;
    }
    public static int getRightDistance(NodeCell nodeCell, NodeCell[][] grid){
        int best = 100;
        for (int i=0;i<grid.length;i++){
            PathFindingAlgorithm pathFindingAlgorithm = new PathFindingAlgorithm(nodeCell,grid[10][i],ColorMode.Blue);
            ArrayList<Move> path = pathFindingAlgorithm.start();
            int dist;
            if(path!=null)
                dist = path.size();
            else
                dist = 100;
            if(dist<best){
                best = dist;
            }
        }
        return best;
    }
    public static int getTopDistance(NodeCell nodeCell, NodeCell[][] grid){
        int best = 100;
        for (int i=0;i<grid.length;i++){

            PathFindingAlgorithm pathFindingAlgorithm = new PathFindingAlgorithm(nodeCell,grid[i][0],ColorMode.Red);
            ArrayList<Move> path = pathFindingAlgorithm.start();
            int dist;
            if(path!=null)
                dist = path.size();
            else
                dist = 100;

            if(dist<best){
                best = dist;
            }
        }
        return best;
    }
    public static int getBottomDistance(NodeCell nodeCell, NodeCell[][] grid){
        int best = 100;
        for (int i=0;i<grid.length;i++){
            PathFindingAlgorithm pathFindingAlgorithm = new PathFindingAlgorithm(nodeCell,grid[i][10],ColorMode.Red);
            ArrayList<Move> path = pathFindingAlgorithm.start();
            int dist;
            if(path!=null)
                dist = path.size();
            else
                dist = 100;
            if(dist<best){
                best = dist;
            }
        }
        return best;
    }

    public static ArrayList<ArrayList<NodeCell>> getGroups(NodeCell[][] grid, ColorMode colorMode){
        ArrayList<ArrayList<NodeCell>> listOfList = new ArrayList<ArrayList<NodeCell>>();
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid.length;j++){
                if(grid[i][j].getColor() == colorMode && !isInGroups(grid[i][j],listOfList)){

                        ArrayList<NodeCell> group = new ArrayList<NodeCell>();
                        listOfList.add(group);
                        group.add(grid[i][j]);
                        System.out.println("Group created: "+i+","+j);
                        if (grid[i][j].getListFriend(colorMode).size() == 0)
                            break;
                        checkfriends(grid[i][j], group);

                }
            }
        }
        return listOfList;
    }

    public static void checkfriends(NodeCell nodeCell, ArrayList<NodeCell> group){
        for (NodeCell node: nodeCell.getListFriend(nodeCell.getColor())){
            if(!isInGroup(node,group)){
                group.add(node);
                checkfriends(node, group);
            }
        }
    }

    public static boolean isInGroup(NodeCell nodeCell, ArrayList<NodeCell> group){
        for (NodeCell node: group){
            if(node == nodeCell) {

                return true;
            }
        }
        return false;
    }
    public static boolean isInGroups(NodeCell nodeCell, ArrayList<ArrayList<NodeCell>> group){
        for (ArrayList<NodeCell> list: group) {
            if(isInGroup(nodeCell,list))
                return true;
        }
        return false;
    }

    public static int getDistanceGroupGroup(ArrayList<NodeCell> group1, ArrayList<NodeCell> group2){
        int bestDistance = 1000;
        for (NodeCell nodeCell: group1){
            for (NodeCell nodeCell2: group2){
                PathFindingAlgorithm algorithm = new PathFindingAlgorithm(nodeCell,nodeCell2,nodeCell.getColor());
                int newDist = algorithm.start().size();
                if(newDist<bestDistance)
                    bestDistance = newDist;
            }
        }
        return bestDistance;
    }

    public static int getDistanceNodeGroup(NodeCell node, ArrayList<NodeCell> group2){
        int bestDistance = 1000;

        for (NodeCell nodeCell2: group2){
            PathFindingAlgorithm algorithm = new PathFindingAlgorithm(node,nodeCell2,nodeCell2.getColor());
            int newDist = algorithm.start().size();
            if(newDist<bestDistance)
                bestDistance = newDist;
        }

        return bestDistance;
    }


    public ArrayList<ArrayList<NodeCell>> getForwardConnection(ArrayList<ArrayList<NodeCell>> list){
        if(list.get(0).get(0).getColor()==ColorMode.Red){
            return redDirection(list);
        }else {
            return blueDirection(list);
        }

    }

    public ArrayList<ArrayList<NodeCell>> redDirection(ArrayList<ArrayList<NodeCell>> list){
        for (ArrayList<NodeCell> group1: list){
            for (ArrayList<NodeCell> group2: list){
                if(group1 == group2)
                    continue;
                //TO COMPLETE
            }
        }
        return null;

    }

    public ArrayList<ArrayList<NodeCell>> blueDirection(ArrayList<ArrayList<NodeCell>> list){
        //TO COMPLETE
        return null;
    }

}
