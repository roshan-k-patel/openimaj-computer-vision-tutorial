package uk.ac.soton.ecs.rp10g17.ch2;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.processing.edges.CannyEdgeDetector;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.geometry.shape.Ellipse;

import javax.swing.*;
import javax.swing.border.Border;
import java.io.IOException;
import java.net.URL;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

/** Chapter 2 */
        try {
            //MBFImage image = ImageUtilities.readMBF(new File("file.jpg"));

            //Read an image from a URL
            MBFImage image = ImageUtilities.readMBF(new URL("http://static.openimaj.org/media/tutorial/sinaface.jpg"));

            //Print out the colorspace of the image (in this instance MBFImage is made up of 3 bands of FImage, RGB)
            System.out.println(image.colourSpace);

            //Displays the image
            //DisplayUtilities.display(image);

            //Displays the second band of the image (G - Green)
            //DisplayUtilities.display(image.getBand(1), "Green Channel");

            //Clones the image and changes the colour of all pixels. You need at least one band unchanged to be able
            //to still see the image (in the case below band 0 is unchanged)


            MBFImage clone = image.clone();
            for (int y=0; y<image.getHeight(); y++) {
                for(int x=0; x<image.getWidth(); x++) {
                    clone.getBand(1).pixels[y][x] = 0;
                    clone.getBand(2).pixels[y][x] = 1;
                }
            }

            //Method that changes all the pixels in a band similar to the nested for loop above
            //clone.getBand(1).fill(1f);

            //Creates a named window to display image in
            JFrame window = DisplayUtilities.createNamedWindow("imageWindow");

            DisplayUtilities.display(image, window);


            //When applied to a colour image, each pixel of each band is replaced with the edge response at that point
            //(for simplicity you can think of this as the difference between that pixel and its neighbouring pixels)
            image.processInplace(new CannyEdgeDetector());


            //Draws speech bubble shapes on the image, and also text
            //(defined by their centre [x, y], axes [major, minor] and rotation)
            image.drawShapeFilled( new Ellipse(700f, 450f, 20f, 10f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(700f,450f,20f,10f,0f), RGBColour.CYAN);
            DisplayUtilities.display(image, window);
            Thread.sleep(500);

            image.drawShapeFilled(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(650f, 425f, 25f, 12f, 0f), RGBColour.CYAN);

            DisplayUtilities.display(image, window);
            Thread.sleep(500);

            image.drawShapeFilled(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(600f, 380f, 30f, 15f, 0f), RGBColour.CYAN);
            DisplayUtilities.display(image, window);
            Thread.sleep(500);

            image.drawShapeFilled(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.WHITE);
            image.drawShape(new Ellipse(500f, 300f, 100f, 70f, 0f), RGBColour.CYAN);

            DisplayUtilities.display(image, window);
            Thread.sleep(500);

            image.drawText("OpenIMAJ is", 425, 300, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
            DisplayUtilities.display(image, window);
            image.drawText("Awesome", 425, 330, HersheyFont.ASTROLOGY, 20, RGBColour.BLACK);
            DisplayUtilities.display(image, window);



        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
