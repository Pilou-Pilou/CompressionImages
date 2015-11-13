

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
    	System.out.println("Compression/décompression de fichiers...");
    	//new TestReadWriteFile();
    	
    	CompressionImage img = new CompressionImage("Baboon.raw");
    	img.iterative();
        img.recursive();
    	
        img.decompress(null);
        
    	System.out.println("Compression/décompression terminée !");
    }
}
