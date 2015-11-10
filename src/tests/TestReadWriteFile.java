package tests;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import data.Data;
import file.BitInputStream;
import file.BitOutputStream;
import file.LoadPictures;

public class TestReadWriteFile {
	private BitInputStream bitInputStream;
	private BitOutputStream bitOutputStream;
	
	/**
	 * Read the file Baboon then write it in baboon-test.raw to check if a file is well read then write
	 */
	public TestReadWriteFile(){

		System.out.println("Test de la lecture/écriture d'un fichier...");
		 try {
			bitInputStream = new BitInputStream(new LoadPictures().getBaboon());
            bitOutputStream = new BitOutputStream(new FileOutputStream("baboon-test.raw"));
            
            Data.arrayOfByte = bitInputStream.readByteToArray(bitInputStream.available());
            
            for(int i=0; i< Data.arrayOfByte.length; i++) {
				bitOutputStream.write(Data.arrayOfByte[i]);
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		System.out.println("Test terminé.");
	}
}
