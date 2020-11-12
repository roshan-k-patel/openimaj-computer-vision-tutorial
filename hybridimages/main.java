package uk.ac.soton.ecs.rp10g17.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.resize.ResizeProcessor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {


        // load and display images
        MBFImage lowImg = null;
        try {
            lowImg = ImageUtilities.readMBF(new File("./src/main/java/uk/ac/soton/ecs/rp10g17/hybridimages/smallmonk.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MBFImage highImg = null;
        try {
            highImg = ImageUtilities.readMBF(new File("./src/main/java/uk/ac/soton/ecs/rp10g17/hybridimages/jaguar.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        int lowSig = 3;

        int highSig = 7;

        MyHybridImages hybridMake = new MyHybridImages();

        MBFImage hybrid = hybridMake.makeHybrid(lowImg,lowSig,highImg,highSig);

        DisplayUtilities.display(hybrid, "Hybrid Image");
        hybrid = ResizeProcessor.halfSize(hybrid);
        DisplayUtilities.display(hybrid, "Half size Image");
        hybrid = ResizeProcessor.halfSize(hybrid);
        DisplayUtilities.display(hybrid, "Quarter size Image");
        hybrid = ResizeProcessor.halfSize(hybrid);
        DisplayUtilities.display(hybrid, "Eighth size Image");




    }
}















































