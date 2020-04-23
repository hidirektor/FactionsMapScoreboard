package com.infumia.t3sl4.facmap.utils.sql;

import com.infumia.t3sl4.facmap.utils.Utils;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

public class ExecutableStatement {
   private PreparedStatement statement;
   private int pos = 1;

   public ExecutableStatement(PreparedStatement statement) {
      this.statement = statement;
   }

   public Integer execute() throws Exception {
      Integer var1;
      try {
         var1 = this.statement.executeUpdate();
      } finally {
         if (this.statement != null) {
            Utils.Try(() -> {
               this.statement.getConnection().close();
            });
         }

         Utils.close(this.statement);
      }

      return var1;
   }

   public void execute(ResultSetIterator iterator) throws Exception {
      ResultSet rs = null;

      try {
         rs = this.statement.executeQuery();

         while(rs.next()) {
            iterator.next(rs);
         }
      } finally {
         if (this.statement != null) {
            Utils.Try(() -> {
               this.statement.getConnection().close();
            });
         }

         Utils.close(this.statement, rs);
      }

   }

   public void executeSingle(ResultSetIterator iterator) throws Exception {
      ResultSet rs = null;

      try {
         rs = this.statement.executeQuery();
         if (rs.next()) {
            iterator.next(rs);
         } else {
            iterator.next((ResultSet)null);
         }
      } finally {
         if (this.statement != null) {
            Utils.Try(() -> {
               this.statement.getConnection().close();
            });
         }

         Utils.close(this.statement, rs);
      }

   }

   public ResultSet executeQuery() throws Exception {
      return this.statement.executeQuery();
   }

   public ExecutableStatement append(Object obj) throws Exception {
      this.statement.setObject(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(String obj) throws Exception {
      this.statement.setString(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(UUID uuid) throws Exception {
      this.statement.setString(this.pos++, uuid.toString().replace("-", ""));
      return this;
   }

   public ExecutableStatement append(Array obj) throws Exception {
      this.statement.setArray(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Integer obj) throws Exception {
      this.statement.setInt(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Short obj) throws Exception {
      this.statement.setShort(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Long obj) throws Exception {
      this.statement.setLong(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Float obj) throws Exception {
      this.statement.setFloat(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Double obj) throws Exception {
      this.statement.setDouble(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Date obj) throws Exception {
      this.statement.setDate(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Timestamp obj) throws Exception {
      this.statement.setTimestamp(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Time obj) throws Exception {
      this.statement.setTime(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(Blob obj) throws Exception {
      this.statement.setBlob(this.pos++, obj);
      return this;
   }

   public ExecutableStatement append(byte[] obj) throws Exception {
      this.statement.setBytes(this.pos++, obj);
      return this;
   }
}
