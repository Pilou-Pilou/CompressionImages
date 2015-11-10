import algo.Decompress;
import tests.TestReadWriteFile;

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
    	
        new CompressionImage();
        System.out.println("Decompression...");
        //new Decompress("tmp-iterative.seg").start();
        new Decompress("tmp-recursive.seg").start();
        System.out.println("Decompression terrminée.");
    }
}
