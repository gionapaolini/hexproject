package AIs.GenEncoder;

import AIs.GenEncoder.Phaenotype.Phaenotype;
import Game.BoardPanel;

import java.util.Random;

/**
 * Created by Nibbla on 15.10.2016.
 */
public abstract class Breeder {
    public abstract Phaenotype create(Random r, BoardPanel bp, GenoType gt);
}
