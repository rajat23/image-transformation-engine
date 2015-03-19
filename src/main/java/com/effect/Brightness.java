package com.effect;

import java.awt.*;
import java.awt.image.BufferedImage;



public class Brightness implements Effect{
    int increasingFactor;
    public Brightness(String factor)
    {
            increasingFactor=Integer.parseInt(factor);
    }


    public BufferedImage getEffect(BufferedImage sourceImage) {


        int w = sourceImage.getWidth();
        int h = sourceImage.getHeight();

        BufferedImage outImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);


        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {


                Color color = new Color(sourceImage.getRGB(i, j));

                int r, g, b;


                r = color.getRed() + increasingFactor;
                g = color.getGreen() + increasingFactor;
                b = color.getBlue() + increasingFactor;


                if (r >= 256) {
                    r = 255;
                } else if (r < 0) {
                    r = 0;
                }

                if (g >= 256) {
                    g = 255;
                } else if (g < 0) {
                    g = 0;
                }

                if (b >= 256) {
                    b = 255;
                } else if (b < 0) {
                    b = 0;
                }


                outImage.setRGB(i, j, new Color(r, g, b).getRGB());

            }
        }

        return outImage;
    }
}

