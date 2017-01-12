package Graphics;

import Graphics.ChessView.ChessView;
import hypertree.HTView;
import hypertree.HyperTree;
import hypertree.demo.HTFileNode;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Nibbla on 11.01.2017.
 */
public class Analysor extends JFrame{
    private  JPanel buttonPanel;
    private  JPanel mainPanel;

    private  JButton chessViewButton = new JButton("Chess Board View");
    private  JButton hyperViewButton = new JButton("Hyperplane View");
    private  JButton neuralViewButton = new JButton("Neuralnet View");

    private ChessView chessView;
    private HTView hyperTreeView;
    private NeuralNetworkView nnw;


    MainGui mainGui;
    public Analysor(MainGui mainGui) {
        this.mainGui = mainGui;

        this.setLayout(new BorderLayout());


        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        this.add(buttonPanel,BorderLayout.WEST);

        mainPanel = new JPanel(); mainPanel.setPreferredSize(new Dimension(800,600));
        this.add(mainPanel, BorderLayout.CENTER);

        buttonPanel.add(chessViewButton);
        buttonPanel.add(hyperViewButton);
        buttonPanel.add(neuralViewButton);


        chessViewButton.addActionListener(e -> showChessView(mainGui));
        hyperViewButton.addActionListener(e -> showHyperView(mainGui));
        neuralViewButton.addActionListener(e -> showNeuralView(mainGui));



        this.pack();


    }

    private void showNeuralView(MainGui mainGui) {


    }

    private void showHyperView(MainGui mainGui) {
        String pathRoot = "I:\\DateienBilderPersönlich";
        File rootFile = new File(pathRoot);
        HTFileNode root = new HTFileNode(rootFile);
        HyperTree hypertree = new HyperTree(root);
        Dimension preveredSize = new Dimension(800,600);
                hyperTreeView = hypertree.getView(preveredSize);
       // this.add(hyperTreeView, BorderLayout.CENTER);
       // this.pack();
       // repaint();
        replaceMainPanel(hyperTreeView);
        hyperTreeView.setVisible(true);
        hyperTreeView.repaint();
    }

    private void replaceMainPanel(Component component) {

       Component[] components = this.rootPane.getComponents();
      //  for (Component c : components){
       //     if (c.equals(buttonPanel)){
        //        continue;
       //     }}

       if (chessView!=null) remove(chessView);
        if (hyperTreeView!=null) remove(hyperTreeView);
        if (nnw!=null) remove(nnw);

        this.add(component, BorderLayout.CENTER);


        pack();
        repaint();
        revalidate();

    }

    private void showChessView(MainGui mainGui) {

    }
}
