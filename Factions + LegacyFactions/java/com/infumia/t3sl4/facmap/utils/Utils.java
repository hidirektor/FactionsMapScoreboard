package com.infumia.t3sl4.facmap.utils;

import java.beans.ConstructorProperties;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Utils {
   public static void Try(Utils.TryLambda r) {
      try {
         r.run();
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

   }

   public static void Try(Utils.TryLambda _try, Utils.Catchlambda _catch) {
      try {
         _try.run();
      } catch (Throwable var3) {
         _catch.run(var3);
      }

   }

   public static void Try(Utils.TryLambda _try, Utils.Catchlambda _catch, Runnable _finally) {
      try {
         _try.run();
      } catch (Throwable var7) {
         _catch.run(var7);
      } finally {
         _finally.run();
      }

   }

   public static void sleep(long time) {
      try {
         Thread.sleep(time);
      } catch (Throwable var3) {
      }

   }

   public static void sleep(long time, int nano) {
      try {
         Thread.sleep(time, nano);
      } catch (Throwable var4) {
      }

   }

   public static void endless(Runnable task, long sleep) {
      try {
         while(true) {
            Try(task::run, Throwable::printStackTrace);
            sleep(sleep);
         }
      } catch (Throwable var4) {
      }
   }

   public static boolean IfError(Utils.TryLambda _try) {
      try {
         _try.run();
         return false;
      } catch (Throwable var2) {
         return true;
      }
   }

   public static void TryIf(Utils.TryLambda r, boolean b) {
      if (b) {
         try {
            r.run();
         } catch (Throwable var3) {
            var3.printStackTrace();
         }
      }

   }

   public static void TryIfNull(Object o, Utils.TryLambda r) {
      if (o == null) {
         try {
            r.run();
         } catch (Throwable var3) {
            var3.printStackTrace();
         }
      }

   }

   public static void TryIfNotNull(Object o, Utils.TryLambda r) {
      if (o != null) {
         try {
            r.run();
         } catch (Throwable var3) {
            var3.printStackTrace();
         }
      }

   }

   public static void Switch(String value, Utils.CaseField... cases) {
      Utils.CaseField[] var2 = cases;
      int var3 = cases.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Utils.CaseField field = var2[var4];
         if (field.trigger.equals(value)) {
            field.task.run();
            return;
         }
      }

   }

   public static boolean contains(String[] strings, String string) {
      String[] var2 = strings;
      int var3 = strings.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String s = var2[var4];
         if (s.equalsIgnoreCase(string)) {
            return true;
         }
      }

      return false;
   }

   public static <T> T[] trim(T[] array, int amount) {
      return Arrays.copyOf(array, array.length - amount);
   }

   public static void close(Closeable... closeables) throws IOException {
      try {
         Closeable[] var1 = closeables;
         int var2 = closeables.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Closeable closeable = var1[var3];
            if (closeable != null) {
               closeable.close();
            }
         }

      } catch (Throwable var5) {
         throw var5;
      }
   }

   public static void close(AutoCloseable... closeables) throws Exception {
      try {
         AutoCloseable[] var1 = closeables;
         int var2 = closeables.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            AutoCloseable closeable = var1[var3];
            if (closeable != null) {
               closeable.close();
            }
         }

      } catch (Throwable var5) {
         throw var5;
      }
   }

   public static String compile(Object[] objs) {
      return compile(objs, " ");
   }

   public static String compile(Object[] objs, String seperator) {
      StringBuilder builder = new StringBuilder();
      Object[] var3 = objs;
      int var4 = objs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object s = var3[var5];
         if (builder.length() != 0) {
            builder.append(seperator);
         }

         builder.append(s.toString());
      }

      return builder.toString();
   }

   public static String compile(int[] ints, String seperator) {
      StringBuilder builder = new StringBuilder();
      int[] var3 = ints;
      int var4 = ints.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object s = var3[var5];
         if (builder.length() != 0) {
            builder.append(seperator);
         }

         builder.append(s.toString());
      }

      return builder.toString();
   }

   public static String compile(long[] longs, String seperator) {
      StringBuilder builder = new StringBuilder();
      long[] var3 = longs;
      int var4 = longs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object s = var3[var5];
         if (builder.length() != 0) {
            builder.append(seperator);
         }

         builder.append(s.toString());
      }

      return builder.toString();
   }

   public static String compile(Object[] objs, String seperator, int start) {
      StringBuilder builder = new StringBuilder();

      for(int i = start; i < objs.length; ++i) {
         Object s = objs[i];
         if (builder.length() != 0) {
            builder.append(seperator);
         }

         builder.append(s.toString());
      }

      return builder.toString();
   }

   public static String getTime() {
      return getTime(-1L);
   }

   public static String getTime(long date) {
      Date d = date == -1L ? new Date() : new Date(date);
      SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return f.format(d);
   }

   public static UUID toUUID(String uuid) {
      return uuid.contains("-") ? UUID.fromString(uuid) : UUID.fromString(uuid.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
   }

   public static int getInt(String string) throws IllegalArgumentException {
      try {
         return Integer.parseInt(string);
      } catch (Exception var2) {
         throw new IllegalArgumentException("Failed to parse " + string + " as an integer");
      }
   }

   public static void reverse(Queue q) {
      Stack s = new Stack();

      while(!q.isEmpty()) {
         s.push(q.poll());
      }

      while(!s.isEmpty()) {
         q.offer(s.pop());
      }

   }

   public static <T> T[] shift(T[] array, T t) {
      System.arraycopy(array, 1, array, 0, array.length - 1);
      array[array.length - 1] = t;
      return array;
   }

   public static <T> List<T> toList(Iterator<T> iterator) {
      ArrayList list = new ArrayList();

      while(iterator.hasNext()) {
         list.add(iterator.next());
         iterator.remove();
      }

      return list;
   }

   public static void createFile(File file) throws IOException {
      if (!file.exists()) {
         if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
         }

         file.createNewFile();
      }

   }

   public static void writeToFile(File file, String string) throws IOException {
      String fixed = string.replaceAll("\r\n|\n|\r", System.lineSeparator());
      PrintWriter writer = new PrintWriter(file);
      writer.write(fixed);
      writer.close();
   }

   public static void extractFirst(File file, File to) throws Exception {
      ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file));
      ZipEntry entry = zipIn.getNextEntry();
      if (entry != null) {
         extractFile(zipIn, to.getPath());
         zipIn.closeEntry();
      }

      zipIn.close();
   }

   public static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
      File file = new File(filePath);
      createFile(file);
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
      byte[] bytesIn = new byte[4096];

      int read;
      while((read = zipIn.read(bytesIn)) != -1) {
         bos.write(bytesIn, 0, read);
      }

      bos.close();
   }

   public interface Catchlambda {
      void run(Throwable var1);
   }

   public interface TryLambda {
      void run() throws Exception;
   }

   public static class CaseField {
      String trigger;
      Runnable task;

      @ConstructorProperties({"trigger", "task"})
      public CaseField(String trigger, Runnable task) {
         this.trigger = trigger;
         this.task = task;
      }
   }
}
