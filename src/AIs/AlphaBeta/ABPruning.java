package AIs.AlphaBeta;

import Game.Board;
import Game.Enums.ColorMode;
import Game.Move;
import Game.NodeCell;

import java.util.ArrayList;

/**
 * Created by giogio on 11/27/16.
 */
public class ABPruning {
    Board initialState;
    ColorMode colorMode;
    NodeTree root;
    int depthLvl;
    int maxTime;
    double startTime;
    public ABPruning(Board board, ColorMode colorMode){
        initialState=board;
        this.colorMode=colorMode;
        depthLvl = 3;
        maxTime = 500;
    }

    public Move start(){
        System.out.println("Begin");
        root = new NodeTree(null);
        if(colorMode==ColorMode.Blue)
            root.setColor(ColorMode.Red);
        else
            root.setColor(ColorMode.Blue);
        startTime = System.currentTimeMillis();
        expansion(root);
        NodeTree best = root.getChildren().get(0);
        for(NodeTree node: root.getChildren()){
            if(node.getValue()>best.getValue())
                best=node;
        }
        return new Move(best.x,best.y);
    }

    public void expansion(NodeTree node){
        if(node.getDepth()!=0) {
            simulation(node);
            evaluate(node);
        }

        if(System.currentTimeMillis()-startTime>maxTime){
            System.out.println("TimeLimit");
            return;
        }

        if(node.getDepth()<depthLvl) {
            Board copy;
            copy = buildBoardWithStone(node);
            for (int i = 0; i < copy.getListFreeCell().size(); i++) {
                if(!prune(node))
                    expansion(new NodeTree(node));
            }
            if(node.getDepth()>0)
                backpropagate(node);
        }else{
            System.out.println("Child value: "+node.getValue());
            backpropagate(node);
        }

    }

    public boolean prune(NodeTree child){
        if(child.getDepth()==0)
            return false;
        if(child.getColor()!=colorMode && child.getValue()<child.getParent().getValue()) {
            return true;

        }

        if(child.getColor()==colorMode && child.getValue()>child.getParent().getValue()){
            return true;

        }

        return false;
    }

    public void simulation(NodeTree child){
        Board testBoard = buildBoardFromParent(child);
        int x,y;
        ArrayList<Move> freeCells = testBoard.getListFreeCell();
        Move move = getFreeMove(child,freeCells);
        child.x = move.x;
        child.y = move.y;
    }

    public void evaluate(NodeTree child){
        Board testBoard = buildBoardWithStone(child);
        float firstEval = EvaluationFunction.get_n_bridges(testBoard.getGrid(),child.getColor());
        ArrayList<ArrayList<NodeCell>> groups = EvaluationFunction.getGroups(testBoard.getGrid(),child.getColor());
        float best = 1;
        for(ArrayList<NodeCell> group: groups){
            if(group.size()>best)
                best=group.size();
        }

        if(testBoard.getListColoredCell(child.getColor()).size()>10){
            firstEval/=2;
        }else {
            best/=2;
        }

        float secondEval = EvaluationFunction.get_n_bridges(testBoard.getGrid(),child.getParent().getColor());
        if(testBoard.getListColoredCell(child.getParent().getColor()).size()>10){
            secondEval/=2;
        }

        child.setValue(firstEval+best-secondEval);
    }

    public void backpropagate(NodeTree child){
        if(child.getColor()==colorMode){
            if(child.getParent().getValue()==0 || child.getParent().getValue()>child.getValue()){
                child.getParent().setValue(child.getValue());
            }
        }else {
            if(child.getParent().getValue()==0 || child.getParent().getValue()<child.getValue()){
                child.getParent().setValue(child.getValue());
            }
        }
    }

    public Move getFreeMove(NodeTree node, ArrayList<Move> freeCells){
        ArrayList<Move> neighbourMoves = new ArrayList<Move>();
        ArrayList<Move> goodMoves = new ArrayList<Move>();
        for (NodeTree neighbour: node.getParent().getChildren()){
            if(neighbour!=node)
                neighbourMoves.add(new Move(neighbour.x,neighbour.y));
        }
        for(Move move: freeCells){
            boolean equal = false;
            for(Move move1: neighbourMoves){
                if (move.x == move1.x && move.y == move1.y){
                    equal = true;
                }
            }
            if(!equal)
                goodMoves.add(move);

        }
        int random = (int)(Math.random()*goodMoves.size());

        return goodMoves.get(random);
    }

    public Board buildBoardWithStone(NodeTree nodeTree){
        Board copyBoard = initialState.getCopy();
        NodeTree currentNode = nodeTree;
        while (currentNode.getParent()!=null){
            copyBoard.placeStone(currentNode.x,currentNode.y,currentNode.getColor());
            currentNode = currentNode.getParent();
        }
        return copyBoard;
    }
    public Board buildBoardFromParent(NodeTree nodeTree){
        Board copyBoard = initialState.getCopy();
        NodeTree currentNode = nodeTree.getParent();
        while (currentNode.getParent()!=null){
            copyBoard.placeStone(currentNode.x,currentNode.y,currentNode.getColor());
            currentNode = currentNode.getParent();
        }
        return copyBoard;
    }
}
