package com.infumia.t3sl4.facmap.utils.console.logging.basic;

import com.infumia.t3sl4.facmap.utils.Utils;

public class Log {
   public static void info(String... messages) {
      if (messages.length == 1) {
         System.out.println(messages[0]);
      } else {
         System.out.println(Utils.compile(messages));
      }

   }

   public static void warn(String... messages) {
      if (messages.length == 1) {
         System.out.println("[WARN] " + messages[0]);
      } else {
         System.out.println("[WARN] " + Utils.compile(messages));
      }

   }

   public static void severe(String... messages) {
      if (messages.length == 1) {
         System.err.println(messages[0]);
      } else {
         System.err.println(Utils.compile(messages));
      }

   }
}
