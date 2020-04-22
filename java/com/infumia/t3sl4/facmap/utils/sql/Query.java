package com.infumia.t3sl4.facmap.utils.sql;

import java.sql.Connection;
import java.sql.SQLException;

import org.intellij.lang.annotations.Language;

public class Query {
   private static MySQL master;
   private static MySQL slave;

   public static ExecutableStatement prepare(@Language("MySQL") String query, boolean master) throws SQLException {
      try {
         Connection con = master ? Query.master.get() : slave.get();
         return new ExecutableStatement(con.prepareStatement(query));
      } catch (Throwable var3) {
         throw var3;
      }
   }

   public static ExecutableStatement prepare(@Language("MySQL") String query, Connection con) throws SQLException {
      try {
         return new ExecutableStatement(con.prepareStatement(query));
      } catch (Throwable var3) {
         throw var3;
      }
   }

   public static void setMaster(MySQL master) {
      Query.master = master;
   }

   public static void setSlave(MySQL slave) {
      Query.slave = slave;
   }
}
