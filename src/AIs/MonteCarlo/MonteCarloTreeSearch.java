package AIs.MonteCarlo;

import AIs.AlphaBeta.EvaluationFunction;
import AIs.PathFinding.PathFindingAlgorithm;
import Game.Board;
import Game.Enums.ColorMode;
import Game.Move;
import Game.NodeCell;

import java.util.ArrayList;
import java.util.Random;
import java.util.jar.Pack200;

/**
 * Created by giogio on 12/1/16.
 */
public class MonteCarloTreeSearch {
    int depthLvl;
    int maxTime;
    double startTime;
    Board initialBoard;
    ColorMode colorMode;

    public MonteCarloTreeSearch(Board board, ColorMode colorMode){
        initialBoard = board;
        this.colorMode = colorMode;
        depthLvl = 5;
        maxTime = 10000;

    }

    public Move start(){

        NodeTree root = new NodeTree(null);
        if(colorMode ==ColorMode.Red)
            root.setColorMode(ColorMode.Blue);
        else
            root.setColorMode(ColorMode.Red);
        startTime = System.currentTimeMillis();
        while (System.currentTimeMillis()-startTime<maxTime){
            NodeTree node= selection(root);
            expansion(node);
        }
        NodeTree bestNode = null;
        float bestVal = 0;
        for(NodeTree child: root.childrens){
            if(child.getValue()>bestVal){
                bestNode = child;
                bestVal = child.getValue();
            }
        }

        return bestNode.getMove();

    }

    public NodeTree selection(NodeTree startingNode){
        if(startingNode.childrens.size()==0)
            return startingNode;
        NodeTree best = startingNode.childrens.get(0);
        float bestVal = best.getValue();
        for (NodeTree node: startingNode.childrens){
            if(node.getValue()>bestVal) {
                best = node;
                bestVal = node.getValue();
            }
        }
        return best;
    }
    public void expansion(NodeTree node){
        Board copy = initialBoard.getCopy();
        NodeTree inBuild = node;
        while (inBuild.parent!=null){
            copy.placeStone(inBuild.getMove().x,inBuild.getMove().y,inBuild.colorMode);
            inBuild = inBuild.getParent();
        }
        ArrayList<Move> freeMoves = copy.getListFreeCell();
        for (Move move: freeMoves){
            if(System.currentTimeMillis()-startTime<maxTime) {
                NodeTree newNode = new NodeTree(node);
                newNode.setMove(move);
                simulation(newNode);
                backpropagation(newNode);
            }else {
                break;
            }
        }
    }
    public void simulation(NodeTree node){
        System.out.println("Starting a simulation!");
        Board copy = initialBoard.getCopy();
        NodeTree inBuild = node;
        while (inBuild.parent!=null){
            copy.placeStone(inBuild.getMove().x,inBuild.getMove().y,inBuild.colorMode);
            inBuild = inBuild.getParent();

        }
        ColorMode currentPlaying;
        if(node.colorMode == ColorMode.Blue)
            currentPlaying = ColorMode.Red;
        else
            currentPlaying = ColorMode.Blue;

        Move previousMove = null;
        while (1 == 1){
            System.out.println("Simulating a move");
            if(currentPlaying == ColorMode.Blue) {
                Move move = blueSimulation(copy,previousMove);
                copy.placeStone(move.x,move.y,currentPlaying);
                previousMove = move;

                if(copy.isConnected(ColorMode.Blue))
                    break;
                currentPlaying = ColorMode.Red;
            }else {
                Move move = redSimulation(copy,previousMove);
                copy.placeStone(move.x,move.y,currentPlaying);
                previousMove = move;

                if(copy.isConnected(ColorMode.Red))
                    break;
                currentPlaying = colorMode.Blue;
            }
        }
        node.games++;
        if(currentPlaying == node.colorMode)
            node.wins++;
        backpropagation(node);
    }

