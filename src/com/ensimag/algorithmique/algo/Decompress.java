package com.ensimag.algorithmique.algo;

import com.ensimag.algorithmique.file.BitInputStream;
import com.ensimag.algorithmique.file.BitOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/*
 * 
 * 
 * ATTENTION
 * 
 * A �t� d�plac� vers com.ensimag.algorithmique.CompressionImage.decompress() ; cette classe n'est plus appel�e
 * 
 * 
 * 
 * 
 * 
 */


/**
 * <b>File :</b> Decompress.java<br>
 * <b>Creation date :</b> 05/11/2015<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Decompress {

    BitInputStream bitInputStream;
    BitOutputStream bitOutputStream;

    /**
     * Constructor of the class Decompress who will open two com.ensimag.algorithmique.file .seg and .raw.
     */
    public Decompress(String pathFileSeg){
        try {
            bitOutputStream = new BitOutputStream(new FileOutputStream("final.raw"));
            bitInputStream = new BitInputStream(pathFileSeg);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * This read all segment in the com.ensimag.algorithmique.file .seg and rewrite all pixel by using 8 bit.
     */
    public void start(){

        String tmp;
        try {
            while(bitInputStream.available()>0) {
                tmp = bitInputStream.readSegment();
                //System.out.println("Will write : "+tmp);
					bitOutputStream.write(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            bitInputStream.close();
            bitOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
