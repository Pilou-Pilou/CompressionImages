import algo.IterativeAlgo;
import algo.RecursiveAlgo;
import algo.Cout;
import data.Bit;
import data.Data;
import file.BitInputStream;
import file.BitOutputStream;
import file.LoadPictures;
import testsAlgo.SegmentForRecursive;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * <b>File :</b> Compressor.java<br>
 * <b>Creation date :</b> 27/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class CompressionImage {
	public static final int NB_HEADERS = 11;

    private BitInputStream bitInputStream;
    private String _picName;
    private HashSet<String> _compressedFiles = new HashSet<String>();

    /**
     * Constructor of the CompressionImage who permit to load the picture
     * and put the image in array of bite.
     */
    public CompressionImage(String picName){

        try {
        	_picName = picName;
            bitInputStream = new BitInputStream(new LoadPictures().get(picName));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       createTableOfByte();
       
    }
    
    /**
     * Constructor of the CompressionImage who permit to load the picture
     * and put the image in array of bite.
     */
    public CompressionImage(String[] array){
       Data.arrayOfByte = array;
    }

    /**
     * This method put the picture choose in array of bite.
     */
    private void createTableOfByte(){

        try {
            Data.arrayOfByte = bitInputStream.readByteToArray(bitInputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public void iterative(){
    	System.out.println("Compression itérative...");
    
    	String compressedFile = _picName+"-iterative.seg";
    	_compressedFiles.add(compressedFile);
    	new IterativeAlgo(compressedFile);
    	
    	System.out.println("Compression itérative terminée.");
    }
    

    public void recursive(){
    	System.out.println("Compression récursive...");
    	String compressedFile = _picName+"-recursive.seg";
    	_compressedFiles.add(compressedFile);
    	new RecursiveAlgo(compressedFile);
    	System.out.println("Compression récursive terminée.");
    }
    
    
    public void decompress(String path){
    	BitInputStream bitInputStream;
    	BitOutputStream bitOutputStream;
    	
    	if(path!=null)
    		_compressedFiles.add(path);
    	
    	Iterator<String> files = _compressedFiles.iterator();
    	
    	 try {
    		 while(files.hasNext()){
    			 String file = files.next();

    		        System.out.println("Decompression de "+file+"...");
    			 
    			 bitOutputStream = new BitOutputStream(new FileOutputStream(file+"-decompressed.raw"));
                 bitInputStream = new BitInputStream(file);
                 
                 String tmp;
                 try {
                     while(bitInputStream.available()>0) {
                         tmp = bitInputStream.readSegment();
                         //System.out.println("Will write : "+tmp);
         					bitOutputStream.write(tmp);
                     }

                 } catch (Exception e) {
                     e.printStackTrace();
                 }finally{

                     try {
	                     bitInputStream.close();
	                     bitOutputStream.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }

                 System.out.println("Decompression de "+file+" terminée.");
    		 }
             
         }catch (FileNotFoundException e){
             e.printStackTrace();
         }
    }
    
    /**
	 * 
	 * bytes : all bytes composing the picture
	 * index : the current byte to analyze : does it must be added to the last 
	 * compressed segment, or a new one must be created to store it and maybe next bytes ?
	 * compressedSegments : here are added all segments which represent the compressed bytes
	 * @return
	 * @throws Exception If the index parameter is less than 0 or one of bytes from the bytes
	 * parameter is not writable with 1 byte (e.g. the corresponding integer value is higher than 255)
	 */
	int getMin(String[] bytes, int index, LinkedList<SegmentForRecursive> compressedSegments) throws Exception{
	
		//check pre-conditions
		if(index<0)
			throw new Exception("The index parameter can't be less than 0");
		if(bytes == null || bytes.length==0)
			return 0;
		if(compressedSegments == null)
			compressedSegments = new LinkedList<SegmentForRecursive>();
		
		
		//last pixel : we only can create a sequence
		if(index==0){
			compressedSegments.add(new SegmentForRecursive(bytes[index]));
			return NB_HEADERS + Bit.getNbBitUseInPixel(bytes[index]);
		}		
	
		//cost at pixel i-1
		int min_i_1 = getMin(bytes, index-1, compressedSegments);
		//number min of required bits in pixels i and i-1
		int b_i = Bit.getNbBitUseInPixel(bytes[index]);
		int b_i_1 = Bit.getNbBitUseInPixel(bytes[index-1]);
		
		//cost of all segment from pixels 0 to i if we add a segment to store the pixel i
		int segmentCreation = min_i_1 + NB_HEADERS + b_i;
				
		int addToSegment;
		int n_i_1 = compressedSegments.get(compressedSegments.size()-1).getNbBits();//number of pixels in the last segment
		
		
		if(b_i<=b_i_1) //current pixel can directly be added to the segment
			addToSegment = min_i_1 + b_i_1;
		else //the segment must be extended to store a new pixel
			addToSegment = min_i_1 + b_i + n_i_1 * (b_i - b_i_1);
		
		
		// return min(segmentCreation, addToSegment)
		if(segmentCreation<addToSegment){ //create a new segment
			compressedSegments.add(new SegmentForRecursive(bytes[index]));			
			return segmentCreation;
		}
		else{ //add to the last segment
			try {
				compressedSegments.getLast().push(bytes[index]);
				return addToSegment;
			} catch (Exception e) { //the last segment is full => we create a new
				compressedSegments.add(new SegmentForRecursive(bytes[index]));
				return segmentCreation;
			}
		}
	}
}
