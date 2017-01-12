package Game;

import AIs.AlphaBeta.ABPruning;
import AIs.MonteCarlo.MonteCarloTreeSearch;
import AIs.MonteCarlo.MonteCarloTreeSearchAlt;
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
    MonteCarloTreeSearchAlt monteCarloTreeSearchAlt;
    public void makeMove(Move lastMove) {

        Thread t = new Thread((() -> makeMoveThread(lastMove)));
        t.start();

    }

    private void makeMoveThread(Move lastMove) {

        Move bestMove;
        if (botType == BotType.MCTS) {
            monteCarloTreeSearch = new MonteCarloTreeSearch(match.getBoard(),color);
            bestMove = monteCarloTreeSearch.start();
        }else if (botType == BotType.MCTS_alt){
            if (monteCarloTreeSearchAlt == null) {
                int thoughtTime = 500;
                if (color == ColorMode.Blue) thoughtTime = 20000;
                if (color == ColorMode.Red) thoughtTime = 20000;
                    monteCarloTreeSearchAlt = new MonteCarloTreeSearchAlt(match.getBoard(),color,thoughtTime);
            }
            monteCarloTreeSearchAlt.adjustRoot(lastMove);
            bestMove = monteCarloTreeSearchAlt.start();
        }
        else if (botType == BotType.PathFinding) {
            pathFindingBot = new PathFindingBot(match,color);
            bestMove = pathFindingBot.start();
        }else {
            alpha = new ABPruning(match.getBoard(),color);
            bestMove = alpha.start();
        }

        match.putStone(bestMove.x, bestMove.y);
        match.lastSearchTree(monteCarloTreeSearchAlt.getRoot());

    }


    public Bot(Match match, BotType botType){
        this.match = match;
        this.botType = botType;
    }
}
