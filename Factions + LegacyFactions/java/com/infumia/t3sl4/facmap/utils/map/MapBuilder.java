package com.infumia.t3sl4.facmap.utils.map;

import java.util.Map;

public class MapBuilder<A, B> {
   private final Map<A, B> map;

   public MapBuilder(Map<A, B> map) {
      this.map = map;
   }

   public MapBuilder<A, B> put(A key, B value) {
      this.map.put(key, value);
      return this;
   }

   public Map<A, B> build() {
      return this.map;
   }
}
