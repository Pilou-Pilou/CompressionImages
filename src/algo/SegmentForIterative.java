package algo;

/**
 * <b>File :</b> Segment.java<br>
 * <b>Creation date :</b> 30/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class SegmentForIterative implements Segment{

    private String headSegment;
    private String bodySegment;
    private int nbBiteUseless;

    /**
     * Constructor of segment who permit to build the head of segment.
     *
     * @param nbPixel : number of pixel in the sequence.
     * @param nbBiteUseless  : number of bit useless by pixel.
     */
    public SegmentForIterative(int nbPixel,int nbBiteUseless){

        String pixelTmp = Integer.toBinaryString(nbPixel);
        String bitTmp = Integer.toBinaryString(nbBiteUseless);
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

        this.nbBiteUseless = nbBiteUseless;
        this.headSegment = pixelTmp+bitTmp;
        this.bodySegment = "";
    }

    /**
     *
     * @param pixel : String of bite to add to the sequence of the body.
     */
    public void fillBodySegement(String pixel){
        this.bodySegment += pixel.substring(this.nbBiteUseless, 8);
    }

    /**
     *
     * @return the complete segment with 11 bite for the head and the next for the sequence.
     */
    public String getSegment(){
        return this.headSegment+this.bodySegment;
    }




}
