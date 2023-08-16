//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import net.minecraft.client.*;
import java.util.*;

public class PartyBotsRunnable implements Runnable
{
    ArrayList<String> igns;
    
    public PartyBotsRunnable(final ArrayList<String> ignArray) {
        this.igns = ignArray;
    }
    
    @Override
    public void run() {
        String partyCommand = "/p ";
        int counter = 0;
        for (final String tempIgn : this.igns) {
            partyCommand = partyCommand + " " + tempIgn;
            if (counter < 3) {
                ++counter;
            }
            else {
                counter = 0;
                Minecraft.getMinecraft().thePlayer.sendChatMessage(partyCommand);
                partyCommand = "/p ";
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (counter > 0) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(partyCommand);
        }
    }
}
