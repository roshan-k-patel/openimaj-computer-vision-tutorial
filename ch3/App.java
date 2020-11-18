package uk.ac.soton.ecs.rp10g17.ch3;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.connectedcomponent.GreyscaleConnectedComponentLabeler;
import org.openimaj.image.pixel.ConnectedComponent;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.processor.PixelProcessor;
import org.openimaj.image.segmentation.FelzenszwalbHuttenlocherSegmenter;
import org.openimaj.image.segmentation.SegmentationUtilities;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.ml.clustering.FloatCentroidsResult;
import org.openimaj.ml.clustering.assignment.HardAssigner;
import org.openimaj.ml.clustering.kmeans.FloatKMeans;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * OpenIMAJ Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

/** Chapter 3
 *
 * K-Means Clustering - K-Means initialises cluster centroids with randomly selected data points and then iteratively assigns the data points to
 * their closest cluster and updates the centroids to the mean of the respective data points.
 *
 * Euclidean Distance - The Euclidean distance is the straight-line distance between two points. It is named after the "Father of Geometry", the
 * Greek mathematician Euclid
 **/

        //Get an input image for segmentation
        MBFImage input = null;
        try {
            input = ImageUtilities.readMBF(new URL("https://cdn.analyticsvidhya.com/wp-content/uploads/2019/03/image-segmentation.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Apply a colour space transform to the image
        input = ColourSpace.convert(input, ColourSpace.CIE_Lab);

        //Construct the K-means algorithm (first parameter is number of clusters, second can specify number of iterations with default being 30)
        FloatKMeans cluster = FloatKMeans.createExact(2);

        //The FloatKMeans algorithm takes its input as an array of floating point vectors (float[][]). We can flatten the pixels of an
        //image into the required form using the getPixelVectorNative() method:
        float[][] imageData = input.getPixelVectorNative(new float[input.getWidth() * input.getHeight()][3]);

        //Runs the K means cluster algorithm to group all the pixels into the requested number of classes
        FloatCentroidsResult result = cluster.cluster(imageData);

        //Each class is represented by its centroid (the average location of all the points belonging to the class)
        //Method to print the coordinates of each centroid - (L, a, b) coordinates of each of the classes
        final float[][] centroids = result.centroids;
        for (float[] fs : centroids) {
            //System.out.println(Arrays.toString(fs));
        }

        //Assign each pixel to one of the centroids defined in our K means clustering
        final HardAssigner<float[],?,?> assigner = result.defaultHardAssigner();
        /*for (int y=0; y<input.getHeight(); y++) {
            for (int x=0; x<input.getWidth(); x++) {
                float[] pixel = input.getPixelNative(x, y);
                // .assign takes a vector (the L, a, b value of a single pixel) and returns the index of the class that it belongs to.
                int centroid = assigner.assign(pixel);
                input.setPixelNative(x, y, centroids[centroid]);
            }
        }*/

        /**
         * 3.1.1 - Exercise 1 - re-implement the loop that replaces each pixel with its class centroid using a PixelProcessor
         *
         *  Using the nested for loop is simpler as there is no need to convert float
         *  processInPlace is easier to reuse once implemented
         */

        input.processInplace(new PixelProcessor<Float[]>() {
            public Float[] processPixel(Float[] pixel) {
                // convert to float value as the assigner doesn't take Float
                float[] fPixel = new float[pixel.length];

                // assign all Float values to the new float pixels
                for(int i=0; i < fPixel.length; i++) {
                    fPixel[i] = pixel[i].floatValue();
                }

                // get the index of the fpixel's cluster
                int centroid = assigner.assign(fPixel);

                // create a new float[] for the new centroid
                float[] newCentroid = centroids[centroid];

                // convert the float value back to Float
                Float[] processedPixel = new Float[pixel.length];

                // set each pixel value to the value of its cluster
                for(int i=0; i < processedPixel.length; i++) {
                    processedPixel[i] = newCentroid[i];
                }
                return processedPixel;
            }
        });


        //Converts the image back to the RGB colour space
        input = ColourSpace.convert(input, ColourSpace.RGB);

        //Each set of pixels representing a segment is often referred to as a connected component
        //The GreyscaleConnectedComponentLabeler class can be used to find the connected components components of the same cluster that are touching i.e. segmentation)
        GreyscaleConnectedComponentLabeler labeler = new GreyscaleConnectedComponentLabeler();

        //The flatten() method on MBFImage merges the colours into grey values by averaging their RGB values
        List<ConnectedComponent> components = labeler.findComponents(input.flatten());


        /*int i = 0;
        while (i < components.size()){
            System.out.println(components.get(i));
            i++;
        }*/


        int i = 0;
        for (ConnectedComponent comp : components) {
            if (comp.calculateArea() < 500)
                continue;
            input.drawText("Point:" + (i++), comp.calculateCentroidPixel(), HersheyFont.TIMES_MEDIUM, 20);

        }

        //Display the image
        DisplayUtilities.display(input);

        /**
         * 3.1.2 - Exercise 2
         */

        //Find an input image for segmentation
        MBFImage input2 = null;
        try {
            input2 = ImageUtilities.readMBF(new URL("https://cdn.analyticsvidhya.com/wp-content/uploads/2019/03/image-segmentation.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FelzenszwalbHuttenlocherSegmenter segmenter = new FelzenszwalbHuttenlocherSegmenter();

        //input2 = ColourSpace.convert(input2, ColourSpace.CIE_Lab);

        // apply the segmenter to the image
        List<ConnectedComponent> components2 = segmenter.segment(input2);

        // draw the connected component produced by the segmenter
        SegmentationUtilities.renderSegments(input2,components2);

        // display image
        DisplayUtilities.display(input2);
    }
}

