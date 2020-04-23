package com.infumia.t3sl4.facmap.utils.antivpn;

import com.infumia.t3sl4.facmap.utils.console.logging.Log;
import com.infumia.t3sl4.facmap.utils.http.HTTP;

public class AntiVPN {
   private static String url;

   public static int getLevel(String ip) {
      try {
         String result = HTTP.get(String.format(url, ip));
         double d = Double.parseDouble(result);
         return (int)(d * 100.0D);
      } catch (Exception var4) {
         Log.severe("Failed to fetch ip status: " + var4.getMessage());
         return 0;
      }
   }

   public static void setUrl(String url) {
      AntiVPN.url = url;
   }
}
