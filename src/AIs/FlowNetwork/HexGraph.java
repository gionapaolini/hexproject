package AIs.FlowNetwork;

import java.util.List;

/**
 * Created by PabloPSoto on 11/01/2017.
 */
public class HexGraph {
    //List<Node> NodeList;
    private Node[][]NodeList;

    public HexGraph(int boardSize){
        NodeList = new Node[boardSize][boardSize];
        createNullGraph(boardSize);
    }

    public void updateGraph(int i, int j, boolean colour) {
        if (colour == true) {
            //Connect adjacent Cells
            killAdjEdges(i,j);
            updateEdges(i,j);
        }else if (colour == false) {
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
}

