package AIs.MonteCarlo;

import AIs.AlphaBeta.EvaluationFunction;
import AIs.PathFinding.PathFindingAlgorithm;
import AIs.PathFinding.PathFindingBot;
import Game.Board;
import Game.Enums.ColorMode;
import Game.Match;
import Game.Move;
import Game.NodeCell;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.jar.Pack200;

/**
 * Created by giogio on 12/1/16.
 */
public class MonteCarloTreeSearchAlt {
    int depthLvl, max_move_simulation;
    int maxTime;
    double startTime;
    Board initialBoard;
    ColorMode colorMode;
    private int simulationCount;

    public MonteCarloTreeSearchAlt(Board board, ColorMode colorMode){
        initialBoard = board;
        this.colorMode = colorMode;
        depthLvl = 5;
        maxTime = 1050;
        max_move_simulation = 30;

    }

    public void setMaxTime(int max){
        maxTime = max;
    }

    public Move start(){
        simulationCount = 0;
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
        NodeTree bestNode = root.getChildrens().get(0);
        int mostWins = bestNode.games;
        for(NodeTree child: root.childrens){
            if(child.getValue()>mostWins){
                bestNode = child;
                mostWins = child.wins;
            }
        }

        printTree(root);

        System.out.println("Simulations made for this turn:" + simulationCount);
        return bestNode.getMove();


    }

    public void printTree(NodeTree root){
        System.out.println("Root: "+root.getValue());
        System.out.println(root.wins + "/" + root.games);
        printChild(root);


    }
    public void printChild(NodeTree leaf){
        for(NodeTree nodeTree: leaf.getChildrens()) {
            System.out.print(leaf.getDepth());
            for (int i = 0; i < nodeTree.getDepth(); i++) {
                System.out.print( " -- ");
            }
            System.out.println(nodeTree.getMove().toString() + " " + nodeTree.getValue()+" WINS/TOTAL: "+nodeTree.wins+"/"+nodeTree.games);
            if(nodeTree.getChildrens().size()>0){
                printChild(nodeTree);
            }
        }
    }

