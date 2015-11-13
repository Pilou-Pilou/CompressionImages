package com.ensimag.algorithmique.data;

/**
 * <b>File :</b> ${CLASS_NAME}.java<br>
 * <b>Creation date :</b> 08/11/2015<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Node {

    Node newSequence = null;
    Node addSequence = null;
    Node previous = null;
    boolean isComplet = false;
    int cost;
    int nbBitUse;
    int nbPixel;

    public Node(Node previous,int cost,int nbBiteUse,int nbPixel){
        this.previous = previous;
        this.cost = cost;
        this.nbBitUse = nbBiteUse;
        this.nbPixel = nbPixel;
    }

    public boolean isComplet(){
        return isComplet;
    }

    public Node getNewSequenceNode(){
        return newSequence;
    }

    public Node getAddSequenceNode(){
        return addSequence;
    }

    public int getCost(){
        return cost;
    }

    public int getNbBitUse(){
        return nbBitUse;
    }

    public int getNbPixel(){
        return nbPixel;
    }

    public void setIscomplet(boolean isComplet){
        this.isComplet = isComplet;
    }

    public Node getPreviousNode(){
        return previous;
    }

    public void setNewSequence(Node newSequence) {
        this.newSequence = newSequence;
    }

    public void setAddSequence(Node addSequence) {
        this.addSequence = addSequence;
    }
}
