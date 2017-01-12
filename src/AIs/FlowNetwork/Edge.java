package AIs.FlowNetwork;

/**
 * Created by PabloPSoto on 11/01/2017.
 */
public class Edge {
    Node from;
    Node to;

    public Edge(Node from, Node to){
        this.from = from;
        this.to = to;
        from.adjEdges.add(this);
        to.adjEdges.add(this);
    }
}
