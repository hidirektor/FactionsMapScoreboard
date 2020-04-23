package com.infumia.t3sl4.facmap.utils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import java.lang.reflect.Field;

public class JavaProcess {
   public static long getPID(Process p) {
      long result = -1L;

      try {
         Field f;
         if (!p.getClass().getName().equals("java.lang.Win32Process") && !p.getClass().getName().equals("java.lang.ProcessImpl")) {
            if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
               f = p.getClass().getDeclaredField("pid");
               f.setAccessible(true);
               result = f.getLong(p);
               f.setAccessible(false);
            }
         } else {
            f = p.getClass().getDeclaredField("handle");
            f.setAccessible(true);
            long handl = f.getLong(p);
            Kernel32 kernel = Kernel32.INSTANCE;
            HANDLE hand = new HANDLE();
            hand.setPointer(Pointer.createConstant(handl));
            result = (long)kernel.GetProcessId(hand);
            f.setAccessible(false);
         }
      } catch (Exception var8) {
         result = -1L;
      }

      return result;
   }
}
