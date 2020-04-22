package com.infumia.t3sl4.facmap.utils.console;

public abstract class ConsoleCommand {
   private String[] command;

   public ConsoleCommand(String... command) {
      this.command = command;
   }

   public abstract void run(String[] var1);

   public String[] getCommand() {
      return this.command;
   }
}
