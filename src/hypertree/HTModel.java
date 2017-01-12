/*
 * HTModel.java
 *
 * www.bouthier.net
 * 2001
 */

package hypertree;


/**
 * The HTModel class implements the model for the HyperTree.
 */
class HTModel {

    private HTModelNode root   = null; // the root of the tree's model 

    private double      lenght = 0.4;  // distance between node and children
    private double      radius = 0.04; // radius of a node


  /* --- Constructor --- */

    /**
     * Constructor.
     *
     * @param root    the root of the real tree 
     */
    HTModel(HTNode root) {
        if (root.isLeaf()) {
            this.root = new HTModelNode(root, this);
        } else {
            this.root = new HTModelNodeComposite(root, this);
        }
        this.root.layoutHyperbolicTree();
    }


  /* --- Accessor --- */

    /**
     * Returns the root of the tree model.
     *
     * @return    the root of the tree model
     */
    HTModelNode getRoot() {
        return root;
    }

    /**
     * Returns the distance between a node and its children
     * in the hyperbolic space.
     *
     * @return    the distance
     */
    double getLenght() {
        return lenght;
    }

    /**
     * Returns the radius of a node in the hyperbolic space.
     *
     * @return    the radius
     */
    double getRadius() {
        return radius;
    }
    
}

