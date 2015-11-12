import algo.Decompress;
import algo.Jeremy;
import algo.TestAlgo;
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
    	
    	CompressionImage img = new CompressionImage("Baboon.raw");
    /*	System.out.println("Compression it�rative...");
        img.iterative();
        System.out.println("Compression r�cursive...");
        img.recursive();*/

    	new Jeremy();
    	
        System.out.println("Decompression...");
        img.decompress("tmp-testJerem.seg");
       // new Decompress("tmp-recursive.seg").start();
        System.out.println("Decompression termin�e.");
    }
}
