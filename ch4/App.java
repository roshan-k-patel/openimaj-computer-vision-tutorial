package uk.ac.soton.ecs.rp10g17.ch4;

import org.openimaj.feature.DoubleFVComparison;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.ColourSpace;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.image.processing.convolution.FGaussianConvolve;
import org.openimaj.image.typography.hershey.HersheyFont;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * OpenIMAJ Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {

/** Chapter 4*/

        URL[] imageURLs = new URL[]{
                new URL("http://openimaj.org/tutorial/figs/hist1.jpg"),
                new URL("http://openimaj.org/tutorial/figs/hist2.jpg"),
                new URL("http://openimaj.org/tutorial/figs/hist3.jpg")
        };


        List<MultidimensionalHistogram> histograms = new ArrayList<MultidimensionalHistogram>();
        HistogramModel model = new HistogramModel(4, 4, 4);

        for (URL u : imageURLs) {
            model.estimateModel(ImageUtilities.readMBF(u));
            histograms.add(model.histogram.clone());
        }

        for (int i = 0; i < histograms.size(); i++) {
            for (int j = i; j < histograms.size(); j++) {
                double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.EUCLIDEAN);
                // System.out.println("Histogram " + i + " and " + j + " have a euclidean distance of: " + distance);
            }
        }

        // Exercise 1 method call
        // findClosest(histograms, imageURLs);

        /**
         * 4.1.2. Exercise 2: Exploring comparison measures
         *
         * DoubleFVComparison.INTERSECTION returns 1 when two images are the same, so the closer to 1 the more similar
         *
         **/

        // compare histograms with Euclidean distance using the .Intersection comparison
        for (int i = 0; i < histograms.size(); i++) {
            for (int j = i; j < histograms.size(); j++) {
                double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.INTERSECTION);
                System.out.println("Histogram " + i + " and " + j + " distance: " + distance);
            }
        }

    }

    /**
     * 4.1.1 - Exercise 1 - Finding and displaying similar images
     */

    public static void findClosest(List<MultidimensionalHistogram> histograms, URL[] imageURLs) throws IOException {
        MBFImage image1 = ImageUtilities.readMBF(imageURLs[0]);
        MBFImage image2 = ImageUtilities.readMBF(imageURLs[1]);
        MBFImage image3 = ImageUtilities.readMBF(imageURLs[2]);

        // creates a list of the read images
        MBFImage[] images = {image1, image2, image3};

        double shortest = Integer.MAX_VALUE;
        int hist1 = 0;
        int hist2 = 0;

        // compare histograms by Euclidean distance
        for (int i = 0; i < histograms.size(); i++) {
            for (int j = 0; j < histograms.size(); j++) {
                double distance = histograms.get(i).compare(histograms.get(j), DoubleFVComparison.EUCLIDEAN);

                if (i == j) {
                    continue;
                } else {
                    if (distance < shortest) {

                        shortest = distance;
                        hist1 = i;
                        hist2 = j;
                    }
                }
            }
        }

        System.out.println("Histogram " + hist1 + " and " + hist2 + " are the most similar images");

        // display both images
        DisplayUtilities.display(images[hist1]);
        DisplayUtilities.display(images[hist2]);
    }
}
