package com.infumia.t3sl4.facmap.utils.sql;

import com.infumia.t3sl4.facmap.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class SQLite {
   private BasicDataSource datasource;

   public static SQLite connect(String name) {
      return new SQLite(name);
   }

   public SQLite(String name) {
      this.datasource = this.getDataSource(name);
   }

   public SQLite open(String name) {
      try {
         if (this.datasource.isClosed()) {
            this.datasource = this.getDataSource(name);
         }

         return this;
      } catch (Throwable var3) {
         throw var3;
      }
   }

   public BasicDataSource getDataSource(String name) {
      BasicDataSource ds = new BasicDataSource();
      ds.setDriverClassName("org.sqlite.JDBC");
      ds.setUrl("jdbc:sqlite:" + name + ".db");
      ds.setMinIdle(1);
      ds.setMaxIdle(1);
      ds.setMaxTotal(1);
      ds.setPoolPreparedStatements(true);
      return ds;
   }

   public SQLite close() throws SQLException {
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

   public SQLite exec(SQLite.Executor e) throws Exception {
      Connection c = this.get();
      e.run(c);
      Utils.close(c);
      return this;
   }

   public SQLite exec(String s, SQLite.StatementExecutor executor) throws Exception {
      Connection c = this.get();
      PreparedStatement statement = null;

      try {
         statement = c.prepareStatement(s);
         if (executor != null) {
            executor.run(statement);
         }

         statement.executeUpdate();
      } catch (Exception var9) {
         var9.printStackTrace();
      } finally {
         Utils.close(c, statement);
      }

      return this;
   }

   public SQLite exec(String s, SQLite.StatementExecutor executor, SQLite.ResultSetExecutor rsexecutor) throws Exception {
      Connection c = this.get();
      PreparedStatement statement = null;
      ResultSet rs = null;

      try {
         statement = c.prepareStatement(s);
         if (executor != null) {
            executor.run(statement);
         }

         rs = statement.executeQuery();
         if (rs.next()) {
            rsexecutor.run(rs);
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      } finally {
         Utils.close(c, statement, rs);
      }

      return this;
   }

   public SQLite exec(String s) throws Exception {
      this.exec(s, (SQLite.StatementExecutor)null);
      return this;
   }

   public BasicDataSource getDatasource() {
      return this.datasource;
   }

   public interface ResultSetExecutor {
      void run(ResultSet var1) throws Exception;
   }

   public interface StatementExecutor {
      void run(PreparedStatement var1) throws Exception;
   }

   public interface Executor {
      void run(Connection var1);
   }
}
