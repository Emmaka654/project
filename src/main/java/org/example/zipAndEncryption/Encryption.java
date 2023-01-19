package org.example.zipAndEncryption;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import static org.example.utils.GSig.saveToFile;
import static org.example.utils.VSig.readFromFile;

public class Encryption {
    public static void encryption(String file) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException, SignatureException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeySpecException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(1024, random);
        KeyPair pair = keyGen.generateKeyPair();
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();
        Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
        dsa.initSign(priv);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bufin = new BufferedInputStream(fis);
        byte[] buffer = new byte[1024];
        int len;
        while (bufin.available() != 0) {
            len = bufin.read(buffer);
            dsa.update(buffer, 0, len);
        }
        bufin.close();
        byte[] realSig = dsa.sign();
        saveToFile(realSig, "signature.txt");
        byte[] key = pub.getEncoded();
        saveToFile(key, "pubkey.txt");


        byte[] encKey = readFromFile("pubkey.txt");
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
        byte[] sigToVerify = readFromFile("signature.txt");
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(pubKey);
        FileInputStream datafis = new FileInputStream(file);
        BufferedInputStream bufin1 = new BufferedInputStream(datafis);
        byte[] buffer1 = new byte[1024];
        int len1;
        while (bufin1.available() != 0) {
            len1 = bufin1.read(buffer1);
            sig.update(buffer1, 0, len1);
        }
        bufin1.close();
        boolean verifies = sig.verify(sigToVerify);
        System.out.println("Signature verifies: " + verifies);
    }

}
