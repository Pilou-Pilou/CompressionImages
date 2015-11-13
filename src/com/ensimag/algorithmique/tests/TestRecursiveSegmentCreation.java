package com.ensimag.algorithmique.tests;

import java.util.LinkedList;

import com.ensimag.algorithmique.testsAlgo.SegmentForRecursive;

public class TestRecursiveSegmentCreation {

	public TestRecursiveSegmentCreation(LinkedList<SegmentForRecursive> segments){
		for(SegmentForRecursive seg : segments){
			System.out.print("Compressed should be : "+Integer.toBinaryString(seg.getNbPixels())+","+Integer.toBinaryString(8-seg.getNbBits()));
			seg.reset();
			while(seg.canPop()){
				try {
					System.out.print(","+seg.popCompressedPixel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.print("\n");
			System.out.println("Compressed    It is  : "+seg.getSegment());
			

			System.out.print("UnCompressed should be : "+Integer.toBinaryString(seg.getNbPixels())+","+Integer.toBinaryString(8-seg.getNbBits()));
			seg.reset();
			while(seg.canPop()){
				try {
					System.out.print(","+seg.pop());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.print("\n");
			System.out.println("UnCompressed    It is  : "+decompressSegment(seg.getSegment()));
		}
	
	}
	
	public String decompressSegment(String seg){
		String completPixel="";
		String tmp="";
		String nbPixel="";
		String nbBiteUseless="";
		int index = 0;
		
		// read the length of segment
		for(int i=0;i<8;i++){
			nbPixel += seg.charAt(index);
			index++;
		}	
		
		//System.out.println(nbPixel);
		// read the number of bite use
		for(int i=0;i<3;i++){
			nbBiteUseless += seg.charAt(index);
			index++;
		}
		
		//System.out.println("Read header : "+nbPixel+nbBiteUseless);
		
		//System.out.println(nbBiteUseless);
		for(int i=0;i<Integer.parseInt(nbBiteUseless, 2);i++)
			tmp += "0";

		
		for(int i=0;i<Integer.parseInt(nbPixel, 2);i++){
			completPixel += tmp;
			for(int j=0;j<(8-Integer.parseInt(nbBiteUseless, 2));j++) {
				completPixel += seg.charAt(index);
				index++;
			}
		}

		//System.out.println(completPixel);
		return completPixel;
	}
}
