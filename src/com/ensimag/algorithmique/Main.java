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
        CompressionImage img = new CompressionImage(args[0]);
        System.out.println("Compression/Décompression");
        img.iterative();
        img.recursive();
        System.out.println("Decompression : ");
        img.decompress("tmp-recursive.seg");
    }
}
