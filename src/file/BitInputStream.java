package file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * <b>File :</b> file.BitInputStream.java<br>
 * <b>Creation date :</b> 26/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class BitInputStream extends FilterInputStream {

	private int bitsLeft;
	private int bitsCountLeft;

 	public BitInputStream(String in) throws FileNotFoundException {
        super(new FileInputStream(in));
		clearBuffer();
	}

	public String[] readBitsToArray(int nbPixels) throws IOException {
		if (nbPixels <= 0) {
			throw new IllegalArgumentException();
		}

        int bit;
		String[] res = new String[nbPixels];
        for(int j = 0; j<nbPixels;j++) {
            res[j] = "";
            for (int i = 0 ; i < 8; i++) {
                bit = readBit();
                if (bit < 0) {
                    return Arrays.copyOf(res, i);
                } else {
                    res[j] += bit;
                }
            }
        }
		return res;
	}

	public int readBit() throws IOException {
		if (bitsCountLeft == 0) {
			bitsLeft = super.read();
			bitsCountLeft = 8;

			if (bitsLeft < 0) {
				bitsCountLeft = 0;
				return -1;
			}
		}

		return (bitsLeft & mask(--bitsCountLeft)) >> bitsCountLeft;
	}

	private void clearBuffer() {
		bitsLeft = 0;
		bitsCountLeft = 0;
	}

	private static byte mask(int bitNumber) {
		if (bitNumber < 0 || bitNumber > 8) {
			throw new IllegalArgumentException();
		}
		return (byte) (1 << bitNumber);
	}
}
