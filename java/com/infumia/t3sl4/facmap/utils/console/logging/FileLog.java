package com.infumia.t3sl4.facmap.utils.console.logging;

import com.infumia.t3sl4.facmap.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class FileLog {
   private static File f;

   public static void setLoggingFile(File f) {
      FileLog.f = f;
   }

   public static void write(String message) throws IOException {
      try {
         if (f != null) {
            PrintWriter pw = new PrintWriter(new FileWriter(f));
            pw.write("[" + (new Date(System.currentTimeMillis())).toString().substring(11, 19) + "] " + message);
            pw.close();
         }
      } catch (Throwable var2) {
         throw var2;
      }
   }

   public static void info(String... messages) throws IOException {
      if (messages.length == 1) {
         write("[INFO] " + messages[0]);
      } else {
         write("[INFO] " + Utils.compile(messages));
      }

   }

   public static void warn(String... messages) throws IOException {
      if (messages.length == 1) {
         write("[WARN] " + messages[0]);
      } else {
         write("[WARN] " + Utils.compile(messages));
      }

   }

   public static void severe(String... messages) throws IOException {
      if (messages.length == 1) {
         write("[SEVERE] " + messages[0]);
      } else {
         write("[SEVERE] " + Utils.compile(messages));
      }

   }
}
