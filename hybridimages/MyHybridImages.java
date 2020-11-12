package uk.ac.soton.ecs.rp10g17.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;
import org.openimaj.image.processing.resize.ResizeProcessor;

import java.io.File;
import java.io.IOException;

public class MyHybridImages {
    /**
     * Compute a hybrid image combining low-pass and high-pass filtered images
     *
     * @param lowImage
     *            the image to which apply the low pass filter
     * @param lowSigma
     *            the standard deviation of the low-pass filter
     * @param highImage
     *            the image to which apply the high pass filter
     * @param highSigma
     *            the standard deviation of the low-pass component of computing the
     *            high-pass filtered image
     * @return the computed hybrid image
     */
    public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
        //implement your hybrid images functionality here.
        //Your submitted code must contain this method, but you can add
        //additional static methods or implement the functionality through
        //instance methods on the `MyHybridImages` class of which you can create
        //an instance of here if you so wish.
        //Note that the input images are expected to have the same size, and the output
        //image will also have the same height & width as the inputs.


        /**
         * Low pass convolution
         */


        // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
        int size = (int) (8.0f * lowSigma + 1.0f);

        // size must be odd
        if (size % 2 == 0) {
            size++; // size must be odd
        }

        float[][] kernel = Gaussian2D.createKernelImage(size, lowSigma).pixels;


        // convolution for low pass image
        MBFImage lowConv = lowImage.process(new MyConvolution(kernel));
        //DisplayUtilities.display(lowConv, "convolved / Low-Passed Dog");

        /**
         * High-pass convolution
         */

        // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
        int size2 = (int) (8.0f * highSigma + 1.0f);

        // size must be odd
        if (size2 % 2 == 0) {
            size2++; // size must be odd
        }

        float[][] kernel2 = Gaussian2D.createKernelImage(size2, highSigma).pixels;


        // convolution for high pass image
        MBFImage highConv = highImage.process(new MyConvolution(kernel2));

        //DisplayUtilities.display(highConv, "convolved / low pass cat");

        // subtract original image with low passed image for convolution
        highConv = highImage.subtract(highConv);

        // cat after subtracting original image with low passed image
        //DisplayUtilities.display(highConv, "Cat original minus low passed");

        highConv = highConv.add(0.5f);

        //DisplayUtilities.display(highConv, "high pass + 0.5");

        // add 0.5 to each pixel so that bright values are positive and dark values are negative

        /**
         * Combine both images together
         */

        highConv = highConv.subtract(0.5f);

        MBFImage hybrid = lowConv.add(highConv);


        return hybrid;
    }
}