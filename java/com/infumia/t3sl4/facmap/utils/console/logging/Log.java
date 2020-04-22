package com.infumia.t3sl4.facmap.utils.console.logging;

import com.infumia.t3sl4.facmap.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
   private static Logger global = LoggerFactory.getLogger("MAIN");

   public static void info(String... messages) {
      if (messages.length == 1) {
         global.info(messages[0]);
      } else {
         global.info(Utils.compile(messages));
      }

   }

   public static void warn(String... messages) {
      if (messages.length == 1) {
         global.warn(messages[0]);
      } else {
         global.warn(Utils.compile(messages));
      }

   }

   public static void severe(String... messages) {
      if (messages.length == 1) {
         global.error(messages[0]);
      } else {
         global.error(Utils.compile(messages));
      }

   }

   public static void severe(Throwable e, String... messages) {
      if (messages.length == 1) {
         global.error(messages[0], e);
      } else {
         global.error(Utils.compile(messages));
      }

   }
}
