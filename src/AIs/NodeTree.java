package AIs;

import Game.Board;
import Game.Enums.ColorMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giogio on 10/9/16.
 */
public class NodeTree {
    private NodeTree parent;
    private List<NodeTree> children;
    public int n_visits, n_wins, x, y;
    public float value;
    private ColorMode player;


    public NodeTree(NodeTree parent){
        this.parent = parent;
        children = new ArrayList<NodeTree>();
        if(parent!=null) {
            parent.addChild(this);
            if(parent.player == ColorMode.Blue){
                player=ColorMode.Red;
            }else {
                player=ColorMode.Blue;
            }
        }
        x=-1;
        y=-1;

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

    public void setPlayer(ColorMode player) {
        this.player = player;
    }

    public ColorMode getPlayer() {
        return player;
    }

    public void addChild(NodeTree child){
        children.add(child);
    }

    public NodeTree getParent() {
        return parent;
    }

    public List<NodeTree> getChildren() {
        return children;
    }
}
