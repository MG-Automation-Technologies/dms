/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openkm.openoffice.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;


/**
 *
 * @author jllort
 */
public class Encryption {

    private String SECRET_PHRASE = "OpenKM Secret Phrase";

    // 8-bytes Salt
    private byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03
        };

        // Iteration count
    private int iterationCount = 19;
    private Cipher ecipher;
    private Cipher dcipher;

    public Encryption() {
        try {
            KeySpec keySpec = new PBEKeySpec(SECRET_PHRASE.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameters to the cipthers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

            System.out.println("be");
        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) { 
        } catch (InvalidKeySpecException ex) {
        } catch (InvalidAlgorithmParameterException ex) {
        }
    }

    public String encrypt(String text)
        { try
          { // Encode the string into bytes using utf-8
              byte[] utf8 = text.getBytes("UTF8");

              // Encrypt
              byte[] enc = ecipher.doFinal(utf8);

              // Encode bytes to base64 to get a string
              return new sun.misc.BASE64Encoder().encode(enc);
          } catch (javax.crypto.BadPaddingException e) {
          } catch (IllegalBlockSizeException e) {
          } catch (UnsupportedEncodingException e) {
          } catch (java.io.IOException e) {
          } return null;
    }

    public String decrypt(String text) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(text);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }

}
