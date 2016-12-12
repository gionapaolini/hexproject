package Game;

import AIs.AlphaBeta.ABPruning;
import AIs.AlphaBeta.AlphaBetaTree;
import AIs.MCTS;
import AIs.MonteCarlo.MonteCarloTreeSearch;
import AIs.NodeTree;
import AIs.PathFinding.PathFindingBot;
import Game.Enums.BotType;
import Game.Enums.ColorMode;

/**
 * Created by giogio on 9/17/16.
 */
public class Bot extends Player{

    BotType botType;
    ABPruning alpha;
    PathFindingBot pathFindingBot;
    MonteCarloTreeSearch monteCarloTreeSearch;
    public void makeMove() {
        Move bestMove;
        if (botType == BotType.MCTS) {
            monteCarloTreeSearch = new MonteCarloTreeSearch(match.getBoard(),color);
            bestMove = monteCarloTreeSearch.start();
        }else if (botType == BotType.PathFinding) {
            pathFindingBot = new PathFindingBot(match,color);
            bestMove = pathFindingBot.start();
        }else {
            alpha = new ABPruning(match.getBoard(),color);
            bestMove = alpha.start();
        }

        match.putStone(bestMove.x,bestMove.y);

    }



    public Bot(Match match, BotType botType){
        this.match = match;
        this.botType = botType;
    }
}
