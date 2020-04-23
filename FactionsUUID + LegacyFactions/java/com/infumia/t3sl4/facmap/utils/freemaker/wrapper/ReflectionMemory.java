package com.infumia.t3sl4.facmap.utils.freemaker.wrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReflectionMemory {
   private Class clazz;
   private HashMap<String, Field> fieldMap = new HashMap();
   private List<Field> fieldList = new ArrayList();

   public ReflectionMemory(Class clazz) {
      this.clazz = clazz;
      this.scan();
   }

   private void scan() {
      Class cur = this.getClass();

      do {
         this.scanFields(this.clazz);
         cur = cur.getSuperclass();
      } while(!cur.equals(Object.class));

   }

   private void scanFields(Class clazz) {
      Field[] fields = clazz.getDeclaredFields();
      Field[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];
         if (!this.fieldMap.containsKey(field.getName())) {
            field.setAccessible(true);
            this.fieldMap.put(field.getName(), field);
            this.fieldList.add(field);
         }
      }

   }

   public Object extractField(String name, Object parent) throws IllegalAccessException {
      try {
         Field f = (Field)this.fieldMap.get(name);
         return f != null ? f.get(parent) : null;
      } catch (Throwable var4) {
         throw var4;
      }
   }

   public List<Field> getFields() {
      return Collections.unmodifiableList(this.fieldList);
   }
}
