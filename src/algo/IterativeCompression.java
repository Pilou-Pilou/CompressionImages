package algo;

import data.Bit;
import data.Data;
import data.Value;
import file.BitOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <b>File :</b> IterativeCompression.java<br>
 * <b>Creation date :</b> 27/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class IterativeCompression {

    private Value[] value;

    /**
     * Constructor of the class IterativeCompression
     */
    public IterativeCompression() throws IOException {

        value = new Value[Data.arrayOfByte.length];

        // initialise the table
        init();

        // fills the table of value
        for(int i= Data.arrayOfByte.length-1; i>=0 ; i--)
            countMin(i);

        // create the sequence and put it in the file
        int idMax,nbBiteUseless;
        BitOutputStream a = new BitOutputStream(new FileOutputStream("tmp-iterative.seg"));
        for(int i=0;i<value.length;i++){
            idMax = value[i].getLengthOfSegment()+i;
            nbBiteUseless = value[i].getNbBitUseless();
            SegmentForIterative segment = new SegmentForIterative(value[i].getLengthOfSegment(),nbBiteUseless);
            while(i < idMax){
                segment.fillBodySegement(Data.arrayOfByte[i]);
                i++;
            }

            a.write(segment);
            // for don't have twice i++ (in while and with for)
            i--;
        }
        a.close();
    }

    /**
     * This method initialise all case of table with object value.
     */
    public void init(){

        for(int i=0;i<value.length;i++){
            value[i]= new Value();
        }
    }

    /**
     *
     * @param index : it is the index of the current index read in the table.
     *
     * @return the cost of the min solution to take.
     */
    public int countMin(int index){

        int min;
        int tmp1,tmp2;
        int nbBitUseless = Bit.getNbBitUselessInPixel(Data.arrayOfByte[index]);
        int nbBitUse = 8 - nbBitUseless;

        value[index].setNbBitUseless(nbBitUseless);
        if(index == Data.arrayOfByte.length-1){
            min = 11 + nbBitUse;
            value[index].setLengthOfSegment(1);
        }else {
            if(value[index+1].getLengthOfSegment() >= 255){
                min = 11 + nbBitUse;
                value[index].setLengthOfSegment(1);
            }else {
                if (nbBitUseless < value[index + 1].getNbBitUseless()) {
                    // 0111 0010 0011
                    tmp1 = value[index + 1].getCost() + 11 + nbBitUse;
                    tmp2 = nbBitUse * (value[index + 1].getLengthOfSegment() + 1);
                    min = Math.min(tmp1, tmp2);
                    if (min == tmp1)
                        value[index].setLengthOfSegment(1);
                    else
                        value[index].setLengthOfSegment(value[index + 1].getLengthOfSegment() + 1);

                } else {// >=
                    // 0011  0111  0101
                    tmp1 = value[index + 1].getCost() + 11 + nbBitUse;
                    tmp2 = value[index + 1].getCost() + nbBitUse;
                    min = Math.min(tmp1, tmp2);
                    if (min == tmp1)
                        value[index].setLengthOfSegment(1);
                    else
                        value[index].setLengthOfSegment(value[index + 1].getLengthOfSegment() + 1);
                        value[index].setNbBitUseless(value[index + 1].getNbBitUseless());
                }
            }
        }

        value[index].setCost(min);
        return min;
    }

}
