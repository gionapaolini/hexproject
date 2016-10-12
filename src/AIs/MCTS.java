package AIs;

import Game.Board;
import Game.Enums.ColorMode;
import Game.Enums.FirstPlayer;
import Game.Match;

/**
 * Created by giogio on 10/9/16.
 */
public class MCTS {
    ColorMode color;
    Board board;
    NodeTree root;
    int nSimulation, lvlDepth, gridSize;
    public MCTS(Board board, ColorMode colorMode, FirstPlayer player, int gridSize){
        color = colorMode;
        this.board = board;
        root = new NodeTree(null);
        if(player == FirstPlayer.Yes){
            root.setPlayer(colorMode.Blue);
        }else {
            root.setPlayer(ColorMode.Red);
        }
        this.gridSize = gridSize;
    }

    public void start(){
        expansion(root);
    }


    public NodeTree selectionP1(NodeTree parentNode){
        if(parentNode.getChildren().size()==0)
            return parentNode;
        float best = 0;
        NodeTree bestNode = null;
        for(NodeTree node: parentNode.getChildren()){
            if(node.value>best) {
                best = node.value;
                bestNode = node;
            }
        }
        return selectionP1(bestNode);
    }


    public void expansion(NodeTree parentNode){
        NodeTree newChild = new NodeTree(parentNode);
        for(int i=0;i<nSimulation;i++){
            simulation(newChild);
        }

    }

    public void simulation(NodeTree child){
        Board testBoard = buildBoard(child);
        int x,y;
        boolean wrong;
        do{
            wrong = false;
            x = (int)Math.random()*gridSize;
            y = (int)Math.random()*gridSize;
            if(testBoard.getGrid()[x][y].getStatus()!=0){
                wrong = true;
            }else {
                for(NodeTree node: child.getParent().getChildren()){
                    if(x == node.x && y == node.y){
                        wrong = true;
                        break;
                    }
                }
            }

        }while(wrong);

        child.x = x;
        child.y = y;
        testBoard.placeStone(x,y,child.getPlayer());
        ColorMode currentPlayer;
        if(child.getPlayer()==ColorMode.Blue){
            currentPlayer = ColorMode.Red;
        }else {
            currentPlayer = ColorMode.Blue;
        }
        while (!testBoard.isConnected(ColorMode.Blue) && !testBoard.isConnected(ColorMode.Red)){
            do{
                wrong = false;
                x = (int)Math.random()*gridSize;
                y = (int)Math.random()*gridSize;
                if(testBoard.getGrid()[x][y].getStatus()!=0){
                    wrong = true;
                }
            }while(wrong);
            testBoard.placeStone(x,y,currentPlayer);
            if(currentPlayer==ColorMode.Blue){
                currentPlayer = ColorMode.Red;
            }else {
                currentPlayer = ColorMode.Blue;
            }
        }
        child.n_visits++;
        if(testBoard.isConnected(color)){
            child.n_wins++;
        }
    }

    public Board buildBoard(NodeTree nodeTree){
        Board copyBoard = board.getCopy();
        NodeTree currentNode = nodeTree.getParent();
        while (currentNode.getParent()!=null){
            copyBoard.placeStone(nodeTree.x,nodeTree.y,currentNode.getPlayer());
            currentNode = currentNode.getParent();
        }
        return copyBoard;
    }

    public void backpropagation(){

    }



}
