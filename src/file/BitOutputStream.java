package file;

import java.io.IOException;
import java.io.OutputStream;

import testsAlgo.Segment;
import testsAlgo.SegmentForIterative;

/**
 * <b>File :</b> BitOutputStream.java<br>
 * <b>Creation date :</b> 04/11/2015<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class BitOutputStream {

    // Underlying byte stream to write to.
    private OutputStream output;

    // The accumulated bits for the current byte. Always in the range 0x00 to 0xFF.
    private int currentByte;

    // The number of accumulated bits in the current byte. Always between 0 and 7 (inclusive).
    private int numBitsInCurrentByte;



    // Creates a bit output stream based on the given byte output stream.
    public BitOutputStream(OutputStream out) {
        if (out == null)
            throw new NullPointerException("Argument is null");
        output = out;
        currentByte = 0;
        numBitsInCurrentByte = 0;
    }

    public void write(Segment segment){

        try {
			write(segment.getSegment());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void write(String bite) throws Exception{
        for(int i=0;i<bite.length();i++){
            try {
                write(Integer.parseInt(String.valueOf(bite.charAt(i))));
            } catch (Exception e) {
            	System.out.println("Tried to write : "+bite);
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }


    // Writes a bit to the stream. The specified bit must be 0 or 1.
    private void write(int b) throws IOException {
        if (!(b == 0 || b == 1))
            throw new IllegalArgumentException("Argument must be 0 or 1 but is "+b);
        currentByte = currentByte << 1 | b;
        numBitsInCurrentByte++;
        if (numBitsInCurrentByte == 8) {
            output.write(currentByte);
            numBitsInCurrentByte = 0;
        }
        
    }


    // Closes this stream and the underlying OutputStream. If called when this bit stream is not at a byte boundary,
    // then the minimum number of "0" bits (between 0 and 7 of them) are written as padding to reach the next byte boundary.
    public void close() throws IOException {
        while (numBitsInCurrentByte != 0)
            write(0);
        output.close();
    }
}
