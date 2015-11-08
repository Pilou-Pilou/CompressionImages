import algo.IterativeCompression;
import algo.RecursiveCompression;
import data.Data;
import file.BitInputStream;
import file.LoadPictures;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * <b>File :</b> Compressor.java<br>
 * <b>Creation date :</b> 27/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class CompressionImage {

    private BitInputStream bitInputStream;

    /**
     * Constructor of the CompressionImage who permit to load the picture
     * and put the image in array of bite.
     */
    public CompressionImage(){

        try {

            bitInputStream = new BitInputStream(new LoadPictures().getBaboon());
            //bitInputStream = new file.BitInputStream(new LoadPictures().getBarbara());
            //bitInputStream = new file.BitInputStream(new LoadPictures().getGoldhill());
            //bitInputStream = new file.BitInputStream(new LoadPictures().getLena());
            //bitInputStream = new file.BitInputStream(new LoadPictures().getPeppers());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        createTableOfByte();


    }

    /**
     * This method put the picture choose in array of bite.
     */
    private void createTableOfByte(){

        try {
        	Date date = new Date();
        	long begin, end;
            Data.arrayOfByte = bitInputStream.readByteToArray(bitInputStream.available());
            
            System.out.println("Iterative method...");
            begin = date.getTime();
            new IterativeCompression();
            end = date.getTime();
            System.out.println("done in "+String.valueOf(end-begin)+"ms.");
            
            System.out.println("Recursive method...");
            begin = date.getTime();
            new RecursiveCompression();
            end = date.getTime();
            System.out.println("done in "+String.valueOf(end-begin)+"ms.");
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
