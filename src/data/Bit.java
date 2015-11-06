package data;

/**
 * <b>File :</b> Bit.java<br>
 * <b>Creation date :</b> 06/11/2015<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Bit {

    public Bit(){}

    /**.
     *
     * @param pixel : it is the pixel who was describe with 8 bits in a string.
     *
     * @return the number of bite who is useless.
     */
    public static int getNbBitUselessInPixel(String pixel){
        if(pixel.contains("1"))
            return pixel.indexOf("1");
        else return 7;
    }

    /**.
     *
     * @param pixel : it is the pixel who was describe with 8 bits in a string.
     *
     * @return the number of bite who is useful.
     */
    public static int getNbBitUseInPixel(String pixel){
        if(!pixel.contains("1"))
            return 1;
        else return 8-pixel.indexOf("1");
    }
}
