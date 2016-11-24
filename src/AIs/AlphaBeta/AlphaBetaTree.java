package AIs.AlphaBeta;

import Game.Board;
import Game.Enums.ColorMode;
import Game.Move;
import Game.NodeCell;

/**
 * Created by giogio on 11/23/16.
 */
public class AlphaBetaTree {
    Board initialState;
    ColorMode colorMode;
    NodeTree root;
    public AlphaBetaTree(Board board, ColorMode colorMode){
        initialState=board;
        colorMode=colorMode;
    }

    public NodeTree start(){
        System.out.println("Begin");
        root = new NodeTree(null);
        root.setColor(colorMode);
        expansion(root);
        return null;


    }

    public void expansion(NodeTree parentNode){

        for(int i=0;i<60;i++){
            for (int j=0;j<2;j++){
                createChild(parentNode);
                NodeTree newChild = new NodeTree(parentNode);
            }
        }

        /*


        if(parentNode.getDepth()==2)
            return;

        for (int i = 0; i < 60; i++) {
            NodeTree newChild = new NodeTree(parentNode);
            simulation(newChild);
            backpropagate(newChild);


            expansion(newChild);

        }
        */



    }

    public void createChild(NodeTree parent){
        
    }

    public void backpropagate(NodeTree child){
        if(child.getParent().getColor()!=colorMode){
            if(child.getParent().getValue()>child.getValue()){
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

            System.out.println("In the 1st simulation Loop");
            x = move.x;
            y = move.y;

        }while(wrong);

        child.x = x;
        child.y = y;
        testBoard.placeStone(x,y,child.getColor());
        child.setValue(EvaluationFunction.EvaluationFunction(testBoard.getGrid(),child.getColor()));



    }

    public Board buildBoard(NodeTree nodeTree){
        Board copyBoard = initialState.getCopy();
        NodeTree currentNode = nodeTree.getParent();
        while (currentNode.getParent()!=null){
            copyBoard.placeStone(currentNode.x,currentNode.y,currentNode.getColor());
            currentNode = currentNode.getParent();
            System.out.println("In the buildboard Loop");
        }
        return copyBoard;
    }


    private void evaluateNode(){

    }



}
