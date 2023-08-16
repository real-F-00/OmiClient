//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;

public class FollowRunnable implements Runnable
{
    String ign;
    Boolean flag;
    private volatile boolean shutdown;
    
    public FollowRunnable(final String in) {
        this.ign = "";
        this.flag = false;
        this.ign = in;
    }
    
    @Override
    public void run() {
        while (!this.shutdown) {
            this.flag = false;
            for (final EntityPlayer entityThing : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (entityThing.getName().contains(this.ign)) {
                    this.flag = true;
                    Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Player " + this.ign + " is in the lobby."));
                }
            }
            if (!this.flag) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/play pit");
            }
            try {
                Thread.sleep(6000L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void shutdown() {
        this.shutdown = true;
    }
}
