package com.infumia.t3sl4.facmap.utils.http;

import com.infumia.t3sl4.facmap.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class HTTP {
   public static String get(String... url) throws Exception {
      return get(url[0] + "/" + Utils.compile(url, "/", 1));
   }

   public static String get(String url) throws Exception {
      StringBuilder result = new StringBuilder();
      URL u = new URL(url);
      HttpURLConnection conn = (HttpURLConnection)u.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      String line;
      while((line = rd.readLine()) != null) {
         result.append(line);
      }

      rd.close();
      return result.toString();
   }

   public static void download(String url, File dest) throws Exception {
      download(new URL(url), dest);
   }

   public static void download(URL url, File dest) throws Exception {
      ReadableByteChannel rbc = Channels.newChannel(url.openStream());
      FileOutputStream fos = new FileOutputStream(dest);
      fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
   }
}
