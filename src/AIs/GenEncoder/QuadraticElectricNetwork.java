package AIs.GenEncoder;

import AIs.GenEncoder.Phaenotype.AIU;
import AIs.GenEncoder.Phaenotype.SliceSet;
import Game.Board;

/**
 * Created by Nibbla on 08.12.2016.
 */
public class QuadraticElectricNetwork extends AIU {


    public static AIU createFromSliceSet(SliceSet sliceSet) {
        byte[][] hslice = sliceSet.getHorizonatals();
        byte[][] vslice = sliceSet.getVerticals();
      //  byte[][] cslice = sliceSet.getCross();




        return null;
    }
}
