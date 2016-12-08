package Graphics;

import AIs.GenEncoder.Phaenotype.AIU;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Nibbla on 08.12.2016.
 */
public class Visualiser {
    ArrayList<AIU> units = new ArrayList<>(3);
    public void feed(AIU abstractInformationUnit) {
        units.add(abstractInformationUnit);
    }
    public AIU spit(){
        if (units.size()==0)return null;
        return units.get(units.size()-1);
    }

    public void visualize(Graphics g) {
       // cards = new JPanel(new CardLayout());
        //cards.add(card1, BUTTONPANEL);
       // cards.add(card2, TEXTPANEL);
        g.setColor(Color.BLACK);
        g.fillRect(100,100,100,100);



    }

    public void empty() {
        units.clear();
    }
}
