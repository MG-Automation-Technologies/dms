/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2010  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.applet.crypto;

import java.util.*;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.SecretKeyFactory;

/**
 * CryptoByPassword
 * 
 * @author jllort
 *
 */
public class CryptoByPassword
{
    private int defaultIterations;
    private String cipherName;

    public static void main (String[] args) 
        throws Exception
    {
        CryptoByPassword pc = new CryptoByPassword();
        String secret = "Secret message";
        String pass = "josep";
        String encrypted = new String(pc.encrypt(secret.getBytes(), pass.toCharArray()));
        System.out.println("encrypted: " + encrypted);
        String decrypted = new String(pc.decrypt(encrypted.getBytes(), pass.toCharArray()));
        System.out.println("decrypted: " + decrypted);
    }

    /**
     * CryptoByPassword
     */
    public CryptoByPassword ()
    {
        cipherName = "PBEWithSHA1AndDESede";
        defaultIterations = 1000;
    }

    /**
     * setCipherName
     * 
     * @param name
     */
    public void setCipherName (String cipherName)
    {
        this.cipherName = cipherName;
    }
    
    /**
     * getCipherName
     * 
     * @return
     */
    public String getCipherName() {
    	return cipherName;
    }

    /**
     * CryptoByPassword
     * 
     * @param input
     * @param password
     * @return
     * @throws Exception
     */
    public byte[] encrypt (byte[] input, char[] password)
        throws Exception
    {
        byte[] salt = new byte[8];
        Random r = new Random();
        r.nextBytes(salt);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cipherName);
        if (keyFactory == null) throw new Exception("cannot initialize cipher: "+cipherName);
        SecretKey pbeKey = keyFactory.generateSecret(new PBEKeySpec(password));

        Cipher pbeCipher = Cipher.getInstance(cipherName);
        if (pbeCipher == null) throw new Exception("cannot initialize cipher: "+cipherName);
        pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, new PBEParameterSpec(salt, defaultIterations));

        byte[] cipherText = pbeCipher.doFinal(input);
        ByteBuffer buf = ByteBuffer.allocate(cipherText.length+salt.length+8+8);
        buf.putInt(defaultIterations);
        buf.putInt(cipherText.length);
        buf.put(salt);
        buf.put(cipherText);
        if (buf.hasArray())
            return buf.array();
        else
            throw new Exception("this ByteBuffer has no array");
        
    }


    /**
     * CryptoByPassword
     * 
     * @param input
     * @param password
     * @return
     * @throws Exception
     */
    public byte[] decrypt (byte[] input, char[] password)
        throws Exception
    {
        byte[] salt = new byte[8];
        ByteBuffer buf = ByteBuffer.allocate(input.length);
        buf.put(input);
        buf.rewind();
        int iterations = buf.getInt();
        int cipherTextLength = buf.getInt();
        buf.get(salt);
        byte[] cipherText = new byte[cipherTextLength];
        buf.get(cipherText);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cipherName);
        if (keyFactory == null) throw new Exception("cannot initialize cipher: "+cipherName);
        SecretKey pbeKey = keyFactory.generateSecret(new PBEKeySpec(password));

        Cipher pbeCipher = Cipher.getInstance(cipherName);
        if (pbeCipher == null) throw new Exception("cannot initialize cipher: "+cipherName);
        pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, new PBEParameterSpec(salt, iterations));

        return pbeCipher.doFinal(cipherText);
    }
    
    /**
     * CryptoByPassword
     * 
     * @param key
     * @param password
     * @return
     * @throws Exception
     */
    public byte[] wrap (PrivateKey key, char[] password)
        throws Exception
    {
        byte[] salt = new byte[8];
        Random r = new Random();
        r.nextBytes(salt);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cipherName);
        if (keyFactory == null) throw new Exception("cannot initialize cipher: "+cipherName);
        SecretKey pbeKey = keyFactory.generateSecret(new PBEKeySpec(password));

        Cipher pbeCipher = Cipher.getInstance(cipherName);
        if (pbeCipher == null) throw new Exception("cannot initialize cipher: "+cipherName);
        pbeCipher.init(Cipher.WRAP_MODE, pbeKey, new PBEParameterSpec(salt, defaultIterations));

        byte[] cipherText = pbeCipher.wrap(key);
        ByteBuffer buf = ByteBuffer.allocate(cipherText.length+salt.length+8+8);
        buf.putInt(defaultIterations);
        buf.putInt(cipherText.length);
        buf.put(salt);
        buf.put(cipherText);
        if (buf.hasArray())
            return buf.array();
        else
            throw new Exception("this ByteBuffer has no array");
    }


    /**
     * CryptoByPassword
     * 
     * @param input
     * @param password
     * @return
     * @throws Exception
     */
    public PrivateKey unwrap (byte[] input, char[] password)
        throws Exception
    {
        byte[] salt = new byte[8];
        ByteBuffer buf = ByteBuffer.allocate(input.length);
        buf.put(input);
        buf.rewind();
        int iterations = buf.getInt();
        int cipherTextLength = buf.getInt();
        buf.get(salt);
        byte[] cipherText = new byte[cipherTextLength];
        buf.get(cipherText);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cipherName);
        if (keyFactory == null) throw new Exception("cannot initialize cipher: "+cipherName);
        SecretKey pbeKey = keyFactory.generateSecret(new PBEKeySpec(password));

        Cipher pbeCipher = Cipher.getInstance(cipherName);
        if (pbeCipher == null) throw new Exception("cannot initialize cipher: "+cipherName);
        pbeCipher.init(Cipher.UNWRAP_MODE, pbeKey, new PBEParameterSpec(salt, iterations));

        return (PrivateKey) pbeCipher.unwrap(cipherText, "RSA", Cipher.PRIVATE_KEY);
    }
}

