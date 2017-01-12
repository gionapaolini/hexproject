package AIs.MonteCarlo;

import Game.Enums.ColorMode;
import Game.Move;

import java.util.ArrayList;

/**
 * Created by giogio on 12/1/16.
 */
public class NodeTree_Backup {
    Move move;
    ColorMode colorMode;
    float value;
    int games, wins;
    NodeTree_Backup parent;
    ArrayList<NodeTree_Backup> childrens;


    public NodeTree_Backup(NodeTree_Backup parent){
        this.parent = parent;
        childrens = new ArrayList<NodeTree_Backup>();
        if(parent!=null){

            parent.addChild(this);

            if (parent.getColorMode()==ColorMode.Blue)
                colorMode = ColorMode.Red;
            else
                colorMode = ColorMode.Blue;


        }


    }

    public int getDepth(){
        NodeTree_Backup node = this;
        int count = 0;
        while (node.parent!=null){
            count++;
            node = node.parent;
        }
        return count;
    }

    public void addChild(NodeTree_Backup child){
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

    public NodeTree_Backup getParent() {
        return parent;
    }

    public void setParent(NodeTree_Backup parent) {
        this.parent = parent;
    }

    public ArrayList<NodeTree_Backup> getChildrens() {
        return childrens;
    }

    public void setChildrens(ArrayList<NodeTree_Backup> childrens) {
        this.childrens = childrens;
    }


}
