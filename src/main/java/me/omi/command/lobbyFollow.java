//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.command;

import me.omi.thread.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.command.*;

public class lobbyFollow extends CommandBase
{
    FollowRunnable newFollow;
    Thread newThread;
    Boolean following;
    
    public lobbyFollow() {
        this.following = false;
    }
    
    public String getCommandName() {
        return "lobbyFollow";
    }
    
    public List<String> getCommandAliases() {
        final List<String> temp = new ArrayList<String>();
        temp.add("lobbyfollow");
        return temp;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "Follows a player Lobby to Lobby";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].contains("start")) {
                if (this.following) {
                    this.newFollow.shutdown();
                }
                this.following = true;
                this.newFollow = new FollowRunnable(args[1]);
                (this.newThread = new Thread(this.newFollow)).start();
                sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Started following Player!"));
            }
            if (args[0].contains("stop")) {
                this.newFollow.shutdown();
                this.following = false;
                sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Stopped following Player!"));
            }
            if (!args[0].contains("stop") && !args[0].contains("start")) {
                sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Usage is /lobbyfollow (start/stop) ign!"));
            }
        }
        else {
            sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Usage is /lobbyfollow (start/stop) ign!"));
        }
    }
}
