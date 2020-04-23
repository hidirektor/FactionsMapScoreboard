package com.infumia.t3sl4.facmap.utils.secure;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;

public class AES {
   public static SecretKey generateKey(int bytes) throws NoSuchAlgorithmException {
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(bytes);
      return keyGenerator.generateKey();
   }

   public static void saveKey(SecretKey key, File file) throws IOException {
      char[] hex = Hex.encodeHex(key.getEncoded());
      FileUtils.writeStringToFile(file, String.valueOf(hex));
   }

   public static SecretKey loadKey(File file) throws IOException {
      String data = new String(FileUtils.readFileToByteArray(file));

      byte[] encoded;
      try {
         encoded = Hex.decodeHex(data.toCharArray());
      } catch (DecoderException var4) {
         var4.printStackTrace();
         return null;
      }

      return new SecretKeySpec(encoded, "AES");
   }
}
