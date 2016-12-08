package AIs.GenEncoder.Phaenotype;

import AIs.GenEncoder.Phaenotype.SoundWorld.Frequence;
import AIs.GenEncoder.Phaenotype.SoundWorld.Sequence;
import AIs.GenEncoder.QuadraticElectricNetwork;

/**
 * Created by Nibbla on 08.12.2016.
 */
public abstract class AIU {
    private AIU slices;
    public AIU quadraticElectricNetwork;
    private AIU firstFrequence;
    private AIU secondFrequence;
    private AIU firstSequence;
    private AIU secondSequence;

    public AIU getSlices() {
        return slices;
    }

    public AIU getFirstFrequence() {
        return firstFrequence;
    }

    public AIU getSecondFrequence() {
        return secondFrequence;
    }

    public AIU getFirstSequence() {
        return firstSequence;
    }

    public AIU getSecondSequence() {
        return secondSequence;
    }

    public static AIU finalize(QuadraticElectricNetwork qen, Frequence f1, Frequence f2, Sequence s1, Sequence s2, SliceSet slices) {
        AIU aiu = new AIU(){};

        aiu.quadraticElectricNetwork= qen;
        aiu.firstFrequence = f1;
        aiu.secondFrequence = f2;
        aiu.firstSequence=s1;
        aiu.secondSequence=s2;
        aiu.slices=slices;



        return aiu;
    }

    public AIU getQuadraticElectricNetwork() {
        return quadraticElectricNetwork;
    }
}
