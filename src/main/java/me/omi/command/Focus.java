//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.command;

import me.omi.*;
import me.omi.module.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.command.*;

public class Focus extends CommandBase
{
    public String getCommandName() {
        return "Focus";
    }
    
    public List<String> getCommandAliases() {
        final List<String> temp = new ArrayList<String>();
        temp.add("focus");
        temp.add("FocusPlayer");
        temp.add("focusPlayer");
        temp.add("focusplayer");
        return temp;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public String getCommandUsage(final ICommandSender sender) {
        return "Focuses on player";
    }
    
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length > 0) {
            for (final Module mod : OmiClient.instance.moduleManager.modules) {
                if (mod.name == "Focus") {
                    mod.argTest = args[0];
                }
            }
            sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Focused Player!"));
        }
        else {
            sender.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Usage is /focus ign!"));
        }
    }
}
