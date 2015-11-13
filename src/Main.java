import algo.Decompress;
import algo.Jeremy;
import algo.JeremyIteratif;
import algo.TestAlgo;
import data.Data;
import tests.TestReadWriteFile;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * <b>File :</b> Main.java<br>
 * <b>Creation date :</b> 26/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Main {

    public static void main (String [] args){
    	//new TestReadWriteFile();
    	
    	CompressionImageOriginel img = new CompressionImageOriginel("Baboon.raw");//Data.arrayOfByte2);
    /*	System.out.println("Compression itérative...");
        img.iterative();
        System.out.println("Compression récursive...");
        img.recursive();*/

    	new JeremyIteratif();
    	
        System.out.println("Decompression...");
        img.decompress("tmp-testJerem.seg");
       // new Decompress("tmp-recursive.seg").start();
        System.out.println("Decompression terminée.");
    }
}
