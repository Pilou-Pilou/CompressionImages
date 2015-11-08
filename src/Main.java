import algo.Decompress;
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
        new CompressionImage();
        new Decompress("tmp-iterative.seg").start();
    }
}
