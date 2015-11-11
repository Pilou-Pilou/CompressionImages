import algo.Cout;
import algo.SegmentForRecursive;
import data.Bit;
import data.Data;
import file.BitInputStream;
import file.BitOutputStream;
import file.LoadPictures;

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
            //bitInputStream = new file.BitInputStream(new LoadPictures().getBarbara());
            //bitInputStream = new file.BitInputStream(new LoadPictures().getGoldhill());
          //  bitInputStream = new file.BitInputStream(new LoadPictures().getLena());
            //bitInputStream = new file.BitInputStream(new LoadPictures().getPeppers());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       // createTableOfByte();
       
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
    	try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream(_picName+"-iterative.seg"));
			_compressedFiles.add(_picName+"-iterative.seg");
			
			LinkedList<Cout> couts = new LinkedList<Cout>();
			int segmentCreation, addToSegment;
			//number min of required bits in pixels i and i-1
			int b_i, b_i_1, n_i_1;
					
			int min_i_1 = NB_HEADERS + Bit.getNbBitUseInPixel(Data.arrayOfByte[0]);
			couts.add(new Cout(Cout.CREATION, min_i_1, Bit.getNbBitUseInPixel(Data.arrayOfByte[0]), 1, 1));

			System.out.println("1st aprcours...");
			//first parcours : cmp i and i-1
			for(int i=1; i<Data.arrayOfByte.length; i++){
				System.out.println(i);
				
				//number min of required bits in pixels i and i-1
				b_i = Bit.getNbBitUseInPixel(Data.arrayOfByte[i]);
				b_i_1 = Bit.getNbBitUseInPixel(Data.arrayOfByte[i-1]);
				n_i_1 = couts.getLast().getNbBits();//number of pixels in the last segment
				
				segmentCreation = min_i_1+NB_HEADERS+ Bit.getNbBitUseInPixel(Data.arrayOfByte[i]);
				
				if(b_i<=b_i_1) //current pixel can directly be added to the segment
					addToSegment = min_i_1 + b_i_1;
				else //the segment must be extended to store a new pixel
					addToSegment = min_i_1 + b_i + n_i_1 * (b_i - b_i_1);
				
				// return min(segmentCreation, addToSegment)
				if(segmentCreation<addToSegment){ //create a new segment
					couts.add(new Cout(Cout.CREATION, segmentCreation, b_i, 1, couts.getLast().getNumSegment()+1));
					min_i_1 = segmentCreation;
				}
				else{ //add to the last segment
					int nbPixelsSeg = couts.getLast().getNbPixels();
					if(nbPixelsSeg<255){
						if(b_i_1>b_i)
							b_i = b_i_1;
						
						couts.add(new Cout(Cout.AJOUT_imoins1, addToSegment, b_i, nbPixelsSeg+1, couts.getLast().getNumSegment()));
						min_i_1 = addToSegment;
					} 
					else { //the last segment is full => we create a new
						couts.add(new Cout(Cout.CREATION, segmentCreation, b_i, 1, couts.getLast().getNumSegment()+1));
						min_i_1 = segmentCreation;
					}
				}
			}
			
			System.out.println("2nd aprcours...");
			
			//second parcours : cmp i+1 and i
			ListIterator<Cout> it = couts.listIterator(couts.size());
			Cout c_i_1 = it.previous();
			int i = couts.size()-1;
			while(it.hasPrevious()){
				System.out.println("\t\t"+i);
				
				Cout c_i = it.previous();
				//add i to i+1 ?
				b_i_1 = c_i_1.getNbBits();
				b_i = Bit.getNbBitUseInPixel(Data.arrayOfByte[i]);
				if(b_i<=b_i_1) //current pixel can directly be added to the segment
					addToSegment = c_i_1.getCout() + b_i;
				else //the segment must be extended to store a new pixel
					addToSegment = c_i_1.getCout() + b_i + c_i_1.getNbPixels() * (b_i - b_i_1);
				
				if(addToSegment<c_i.getCout()){
					if(c_i_1.getNbPixels()<255){
						if(b_i_1>b_i)
							b_i = b_i_1;
						
						c_i.setCout(addToSegment);
						c_i.setDecision(Cout.AJOUT_iplus1);
						c_i.setNbBits(b_i);
						c_i.setNbPixels(c_i_1.getNbPixels()+1);
						c_i.setNumSegment(c_i_1.getNumSegment());
					}
				}
				
				c_i_1 = c_i;
				i--;
			}
			

			System.out.println("Segments creation...");
			//dernier aprcours pour construire les segments
			LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
			it = couts.listIterator();
			i=0;
			while(it.hasNext()){
				if(it.next().getNumSegment()>compressedSegments.size())//new segment
					compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[i]));
				else //add to the segment
					compressedSegments.getLast().push(Data.arrayOfByte[i]);	
				
				i++;
			}

			System.out.println("Writing segments...");
			//writing segments
			for(SegmentForRecursive seg : compressedSegments){
				out.write(seg.getSegment());
			}
			
			out.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Erreur : File not found");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
			System.out.println("Erreur : ");
			e.printStackTrace();
		}
    }
    
    public void iterativeNotWorking(){
    	try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream(_picName+"-iterative.seg"));
			_compressedFiles.add(_picName+"-iterative.seg");
			
			LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
			int segmentCreation, addToSegment;
			//number min of required bits in pixels i and i-1
			int b_i, b_i_1, n_i_1;
					
			int min_i_1 = NB_HEADERS + Bit.getNbBitUseInPixel(Data.arrayOfByte[0]);
			compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[0]));
			
			for(int i=1; i<Data.arrayOfByte.length; i++){
				//number min of required bits in pixels i and i-1
				b_i = Bit.getNbBitUseInPixel(Data.arrayOfByte[i]);
				b_i_1 = Bit.getNbBitUseInPixel(Data.arrayOfByte[i-1]);
				n_i_1 = compressedSegments.get(compressedSegments.size()-1).getNbBits();//number of pixels in the last segment
				
				segmentCreation = min_i_1+NB_HEADERS+ Bit.getNbBitUseInPixel(Data.arrayOfByte[i]);
				
				if(b_i<=b_i_1) //current pixel can directly be added to the segment
					addToSegment = min_i_1 + b_i_1;
				else //the segment must be extended to store a new pixel
					addToSegment = min_i_1 + b_i + n_i_1 * (b_i - b_i_1);
				
				// return min(segmentCreation, addToSegment)
				if(segmentCreation<addToSegment){ //create a new segment
					compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[i]));			
					min_i_1 = segmentCreation;
				}
				else{ //add to the last segment
					try {
						compressedSegments.getLast().push(Data.arrayOfByte[i]);
						min_i_1 = addToSegment;
					} catch (Exception e) { //the last segment is full => we create a new
						compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[i]));
						min_i_1 = segmentCreation;
					}
				}
			}
			
			
			//writing segments
			for(SegmentForRecursive seg : compressedSegments){
				out.write(seg.getSegment());
			}
			
			out.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Erreur : File not found");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
			System.out.println("Erreur : ");
			e.printStackTrace();
		}
    }
    
    public void recursive(){
    	try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream(_picName+"-recursive.seg"));
			_compressedFiles.add(_picName+"-recursive.seg");
		
			//computing segments
			LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
			getMin(Data.arrayOfByte, Data.arrayOfByte.length-1, compressedSegments);
			
			//TestRecursiveSegmentCreation test = new TestRecursiveSegmentCreation(compressedSegments);
			
			//writing segments
			for(SegmentForRecursive seg : compressedSegments){
				out.write(seg.getSegment());
			}
			
			out.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Erreur : File not found");
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (Exception e) {
			System.out.println("Erreur : ");
			e.printStackTrace();
		} 
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
