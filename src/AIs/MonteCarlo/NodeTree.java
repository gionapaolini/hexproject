package AIs.MonteCarlo;

import Game.Enums.ColorMode;
import Game.Move;

import java.util.ArrayList;

/**
 * Created by giogio on 12/1/16.
 */
public class NodeTree {
    Move move;
    ColorMode colorMode;
    float value;
    int games, wins;
    NodeTree parent;
    ArrayList<NodeTree> childrens;


    public NodeTree(NodeTree parent){
        this.parent = parent;
        childrens = new ArrayList<NodeTree>();
        if(parent!=null){

            parent.addChild(this);

            if (parent.getColorMode()==ColorMode.Blue)
                colorMode = ColorMode.Red;
            else
                colorMode = ColorMode.Blue;


        }


    }

    public int getDepth(){
        NodeTree node = this;
        int count = 0;
        while (node.parent!=null){
            count++;
            node = node.parent;
        }
        return count;
    }

    public void addChild(NodeTree child){
        childrens.add(child);
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public void setColorMode(ColorMode colorMode) {
        this.colorMode = colorMode;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public NodeTree getParent() {
        return parent;
    }

    public void setParent(NodeTree parent) {
        this.parent = parent;
    }

    public ArrayList<NodeTree> getChildrens() {
        return childrens;
    }

    public void setChildrens(ArrayList<NodeTree> childrens) {
        this.childrens = childrens;
    }


}
