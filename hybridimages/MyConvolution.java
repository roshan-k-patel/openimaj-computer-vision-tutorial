package uk.ac.soton.ecs.rp10g17.hybridimages;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processor.SinglebandImageProcessor;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
    private float[][] kernel;

    public MyConvolution(float[][] kernel) {
        //note that like the image pixels kernel is indexed by [row][column]
        float[][] invKernel = new float[kernel.length][kernel[0].length];

        for (int i = 0; i < kernel.length; i++){
            for (int j = 0; j < kernel[i].length; j++){
                invKernel[i][j] = kernel[kernel.length-1-i][kernel[i].length-1-j];
            }
        }

        this.kernel = invKernel;

    }

    @Override
    public void processImage(FImage image) {
        // convolve image with kernel and store result back in image
        //
        // hint: use FImage#internalAssign(FImage) to set the contents
        // of your temporary buffer image to the image.

        // Creates a FImage for the convolved image which is set as the original image for now


        // Stores half of the kernel length for convolution
        int halfKernI = (kernel.length) / 2;
        int halfKernJ = (kernel[0].length) / 2;

        FImage resultImage = new FImage(image.getWidth(), image.getHeight());
        FImage convolutionImage = image.padding(halfKernI, halfKernJ, 0f);


        // Main convolution process, convolves the image using the kernel and stores the result in convolutionImage
        for(int x = 0; x < (convolutionImage.getHeight() - 2*halfKernI); x++){
            for (int y = 0; y < (convolutionImage.getWidth() - 2*halfKernJ); y++){

                // variable to store the value of the weighted pixel (i.e. kernel val x pixel val)
                float weightedPixel = 0f;

                for (int i = 0; i < kernel.length; i++){
                    for (int j = 0; j < kernel.length; j++){

                        // store the coordinates of pixel in each kernel cell
                        int coordX = x + i;
                        int coordY = y + j;

                        // every kernel weight x corresponding pixel value
                        weightedPixel = weightedPixel + kernel[i][j] * convolutionImage.pixels[coordX][coordY];


                    }
                }

                // overwrites the pixel value with the new convolved weighted pixel value
                resultImage.pixels[x][y] = weightedPixel;



            }
        }


        // assigns the internal state of the image to the now convolved image
        image.internalAssign(resultImage);


    }

}