//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.command;

import java.util.*;
import me.omi.thread.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.command.*;

public class Denick extends CommandBase
{
    public String getCommandName() {
        return "Dn";
    }
    
    public List<String> getCommandAliases() {
        final List<String> temp = new ArrayList<String>();
        temp.add("dn");
        return temp;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "Denicks a player";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0) {
            final DenickRunnable newDenick = new DenickRunnable(args[0]);
            final Thread newThread = new Thread(newDenick);
            newThread.start();
        }
        else {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Usage is /dn ign"));
        }
    }
}
