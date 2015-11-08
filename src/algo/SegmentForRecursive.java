package algo;

import data.Bit;

import java.util.Iterator;
import java.util.LinkedList;

public class SegmentForRecursive implements Segment {
	private int _nbBits = 0; //number of bits for each pixel in this segment
	
	private LinkedList<String> _pixels = new LinkedList<String>(); //pixels in the segment
	private Iterator<String> _pixelsIt = _pixels.iterator();
	
	public SegmentForRecursive(){
	}
	
	
	public SegmentForRecursive(String pixel) {
		try {
			this.push(pixel);
			this._nbBits = Bit.getNbBitUseInPixel(pixel);
		} catch (Exception e) {
			// cannot be reached in the constructor
			// (push throws an exception only if the HashSet has more than 255 values -> impossible)
		}
	}
	
	
	public void incrNbBits() throws Exception{
		if(_nbBits==7)
			throw new Exception("The number of bits by pixel in the segment cannot be written with more than 3 bits");
		
		_nbBits++;
	}
		
	
	/**
	 * 
	 * @return Number of pixels in the segment
	 */
	public int getNbPixels(){
		return _pixels.size();
	}

	/**
	 * 
	 * @return Number of bits by pixel in the segment
	 */
	public int getNbBits(){
		return _nbBits;
	}
	
	public void push(String pixel) throws Exception{
		if(_pixels.size()==255)
			throw new Exception("The number of pixels in the segment cannot be written with more than 8 bits => a segment cannot contain more than 255 pixels");
		
		_pixels.add(pixel);
	}
	
	public boolean canPop(){
		return _pixelsIt.hasNext();
	}
	
	public String pop() throws Exception{
		if(_pixelsIt.hasNext())
			return _pixelsIt.next();
		else
			throw new Exception("There is no pixels to pop");
	}
	
	/**
	 * Pop a pixel veryfying it has the well number of bits it mus have in this sequence
	 * E.g. add some 0 in front of the string to get _nbBits characters, or remove some
	 * bits in front of the String to get _nbBits characters
	 * @throws Exception 
	 **/
	public String popCompressedPixel() throws Exception{
		if(_pixelsIt.hasNext()){
			String pixel = _pixelsIt.next();
			
			//pixel to pop is too big -> remove some bits
			if(pixel.length()>_nbBits)
				pixel = pixel.substring(pixel.length()-this._nbBits);
			
			//pixel to pop is too little -> add some bits
			while(pixel.length()<_nbBits)
				pixel = '0' + pixel;
			
			return pixel;				
		}
		else
			throw new Exception("There is no pixels to pop");
	}
		
	public void reset(){
		_pixelsIt = _pixels.iterator();
	}


	@Override
	public String getSegment() {
		String segment = "";

		try {
			//create the header
			segment = String.valueOf(_pixels.size());
			segment += String.valueOf(8-_nbBits);
			
			//add each pixel
			while(this.canPop()){
				segment += String.valueOf(this.popCompressedPixel());
			}
			
			return segment;
		}
		catch (Exception e) {
			return segment;
		}
	}
}
