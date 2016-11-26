package AIs.AlphaBeta;

import Game.Board;
import Game.Enums.ColorMode;
import Game.Move;
import Game.NodeCell;

import java.util.ArrayList;

/**
 * Created by giogio on 11/23/16.
 */
public class AlphaBetaTree {
    Board initialState;
    ColorMode colorMode;
    NodeTree root;
    public AlphaBetaTree(Board board, ColorMode colorMode){
        initialState=board;
        this.colorMode=colorMode;
    }

    public Move start(){
        System.out.println("Begin");
        root = new NodeTree(null);
        root.setColor(colorMode);
        expansion(root);
        NodeTree best = root.getChildren().get(0);
        for(NodeTree node: root.getChildren()){
            if(node.getValue()>best.getValue())
                best=node;
        }
        return new Move(best.x,best.y);


    }

    public void expansion(NodeTree parentNode){
        double startTime = System.currentTimeMillis();
        for(int i=0;i<initialState.getListFreeCell().size();i++){
            NodeTree newChild = new NodeTree(parentNode);
            simulation(newChild);
            Board testboard = buildBoard(newChild);

            if(!EvaluationFunction.useless(testboard,newChild.x,newChild.y,newChild.getColor())){
                for (int j=0;j<initialState.getListFreeCell().size()-1;j++){
                    System.out.println("HereINSIDE");
                    NodeTree secondChild = new NodeTree(newChild);
                    simulation(secondChild);
                    evaluate(secondChild);
                    if(secondChild.getValue()==0) {
                        System.out.println("Valued0");
                        break;
                    }
                    backpropagate(secondChild);
                    if(newChild.getValue()<newChild.getParent().getValue()){
                        System.out.println("Pruned!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        break;
                    }
                }
                backpropagate(newChild);
            }

            if(startTime-System.currentTimeMillis()>10000){
                System.out.println("BREAKED");
                break;
            }
        }
    }



    public void backpropagate(NodeTree child){
        if(child.getParent().getColor()!=colorMode){
            if(child.getParent().getValue()==0 || child.getParent().getValue()>child.getValue()){
                child.getParent().setValue(child.getValue());
            }
        }else {
            if(child.getParent().getValue()<child.getValue()){
                child.getParent().setValue(child.getValue());
            }
        }
    }

    public void simulation(NodeTree child){
        Board testBoard = buildBoard(child);
        int x,y;
        ArrayList<Move> freeCells = testBoard.getListFreeCell();
        Move move = getFreeMove(child,freeCells);
        /*
        boolean wrong;
        do{
            wrong = false;
            int random = (int)(Math.random()*testBoard.getListFreeCell().size());
            Move move = testBoard.getListFreeCell().get(random);
            for(NodeTree node: child.getParent().getChildren()){

                if(move.x == node.x && move.y == node.y){
                    wrong = true;
                    break;
                }
            }

            x = move.x;
            y = move.y;

        }while(wrong);
        */

        child.x = move.x;
        child.y = move.y;
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

    public void evaluate(NodeTree child){
        Board testBoard = buildBoard(child);
        testBoard.placeStone(child.x,child.y,child.getColor());
        child.setValue(EvaluationFunction.EvaluationFunction(testBoard.getGrid(),child.getColor()));

    }

    public Board buildBoard(NodeTree nodeTree){
        Board copyBoard = initialState.getCopy();
        NodeTree currentNode = nodeTree.getParent();
        while (currentNode.getParent()!=null){
            copyBoard.placeStone(currentNode.x,currentNode.y,currentNode.getColor());
            currentNode = currentNode.getParent();
        }
        return copyBoard;
    }



}
