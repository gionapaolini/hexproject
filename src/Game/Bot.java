package Game;

import AIs.AlphaBeta.ABPruning;
import AIs.AlphaBeta.AlphaBetaTree;
import AIs.MCTS;
import AIs.NodeTree;
import Game.Enums.ColorMode;

/**
 * Created by giogio on 9/17/16.
 */
public class Bot extends Player{

    MCTS mcts;
    ABPruning alpha;
    public void makeMove(){
       // NodeTree bestMove = mcts.start();
        Move bestMove = alpha.start();
        match.putStone(bestMove.x,bestMove.y);
    }

    public Bot(Match match){
        this.match = match;
        alpha = new ABPruning(match.getBoard(),color);
        mcts = new MCTS(match.getBoard(),color,match.getSideLength());
        mcts.setnSimulation(3);
        mcts.setLvlDepth(3);

    }
}
