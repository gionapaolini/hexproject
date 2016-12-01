package Game;

import AIs.AlphaBeta.ABPruning;
import AIs.AlphaBeta.AlphaBetaTree;
import AIs.MCTS;
import AIs.NodeTree;
import AIs.PathFinding.PathFindingBot;
import Game.Enums.BotType;
import Game.Enums.ColorMode;

/**
 * Created by giogio on 9/17/16.
 */
public class Bot extends Player{

    MCTS mcts;
    ABPruning alpha;
    BotType botType;
    PathFindingBot pathFindingBot;
    public void makeMove() {
        // NodeTree bestMove = mcts.start();
        Move bestMove;
        if (botType != BotType.PathFinding) {
            bestMove = alpha.start();
        }else {
            pathFindingBot = new PathFindingBot(match,color);
            bestMove = pathFindingBot.start();
        }
        match.putStone(bestMove.x,bestMove.y);

    }

    public Bot(Match match, BotType botType){
        this.match = match;
        this.botType = botType;
        alpha = new ABPruning(match.getBoard(),color);

        mcts = new MCTS(match.getBoard(),color,match.getSideLength());
        mcts.setnSimulation(3);
        mcts.setLvlDepth(3);

    }
}
