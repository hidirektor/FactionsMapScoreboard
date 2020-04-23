package com.infumia.t3sl4.facmap.utils.config;

import java.util.HashMap;
import java.util.Map;

public class SimpleMemoryConfig<V> {
   private Map<String, V> config = new HashMap();

   public Map<String, V> getMap() {
      return this.config;
   }

   public void set(String key, V obj) {
      this.config.put(key, obj);
   }

   public Object get(String key) {
      return this.config.get(key);
   }

   public String getString(String key) {
      return (String)this.config.get(key);
   }

   public String getString(String key, String def) {
      String s = (String)this.config.get(key);
      return s == null ? def : s;
   }

   public Boolean getBoolean(String key) {
      return (Boolean)this.config.get(key);
   }

   public Boolean getBoolean(String key, boolean def) {
      Boolean bol = (Boolean)this.config.get(key);
      return bol == null ? def : bol;
   }

   public Integer getInt(String key) {
      return (Integer)this.config.get(key);
   }

   public Integer getInt(String key, int def) {
      Integer i = (Integer)this.config.get(key);
      return i == null ? def : i;
   }
}
