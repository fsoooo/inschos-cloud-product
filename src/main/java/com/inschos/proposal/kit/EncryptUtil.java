package com.inschos.proposal.kit;

/**
 * Created by IceAnt on 2018/3/24.
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    private static final String Algorithm = "DESede";
    private static final String sKey = "hbIf32Eco";
    private static final String dKey = "cejFalk3fAw3rp2L3d2c3o23";
    private static final BASE64Encoder Encoder = new BASE64Encoder();
    private static final BASE64Decoder Decoder = new BASE64Decoder();

    public EncryptUtil() {
    }

    public static final String getKey() {
        return "hbIf32Eco";
    }

    public static final String getDKey() {
        return "cejFalk3fAw3rp2L3d2c3o23";
    }

    public static final String encode(String input, String sKey) {
        byte[] key = sKey.getBytes();
        SecretKeySpec deskey = new SecretKeySpec(key, "DESede");

        try {
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(1, deskey);
            byte[] cipherByte = c1.doFinal(input.getBytes());
            return Encoder.encode(cipherByte);
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        } catch (NoSuchPaddingException var7) {
            var7.printStackTrace();
        } catch (InvalidKeyException var8) {
            var8.printStackTrace();
        } catch (IllegalBlockSizeException var9) {
            var9.printStackTrace();
        } catch (BadPaddingException var10) {
            var10.printStackTrace();
        }

        return null;
    }

    public static final String decode(String input, String sKey) {
        byte[] key = sKey.getBytes();
        SecretKeySpec deskey = new SecretKeySpec(key, "DESede");

        try {
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(2, deskey);
            byte[] clearByte = c1.doFinal(Decoder.decodeBuffer(input));
            return new String(clearByte,"gbk");
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        } catch (NoSuchPaddingException var7) {
            var7.printStackTrace();
        } catch (InvalidKeyException var8) {
            var8.printStackTrace();
        } catch (IllegalBlockSizeException var9) {
            var9.printStackTrace();
        } catch (BadPaddingException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

        return null;
    }

    public static String byteToHexStr(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length);

        for(int i = 0; i < paramArrayOfByte.length; ++i) {
            String str = Integer.toHexString(255 & paramArrayOfByte[i]);
            if(str.length() < 2) {
                localStringBuffer.append(0);
            }

            localStringBuffer.append(str.toUpperCase());
        }

        return localStringBuffer.toString();
    }

    public static byte[] HexStrtoByte(String hexstr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexstr.toCharArray();
        byte[] bytes = new byte[hexstr.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            int n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte)(n & 255);
        }

        return bytes;
    }
}

