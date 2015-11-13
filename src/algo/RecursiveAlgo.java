package algo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import data.Bit;
import data.Data;
import data.Minimum;
import file.BitOutputStream;
import testsAlgo.SegmentForRecursive;

public class RecursiveAlgo {

	public static final int NB_HEADERS = 11;
	public static int INFINI= -1;
	
	public RecursiveAlgo(String compressedFile){
		Integer[][] costs = new Integer[Data.arrayOfByte.length][8];
		try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream(compressedFile));
					

			System.out.println("Computing costs with "+Data.arrayOfByte.length+" pixels...");
			Minimum[] mins = new Minimum[Data.arrayOfByte.length]; //minimum cost on a line
			getMin(0, costs, mins);
			
			//displayCosts(costs);
			//displayMins(mins);
			

			System.out.println("Creating segments...");
			LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
			segmentCreation(Data.arrayOfByte.length-1, costs, mins, compressedSegments);
			//displaySegments(compressedSegments);
					
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
			displayCosts(costs);
		} 
	}
	
/**
 * Return the min cost for the pixel indice
 * @param indice : pixel to study
 * @param costs : all computed costs ; for each pixel, for each possible number of bits to code a pixel in a segment (1..8), the computed cost
 * @param mins : for each pixel, the min cost
 * @return
 * @throws Exception
 */
	public int getMin(int indice, Integer[][] costs, Minimum[] mins) throws Exception{
		if(mins[indice]!=null){
			return mins[indice].getValue();			
		}
			
		int b_i = Bit.getNbBitUseInPixel(Data.arrayOfByte[indice]);
		int creation;
		int add;
		
		/* intial state -> last pixel */
		if(indice==Data.arrayOfByte.length-1){			
			//fill the line for the pixel
			for(int i=0;i<8;i++){
				if(b_i>(i+1)){//the pixel cannot be coded on i bits
					costs[indice][i] = INFINI;
				}
				else{
					//creation
					costs[indice][i] = NB_HEADERS+(i+1);
				}
				

				if(costs[indice][i]<-1)//we only do additions of positive numbers => result must be a positive number or INFINI => a negative result means a buffer overflow
					throw new Exception("Buffer overflow - Cost for pixel "+indice+" with "+Integer.toString(i+1)+" bits shouldn't have a negative value");
			

				//to get the min of all computed costs for the pixel indice
				if(i==0)
					mins[indice] = new Minimum(costs[indice][0], -1);
				else{
					if(mins[indice].getValue()==INFINI || (costs[indice][i]!=INFINI && costs[indice][i]<mins[indice].getValue())){
						mins[indice].setValue(costs[indice][i]);
						mins[indice].setIndice(i);
					}
				}
			}
			
		}
		else{ /* all other pixels */
			int min_iplus1 = getMin(indice+1, costs, mins);
			
			//fill the line for the pixel
			for(int i=0;i<8;i++){
				
				if(b_i>(i+1)){//the pixel cannot be coded on i bits
					costs[indice][i] = INFINI;
				}
				else{
					/* creation or add ? */
					if(min_iplus1==INFINI)
						throw new Exception("Le pixel suivant a forcément une valeur inférieure à l'infini");
					else
						creation = NB_HEADERS+(i+1)+min_iplus1;
					
					
					if(costs[indice+1][i]==INFINI)
						add = INFINI;
					else
						add = (i+1)+costs[indice+1][i];
					
					
					//costs[indice][i] = min(creation, add)
					if(creation == INFINI)
						costs[indice][i] = add;
					else if(add == INFINI)
						costs[indice][i] = creation;
					else
						costs[indice][i] = Math.min(creation, add);
				}
				

				if(costs[indice][i]<-1) //we only do additions of positive numbers => result must be a positive number or INFINI => a negative result means a buffer overflow
					throw new Exception("Buffer overflow - Cost for pixel "+indice+" with "+Integer.toString(i+1)+" bits shouldn't have a negative value");
			
				//to get the min of all computed costs for the pixel indice
				if(i==0)
					mins[indice] = new Minimum(costs[indice][0], 0);
				else{
					if(mins[indice].getValue()==INFINI || (costs[indice][i]!=INFINI && costs[indice][i]<mins[indice].getValue())){
						mins[indice].setValue(costs[indice][i]);
						mins[indice].setIndice(i);
					}
				}
			}
		}
		
		
		
		return mins[indice].getValue();
	}
	
/**
 * Create or add to the alst segment the pixel indice studying specified computed costs
 * @param indice
 * @param costs
 * @param mins
 * @param compressedSegments
 * @return
 */
	private int segmentCreation(int indice, Integer[][] costs, Minimum[] mins,
			LinkedList<SegmentForRecursive> compressedSegments) {
		/* initial state => create a segemnt */ 
		if(indice==0){
			compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[0]));
			return mins[0].getIndice();
		}
		
		/* all following pixels */
		int indiceToLook = segmentCreation(indice-1, costs, mins, compressedSegments);
		
		try {	
			//creation of a segment
			if(compressedSegments.getLast().getNbPixels()==256 || costs[indice][indiceToLook]==INFINI || mins[indice].getIndice()<indiceToLook){
				compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[indice]));
				indiceToLook = mins[indice].getIndice();
			}
			else{ //ajout
				compressedSegments.getLast().push(Data.arrayOfByte[indice]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return indiceToLook;
		
	}
	

	private void displaySegments(LinkedList<SegmentForRecursive> compressedSegments) {
		int i=0;
		for(SegmentForRecursive seg : compressedSegments){
			System.out.println(i+" "+seg.getNbPixels()+" "+seg.getNbBits()+" "+seg.getNbBitsEachPixel());
			i++;
		}
		
	}

	private void displayMins(Minimum[] mins) {
		for(int i=0; i<mins.length; i++){
			System.out.println(i+"\t"+mins[i].getValue()+"\t"+mins[i].getIndice());
		}		
	}

	private void displayCosts(Integer[][] costs) {
		if(costs.length == 0)
			System.out.println("Costs is empty");
		
		for(int i=0; i<costs.length; i++){
			System.out.print(i+"\t");
			for(int j=0; j<costs[i].length; j++){
				System.out.print(costs[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	
}
