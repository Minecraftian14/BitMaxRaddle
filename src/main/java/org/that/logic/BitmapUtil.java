package org.that.logic;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

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
//            FileInputStream fis = new FileInputStream(file);
//            byte[] ar = new byte[fis.available()];
//            for (int i = 0; i < fis.available(); i++) {
//                fis.read(new byte[i]);
//            }
            return new Bitmap(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
