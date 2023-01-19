package org.example.zipAndEncryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZIP {
    public static void readZIP(String arh) throws IOException {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(arh));
        ZipEntry entry;
        while ((entry = zin.getNextEntry()) != null) {
            String name = entry.getName();
            FileOutputStream fout = new FileOutputStream("new" + name);
            for (int c = zin.read(); c != -1; c = zin.read()) {
                fout.write(c);
            }
            fout.flush();
            zin.closeEntry();
            fout.close();
        }
    }

    public static void writeZIP(String fileToArh, String arh) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(arh));
        FileInputStream fis = new FileInputStream(fileToArh);
        ZipEntry zipEntry = new ZipEntry("out.txt");
        zipOutputStream.putNextEntry(zipEntry);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        zipOutputStream.write(buffer);
        fis.close();
        zipOutputStream.close();
    }
}
