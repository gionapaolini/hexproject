package AIs;

import Game.Board;
import Game.Enums.ColorMode;
import Game.Enums.FirstPlayer;
import Game.Match;
import Game.Move;

/**
 * Created by giogio on 10/9/16.
 */
public class MCTS {
    ColorMode color;
    Board board;
    NodeTree root;
    int nSimulation, lvlDepth, gridSize;
    public MCTS(Board board, ColorMode colorMode, int gridSize){
        color = colorMode;
        this.board = board;
        this.gridSize = gridSize;
    }


    public void setnSimulation(int nSimulation) {
        this.nSimulation = nSimulation;
    }

    public void setLvlDepth(int lvlDepth) {
        this.lvlDepth = lvlDepth;
    }

    public NodeTree start(){
        System.out.println("Begin");
        root = new NodeTree(null);
        root.setPlayer(color);
        expansion(root);

        return getBestNextMove();

    }


    public NodeTree selection(NodeTree parentNode){
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
        return selection(bestNode);
    }

    public NodeTree getBestNextMove(){
        float best = root.getChildren().get(0).value;
        NodeTree bestNode = root.getChildren().get(0);
        for(NodeTree node: root.getChildren()){
            if(node.value>best) {
                best = node.value;
                bestNode = node;
            }
        }
        return bestNode;
    }


    public void expansion(NodeTree parentNode){

        if(parentNode.getDepth()==lvlDepth)
            return;

        for (int i = 0; i < nSimulation; i++) {
            NodeTree newChild = new NodeTree(parentNode);
            simulation(newChild);
            expansion(newChild);
        }
        int visitsparent = 0;
        int wins = 0;
        for (NodeTree child : parentNode.getChildren()) {
            visitsparent += child.n_visits;
            wins += child.n_wins;
        }
        parentNode.n_wins = wins;
        parentNode.n_visits = visitsparent;
        parentNode.value = ((float) wins) / visitsparent;


    }

    public void simulation(NodeTree child){
        Board testBoard = buildBoard(child);
        int x,y;
        boolean wrong;
        do{
            wrong = false;
            x = (int)(Math.random()*gridSize);

            y = (int)(Math.random()*gridSize);
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
            System.out.println("In the 1st simulation Loop");

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

            int random = (int)(Math.random()*testBoard.getListFreeCell().size());
            Move move = testBoard.getListFreeCell().get(random);
            testBoard.placeStone(move.x,move.y,currentPlayer);
            if(currentPlayer==ColorMode.Blue){
                currentPlayer = ColorMode.Red;
            }else {
                currentPlayer = ColorMode.Blue;
            }
            System.out.println("In the 2st simulation Loop");
        }

        child.n_visits++;
        if(testBoard.isConnected(color)){
            child.n_wins++;
        }
        child.value = ((float)child.n_wins)/child.n_visits;

    }

    public Board buildBoard(NodeTree nodeTree){
        Board copyBoard = board.getCopy();
        NodeTree currentNode = nodeTree.getParent();
        while (currentNode.getParent()!=null){
            copyBoard.placeStone(currentNode.x,currentNode.y,currentNode.getPlayer());
            currentNode = currentNode.getParent();
            System.out.println("In the buildboard Loop");
        }
        return copyBoard;
    }


}
