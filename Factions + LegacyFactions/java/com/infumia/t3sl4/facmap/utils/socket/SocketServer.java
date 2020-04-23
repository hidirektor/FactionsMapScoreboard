package com.infumia.t3sl4.facmap.utils.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.infumia.t3sl4.facmap.utils.Utils;
import com.infumia.t3sl4.facmap.utils.console.logging.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SocketServer implements Runnable {
   private static final Gson gson = new Gson();
   boolean running = true;
   ServerSocket server;
   String ip;
   int port;
   Map<UUID, SocketServer.BufferedSocket> clients = new HashMap();
   SocketServer.InputHandler handler;

   public SocketServer(String ip, int port) {
      this.ip = ip;
      this.port = port;
      (new Thread(this, "WebServer")).start();
   }

   public void run() {
      while(this.isRunning()) {
         try {
            this.server = new ServerSocket(this.port, 50, InetAddress.getByName(this.ip));

            while(this.server.isBound() && !this.server.isClosed()) {
               try {
                  Socket socket = this.server.accept();
                  UUID uuid = UUID.randomUUID();
                  SocketServer.BufferedSocket bs = new SocketServer.BufferedSocket(uuid, this, socket);
                  this.clients.put(uuid, bs);
               } catch (Exception var4) {
                  if (!var4.getMessage().equalsIgnoreCase("socket closed")) {
                     var4.printStackTrace();
                     Log.severe("Failed to establish connection to socket " + var4.getMessage());
                  }
               }
            }
         } catch (Exception var5) {
            Log.severe("Webserver crashed or failed to initialize: " + var5.getMessage());
         }

         if (this.isRunning()) {
            Log.severe("Restarting the webserver in 3 seconds");
            Utils.sleep(3000L);
         }
      }

   }

   public void shutdown() throws IOException {
      this.running = false;
      Utils.close(this.server);
   }

   protected boolean isRunning() {
      return this.running;
   }

   protected ServerSocket getServer() {
      return this.server;
   }

   protected String getIp() {
      return this.ip;
   }

   protected int getPort() {
      return this.port;
   }

   protected Map<UUID, SocketServer.BufferedSocket> getClients() {
      return this.clients;
   }

   protected SocketServer.InputHandler getHandler() {
      return this.handler;
   }

   public void setHandler(SocketServer.InputHandler handler) {
      this.handler = handler;
   }

   public interface InputHandler {
      void connect(SocketServer.BufferedSocket var1) throws Exception;

      void handle(SocketServer.BufferedSocket var1, Object var2) throws Exception;

      void disconnect(SocketServer.BufferedSocket var1) throws Exception;
   }

   public static class BufferedSocket implements Runnable {
      UUID id;
      SocketServer server;
      Socket socket;
      DataInputStream input;
      DataOutputStream output;

      public BufferedSocket(UUID id, SocketServer server, Socket socket) throws Exception {
         this.id = id;
         this.socket = socket;
         this.server = server;
         (new Thread(this, "Socket(" + socket.getInetAddress().getHostName() + ":" + socket.getPort() + ")")).start();
      }

      public void writeObject(Object o) throws Exception {
         JsonObject jo = (JsonObject)SocketServer.gson.fromJson(SocketServer.gson.toJson(o), JsonObject.class);
         jo.add("class", new JsonPrimitive(o.getClass().getName()));
         this.output.writeUTF(SocketServer.gson.toJson(jo));
      }

      public void run() {
         try {
            this.input = new DataInputStream(this.socket.getInputStream());
            this.output = new DataOutputStream(this.socket.getOutputStream());
            this.server.handler.connect(this);

            while(!this.socket.isClosed() && this.socket.isBound() && this.server.isRunning()) {
               try {
                  JsonObject obj = (JsonObject)SocketServer.gson.fromJson(this.input.readUTF(), JsonObject.class);
                  Class<?> clazz = Class.forName(obj.get("class").getAsString());
                  obj.remove("class");
                  Object o = SocketServer.gson.fromJson(obj, clazz);
                  this.server.handler.handle(this, o);
               } catch (SocketException var5) {
                  break;
               } catch (EOFException var6) {
                  break;
               } catch (OptionalDataException var7) {
                  break;
               } catch (Exception var8) {
                  var8.printStackTrace();
               }
            }
         } catch (Exception var9) {
            var9.printStackTrace();
         }

         try {
            this.server.handler.disconnect(this);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }

      public UUID getId() {
         return this.id;
      }

      public void setId(UUID id) {
         this.id = id;
      }

      public Socket getSocket() {
         return this.socket;
      }
   }
}
