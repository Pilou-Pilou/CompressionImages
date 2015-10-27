import data.Data;
import file.BitInputStream;
import file.LoadPictures;

import java.io.FileNotFoundException;
import java.io.IOException;

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

    private void createTableOfByte(){

        try {
            Data.arrayOfByte = bitInputStream.readBitsToArray(512*512);
            System.out.println(Data.arrayOfByte[512*512-1]); // derniere case
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
