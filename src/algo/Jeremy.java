package algo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import data.Bit;
import data.Data;
import data.Minimum;
import file.BitOutputStream;

public class Jeremy {

	public static final int NB_HEADERS = 11;
	public static int INFINI= -1;
	
	public Jeremy(){
		Integer[][] costs = new Integer[Data.arrayOfByte.length][8];
		try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream("tmp-testJerem.seg"));
					

			System.out.println("Computing costs with "+Data.arrayOfByte.length+" pixels...");
			Minimum[] mins = new Minimum[Data.arrayOfByte.length]; //minimum cost on a line
			getMin(0, costs, mins);
			
			//displayCosts(costs);
			//displayMins(mins);
			

			System.out.println("Creating segments...");
			LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
			segmentCreation(Data.arrayOfByte.length-1, costs, mins, compressedSegments);
			displaySegments(compressedSegments);
					
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
	
	

	private int segmentCreation(int indice, Integer[][] costs, Minimum[] mins,
			LinkedList<SegmentForRecursive> compressedSegments) {
		
		if(indice==0){
			compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[0]));
			return mins[0].getIndice();
		}
		
		int indiceToLook = segmentCreation(indice-1, costs, mins, compressedSegments);
		
			
		//	System.out.println("Nb pixels i-1 : "+compressedSegments.getLast().getNbPixels());
			//System.out.println("Indices "+Integer.toString(j-1)+" et "+j+" : "+costs[j-1][indiceToLook]+" "+costs[j][indiceToLook]);
			
			
			//creation of a segment
			//if(compressedSegments.getLast().getNbPixels()==256 || costs[j][indiceToLook] == INFINI || costs[j][indiceToLook]>costs[j-1][indiceToLook]){
			try {
			//	System.out.println(getMin(j,costs,mins)+"+"+costs[j-1][indiceToLook]+"<"+costs[j-1][indiceToLook]+"+"+indiceToLook);
				//System.out.println(Integer.toString(getMin(j,costs,mins)+costs[j-1][indiceToLook])+"<"+Integer.toString(costs[j-1][indiceToLook]+indiceToLook));
				if(compressedSegments.getLast().getNbPixels()==256 || costs[indice][indiceToLook]==INFINI || mins[indice].getIndice()<indiceToLook){
					System.out.println("Creation");
					compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[indice]));
					indiceToLook = mins[indice].getIndice();
					//System.out.println("Nouvel indice "+indiceToLook);
				}
				else{ //ajout
					System.out.println("Ajout");
					compressedSegments.getLast().push(Data.arrayOfByte[indice]);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return indiceToLook;
		
	}

	public int getMin(int indice, Integer[][] costs, Minimum[] mins) throws Exception{
		//System.out.println("getMin : "+indice);
		if(mins[indice]!=null){
			//System.out.println("mins!= null pr indice "+indice);
			return mins[indice].getValue();			
		}
			
		int b_i = Bit.getNbBitUseInPixel(Data.arrayOfByte[indice]);
		int creation;
		int add;
		
		//fill the line
		if(indice==Data.arrayOfByte.length-1){
			for(int i=0;i<8;i++){
				if(b_i>(i+1)){//the pixel cannot be coded on i bits
					costs[indice][i] = INFINI;
					//System.out.println("Infini : "+indice+", "+i+" puisque "+b_i+">"+Integer.toString(i+1));
				}
				else{
					//creation
					costs[indice][i] = NB_HEADERS+(i+1);
					System.out.println("Pas infini : "+indice+", "+Integer.toString(NB_HEADERS+i));
				}
				

				if(costs[indice][i]<-1)
					throw new Exception("Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(costs[indice][i]));
			}
			
		}
		else{
			int min_iplus1 = getMin(indice+1, costs, mins);
	//	System.out.println("Min de "+Integer.toString(indice+1)+" : "+min_iplus1);
		
			for(int i=0;i<8;i++){
				//System.out.println("Costs "+Integer.toString(i+1)+" : "+costs[indice+1][i]);
				
				if(b_i>(i+1)){//the pixel cannot be coded on i bits
					costs[indice][i] = INFINI;
				}
				else{
					/* creation or add ? */
					if(min_iplus1<-1)
						throw new Exception("Min_iplus1 Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(min_iplus1));
				
					if(min_iplus1==INFINI)
						throw new Exception(indice+" i_plus1 a forc�ment une valeur inf�rieure � l'infini");
					else
						creation = NB_HEADERS+(i+1)+min_iplus1;
					
					if(creation<-1)
						throw new Exception("creation Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(creation));
				
				//	System.out.println("costs indice+1 i "+costs[indice+1][i]);
					if(costs[indice+1][i]==INFINI)
						add = INFINI;
					else
						add = (i+1)+costs[indice+1][i];
					if(add<-1)
						throw new Exception("add Indice i:"+indice+", +1:"+Integer.toString(indice+1)+" - "+Integer.toString(add));
				
					
					if(creation == INFINI)
						costs[indice][i] = add;
					else if(add == INFINI)
						costs[indice][i] = creation;
					else
						costs[indice][i] = Math.min(creation, add);
				}
				

				if(costs[indice][i]<-1)
					throw new Exception("costs Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(costs[indice][i]));
				
		//		System.out.println("Costs "+Integer.toString(i+1)+" : "+costs[indice+1][i]);
			}
		}
		
		//get the min of the line
		mins[indice] = new Minimum(costs[indice][0], 0);
		for(int i=1; i<8; i++){
			if(mins[indice].getValue()==INFINI || (costs[indice][i]!=INFINI && costs[indice][i]<mins[indice].getValue())){
				mins[indice].setValue(costs[indice][i]);
				mins[indice].setIndice(i);
			}
		}
		
		return mins[indice].getValue();
	}
	
}