    public NodeTree selection(NodeTree startingNode){
        if(startingNode.childrens.size()==0)
            return startingNode;
        NodeTree best = startingNode.childrens.get(0);

        double logT = Math.log(startingNode.games);

        double bestVal = 0;

        for (NodeTree node: startingNode.childrens){

            double value = node.wins/node.games+Math.sqrt(2*logT/node.games);
            if(value>bestVal) {
                best = node;
                bestVal = value;
            }
        }

        return selection(best);


    }
    public void expansion(NodeTree node){
        Board copy = initialBoard.getCopy();
        NodeTree inBuild = node;
        while (inBuild.parent!=null){
            copy.placeStone(inBuild.getMove().x,inBuild.getMove().y,inBuild.colorMode);
            inBuild = inBuild.getParent();
        }
        ArrayList<Move> freeMoves = (ArrayList<Move>) copy.getListFreeCell().clone();
        int countMoves = 0;
        while (freeMoves.size()>0 && countMoves<max_move_simulation){
            if(System.currentTimeMillis()-startTime<maxTime) {
                countMoves++;
                Move move = freeMoves.remove((int)(Math.random()*freeMoves.size()));
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
        simulationCount++;
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
            if(currentPlaying == ColorMode.Blue) {
                Move move = blueSimulation(copy,previousMove);
                if(move==null) {
                    currentPlaying = ColorMode.Red;
                    break;
                }
                copy.placeStone(move.x,move.y,currentPlaying);
                previousMove = move;

                if(copy.isConnected(ColorMode.Blue))
                    break;
                currentPlaying = ColorMode.Red;
            }else {
                Move move = redSimulation(copy,previousMove);
                if(move==null) {
                    currentPlaying = ColorMode.Blue;

                    break;
                }
                copy.placeStone(move.x,move.y,currentPlaying);
                previousMove = move;

                if(copy.isConnected(ColorMode.Red))
                    break;
                currentPlaying = colorMode.Blue;
            }
        }
        node.games++;
        if(currentPlaying == node.colorMode) node.wins++;

        node.setValue(node.wins/(float)node.games);

    }

    public static Move redSimulation(Board board, Move enemyMove){
        if(board.getListFreeCell().size()==0){
            return null;
        }
        if(Math.random()<0.1 || board.getListColoredCell(ColorMode.Red).size()==0){
            return board.getListFreeCell().get((int)(Math.random()*board.getListFreeCell().size()));
        }

        if(Math.random()<0.5){

            for (int i = 0;i<board.getListFreeCell().size();i++){
                Move move = board.getListFreeCell().get(i);
                boolean neighbour = false;
                if(board.getGrid()[move.x][move.y].getListFriend(ColorMode.Red).size()==2){
                    NodeCell nodeCell1 = board.getGrid()[move.x][move.y].getListFriend(ColorMode.Red).get(0);
                    NodeCell nodeCell2 = board.getGrid()[move.x][move.y].getListFriend(ColorMode.Red).get(1);
                    for(NodeCell node: nodeCell1.getListFriend(ColorMode.Red)){
                        if(node.equals(nodeCell2))
                            neighbour=true;
                        break;
                    }
                    if(!neighbour)
                        return move;
                }


            }
        }

        ArrayList<Move> moves = (ArrayList<Move>) board.getListColoredCell(ColorMode.Red).clone();
        while (moves.size()<0){
            Move move = moves.remove((int)(Math.random()*moves.size()));
            NodeCell nodeCell = board.getGrid()[move.x][move.y];
            NodeCell bestBridge = EvaluationFunction.getBestBridge(nodeCell,board.getGrid());
            if(bestBridge!=null)
                return new Move(bestBridge.getX(),bestBridge.getY());

        }

        return board.getListFreeCell().get((int)(Math.random()*board.getListFreeCell().size()));






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
        ArrayList<ArrayList<NodeCell>> groups = EvaluationFunction.getGroups(board.getGrid(),ColorMode.Red);
        if(board.getListFreeCell().size()==0)
            return null;
        if(Math.random()<0.1 || board.getListColoredCell(ColorMode.Red).size()==0){
            return board.getListFreeCell().get((int)(Math.random()*board.getListFreeCell().size()));
        }
        if(Math.random()<0.5){

            for (int i = 0;i<board.getListFreeCell().size();i++){
                Move move = board.getListFreeCell().get(i);
                boolean neighbour = false;
                if(board.getGrid()[move.x][move.y].getListFriend(ColorMode.Blue).size()==2){
                    NodeCell nodeCell1 = board.getGrid()[move.x][move.y].getListFriend(ColorMode.Blue).get(0);
                    NodeCell nodeCell2 = board.getGrid()[move.x][move.y].getListFriend(ColorMode.Blue).get(1);
                    for(NodeCell node: nodeCell1.getListFriend(ColorMode.Blue)){
                        if(node.equals(nodeCell2))
                            neighbour=true;
                        break;
                    }
                    if(!neighbour)
                        return move;
                }


            }
        }

        ArrayList<Move> moves = (ArrayList<Move>) board.getListColoredCell(ColorMode.Blue).clone();
        while (moves.size()<0){
            Move move = moves.remove((int)(Math.random()*moves.size()));
            NodeCell nodeCell = board.getGrid()[move.x][move.y];
            NodeCell bestBridge = EvaluationFunction.getBestBridge(nodeCell,board.getGrid());
            if(bestBridge!=null)
                return new Move(bestBridge.getX(),bestBridge.getY());

        }

        return board.getListFreeCell().get((int)(Math.random()*board.getListFreeCell().size()));
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
            boolean win = (nodeTree.wins>0);
       // System.out.println("win:" + nodeTree.wins);
        while (current.parent!=null){
            win = !win;
            current.parent.games++;
            if (win)current.parent.wins+=1;//
            System.out.println("win:" + current.parent.wins);
            current = current.getParent();

            float value = current.wins/(float)current.games;
            current.setValue(value);

        }
     //   System.out.println();
        printTree(current); //prints the tree every time
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
