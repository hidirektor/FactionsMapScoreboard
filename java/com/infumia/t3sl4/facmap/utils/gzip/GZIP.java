package com.infumia.t3sl4.facmap.utils.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP {
   public static byte[] compress(byte[] array) {
      try {
         ByteArrayOutputStream byteStream = new ByteArrayOutputStream(array.length);

         try {
            GZIPOutputStream zipStream = new GZIPOutputStream(byteStream);
            Throwable var3 = null;

            try {
               zipStream.write(array);
            } catch (Throwable var21) {
               var3 = var21;
               throw var21;
            } finally {
               if (zipStream != null) {
                  if (var3 != null) {
                     try {
                        zipStream.close();
                     } catch (Throwable var20) {
                        var3.addSuppressed(var20);
                     }
                  } else {
                     zipStream.close();
                  }
               }

            }
         } finally {
            byteStream.close();
         }

         return byteStream.toByteArray();
      } catch (Exception var24) {
         var24.printStackTrace();
         return null;
      }
   }

   public static byte[] uncompress(byte[] array) {
      try {
         ByteArrayInputStream byteStream = new ByteArrayInputStream(array);

         byte[] var6;
         try {
            GZIPInputStream zipStream = new GZIPInputStream(byteStream);
            Throwable var3 = null;

            try {
               byte[] bytes = new byte[512];
               int size = zipStream.read(bytes);
               if (size <= 512) {
                  var6 = Arrays.copyOf(bytes, size);
                  return var6;
               }

               var6 = null;
            } catch (Throwable var26) {
               var3 = var26;
               throw var26;
            } finally {
               if (zipStream != null) {
                  if (var3 != null) {
                     try {
                        zipStream.close();
                     } catch (Throwable var25) {
                        var3.addSuppressed(var25);
                     }
                  } else {
                     zipStream.close();
                  }
               }

            }
         } finally {
            byteStream.close();
         }

         return var6;
      } catch (Exception var29) {
         var29.printStackTrace();
         return null;
      }
   }
}