    public static Move redSimulation(Board board, Move enemyMove){
        NodeCell[][] grid = board.getGrid();
        ArrayList<Move> best = new ArrayList<Move>();
        int bestMovesSize = 10000;
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid.length;j++){
                if(grid[i][0].getColor()==null && grid[j][10].getColor()==null) {
                    PathFindingAlgorithm path = new PathFindingAlgorithm(grid[i][0], grid[j][10], ColorMode.Red);
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
                if(grid[i][j].getColor()!= null && grid[i][j].getColor()==ColorMode.Red){
                    for (int k=0;k<grid.length;k++){
                        PathFindingAlgorithm path = new PathFindingAlgorithm(grid[i][j],grid[k][10],ColorMode.Red);
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

        return best.get((int)Math.random()*best.size());





    }
/*
    public static Move redSimulation(Board board, Move enemyMove){

        if(enemyMove!=null) {
            Board copy = board.getCopy();
            copy.getGrid()[enemyMove.x][enemyMove.y].setStatus((byte)0);

            int n_old_bridges = EvaluationFunction.get_n_bridges(copy.getGrid(), ColorMode.Red);
            int currentBridges = EvaluationFunction.get_n_bridges(board.getGrid(), ColorMode.Red);
            //if a bridge was attacked, then it completes it.
            if(n_old_bridges>currentBridges){
                ArrayList<NodeCell> redBridge = board.getGrid()[enemyMove.x][enemyMove.y].getListFriend(ColorMode.Red);
                if(redBridge.size()==1){

                    //to change
                    if(enemyMove.y==0){
                        if(board.getGrid()[enemyMove.x][enemyMove.y].getUpperR().getColor()==ColorMode.Red)
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getUpperL().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getUpperL().getY());
                        else if(board.getGrid()[enemyMove.x][enemyMove.y].getLowerR()!=null)
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getLowerR().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getLowerR().getY());

                    }else{
                        if(board.getGrid()[enemyMove.x][enemyMove.y].getLeft().getColor()==ColorMode.Red)
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getUpperL().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getUpperL().getY());
                        else if(board.getGrid()[enemyMove.x][enemyMove.y].getLowerR()!=null)
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getLowerR().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getLowerR().getY());
                    }

                }else {
                    ArrayList<NodeCell> firstPartFreeCells = redBridge.get(0).getListFreeNeighbours();
                    ArrayList<NodeCell> secondPartFreeCells = redBridge.get(1).getListFreeNeighbours();
                    for (NodeCell nodeCell1 : firstPartFreeCells) {
                        for (NodeCell nodeCell2 : secondPartFreeCells) {
                            if (nodeCell1.equals(nodeCell2))
                                return new Move(nodeCell1.getX(), nodeCell1.getY());
                        }
                    }
                }
            }


        }
        ArrayList<NodeCell> redBestBridge = new ArrayList<NodeCell>();
        ArrayList<NodeCell> blueBestBridge = new ArrayList<NodeCell>();
        for(int i=0;i<board.getGrid().length;i++) {
            for (int j = 0; j < board.getGrid().length; j++) {
                if(board.getGrid()[i][j].getColor()==ColorMode.Blue){
                    blueBestBridge.add(EvaluationFunction.getBestBridge(board.getGrid()[i][j],board.getGrid()));
                }else if(board.getGrid()[i][j].getColor()==ColorMode.Red){
                    redBestBridge.add(EvaluationFunction.getBestBridge(board.getGrid()[i][j],board.getGrid()));
                }
            }
        }

        for (NodeCell red: redBestBridge){
            for (NodeCell blue: blueBestBridge){
                if (red!=null && blue!=null && red.equals(blue)) {
                    return new Move(red.getX(), red.getY());
                }
            }
        }
        Move last = selectBridge(board,ColorMode.Red);
        if(last != null)
            return last;
        else
            return board.getListFreeCell().get((int)Math.random()*board.getListFreeCell().size());

    }
*/
    public static Move selectBridge(Board board, ColorMode colorMode){
        int bestDistance = 1000;
        NodeCell bestCell = null;
        for(int i=0;i<board.getGrid().length;i++){
            for(int j=0;j<board.getGrid().length;j++){

                if(board.getGrid()[i][j].getColor()==colorMode){
                    NodeCell cell = EvaluationFunction.getBestBridge(board.getGrid()[i][j], board.getGrid());
                    if(cell!=null) {

                        int firstDist, secondDist;
                        if (colorMode == ColorMode.Blue) {
                            firstDist = EvaluationFunction.getLeftDistance(cell, board.getGrid());
                            secondDist = EvaluationFunction.getRightDistance(cell, board.getGrid());
                        } else {
                            firstDist = EvaluationFunction.getTopDistance(cell, board.getGrid());
                            secondDist = EvaluationFunction.getBottomDistance(cell, board.getGrid());
                        }

                        if (firstDist < bestDistance) {
                            bestCell = cell;
                            bestDistance = firstDist;
                        }
                        if (secondDist < bestDistance) {
                            bestCell = cell;
                            bestDistance = secondDist;
                        }
                    }

                }


            }
        }
        if(bestCell!=null)
            return new Move(bestCell.getX(),bestCell.getY());
        else
            return null;
    }

    public static Move blueSimulation(Board board, Move enemyMove){
        NodeCell[][] grid = board.getGrid();
        ArrayList<Move> best = new ArrayList<Move>();
        int bestMovesSize = 10000;
        for (int i=0;i<grid.length;i++){
            for (int j=0;j<grid.length;j++){
                if(grid[0][i].getColor()==null && grid[10][j].getColor()==null) {
                    PathFindingAlgorithm path = new PathFindingAlgorithm(grid[0][i], grid[10][j], ColorMode.Blue);
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
                if(grid[i][j].getColor()!= null && grid[i][j].getColor()==ColorMode.Blue){
                    for (int k=0;k<grid.length;k++){
                        PathFindingAlgorithm path = new PathFindingAlgorithm(grid[i][j],grid[10][k],ColorMode.Blue);
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

        return best.get((int)Math.random()*best.size());



    }


/*
    public static Move blueSimulation(Board board, Move enemyMove){

        if(enemyMove!=null) {
            Board copy = board.getCopy();
            copy.getGrid()[enemyMove.x][enemyMove.y].setStatus((byte)0);
            int n_old_bridges = EvaluationFunction.get_n_bridges(copy.getGrid(), ColorMode.Blue);
            int currentBridges = EvaluationFunction.get_n_bridges(board.getGrid(), ColorMode.Blue);
            //if a bridge was attacked, then it completes it.
            if(n_old_bridges>currentBridges){
                ArrayList<NodeCell> blueBridge = board.getGrid()[enemyMove.x][enemyMove.y].getListFriend(ColorMode.Blue);
                if(blueBridge.size()==1){
                    if(enemyMove.x==0){
                        if(board.getGrid()[enemyMove.x][enemyMove.y].getLowerR().getColor()==ColorMode.Blue)
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getRight().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getRight().getY());
                        else
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getLeft().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getLeft().getY());

                    }else{
                        if(board.getGrid()[enemyMove.x][enemyMove.y].getUpperR().getColor()==ColorMode.Blue)
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getRight().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getRight().getY());
                        else
                            return new Move(board.getGrid()[enemyMove.x][enemyMove.y].getLeft().getX(),board.getGrid()[enemyMove.x][enemyMove.y].getLeft().getY());
                    }

                }else {
                    ArrayList<NodeCell> firstPartFreeCells = blueBridge.get(0).getListFreeNeighbours();
                    ArrayList<NodeCell> secondPartFreeCells = blueBridge.get(1).getListFreeNeighbours();
                    for (NodeCell nodeCell1 : firstPartFreeCells) {
                        for (NodeCell nodeCell2 : secondPartFreeCells) {
                            if (nodeCell1.equals(nodeCell2))
                                return new Move(nodeCell1.getX(), nodeCell1.getY());
                        }
                    }
                }
            }


        }
        ArrayList<NodeCell> redBestBridge = new ArrayList<NodeCell>();
        ArrayList<NodeCell> blueBestBridge = new ArrayList<NodeCell>();
        for(int i=0;i<board.getGrid().length;i++) {
            for (int j = 0; j < board.getGrid().length; j++) {
                if(board.getGrid()[i][j].getColor()==ColorMode.Blue){
                    blueBestBridge.add(EvaluationFunction.getBestBridge(board.getGrid()[i][j],board.getGrid()));
                }else if(board.getGrid()[i][j].getColor()==ColorMode.Red){
                    redBestBridge.add(EvaluationFunction.getBestBridge(board.getGrid()[i][j],board.getGrid()));
                }
            }
        }
        if(redBestBridge.size()>0 && blueBestBridge.size()>0) {
            for (NodeCell red : redBestBridge) {
                for (NodeCell blue : blueBestBridge) {

                    if (red!=null && blue!=null && red.equals(blue)) {
                        return new Move(red.getX(), red.getY());
                    }
                }
            }
        }
        Move last = selectBridge(board,ColorMode.Blue);
        if(last != null)
            return last;
        else
            return board.getListFreeCell().get((int)Math.random()*board.getListFreeCell().size());

    }
    */
    public void backpropagation(NodeTree nodeTree){
        NodeTree current = nodeTree;
        while (current.parent!=null){
            current.parent.games++;
            current.parent.wins+=nodeTree.wins;
            current = current.getParent();
            current.setValue(current.wins/current.games);
        }
    }


    public static ArrayList<ArrayList<NodeCell>> getRedDirection(NodeCell[][] grid){

        ArrayList<ArrayList<NodeCell>> groups = EvaluationFunction.getGroups(grid,ColorMode.Red);
        ArrayList<ArrayList<NodeCell>> orderedListLR = new  ArrayList<ArrayList<NodeCell>>();
        while (orderedListLR.size()<groups.size()){
            int mostLeft = 1000;
            ArrayList<NodeCell> mostLeftGroup=null;
            for(ArrayList<NodeCell> group: groups){
                boolean ingroup =false;
                for(ArrayList<NodeCell> orderGroup: orderedListLR){
                    if(group==orderGroup){
                        ingroup = true;
                    }
                }
                if(!ingroup)
                    for (NodeCell node:group)
                        if(node.getY()<mostLeft){
                            mostLeft = node.getY();
                            mostLeftGroup = group;
                        }
            }
            orderedListLR.add(mostLeftGroup);
        }

        ArrayList<ArrayList<NodeCell>> orderedListRL = new  ArrayList<ArrayList<NodeCell>>();
        while (orderedListRL.size()<groups.size()){
            int mostRight= -1;
            ArrayList<NodeCell> mostRightGroup=null;
            for(ArrayList<NodeCell> group: groups){
                boolean ingroup =false;
                for(ArrayList<NodeCell> orderGroup: orderedListRL){
                    if(group==orderGroup){
                        ingroup = true;
                    }
                }
                if(!ingroup)
                    for (NodeCell node:group)
                        if(node.getY()>mostRight){
                            mostRight = node.getY();
                            mostRightGroup = group;
                        }
            }
            orderedListRL.add(mostRightGroup);
        }
/*
        for (int i=0;i<orderedListLR.size();i++){
            if(!orderedListLR.get(i).equals(orderedListRL.get(orderedListRL.size()-1-i))){
                orderedListLR.remove(i+1);
                orderedListRL.remove(orderedListRL.size()-1-i);
                i--;
            }
        }
*/
        return orderedListLR;


    }
    public static ArrayList<ArrayList<NodeCell>> getBlueDirection(NodeCell[][] grid){

        ArrayList<ArrayList<NodeCell>> groups = EvaluationFunction.getGroups(grid,ColorMode.Blue);
        ArrayList<ArrayList<NodeCell>> orderedListUD = new  ArrayList<ArrayList<NodeCell>>();
        while (orderedListUD.size()<groups.size()){
            int mostUp = 1000;
            ArrayList<NodeCell> mostUpGroup=null;
            for(ArrayList<NodeCell> group: groups){
                boolean ingroup =false;
                for(ArrayList<NodeCell> orderGroup: orderedListUD){
                    if(group.equals(orderGroup)){
                        ingroup = true;
                    }
                }
                if(!ingroup)
                    for (NodeCell node:group) {
                        if (node.getX() < mostUp) {
                            mostUp = node.getX();
                            mostUpGroup = group;
                        }
                    }
            }
            orderedListUD.add(mostUpGroup);
            System.out.println("X: "+mostUpGroup.get(0).getX());
        }

        ArrayList<ArrayList<NodeCell>> orderedListDU = new  ArrayList<ArrayList<NodeCell>>();
        while (orderedListDU.size()<groups.size()){
            int mostDown= -1;
            ArrayList<NodeCell> mostDownGroups=null;
            for(ArrayList<NodeCell> group: groups){
                boolean ingroup =false;
                for(ArrayList<NodeCell> orderGroup: orderedListDU){
                    if(group.equals(orderGroup)){
                        ingroup = true;
                    }
                }
                if(!ingroup)
                    for (NodeCell node:group)
                        if(node.getX()>mostDown){
                            mostDown = node.getX();
                            mostDownGroups = group;
                        }
            }
            orderedListDU.add(mostDownGroups);
        }
        System.out.println(orderedListUD.size());
        System.out.println(orderedListDU.size());
        /*
        for (int i=0;i<orderedListUD.size();i++){
            if(!orderedListUD.get(i).equals(orderedListDU.get(orderedListDU.size()-1-i))){
                orderedListUD.remove(i+1);
                orderedListDU.remove(orderedListDU.size()-1-i);
                i--;
            }
        }
*/
        return orderedListUD;

    }
}
