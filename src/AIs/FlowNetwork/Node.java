package AIs.FlowNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PabloPSoto on 11/01/2017.
 */
public class Node {
    public Boolean colour;
    public List<Edge> adjEdges;
    private int i, j;

    public Node(int i, int j, Boolean colour){
        this.colour = colour;
        adjEdges = new ArrayList<Edge>();
        this.i = i;
        this.j = j;
    }
    public Node(int j){
        adjEdges = new ArrayList<Edge>();
        this.j = j;
    }

    public void setColour(Boolean colour){
        this.colour = colour;
    }
    public int getI(){
        return i;
    }
    public int getJ(){
        return j;
    }
    public String toString(){
        return "";
    }
}
