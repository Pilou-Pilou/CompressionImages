package data;

/**
 * <b>File :</b> Element.java<br>
 * <b>Creation date :</b> 08/11/2015<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Element {

    private int previous;
    private int cost;
    private int nbBiteUse;
    private int nbPixel;

    public Element(int previous, int nbPixel, int nbBiteUse, int cost){
        this.previous = previous;
        this.cost = cost;
        this.nbBiteUse = nbBiteUse;
        this.nbPixel = nbPixel;
    }

    public int getPrevious() {
        return previous;
    }


    public int getCost() {
        return cost;
    }

    public int getNbBiteUse() {
        return nbBiteUse;
    }

    public int getNbPixel() {
        return nbPixel;
    }
}
