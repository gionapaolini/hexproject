package AIs;

import Game.Board;
import Game.Enums.ColorMode;

/**
 * Created by giogio on 10/9/16.
 */
public class MCTS {
    ColorMode color;
    Board board;
    NodeTree root;

    public MCTS(Board board, ColorMode colorMode){
        color = colorMode;
        this.board = board;
        root = new NodeTree(null);
    }

    public void start(){
        root.board = board;
        expansion(root);
    }


    public void selection(NodeTree startingNode){

    }

    public void expansion(NodeTree nodeTree){



    }

    public void simulation(){

    }

    public void backpropagation(){

    }



}
