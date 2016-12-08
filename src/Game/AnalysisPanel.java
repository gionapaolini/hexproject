package Game;

import AIs.GenEncoder.Phaenotype.AIU;
import AIs.GenEncoder.Phaenotype.SliceSet;
import AIs.GenEncoder.Phaenotype.SoundWorld.Frequence;
import AIs.GenEncoder.Phaenotype.SoundWorld.Sequence;
import AIs.GenEncoder.QuadraticElectricNetwork;
import Graphics.Visualiser;

import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by Nibbla on 07.12.2016.
 */
public class AnalysisPanel extends JPanel implements Observer{

    private Board board;
    Visualiser v;
    private Match match;


    @Override
    public void update() {
        System.out.println("notified Analysispanel");
        AIU a = evaluateBoard(match.getBoard());
        v.empty();
        v.feed(a.getSlices());
        v.feed(a.getQuadraticElectricNetwork());
        v.feed(a.getFirstFrequence());
        v.feed(a.getSecondFrequence());
        v.feed(a.getFirstSequence());
        v.feed(a.getSecondSequence());

        repaint();
    }

    private AIU evaluateBoard(QuadraticElectricNetwork qen, Frequence f1, Frequence f2, Sequence s1, Sequence s2, SliceSet slices) {

        return AIU.finalize(qen,f1,f2,s1,s2,slices);
    }

    private AIU evaluateBoard(Board board) {
        SliceSet slices = (SliceSet) createSlices(board);
        QuadraticElectricNetwork qen = (QuadraticElectricNetwork) createQuadraticNetwork(slices);
        Frequence f1 = assignFrequence(qen,0);
        Frequence f2 = assignFrequence(qen,1);
        Sequence s1 = assignSequence(qen,f1);
        Sequence s2 = assignSequence(qen,f2);


        AIU abstractInformationUnit3 = evaluateBoard(qen,f1,f2,s1,s2,slices);

        return abstractInformationUnit3;
    }

    private Sequence assignSequence(QuadraticElectricNetwork qen, Frequence f1) {
        return null;
    }

    private Frequence assignFrequence(QuadraticElectricNetwork qen, int i) {
        return null;
    }


    private AIU createQuadraticNetwork(SliceSet sliceSet) {

        return QuadraticElectricNetwork.createFromSliceSet(sliceSet);
    }

    private AIU createSlices(Board board) {

        return SliceSet.createSlicesFromGrid(board.getGrid());
    }

   

    public void paintComponent(Graphics g){
        v.visualize(g);

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public AnalysisPanel(){
        v = new Visualiser();
    }

    public void setMatch(Match match) {
        this.match = match;
        match.addObserver(this);
    }
}
