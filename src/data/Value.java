package data;

/**
 * <b>File :</b> Value.java<br>
 * <b>Creation date :</b> 28/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Value {

    private int cost;
    private int lengthOfSegment;
    private int nbBitUse;

    /**
     * Constructor of the class Value who permit to set all value to 0.
     */
    public Value(){
        this.cost =0;
        this.lengthOfSegment =0;
        this.nbBitUse=0;
    }

    /**
     *
     * @return the number of bite for write the segment.
     */
    public int getCost() {
        return cost;
    }

    /**
     *
     * @return the number of pixel use in the segment
     */
    public int getLengthOfSegment() {
        return lengthOfSegment;
    }

    /**
     *
     * @return the number of bite use for the pixel who corresponding.
     */
    public int getNbBitUse() {
        return nbBitUse;
    }

    /**
     *
     * @param cost : it is number of bite for write the segment.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     *
     * @param lengthOfSegment : it is the number of pixel that we will use in the sequence.
     */
    public void setLengthOfSegment(int lengthOfSegment) {
        this.lengthOfSegment = lengthOfSegment;
    }

    /**
     *
     * @param nbBitUse : it is the number of bit for the pixel who corresponding.
     */
    public void setNbBitUse(int nbBitUse) {
        this.nbBitUse = nbBitUse;
    }

}
