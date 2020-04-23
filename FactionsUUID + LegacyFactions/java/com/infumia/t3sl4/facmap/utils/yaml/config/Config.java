package com.infumia.t3sl4.facmap.utils.yaml.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Config {
   YamlConfiguration yaml;
   public File file;
   boolean cache;

   private static File getConfigFile(String dir, File loc) {
      if (dir == null) {
         return null;
      } else {
         File file;
         if (dir.contains("/")) {
            if (dir.startsWith("/")) {
               file = new File(loc + dir.replace("/", File.separator));
            } else {
               file = new File(loc + File.separator + dir.replace("/", File.separator));
            }
         } else {
            file = new File(loc, dir);
         }

         return file;
      }
   }

   private static File parseFile(String filePath, InputStream resource, File loc) {
      File file = getConfigFile(filePath, loc);
      if (!file.exists()) {
         try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            if (resource != null) {
               try {
                  OutputStream out = new FileOutputStream(file);
                  byte[] buf = new byte[1024];

                  int len;
                  while((len = resource.read(buf)) > 0) {
                     out.write(buf, 0, len);
                  }

                  out.close();
                  resource.close();
               } catch (Exception var7) {
                  var7.printStackTrace();
               }
            }
         } catch (IOException var8) {
            var8.printStackTrace();
         }
      }

      return file;
   }

   public Config(String name, InputStream resource, File loc) {
      this(parseFile(name, resource, loc));
   }

   public Config(String name, InputStream resource, JavaPlugin pl) {
      this(parseFile(name, resource, pl.getDataFolder()));
   }

   public Config(String name, File loc) {
      this(name, (InputStream)null, (File)loc);
   }

   public Config(String name, JavaPlugin pl) {
      this(name, (InputStream)null, (File)pl.getDataFolder());
   }

   public Config(File file) {
      this.cache = false;
      this.yaml = new YamlConfiguration();
      this.file = file;
      this.reload();
   }

   public Config() {
      this.cache = false;
   }

   public void reload() {
      try {
         this.yaml.load(this.file);
      } catch (InvalidConfigurationException | IOException var2) {
         var2.printStackTrace();
      }

   }

   public void save() {
      try {
         if (!this.cache) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.file));
            writer.write(this.yaml.saveToString());
            writer.flush();
            writer.close();
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void set(String path, Object value) {
      this.yaml.set(path, value);
      this.save();
   }

   public void set(String path, Object value, boolean save) {
      this.yaml.set(path, value);
      if (save) {
         this.save();
      }

   }

   public void setDefault(String path, Object value) {
      if (this.get(path) == null) {
         this.set(path, value);
      }

      this.save();
   }

   public void setDefault(String path, Object value, boolean save) {
      if (this.get(path) == null) {
         this.set(path, value, save);
      }

   }

   public void removeKey(String path) {
      this.set(path, (Object)null, true);
      this.save();
   }

   public void addDefault(String path, Object value) {
      this.yaml.addDefault(path, value);
   }

   public void addDefaults(Map<String, Object> defaults) {
      this.yaml.addDefaults(defaults);
   }

   public void addDefaults(Configuration defaults) {
      this.yaml.addDefaults(defaults);
   }

   public void setDefaults(Configuration defaults) {
      this.yaml.setDefaults(defaults);
   }

   public Configuration getDefaults() {
      return this.yaml.getDefaults();
   }

   public ConfigurationSection getParent() {
      return this.yaml.getParent();
   }

   public Set<String> getKeys(boolean deep) {
      return this.yaml.getKeys(deep);
   }

   public Map<String, Object> getValues(boolean deep) {
      return this.yaml.getValues(deep);
   }

   public boolean contains(String path) {
      return this.yaml.contains(path);
   }

   public boolean isSet(String path) {
      return this.yaml.isSet(path);
   }

   public String getCurrentPath() {
      return this.yaml.getCurrentPath();
   }

   public String getName() {
      return this.yaml.getName();
   }

   public Configuration getRoot() {
      return this.yaml.getRoot();
   }

   public ConfigurationSection getDefaultSection() {
      return this.yaml.getDefaultSection();
   }

   public Object get(String path) {
      return this.yaml.get(path);
   }

   public Object get(String path, Object def) {
      return this.yaml.get(path, def);
   }

   public ConfigurationSection createSection(String path) {
      return this.yaml.createSection(path);
   }

   public ConfigurationSection createSection(String path, Map<?, ?> map) {
      return this.yaml.createSection(path, map);
   }

   public String getString(String path) {
      return this.yaml.getString(path);
   }

   public String getString(String path, String def) {
      return this.yaml.getString(path, def);
   }

   public boolean isString(String path) {
      return this.yaml.isString(path);
   }

   public int getInt(String path) {
      return this.yaml.getInt(path);
   }

   public int getInt(String path, int def) {
      return this.yaml.getInt(path, def);
   }

   public boolean isInt(String path) {
      return this.yaml.isInt(path);
   }

   public boolean getBoolean(String path) {
      return this.yaml.getBoolean(path);
   }

   public boolean getBoolean(String path, boolean def) {
      return this.yaml.getBoolean(path, def);
   }

   public boolean isBoolean(String path) {
      return this.yaml.isBoolean(path);
   }

   public double getDouble(String path) {
      return this.yaml.getDouble(path);
   }

   public double getDouble(String path, double def) {
      return this.yaml.getDouble(path, def);
   }

   public boolean isDouble(String path) {
      return this.yaml.isDouble(path);
   }

   public long getLong(String path) {
      return this.yaml.getLong(path);
   }

   public long getLong(String path, long def) {
      return this.yaml.getLong(path, def);
   }

   public boolean isLong(String path) {
      return this.yaml.isLong(path);
   }

   public List<?> getList(String path) {
      return this.yaml.getList(path);
   }

   public List<?> getList(String path, List<?> def) {
      return this.yaml.getList(path, def);
   }

   public boolean isList(String path) {
      return this.yaml.isList(path);
   }

   public List<String> getStringList(String path) {
      return this.yaml.getStringList(path);
   }

   public List<Integer> getIntegerList(String path) {
      return this.yaml.getIntegerList(path);
   }

   public List<Boolean> getBooleanList(String path) {
      return this.yaml.getBooleanList(path);
   }

   public List<Double> getDoubleList(String path) {
      return this.yaml.getDoubleList(path);
   }

   public List<Float> getFloatList(String path) {
      return this.yaml.getFloatList(path);
   }

   public List<Long> getLongList(String path) {
      return this.yaml.getLongList(path);
   }

   public List<Byte> getByteList(String path) {
      return this.yaml.getByteList(path);
   }

   public List<Character> getCharacterList(String path) {
      return this.yaml.getCharacterList(path);
   }

   public List<Short> getShortList(String path) {
      return this.yaml.getShortList(path);
   }

   public List<Map<?, ?>> getMapList(String path) {
      return this.yaml.getMapList(path);
   }

   public Vector getVector(String path) {
      return this.yaml.getVector(path);
   }

   public Vector getVector(String path, Vector def) {
      return this.yaml.getVector(path, def);
   }

   public boolean isVector(String path) {
      return this.yaml.isVector(path);
   }

   public OfflinePlayer getOfflinePlayer(String path) {
      return this.yaml.getOfflinePlayer(path);
   }

   public OfflinePlayer getOfflinePlayer(String path, OfflinePlayer def) {
      return this.yaml.getOfflinePlayer(path, def);
   }

   public boolean isOfflinePlayer(String path) {
      return this.yaml.isOfflinePlayer(path);
   }

   public ItemStack getItemStack(String path) {
      return this.yaml.getItemStack(path);
   }

   public ItemStack getItemStack(String path, ItemStack def) {
      return this.yaml.getItemStack(path, def);
   }

   public boolean isItemStack(String path) {
      return this.yaml.isItemStack(path);
   }

   public Color getColor(String path) {
      return this.yaml.getColor(path);
   }

   public Color getColor(String path, Color def) {
      return this.yaml.getColor(path, def);
   }

   public boolean isColor(String path) {
      return this.yaml.isColor(path);
   }

   public ConfigurationSection getConfigurationSection(String path) {
      return this.yaml.getConfigurationSection(path);
   }

   public boolean isConfigurationSection(String path) {
      return this.yaml.isConfigurationSection(path);
   }

   public static String createPath(ConfigurationSection section, String key) {
      return MemorySection.createPath(section, key);
   }

   public static String createPath(ConfigurationSection section, String key, ConfigurationSection relativeTo) {
      return MemorySection.createPath(section, key, relativeTo);
   }

   public String toString() {
      return this.yaml.toString();
   }

   public void setCache(boolean cache) {
      this.cache = cache;
   }
}
