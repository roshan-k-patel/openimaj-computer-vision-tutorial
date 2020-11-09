package uk.ac.soton.ecs.rp10g17.hybridimages;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processor.SinglebandImageProcessor;
import java.io.IOException;
import java.net.URL;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
    private float[][] kernel;
    public static int cropSize;

    public MyConvolution(float[][] kernel) {
        //note that like the image pixels kernel is indexed by [row][column]
        this.kernel = kernel;
    }

    @Override
    public void processImage(FImage image) {
        // convolve image with kernel and store result back in image
        //
        // hint: use FImage#internalAssign(FImage) to set the contents
        // of your temporary buffer image to the image.

        // Creates a FImage for the convolved image which is set as the original image for now
        FImage convolutionImage = image.clone();

        // Stores half of the kernel length for convolution
        int halfKernI = (kernel.length-1) / 2;
        int halfKernJ = (kernel[0].length-1) / 2;

        // Main convolution process, convolves the image using the kernel and stores the result in convolutionImage
        for(int x = 0; x < image.getHeight(); x++){
            for (int y = 0; y < image.getWidth(); y++){

                // if pixel is one of the discarded border pixels, set it to black
                if (x < halfKernI || x >= image.height - halfKernI || y < halfKernJ || y >= image.width - halfKernJ) {
                    convolutionImage.pixels[x][y] = 0f;
                } else {
                    // variable to store the value of the weighted pixel (i.e. kernel val x pixel val)
                    float weightedPixel = 0f;

                    for (int i = 0; i < kernel.length; i++){
                        for (int j = 0; j < kernel.length; j++){
                            // store the coordinates of pixel in each kernel cell
                            int coordX = x - halfKernI + i;
                            int coordY = y - halfKernJ + j;

                            // every kernel weight x corresponding pixel value
                            weightedPixel = weightedPixel + kernel[i][j] * image.pixels[coordX][coordY];
                        }
                    }

                    // overwrites the pixel value with the new convolved weighted pixel value
                    convolutionImage.pixels[x][y] = weightedPixel;
                }
            }
        }

        // normalisation of the image to make all pixel values fall between the values of 0 and 1
        convolutionImage = convolutionImage.normalise();

        // assigns the internal state of the image to the now convolved image
        image.internalAssign(convolutionImage);


    }

}