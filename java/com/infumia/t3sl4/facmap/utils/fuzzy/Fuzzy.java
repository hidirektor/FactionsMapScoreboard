package com.infumia.t3sl4.facmap.utils.fuzzy;

import java.beans.ConstructorProperties;

public class Fuzzy {
   private boolean[] booleans;

   public static Fuzzy compare(boolean... booleans) {
      return new Fuzzy(booleans);
   }

   public boolean get(int num) {
      return this.booleans[num];
   }

   public boolean allTrue() {
      boolean[] var1 = this.booleans;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         boolean bol = var1[var3];
         if (!bol) {
            return false;
         }
      }

      return true;
   }

   public boolean anyTrue() {
      boolean[] var1 = this.booleans;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         boolean bol = var1[var3];
         if (bol) {
            return true;
         }
      }

      return false;
   }

   @ConstructorProperties({"booleans"})
   public Fuzzy(boolean[] booleans) {
      this.booleans = booleans;
   }
}
