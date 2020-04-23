package com.infumia.t3sl4.facmap.utils.sql;

import com.infumia.t3sl4.facmap.utils.Utils;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class MySQL {
   private BasicDataSource datasource;

   public static MySQL connect(String ip, String database, String user, String password) {
      return new MySQL("jdbc:mysql://" + ip + ":3306/" + database, user, password);
   }

   public MySQL(String url, String username, String password) {
      this.datasource = this.getDataSource(url, username, password);
   }

   public MySQL open(String url, String username, String password) {
      try {
         if (this.datasource.isClosed()) {
            this.datasource = this.getDataSource(url, username, password);
         }

         return this;
      } catch (Throwable var5) {
         throw var5;
      }
   }

   public BasicDataSource getDataSource(String url, String username, String password) {
      BasicDataSource ds = new BasicDataSource();
      ds.setDriverClassName("com.mysql.jdbc.Driver");
      ds.setUsername(username);
      ds.setPassword(password);
      ds.setUrl(url + "?useSSL=false");
      ds.setMinIdle(5);
      ds.setMaxIdle(50);
      ds.setMaxTotal(100);
      return ds;
   }

   public MySQL close() throws SQLException {
      try {
         this.datasource.close();
         return this;
      } catch (Throwable var2) {
         throw var2;
      }
   }

   public Connection get() throws SQLException {
      try {
         return this.datasource.getConnection();
      } catch (Throwable var2) {
         throw var2;
      }
   }

   public MySQL exec(MySQL.Executor e) throws Exception {
      Connection c = this.get();
      e.run(c);
      Utils.close(c);
      return this;
   }

   public BasicDataSource getDatasource() {
      return this.datasource;
   }

   public interface Executor {
      void run(Connection var1);
   }
}
