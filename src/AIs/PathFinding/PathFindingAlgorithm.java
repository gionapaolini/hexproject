package AIs.PathFinding;

import Game.NodeCell;

import java.util.ArrayList;

/**
 * Created by giogio on 11/26/16.
 */
public class PathFindingAlgorithm {

    ArrayList<StarNode> starNodes;

    public PathFindingAlgorithm(NodeCell[] grid, NodeCell startPoint, NodeCell endPoint){
        starNodes = new ArrayList<StarNode>();
        StarNode start = new StarNode(null, startPoint);
        start.visitNode();
        starNodes.add(start);
        
    }


}
