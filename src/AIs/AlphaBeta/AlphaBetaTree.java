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

            NodeTree newChild = new NodeTree(parentNode);
            simulation(newChild);
            if(!useless(newChild)){
                for (int j=0;j<60;j++){
                    NodeTree secondChild = new NodeTree(newChild);
                    simulation(secondChild);
                    evaluate(secondChild);
                    if(secondChild.getValue()==0)
                        break;
                    backpropagate(secondChild);
                    if(newChild.getValue()<newChild.getParent().getValue()){
                        break;
                    }
                }
                backpropagate(newChild);
            }


        }
    }


    public boolean useless(NodeTree child){
        Board testBoard = buildBoard(child);
        testBoard.placeStone(child.x,child.y,child.getColor());
        int count=0;

        for(int i=child.y-1;i<child.y+2;i++){
            for (int j=child.x-1;j<child.x+2;j++){
                if((i==child.y-1 && j==child.x-1)||(i==child.y+1 && j==child.x+1)||(i==child.y && j==child.x))
                    continue;
                if(testBoard.getGrid()[j][i].getStatus()==0)
                    count++;
            }
        }
        if (count==2){
            int[][] coords = {{-1,0},{-1,+1},{0,+1},{+1,0},{+1,-1},{0,-1},{-1,0}};
            boolean checks = false;
            for(int i = 0;i<7;i++){
                int currentX = child.x+coords[i][0];
                int currentY = child.y+coords[i][0];
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
            System.out.println("In the buildboard Loop");
        }
        return copyBoard;
    }


    private void evaluateNode(){

    }



}
