package org.that.logic;

import junit.framework.TestCase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BitmapTest extends TestCase {

    public static void main(String[] args) throws IOException {

        BufferedImage i = new BufferedImage(5, 3, 2);
        for (int j = 0; j < i.getWidth(); j++) {
            for (int k = 0; k < i.getHeight(); k++) {
                int c = (j + k) % 3 == 0 ? 255 : 0;
                i.setRGB(j, k, new Color(c, c, c).getRGB());
            }
        }
        for (int j = 0; j < i.getHeight(); j++) {
            for (int k = 0; k < i.getWidth(); k++) {
                System.out.print(new Color(i.getRGB(k, j)).getRed() > 128 ? 1 : 0);
            }
            System.out.println();
        }
        Bitmap map = new Bitmap(i);

        System.out.println();
        System.out.println(map.toString());

        System.out.println(new Bitmap(map.asImage()).toString());

    }


}