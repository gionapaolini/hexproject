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


    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getG() {
        return g;
    }

    public void setG() {
        if(explorer!=null)
            g = explorer.g+1;
        else
            g = 0;
    }

    public int getF() {
        return f;
    }

    public void setF() {
        f = g+h;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public NodeCell getNode() {
        return node;
    }

    public void setNode(NodeCell node) {
        this.node = node;
    }

    public StarNode getExplorer() {
        return explorer;
    }

    public void setExplorer(StarNode explorer) {
        this.explorer = explorer;
    }
}
