package AIs.AlphaBeta;

import Game.Enums.ColorMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giogio on 11/23/16.
 */
public class NodeTree {
    private NodeTree parent;
    private List<NodeTree> children;
    public int value, x, y;
    private ColorMode color;

    public NodeTree(NodeTree parent){
        this.parent = parent;
        children = new ArrayList<NodeTree>();
        if(parent!=null) {
            parent.addChild(this);
            if(parent.color == ColorMode.Blue){
                color=ColorMode.Red;
            }else {
                color=ColorMode.Blue;
            }
        }
        x=-1;
        y=-1;

    }

    private void addChild(NodeTree child){
        children.add(child);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColor(ColorMode color) {
        this.color = color;
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

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ColorMode getColor() {
        return color;
    }

    public NodeTree getParent() {
        return parent;
    }

    public List<NodeTree> getChildren() {
        return children;
    }
}
