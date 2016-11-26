package AIs.PathFinding;

import Game.NodeCell;

/**
 * Created by giogio on 11/26/16.
 */
public class StarNode {
    int h, g, f;
    boolean visited;
    NodeCell node;
    StarNode explorer;

    public StarNode(StarNode explorer, NodeCell nodeCell){
        this.explorer=explorer;
        node = nodeCell;
    }

    public void visitNode(){
        visited = true;
    }



}
