package com.ensimag.algorithmique.tests;

import java.io.FileOutputStream;

import com.ensimag.algorithmique.data.Data;
import com.ensimag.algorithmique.file.BitInputStream;
import com.ensimag.algorithmique.file.BitOutputStream;
import com.ensimag.algorithmique.file.LoadPictures;

public class TestReadWriteFile {
	private BitInputStream bitInputStream;
	private BitOutputStream bitOutputStream;
	
	/**
	 * Read the com.ensimag.algorithmique.file Baboon then write it in baboon-test.raw to check if a com.ensimag.algorithmique.file is well read then write
	 */
	public TestReadWriteFile(){

		System.out.println("Test de la lecture/�criture d'un fichier...");
		 try {
			bitInputStream = new BitInputStream(new LoadPictures().getPng());
            bitOutputStream = new BitOutputStream(new FileOutputStream("face-test.png"));
            
            Data.arrayOfByte = bitInputStream.readByteToArray(bitInputStream.available());
            
            for(int i=0; i< Data.arrayOfByte.length; i++) {
				bitOutputStream.write(Data.arrayOfByte[i]);
            }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		System.out.println("Test termin�.");
	}
}
