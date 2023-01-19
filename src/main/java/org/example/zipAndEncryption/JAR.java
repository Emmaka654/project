package org.example.zipAndEncryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class JAR {
    public static void readJAR(String path) throws IOException {
        JarInputStream zin = new JarInputStream(new FileInputStream(path));
        JarEntry entry;
        while ((entry = zin.getNextJarEntry()) != null) {
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

    public static void writeJAR(String fileToArh, String arh) throws IOException {
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(arh));
        FileInputStream fis = new FileInputStream(fileToArh);
        JarEntry jarEntry = new JarEntry("out.txt");
        jarOutputStream.putNextEntry(jarEntry);
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        jarOutputStream.write(buffer);
        fis.close();
        jarOutputStream.close();
    }
}
