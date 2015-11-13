package com.ensimag.algorithmique;

/**
 * <b>File :</b> com.ensimag.algorithmique.Main.java<br>
 * <b>Creation date :</b> 26/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class Main {

    public static void main (String [] args){
    	if(args.length < 2){
    		displayHelp();
    		return;
    	}
    	
    	String cmd = args[0];
    	String picFile = args[1];
    	
    	if(cmd.equals("compress")){
    		System.out.println("Compression de "+picFile+"...");
	    	int factor = 1;
	    	if(args.length>2)
	    		factor = Integer.valueOf(args[2]);
	    	
	        CompressionImage img = new CompressionImage(picFile, factor);
	        img.iterative();
	        img.recursive();  
    		System.out.println("Compression de "+picFile+" terminee !");  		
    	}
    	else if(cmd.equals("decompress")){
	        CompressionImage.decompress(picFile);  
    	}
    	else{
    		displayHelp();
    	}
    }
    
    public static void displayHelp(){
		System.out.println("Incorrect command");
		System.out.println("compress|decompress pathFile [numberDuplicatePic]");    	
    }
}
