//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.command;

import java.util.*;
import me.omi.thread.*;
import java.io.*;
import net.minecraft.command.*;

public class partybots extends CommandBase
{
    public String getCommandName() {
        return "partybots";
    }
    
    public List<String> getCommandAliases() {
        final List<String> temp = new ArrayList<String>();
        temp.add("partyBots");
        return temp;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "Takes raw oq input and parties the bots.";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Desktop/oq.txt"));
            String line = reader.readLine();
            final ArrayList<String> temp = new ArrayList<String>();
            while (line != null) {
                final String tempString = line.split("\tHealth")[0].substring("True\t".length(), line.split("\tHealth")[0].length());
                System.out.println(tempString);
                temp.add(tempString);
                line = reader.readLine();
            }
            final PartyBotsRunnable newParty = new PartyBotsRunnable(temp);
            final Thread newThread = new Thread(newParty);
            newThread.start();
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
