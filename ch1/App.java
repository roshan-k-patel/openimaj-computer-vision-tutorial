package uk.ac.soton.ecs.rp10g17.ch1;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.typography.hershey.HersheyFont;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

/** Chapter 1, 1.2.1 ex 1 */

        //Create an image
        MBFImage image = new MBFImage(450,70, ColourSpace.RGB);

        //Fill the image with white
        image.fill(RGBColour.BLACK);

        //Render some test into the image
        image.drawText("Hello New York!", 10, 60, HersheyFont.CURSIVE, 50, RGBColour.PINK);

        //Apply a Gaussian blur
        image.processInplace(new FGaussianConvolve(2f));

        //Display the image
        DisplayUtilities.display(image);
    }
}
