package org.example.utils;

import java.io.FileInputStream;

public class VSig {
    public static byte[] readFromFile(String fileName) {
        byte[] info;
        try {
            FileInputStream fis =
                    new FileInputStream(fileName);
            info = new byte[fis.available()];
            fis.read(info);
            fis.close();
        } catch (Exception e) {
            System.err.println("Caught exception " +
                    e.toString());
            info = new byte[0];
        }
        return (info);
    }
}
