package com.infumia.t3sl4.facmap.utils.config;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;

public class SimplePrimitiveConfig {
   DefaultConfigurationBuilder config;
   File file;

   public SimplePrimitiveConfig(File file) throws IOException {
      try {
         if (!file.exists()) {
            if (file.getParentFile() != null) {
               file.getParentFile().mkdirs();
            }

            file.createNewFile();
         }

         this.file = file;
         this.config = new DefaultConfigurationBuilder(file);
      } catch (Throwable var3) {
         throw var3;
      }
   }

   public void save() throws ConfigurationException {
      try {
         this.config.save(this.file);
      } catch (Throwable var2) {
         throw var2;
      }
   }

   public Configuration subset(String s) {
      return this.config.subset(s);
   }

   public boolean isEmpty() {
      return this.config.isEmpty();
   }

   public boolean containsKey(String s) {
      return this.config.containsKey(s);
   }

   public void addProperty(String s, Object o) {
      this.config.addProperty(s, o);
   }

   public void setProperty(String s, Object o) {
      this.config.setProperty(s, o);
   }

   public void clearProperty(String s) {
      this.config.clearProperty(s);
   }

   public void clear() {
      this.config.clear();
   }

   public Object getProperty(String s) {
      return this.config.getProperty(s);
   }

   public Iterator<String> getKeys(String s) {
      return this.config.getKeys(s);
   }

   public Iterator<String> getKeys() {
      return this.config.getKeys();
   }

   public Properties getProperties(String s) {
      return this.config.getProperties(s);
   }

   public boolean getBoolean(String s) {
      return this.config.getBoolean(s);
   }

   public boolean getBoolean(String s, boolean b) {
      return this.config.getBoolean(s, b);
   }

   public Boolean getBoolean(String s, Boolean aBoolean) {
      return this.config.getBoolean(s, aBoolean);
   }

   public byte getByte(String s) {
      return this.config.getByte(s);
   }

   public byte getByte(String s, byte b) {
      return this.config.getByte(s, b);
   }

   public Byte getByte(String s, Byte aByte) {
      return this.config.getByte(s, aByte);
   }

   public double getDouble(String s) {
      return this.config.getDouble(s);
   }

   public double getDouble(String s, double v) {
      return this.config.getDouble(s, v);
   }

   public Double getDouble(String s, Double aDouble) {
      return this.config.getDouble(s, aDouble);
   }

   public float getFloat(String s) {
      return this.config.getFloat(s);
   }

   public float getFloat(String s, float v) {
      return this.config.getFloat(s, v);
   }

   public Float getFloat(String s, Float aFloat) {
      return this.config.getFloat(s, aFloat);
   }

   public int getInt(String s) {
      return this.config.getInt(s);
   }

   public int getInt(String s, int i) {
      return this.config.getInt(s, i);
   }

   public Integer getInteger(String s, Integer integer) {
      return this.config.getInteger(s, integer);
   }

   public long getLong(String s) {
      return this.config.getLong(s);
   }

   public long getLong(String s, long l) {
      return this.config.getLong(s, l);
   }

   public Long getLong(String s, Long aLong) {
      return this.config.getLong(s, aLong);
   }

   public short getShort(String s) {
      return this.config.getShort(s);
   }

   public short getShort(String s, short i) {
      return this.config.getShort(s, i);
   }

   public Short getShort(String s, Short aShort) {
      return this.config.getShort(s, aShort);
   }

   public BigDecimal getBigDecimal(String s) {
      return this.config.getBigDecimal(s);
   }

   public BigDecimal getBigDecimal(String s, BigDecimal bigDecimal) {
      return this.config.getBigDecimal(s, bigDecimal);
   }

   public BigInteger getBigInteger(String s) {
      return this.config.getBigInteger(s);
   }

   public BigInteger getBigInteger(String s, BigInteger bigInteger) {
      return this.config.getBigInteger(s, bigInteger);
   }

   public String getString(String s) {
      return this.config.getString(s);
   }

   public String getString(String s, String s1) {
      return this.config.getString(s, s1);
   }

   public String[] getStringArray(String s) {
      return this.config.getStringArray(s);
   }

   public List<Object> getList(String s) {
      return this.config.getList(s);
   }

   public List<Object> getList(String s, List<?> list) {
      return this.config.getList(s, (List<Object>) list);
   }
}
