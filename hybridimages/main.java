package uk.ac.soton.ecs.rp10g17.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class main {
    public static void main(String[] args) {
/*        float[][] kernel = {
                {3,0,2},
                {1,4,0},
                {1,0,0}
        };


        //Retrieve an input image for convolution
        MBFImage input = null;
        try {
            input = ImageUtilities.readMBF(new URL("https://cdn.analyticsvidhya.com/wp-content/uploads/2019/03/image-segmentation.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MyConvolution conv = new MyConvolution(kernel);

        //flatten the MBFI image into a single band using the avg values of the pixels at each location
        conv.processImage(input.flatten());*/

        // load and display images
        MBFImage lowImg = null;
        try {
            lowImg = ImageUtilities.readMBF(new File("./src/main/java/uk/ac/soton/ecs/rp10g17/hybridimages/dog.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MBFImage highImg = null;
        try {
            highImg = ImageUtilities.readMBF(new File("./src/main/java/uk/ac/soton/ecs/rp10g17/hybridimages/cat.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lowSig = 3;

        int highSig = 8;

        MyHybridImages hybrid = new MyHybridImages();


        hybrid.makeHybrid(lowImg,lowSig,highImg,highSig);

        //DisplayUtilities.display(hybrid.makeHybrid(lowImg,lowSig,highImg,highSig), "hybrid image");


    }
}
