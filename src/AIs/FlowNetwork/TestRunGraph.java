package AIs.FlowNetwork;

import java.util.List;

/**
 * Created by PabloPSoto on 11/01/2017.
 */
public class TestRunGraph {
    public static void main (String[] args){
        HexGraph testGraph = new HexGraph(11);
        List<List<Node>> paths = testGraph.findPaths();
        System.out.println(paths.size());
    }
}
