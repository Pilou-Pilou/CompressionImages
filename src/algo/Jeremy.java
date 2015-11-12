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
	
	public Jeremy(){
		try {
			BitOutputStream out = new BitOutputStream(new FileOutputStream("tmp-testJerem.seg"));
					

			System.out.println("Computing costs with "+Data.arrayOfByte.length+" pixels...");
			Integer[][] costs = new Integer[512*512][8];
			Minimum[] mins = new Minimum[512*512]; //minimum cost on a line
			getMin(0, costs, mins);
			
		//	displayCosts(costs);
			displayMins(mins);
			
/*
			System.out.println("Creating segments...");
			LinkedList<SegmentForRecursive> compressedSegments = new LinkedList<SegmentForRecursive>();
			segmentCreation(costs, mins, compressedSegments);
			
						
			System.out.println("Writing segments...");
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
	
	private void displayMins(Minimum[] mins) {
		for(int i=0; i<mins.length; i++){
			System.out.println(i+"\t"+mins[i].getValue()+"\t"+mins[i].getIndice());
		}		
	}

	private void displayCosts(Integer[][] costs) {
		for(int i=0; i<costs.length; i++){
			System.out.print(i+"\t");
			for(int j=0; j<costs[i].length; j++){
				System.out.print(costs[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	

	private void segmentCreation(Integer[][] costs, Minimum[] mins,
			LinkedList<SegmentForRecursive> compressedSegments) {
		System.out.println("Creation i=0");
		compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[0]));
		
		for(int j=1; j<Data.arrayOfByte.length; j++){
			int indiceMin_imoins1 = mins[j-1].getIndice();
			
			System.out.println("Nb pixels i-1 : "+compressedSegments.getLast().getNbPixels());
			System.out.println("Indices "+Integer.toString(j-1)+" et "+j+" : "+costs[j-1][indiceMin_imoins1]+" "+costs[j][indiceMin_imoins1]);
			
			
			//creation of a segment
			if(compressedSegments.getLast().getNbPixels()==256 || costs[j][indiceMin_imoins1]>costs[j-1][indiceMin_imoins1]){

				System.out.println("Creation");
				compressedSegments.add(new SegmentForRecursive(Data.arrayOfByte[j]));
			}
			else{ //ajout
				try {
					System.out.println("Ajout");
					compressedSegments.getLast().push(Data.arrayOfByte[j]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public int getMin(int indice, Integer[][] costs, Minimum[] mins) throws Exception{
		if(mins[indice]!=null){
			System.out.println("mins!= null pr indice "+indice);
			return mins[indice].getValue();			
		}
			
		int b_i = Bit.getNbBitUseInPixel(Data.arrayOfByte[indice])-1;
		int creation;
		int add;
		
		//fill the line
		if(indice==Data.arrayOfByte.length-1){
			for(int i=0;i<8;i++){
				if(b_i>i){//the pixel cannot be coded on i bits
					costs[indice][i] = Integer.MAX_VALUE;
				}
				else{
					//creation
					costs[indice][i] = NB_HEADERS+i;
				}
				

				if(costs[indice][i]<0)
					throw new Exception("Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(costs[indice][i]));
			}
			
		}
		else{
			for(int i=0;i<8;i++){
				if(b_i>i){//the pixel cannot be coded on i bits
					costs[indice][i] = Integer.MAX_VALUE;
				}
				else{
					/* creation or add ? */
					int min_iplus1 = getMin(indice+1, costs, mins);
					if(min_iplus1<0)
						throw new Exception("Min_iplus1 Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(min_iplus1));
				
					creation = NB_HEADERS+i+min_iplus1;
					if(creation<0)
						throw new Exception("creation Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(creation));
				
					System.out.println("costs indice+1 i "+costs[indice+1][i]);
					add = i+costs[indice+1][i];
					if(add<0)
						throw new Exception("add Indice i:"+indice+", +1:"+Integer.toString(indice+1)+" - "+Integer.toString(add));
				
					
					costs[indice][i] = Math.min(creation, add);
				}
				

				if(costs[indice][i]<0)
					throw new Exception("costs Indice i:"+indice+", +1:"+indice+1+" - "+Integer.toString(costs[indice][i]));
			}
		}
		
		//get the min of the line
		mins[indice] = new Minimum(costs[indice][0], 0);
		for(int i=1; i<8; i++){
			if(costs[indice][i]<mins[indice].getValue()){
				mins[indice].setValue(costs[indice][i]);
				mins[indice].setIndice(i);
			}
		}
		
		return mins[indice].getValue();
	}
	
}
