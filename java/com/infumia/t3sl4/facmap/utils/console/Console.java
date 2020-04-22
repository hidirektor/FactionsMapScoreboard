package com.infumia.t3sl4.facmap.utils.console;

import com.infumia.t3sl4.facmap.utils.Utils;
import com.infumia.t3sl4.facmap.utils.console.logging.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Console implements Runnable {
   private List<ConsoleCommand> commands = new ArrayList();
   private boolean running = true;
   private Scanner scanner;

   public Console() {
      this.scanner = new Scanner(System.in);
   }

   public void start() {
      Thread t = new Thread(this);
      t.setDaemon(true);
      t.start();
   }

   public void shutdown() {
      this.running = false;
      this.scanner.close();
   }

   public ConsoleCommand getCommand(String command) {
      Iterator var2 = this.commands.iterator();

      ConsoleCommand cmd;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         cmd = (ConsoleCommand)var2.next();
      } while(!Utils.contains(cmd.getCommand(), command));

      return cmd;
   }

   public void addCommand(ConsoleCommand command) {
      this.commands.add(command);
   }

   public void run() {
      try {
         while(this.running) {
            String line = this.scanner.nextLine();
            if (line != null && line.trim().length() > 0) {
               String[] args = line.split(" ");
               ConsoleCommand cmd = this.getCommand(args[0]);
               if (cmd != null) {
                  cmd.run((String[])Utils.trim(args, 1));
               } else {
                  Log.info("Unknown command " + args[0]);
               }
            }
         }

      } catch (Throwable var4) {
         throw var4;
      }
   }
}
