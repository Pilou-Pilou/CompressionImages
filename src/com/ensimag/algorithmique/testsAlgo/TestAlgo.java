package com.ensimag.algorithmique.testsAlgo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import com.ensimag.algorithmique.data.Bit;
import com.ensimag.algorithmique.data.Data;

import com.ensimag.algorithmique.file.BitOutputStream;

public class TestAlgo {
	public static final int NB_HEADERS = 11;
	
	//abscisses : pixels
	//ordonnees : number of pixels in the segment
	//case : cost having a segment beginning with the pixel in abscisse and having a number of pixels specified in ordonnees
	static Integer[][] _creationCosts = new Integer[512*512][255];
	
	static Integer[] _numPixels = new Integer[512*512];

	
	static LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
	
	public static void testSansHeaders(){
		try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream("tmp-testSsHeaders.seg"));
					

			System.out.println("Creating segments...");
			compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[0]));
			
			for(int i=1; i<Data.arrayOfByte.length;i++){

				SegmentForRecursive lastSeg = compressedSegments.getLast();
				
				int b_seg = lastSeg.getNbBits();
				int b_i = Bit.getNbBitUseInPixel(Data.arrayOfByte[i]);
				
				int b_seg_with_i = b_seg>b_i?b_seg:b_i;
				
				int cost_seg_with_i = b_seg_with_i*(lastSeg.getNbPixels()+1);
				int total_cost_without_i = b_i+lastSeg.getCout();
				
				if(cost_seg_with_i <= total_cost_without_i){
					lastSeg.push(Data.arrayOfByte[i]);
				}
				else{
					compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[i]));
				}
			}
			
			System.out.println(compressedSegments.size()+" segments");
			Iterator<SegmentForRecursive> it = compressedSegments.iterator();
			for(int i=0; it.hasNext(); i++){
				System.out.println("Segment "+i);
				SegmentForRecursive seg = it.next();
				System.out.println("Size : "+seg.getNbPixels());
				System.out.println("Nb bits : "+seg.getNbBits());
			}
			/*
			//writing segments
			for(SegmentForRecursive seg : compressedSegments){
				out.write(seg.getSegment());
			}
			*/
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
	
	
	public static Integer compute(int y, int x, int nbUsedBitsByY){
		if(x==0)
			_creationCosts[y][x] = NB_HEADERS+nbUsedBitsByY;
		else	
			_creationCosts[y][x] = compute(y, x-1, nbUsedBitsByY)+nbUsedBitsByY;
		
		return _creationCosts[y][x];
	}
	
	
	public static void testDebile(){
		try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream("tmp-testDeb.seg"));
					

			System.out.println("Creating segments...");
			compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[0]));
			for(int i=1; i<Data.arrayOfByte.length;i++){
				if(Bit.getNbBitUseInPixel(Data.arrayOfByte[i]) == compressedSegments.getLast().getNbBits())
					compressedSegments.getLast().push(Data.arrayOfByte[i]);
				else
					compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[i]));
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
	
	public static void testAlgo(String saved){
		/*
			//computing costs
			System.out.println("Computing costs...");
			LinkedList<Integer> couts_iplus1 = new LinkedList<Integer>();
			couts_iplus1.add(NB_HEADERS+Bit.getNbBitUseInPixel(Data.arrayOfByte[Data.arrayOfByte.length-1]));
			_creationCosts.add(couts_iplus1);
			
			for(int i = Data.arrayOfByte.length-2; i>=0; i--){
				System.out.println(i);
				int cost = Bit.getNbBitUseInPixel(Data.arrayOfByte[i]);
				
				LinkedList<Integer> couts_i = new LinkedList<Integer>();
				couts_i.add(NB_HEADERS+cost);
				
				Iterator<Integer> couts_iplus1It = couts_iplus1.iterator();
				for(int j=0; couts_iplus1It.hasNext() && j<255; j++){
					System.out.print(j+",");
					couts_i.add(couts_iplus1It.next()+cost);
				}
				
				couts_iplus1 = couts_i;
				_creationCosts.add(couts_iplus1);
			}
	*/
		 
		try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream("tmp-testAlgo.seg"));
					

			System.out.println("Creating segments...");
			
			for(int y=0; y<_creationCosts.length; y++){
				System.out.println(y);
				
				
				Integer[] pixelCosts = _creationCosts[y];
				for(int x=0; x<pixelCosts.length; x++){
					if(pixelCosts[x] == null)
						compute(y,x, Bit.getNbBitUseInPixel(Data.arrayOfByte[y]));
					
					if(pixelCosts[x]==null)
						throw new Exception("ERROR : couldnt compute "+y+","+x);
					
					//if lineX create its own segment => we dont use it
					if(createSegment(x, pixelCosts[x])){
						SegmentForRecursive seg = new SegmentForRecursive();
						
						while(y<x){
							seg.push(Data.arrayOfByte[y]);
							y++;
						}
						
						compressedSegments.add(seg);
						
					}
										
					x++;
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
	
	//create a segment for pixel y ?
	/**
	 * 
	 * @param y
	 * @param cost
	 * @return false : no better segment can be created for pixel y
	 * @throws Exception
	 */
	public static boolean createSegment(int y, int cost) throws Exception{
		int end = Data.arrayOfByte.length;
		
		for(int i =0; i<255 && (end-i)>y; i++){
			if(_creationCosts[y][i] == null)
				compute(y,i, Bit.getNbBitUseInPixel(Data.arrayOfByte[y]));
			if(_creationCosts[y][i] == null)
				throw new Exception("ERROR : couldnt compute "+y+","+i);
			
			if(_creationCosts[y][i] < cost){
				if(!createSegment(i, _creationCosts[y][i])){
					return true;
				}
			}
		}
		
		return false;
	}
	
}

