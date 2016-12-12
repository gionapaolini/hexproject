




package AIs.PathFinding;

import Game.Enums.ColorMode;
import Game.Move;
import Game.NodeCell;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by giogio on 11/26/16.
 */
public class PathFindingAlgorithm {

    HashMap<NodeCell,StarNode> cellMap = new HashMap<NodeCell,StarNode>();
    NodeCell startPoint, endPoint;
    ColorMode colorMode;

    public PathFindingAlgorithm(NodeCell startPoint, NodeCell endPoint, ColorMode colorMode){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        StarNode root = new StarNode(null, startPoint);
        cellMap.put(startPoint,root);
        this.colorMode = colorMode;
    }

    public ArrayList<Move> start(){
        StarNode currentNode = cellMap.get(startPoint);
        evaluateNode(currentNode);
        currentNode.setVisited(true);
        while (currentNode.h!=0){
            exploreNeighbours(currentNode);
            currentNode = pickBestNode();
            currentNode.setVisited(true);

        }
        if(currentNode.getNode()==null){
            return null;
        }

        return getPath(currentNode);

    }

    public ArrayList<Move> getPath(StarNode end){
        ArrayList<Move> path = new ArrayList<Move>();
        while (end!=null) {

            Move move = new Move(end.getNode().getX(), end.getNode().getY());
            path.add(move);
            end = end.explorer;
        }
        return path;
    }

    public void evaluateNode(StarNode node){
        int currentX = node.getNode().getX();
        int currentY = node.getNode().getY();
        int count=0;
        while (currentX != endPoint.getX() || currentY != endPoint.getY()){
            if(currentX<endPoint.getX()) {
                currentX++;
                count++;
            }
            if(currentX>endPoint.getX()) {
                currentX--;
                count++;
            }
            if(currentY<endPoint.getY()) {
                currentY++;
                count++;
            }
            if(currentY>endPoint.getY()) {
                currentY--;
                count++;
            }

        }

        node.setH(count);
        node.setG();
        node.setF();
    }
    public void exploreNeighbours(StarNode node){
        NodeCell cell = node.getNode();

        for (NodeCell nodeCell: cell.getListGoodNeighbours(colorMode)){
            StarNode starNode = cellMap.get(nodeCell);
            if(starNode==null) {
                createStarNode(nodeCell, node);

            }else if(!starNode.isVisited() && isBetterConnection(starNode, node)){
                starNode.setExplorer(node);
                starNode.setG();
                starNode.setF();
            }
        }



    }

    public StarNode pickBestNode(){
        StarNode best = new StarNode(null,null);
        for (StarNode starNode: cellMap.values()){
            if(!starNode.isVisited())
                best = starNode;
        }
        for (StarNode starNode: cellMap.values()){
            if(!starNode.isVisited() && ((starNode.f<best.f) || (starNode.f == best.f && starNode.h < best.h)))
                best = starNode;
        }
        return best;
    }

    public boolean isBetterConnection(StarNode node, StarNode newExplorer){
        if(node.getG()>newExplorer.getG()+1){
            return true;
        }
        return false;
    }

    public void createStarNode(NodeCell cell, StarNode explorer){
        StarNode starNode = new StarNode(explorer,cell);
        cellMap.put(cell,starNode);
        evaluateNode(starNode);

    }


}
