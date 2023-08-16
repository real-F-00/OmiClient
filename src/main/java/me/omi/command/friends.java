//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.command;

import net.minecraft.client.*;
import net.minecraft.util.*;
import me.omi.*;
import me.omi.module.*;
import me.omi.module.render.*;
import java.util.*;
import net.minecraft.command.*;

public class friends extends CommandBase
{
    public String getCommandName() {
        return "friends";
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "[OMI CLIENT] Usage is /friends (add/remove/list) [ign]";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Usage is /friends (add/remove/list) [ign]"));
        }
        else {
            if (args[0].contains("add")) {
                for (final Module tempModule : OmiClient.instance.moduleManager.modules) {
                    if (tempModule.name == "Player List") {
                        ((PlayerList)tempModule).addToFriends(args[1]);
                        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] User " + args[1] + " added to friend list."));
                        return;
                    }
                }
            }
            if (args[0].contains("remove")) {
                for (final Module tempModule : OmiClient.instance.moduleManager.modules) {
                    if (tempModule.name == "Player List") {
                        final int response = ((PlayerList)tempModule).removeFromFriends(args[1]);
                        if (response == 0) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] User " + args[1] + " removed from friend list."));
                            return;
                        }
                        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Failed to remove user from friend list."));
                        return;
                    }
                }
            }
            if (args[0].contains("list")) {
                for (final Module tempModule : OmiClient.instance.moduleManager.modules) {
                    if (tempModule.name == "Player List") {
                        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] === FRIEND LIST ==="));
                        for (final String tempKos : ((PlayerList)tempModule).friendList) {
                            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] User " + tempKos));
                        }
                        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] === FRIEND LIST ==="));
                        return;
                    }
                }
            }
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Usage is /friends (add/remove/list) [ign]"));
        }
    }
}
