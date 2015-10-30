package algo;

/**
 * <b>File :</b> Segment.java<br>
 * <b>Creation date :</b> 30/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Segment {

    private String headSegment;
    private String bodySegment;
    private int nbBiteUse;

    /**
     * Constructor of segment who permit to build the head of segment.
     *
     * @param nbPixel : number of pixel in the sequence.
     * @param nbBite  : number of bit use by pixel.
     */
    public Segment(int nbPixel,int nbBite){

        String pixelTmp = Integer.toBinaryString(nbPixel);
        String bitTmp = Integer.toBinaryString(nbBite);
        String tmp = "";
        for(int i=pixelTmp.length();i<8;i++){
            tmp += "0";
        }
        pixelTmp = tmp + pixelTmp;
        tmp = "";
        for(int i=bitTmp.length();i<3;i++){
            tmp += "0";
        }
        bitTmp = tmp + bitTmp;

        this.nbBiteUse = nbBite;
        this.headSegment = pixelTmp+bitTmp;
        this.bodySegment = "";
    }

    /**
     *
     * @param pixel : String of bite to add to the sequence of the body.
     */
    public void fillBodySegement(String pixel){
        this.bodySegment += pixel.substring(8-this.nbBiteUse,7);
    }

    /**
     *
     * @return the complete segment with 11 bite for the head and the next for the sequence.
     */
    public String getSegment(){
        return this.headSegment+this.bodySegment;
    }




}