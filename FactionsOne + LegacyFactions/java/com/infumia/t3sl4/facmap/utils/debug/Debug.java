package com.infumia.t3sl4.facmap.utils.debug;

import java.lang.management.ManagementFactory;

public class Debug {
   private static boolean enabled = ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

   public static boolean isEnabled() {
      return enabled;
   }
}
