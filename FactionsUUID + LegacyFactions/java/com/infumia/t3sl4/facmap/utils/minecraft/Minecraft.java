package com.infumia.t3sl4.facmap.utils.minecraft;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.bukkit.ChatColor;

public class Minecraft {
   public static int getPlayercount(String ip, int port) {
      try {
         Socket sock = new Socket(ip, port);
         DataOutputStream out = new DataOutputStream(sock.getOutputStream());
         DataInputStream in = new DataInputStream(sock.getInputStream());
         out.write(254);
         StringBuilder str = new StringBuilder();

         int b;
         while((b = in.read()) != -1) {
            if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
               str.append((char)b);
            }
         }

         return Integer.parseInt(str.toString().split("§")[1]);
      } catch (IOException var7) {
         return 0;
      }
   }

   public static String parse(String raw) {
      return ChatColor.translateAlternateColorCodes('&', raw);
   }
}
