package algo;

import java.util.LinkedList;

import data.Bit;
import data.Data;

public class RecursiveCompression {
	public final int NB_HEADERS = 11;
	
	public RecursiveCompression(){
		LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
		try {
			getMin(Data.arrayOfByte, Data.arrayOfByte.length-1, compressedSegments);
		} catch (Exception e) {
			System.out.println("Erreur : ");
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
			return NB_HEADERS;
		}
		
		
		
		
		int min_i_1 = getMin(bytes, index-1, compressedSegments);
		int b_i = Bit.getNbBitUseInPixel(bytes[index]);
		int b_i_1 = Bit.getNbBitUseInPixel(bytes[index-1]);
		
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
	
	/**
	 * 
	 * @param octet : An integer coded on 1 byte, thus a number between 0 and 255
	 * @return Nb of signficative bits in the byte (eg : 8-(index of the first 1 starting from the left) ) 
	 * @throws Exception 
	 *
	static int getNbSignificatives(int octet) throws Exception{
		if(octet>255)
			throw new Exception("Error - The specified number should be codable on 1 byte");
		else if(octet>=128)
			return 8;
		else if(octet>=64)
			return 7;
		else if(octet>=32)
			return 6;
		else if(octet>=16)
			return 5;
		else if(octet>=8)
			return 4;
		else if(octet>=4)
			return 3;
		else if(octet>=2)
			return 2;
		else if(octet>=1)
			return 1;
		else
			return 0;		
	}*/
}
