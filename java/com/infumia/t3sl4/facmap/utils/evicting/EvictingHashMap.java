package com.infumia.t3sl4.facmap.utils.evicting;

import java.util.HashMap;
import java.util.Map;

public class EvictingHashMap<K, V> extends HashMap<K, V> {
   final int size;

   public EvictingHashMap(int initialCapacity, float loadFactor, int size) {
      super(initialCapacity, loadFactor);
      this.size = size;
   }

   public EvictingHashMap(int initialCapacity, int size) {
      super(initialCapacity);
      this.size = size;
   }

   public EvictingHashMap(int size) {
      this.size = size;
   }

   public EvictingHashMap(Map<? extends K, ? extends V> m, int size) {
      super(m);
      this.size = size;
   }

   public V put(K key, V value) {
      return super.put(key, value);
   }

   public void putAll(Map<? extends K, ? extends V> m) {
      super.putAll(m);
   }

   public V putIfAbsent(K key, V value) {
      return super.putIfAbsent(key, value);
   }
}
