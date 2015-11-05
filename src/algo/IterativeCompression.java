package algo;

import data.Data;
import data.Value;
import file.BitOutputStream;

import javax.xml.crypto.OctetStreamData;
import java.io.FileNotFoundException;
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

        System.out.println("----Segment-Start---");
        // create the sequence and put it in the file
        int idMax,nbBiteUse;
        BitOutputStream a = new BitOutputStream(new FileOutputStream("tt.raw"));
        for(int i=0;i<value.length;i++){

            idMax = value[i].getLengthOfSegment()+i;
            nbBiteUse = value[i].getNbBitUse();
            Segment segment = new Segment(value[i].getLengthOfSegment(),nbBiteUse);
            System.out.println(i+" "+idMax);
            while(i < idMax){
                segment.fillBodySegement(Data.arrayOfByte[i]);
                i++;
            }
            a.write(segment);
            // for don't have twice i++ (in while and with for)
            i--;
        }
        a.close();
        System.out.println("----Segment-End---");
    }

    /**
     * This method initialise all case of table with object value.
     */
    public void init(){

        for(int i=0;i<value.length;i++){
            value[i]= new Value();
        }
    }

    /**.
     *
     * @param pixel : it is the pixel who was describe with 8 bits in a string.
     *
     * @return the number of bite who is useful.
     */
    public int nbBitUseInPixel(String pixel){

        int tmp =0;
           if(pixel.charAt(0)=='0'){
                tmp ++;
               if(pixel.charAt(1)=='0'){
                   tmp ++;
                   if(pixel.charAt(2)=='0'){
                       tmp ++;
                       if(pixel.charAt(3)=='0'){
                           tmp ++;
                           if(pixel.charAt(4)=='0'){
                               tmp ++;
                               if(pixel.charAt(5)=='0'){
                                   tmp ++;
                                   if(pixel.charAt(6)=='0'){
                                        tmp ++;
                                        if(pixel.charAt(7)=='0'){
                                            tmp ++;
                                        }
                                   }
                               }
                           }
                       }
                   }
               }
           }
        return 8-tmp;
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
        int nbBitUse = nbBitUseInPixel(Data.arrayOfByte[index]);

        if(index == Data.arrayOfByte.length-1){
            min = 11 + nbBitUse;
            value[index].setLengthOfSegment(1);
            value[index].setNbBitUse(nbBitUse);
        }else {
            if(value[index+1].getLengthOfSegment() >= 256){
                min = 11 + nbBitUse;
                value[index].setLengthOfSegment(1);
                value[index].setNbBitUse(nbBitUse);
            }else {
                if (nbBitUse > value[index + 1].getNbBitUse()) {
                    // 0111 0010 0011
                    tmp1 = value[index + 1].getCost() + 11 + nbBitUse;
                    tmp2 = value[index + 1].getCost() + nbBitUse + value[index + 1].getLengthOfSegment() * (nbBitUse - value[index + 1].getNbBitUse());
                    min = Math.min(tmp1, tmp2);
                    if (min == tmp1) {
                        value[index].setLengthOfSegment(1);
                        value[index].setNbBitUse(nbBitUse);
                    } else {
                        value[index].setLengthOfSegment(value[index + 1].getLengthOfSegment() + 1);
                        value[index].setNbBitUse(nbBitUse);
                    }
                } else if (nbBitUse == value[index + 1].getNbBitUse()) {
                    // un seul choix possible car on ne peut pas prendre moin de bit et si on prend plus de bit sa nous coutera plus chére
                    tmp2 = value[index + 1].getCost() + value[index + 1].getNbBitUse();
                    min = tmp2;
                    value[index].setLengthOfSegment(value[index + 1].getLengthOfSegment() + 1);
                    value[index].setNbBitUse(nbBitUse);
                } else {
                    // 0011  0111  0101
                    tmp1 = value[index + 1].getCost() + 11 + nbBitUse;
                    tmp2 = value[index + 1].getCost() + value[index + 1].getNbBitUse();
                    min = Math.min(tmp1, tmp2);
                    if (min == tmp1) {
                        value[index].setLengthOfSegment(1);
                        value[index].setNbBitUse(nbBitUse);
                    } else {
                        value[index].setLengthOfSegment(value[index + 1].getLengthOfSegment() + 1);
                        value[index].setNbBitUse(value[index + 1].getNbBitUse());
                    }
                }
            }
        }
        value[index].setCost(min);
        return min;
    }

}
