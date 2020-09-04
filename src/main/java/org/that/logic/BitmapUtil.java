package org.that.logic;

import java.io.*;

public class BitmapUtil {

    public static void write(File file, Bitmap map) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(map.getStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap read(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return new Bitmap(fis.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
