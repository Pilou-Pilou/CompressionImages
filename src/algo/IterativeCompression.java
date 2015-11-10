package algo;

import data.*;
import file.BitOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*
 * 
 * 
 * ATTENTION
 * 
 * Cette classe n'est plus appelée ; j'ai fait l'algo dans CompressionImage.iterative()
 * 
 * 
 * 
 * 
 * 
 */


/**
 * <b>File :</b> IterativeCompression.java<br>
 * <b>Creation date :</b> 27/10/15<br>
 * <b>Description :</b><br>
 * TODO enter a description
 *
 * @author Julien SERGENT
 */
public class IterativeCompression {
    
	public static void IterativeCompression()  throws IOException {
        int costMin =  Integer.MAX_VALUE;
        int index = 0;
        int nbPixel = Data.arrayOfByte.length;
        String pixel = Data.arrayOfByte[index];
        Node tmpNode,currentNode,lastNode = null;
        int currentCost;

        Node initialNode = new Node(null,11+Bit.getNbBitUseInPixel(pixel),Bit.getNbBitUseInPixel(pixel),1);

        currentNode = initialNode;
        while(!initialNode.isComplet()) {

            if (currentNode.getNewSequenceNode() == null && index < (nbPixel - 1)) {
                pixel = Data.arrayOfByte[index + 1];
                currentCost = currentNode.getCost() + 11 + Bit.getNbBitUseInPixel(pixel);

                tmpNode = new Node(currentNode, currentCost, Bit.getNbBitUseInPixel(pixel), 1);

                currentNode.setNewSequence(tmpNode);

                if(currentCost<costMin) {
                    currentNode = tmpNode;
                    index++;
                }

            } else if (currentNode.getAddSequenceNode() == null && (index < nbPixel - 1) && currentNode.getNbPixel() < 255) {
                pixel = Data.arrayOfByte[index + 1];
                currentCost = currentNode.getCost()
                        - (currentNode.getNbBitUse() * currentNode.getNbPixel())
                        + ((currentNode.getNbPixel() + 1) * Math.max(Bit.getNbBitUseInPixel(pixel), currentNode.getNbBitUse()));

                tmpNode = new Node(currentNode, currentCost, Math.max(Bit.getNbBitUseInPixel(pixel), currentNode.getNbBitUse()), currentNode.getNbPixel() + 1);

                currentNode.setAddSequence(tmpNode);

                if(currentCost<costMin) {
                    currentNode = tmpNode;
                    index++;
                }

            } else {
                if (currentNode.getNbPixel() == 255 || index == (nbPixel - 1) || currentNode.getNewSequenceNode() != null && currentNode.getAddSequenceNode() != null)
                    currentNode.setIscomplet(true);

                if (index == (nbPixel-1))
                    costMin = Math.min(costMin, currentNode.getCost());

                if(currentNode.getCost() == costMin)
                    lastNode = currentNode;

                currentNode = currentNode.getPreviousNode();
                index--;
            }



        }

        // create the sequence and put it in the file
        BitOutputStream file = new BitOutputStream(new FileOutputStream("tmp-iterative.seg"));
        currentNode = lastNode;
        index = Data.arrayOfByte.length;
        List<SegmentForIterative> listSegments = new ArrayList<>();
        while(currentNode != null){
            nbPixel = currentNode.getNbPixel();
            SegmentForIterative segment = new SegmentForIterative(nbPixel,8-currentNode.getNbBitUse());
            int a = index - nbPixel;
            for(int i=0;i<nbPixel;i++){

                segment.fillBodySegement(Data.arrayOfByte[a+i]);
                index--;
                currentNode = currentNode.getPreviousNode();
            }
            System.out.println(segment.getSegment());
            listSegments.add(0,segment);
        }

        for (int i=0;i<listSegments.size();i++)
            file.write(listSegments.get(i));
        file.close();
    }



}
