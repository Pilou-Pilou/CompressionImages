package algo;

import java.util.LinkedList;

import data.Bit;
import data.Data;

public class Julien {
	public final static int NB_HEADERS = 11;
	
	
	public static int proposition(int n_i_1,int b_i_1,int cost,String[] bytes, int index, LinkedList<SegmentForRecursive> compressedSegments) throws Exception {
	   //last pixel : we only can create a sequence
	   int b_i = Bit.getNbBitUseInPixel(bytes[index]);
	   if (index == 0) {
	      //compressedSegments.add(new SegmentForRecursive(bytes[index]));
	      return proposition(1, b_i, NB_HEADERS + b_i, bytes, index + 1, compressedSegments);
	   }
	   if (index == Data.arrayOfByte.length - 1) {
	      return Math.min(cost + NB_HEADERS + b_i, cost - (n_i_1 * b_i_1) + Math.max(b_i, b_i_1) * (n_i_1 + 1));
	   }
	   if (n_i_1 == 255)
	      return proposition(1, b_i, cost + NB_HEADERS + b_i, bytes, index + 1, compressedSegments);
	   else {
	      //cost of all segment from pixels 0 to i if we add a segment to store the pixel i
	      int addToSegment = proposition(n_i_1 + 1, Math.max(b_i, b_i_1), cost - (n_i_1 * b_i_1) + Math.max(b_i, b_i_1) * (n_i_1 + 1), bytes, index + 1, compressedSegments);
	      int segmentCreation =proposition(1, b_i, cost + NB_HEADERS + b_i, bytes, index + 1, compressedSegments);
	      return Math.min(segmentCreation, addToSegment);
	   }
	}
}
