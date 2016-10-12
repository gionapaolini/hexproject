package AIs;

import Game.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giogio on 10/9/16.
 */
public class NodeTree {
    private NodeTree parent;
    private List<NodeTree> children;
    public int n_visits, n_wins, value;
    public Board board;



    public NodeTree(NodeTree parent){
        this.parent = parent;
        children = new ArrayList<NodeTree>();
        if(parent!=null)
            parent.addChild(this);
    }

    public void addChild(NodeTree child){
        children.add(child);
    }






}
