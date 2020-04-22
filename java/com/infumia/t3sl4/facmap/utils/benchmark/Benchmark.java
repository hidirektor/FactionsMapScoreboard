package com.infumia.t3sl4.facmap.utils.benchmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class Benchmark {
   private static String name;
   private static long start;
   private static List<SimpleEntry<String, Long>> times = new ArrayList();

   public static void start(String name) {
      Benchmark.name = name;
      start = System.currentTimeMillis();
      System.out.println("[" + name + "]=[START]");
   }

   public static void tree(String tree) {
      times.add(new SimpleEntry(tree, System.currentTimeMillis() - start));
      start = System.currentTimeMillis();
   }

   public static void stop() {
      System.out.println("[" + name + "]=[STOP]");
      times.add(new SimpleEntry("LAST", System.currentTimeMillis() - start));
      Iterator var0 = times.iterator();

      while(var0.hasNext()) {
         Entry<String, Long> e = (Entry)var0.next();
         System.out.println("[" + name + "]=[" + (String)e.getKey() + "]=[" + e.getValue() + "ms]");
      }

      System.out.println("[" + name + "]=[END]");
      times.clear();
   }
}
