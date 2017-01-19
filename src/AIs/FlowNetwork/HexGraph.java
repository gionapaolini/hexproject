package AIs.FlowNetwork;


import Game.Enums.ColorMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PabloPSoto on 11/01/2017.
 */
public class HexGraph {

    private Node[][]NodeList;
    Node s;
    Node t;
    List<List<Node>> paths;


    public HexGraph(int boardSize){
        NodeList = new Node[boardSize][boardSize];
        createNullGraph(boardSize);
    }



    public void updateGraph(int i, int j, ColorMode colour) {
        if (colour == ColorMode.Blue) {
            //Connect adjacent Cells
            killAdjEdges(i,j);
            updateEdges(i,j);
        }else if (colour == ColorMode.Red) {
            //Eliminate Node
            killAdjEdges(i,j);

        }

    }

    public void createNullGraph(int BoardSize){
        for(int i=0;i<BoardSize;i++){
            for(int j=0;j<BoardSize;j++){
                NodeList[i][j] = new Node(i, j, true);
            }
        }
        createEdges();
        finaliseGraph();
    }
    public void createEdges(){
        for(int i=0;i<NodeList.length;i++){
            for(int j=0;j<NodeList.length;j++){
                NodeList[i][j].toString();
                try {
                    new Edge(NodeList[i][j], NodeList[i-1][j]);
                    System.out.println("Edge created successfully.");
                }catch (ArrayIndexOutOfBoundsException e1){
                    System.out.println("Edge out of Bound.");
                }try {
                    new Edge(NodeList[i][j], NodeList[i-1][j+1]);
                    System.out.println("Edge created successfully.");
                }catch (ArrayIndexOutOfBoundsException e1){
                    System.out.println("Edge out of Bound.");
                }try {
                    new Edge(NodeList[i][j], NodeList[i][j+1]);
                    System.out.println("Edge created successfully.");
                }catch (ArrayIndexOutOfBoundsException e1){
                    System.out.println("Edge out of Bound.");
                }
            }
        }

    }

    public void finaliseGraph (){
        s = new Node(0);
        t = new Node(NodeList.length);

        for (int i=0; i<NodeList.length; i++){
            new Edge(s,NodeList[i][0]);
        }
        for (int i=0;i<NodeList.length; i++){
            new Edge(NodeList[i][NodeList.length-1], t);
        }
        System.out.println("Finalised Graph");
    }
    public void killAdjEdges(int i, int j){
        NodeList[i][j].adjEdges = null;
        try {
            for (Edge e : NodeList[i - 1][j].adjEdges) {
                if (e.to.getI() == i && e.to.getJ() == j ||
                        e.from.getI() == i && e.from.getJ() == j) {
                    e = null;
                }
            }
            System.out.println("Edge deleted successfully.");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Edge out of Bound.");
        }
        try {
            for (Edge e : NodeList[i - 1][j+1].adjEdges) {
                if (e.to.getI() == i && e.to.getJ() == j ||
                        e.from.getI() == i && e.from.getJ() == j) {
                    e = null;
                }
            }
            System.out.println("Edge deleted successfully.");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Edge out of Bound.");
        }try {
            for (Edge e : NodeList[i][j+1].adjEdges) {
                if (e.to.getI() == i && e.to.getJ() == j ||
                        e.from.getI() == i && e.from.getJ() == j) {
                    e = null;
                }
            }
            System.out.println("Edge deleted successfully.");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Edge out of Bound.");
        }try {
            for (Edge e : NodeList[i+1][j].adjEdges) {
                if (e.to.getI() == i && e.to.getJ() == j ||
                        e.from.getI() == i && e.from.getJ() == j) {
                    e = null;
                }
            }
            System.out.println("Edge deleted successfully.");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Edge out of Bound.");
        }try {
            for (Edge e : NodeList[i+1][j-1].adjEdges) {
                if (e.to.getI() == i && e.to.getJ() == j ||
                        e.from.getI() == i && e.from.getJ() == j) {
                    e = null;
                }
            }
            System.out.println("Edge deleted successfully.");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Edge out of Bound.");
        }try {
            for (Edge e : NodeList[i][j-1].adjEdges) {
                if (e.to.getI() == i && e.to.getJ() == j ||
                        e.from.getI() == i && e.from.getJ() == j) {
                    e = null;
                }
            }
            System.out.println("Edge deleted successfully.");
        } catch (ArrayIndexOutOfBoundsException e1) {
            System.out.println("Edge out of Bound.");
        }
        NodeList[i][j]=null;
    }
    public void updateEdges(int i, int j){
        try {
            new Edge(NodeList[i-1][j], NodeList[i][j+1]);
            System.out.println("Edge updated successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i-1][j+1], NodeList[i][j-1]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i-1][j+1], NodeList[i+1][j-1]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i-1][j+1], NodeList[i+1][j]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i][j+1], NodeList[i][j-1]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i][j+1], NodeList[i+1][j-1]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i-1][j], NodeList[i+1][j]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i][j-1], NodeList[i+1][j]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }try {
            new Edge(NodeList[i-1][j], NodeList[i+1][j-1]);
            System.out.println("Edge created successfully.");
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("Edge out of Bound.");
        }
    }
    public void findPaths(){
        paths = new ArrayList<List<Node>>();
        ArrayList<Node> iPath = new ArrayList<Node>();
        paths.add(expand(s, iPath));
        System.out.print("-----");
    }
    public ArrayList<Node> expand(Node selectedNode, ArrayList<Node> inputPath){
        inputPath.add(selectedNode);
        if (selectedNode == t){
            System.out.println("Found Path.");
            return inputPath;
        }else{
            int sizeChildren = selectedNode.adjEdges.size();
            for (int i = 0; i < sizeChildren; i++){
                if (selectedNode.adjEdges.get(i).to != selectedNode && !contains(inputPath, selectedNode.adjEdges.get(i).to)) {
                    if (selectedNode.adjEdges.get(i).to.getJ() >= selectedNode.getJ()) {
                        System.out.println("Expanding from " + selectedNode.getI() + "/" + selectedNode.getJ()+ " to " + selectedNode.adjEdges.get(i).to.getI() + "/" + selectedNode.adjEdges.get(i).to.getJ());
                        return expand(selectedNode.adjEdges.get(i).to, inputPath);
                    }
                }
                if (selectedNode.adjEdges.get(i).from != selectedNode && !contains(inputPath, selectedNode.adjEdges.get(i).from)){
                    if (selectedNode.adjEdges.get(i).from.getJ() >= selectedNode.getJ()){
                        System.out.println("Expanding from " + selectedNode.getI() + "/" + selectedNode.getJ()+ " to " + selectedNode.adjEdges.get(i).from.getI() + "/" + selectedNode.adjEdges.get(i).from.getJ());
                        return expand(selectedNode.adjEdges.get(i).from, inputPath);
                    }
                }
            }
        }
        return null;
    }
    public boolean contains(ArrayList<Node> path, Node node){
        for(int i=0;i<path.size();i++){
            if (node==path.get(i))
                return true;
        }
        return false;
    }
}

