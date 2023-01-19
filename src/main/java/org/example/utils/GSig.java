package org.example.utils;

import java.io.FileOutputStream;

public class GSig {
    public static void saveToFile(byte[] info,
                                  String filename) {
        try {
            FileOutputStream fos = new FileOutputStream
                    (filename);
            fos.write(info);
            fos.close();
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }
}
