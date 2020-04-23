package com.infumia.t3sl4.facmap.utils.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.infumia.t3sl4.facmap.utils.Utils;
import com.infumia.t3sl4.facmap.utils.console.logging.Log;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class SocketClient implements Runnable {
   private static final Gson gson = new Gson();
   boolean running = true;
   Socket socket;
   String host;
   int port;
   DataInputStream input;
   DataOutputStream output;
   SocketClient.InputHandler handler;

   public SocketClient(String host, int port, SocketClient.InputHandler handler) {
      this.host = host;
      this.port = port;
      this.handler = handler;
      (new Thread(this)).start();
   }

   public void writeObject(Object o) throws Exception {
      try {
         if (this.socket == null || !this.socket.isConnected() || !this.socket.isBound()) {
            return;
         }

         JsonObject jo = (JsonObject)gson.fromJson(gson.toJson(o), JsonObject.class);
         jo.add("class", new JsonPrimitive(o.getClass().getName()));
         this.output.writeUTF(gson.toJson(jo));
      } catch (Exception var3) {
         if (var3.getMessage() == null || !var3.getMessage().toLowerCase().contains("socket write error") && !var3.getMessage().toLowerCase().contains("broken pipe")) {
            throw var3;
         }
      }

   }

   public void run() {
      for(; this.isRunning(); Utils.sleep(3000L)) {
         try {
            this.socket = new Socket(this.host, this.port);
            this.socket.setKeepAlive(true);
            this.socket.setTcpNoDelay(true);
            this.output = new DataOutputStream(this.socket.getOutputStream());
            this.input = new DataInputStream(this.socket.getInputStream());
            this.handler.connect(this);

            while(this.socket != null && !this.socket.isClosed() && this.socket.isBound()) {
               try {
                  JsonObject obj = (JsonObject)gson.fromJson(this.input.readUTF(), JsonObject.class);
                  Class<?> clazz = Class.forName(obj.get("class").getAsString());
                  obj.remove("class");
                  Object o = gson.fromJson(obj, clazz);
                  this.handler.handle(this, o);
               } catch (SocketException var5) {
                  break;
               } catch (EOFException var6) {
                  break;
               } catch (OptionalDataException var7) {
                  break;
               } catch (Exception var8) {
                  var8.printStackTrace();
                  break;
               }
            }
         } catch (Exception var9) {
            Log.severe("Connection lost or failed: " + var9.getMessage());
         }

         try {
            this.handler.disconnect(this);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

         if (this.isRunning()) {
            Log.severe("Reconnecting to the webserver in 3 seconds");
         }
      }

   }

   public void shutdown() throws IOException {
      this.running = false;
      Utils.close(this.input, this.output, this.socket);
   }

   public boolean isRunning() {
      return this.running;
   }

   public Socket getSocket() {
      return this.socket;
   }

   public void setHandler(SocketClient.InputHandler handler) {
      this.handler = handler;
   }

   public interface InputHandler {
      void connect(SocketClient var1) throws Exception;

      void handle(SocketClient var1, Object var2) throws Exception;

      void disconnect(SocketClient var1) throws Exception;
   }
}
