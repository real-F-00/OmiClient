//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.utility;

import me.omi.module.*;
import net.minecraftforge.client.event.sound.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoLogGlitch extends Module
{
    public AutoLogGlitch() {
        super("Auto Log Glitch", "Automatically log glitches after arrow fired.", Category.UTILITY);
    }
    
    @SubscribeEvent
    public void onSound(final PlaySoundEvent event) {
        if (event.name.equalsIgnoreCase("random.successful_hit")) {
            final Thread logGlitch = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException ex) {}
                    Minecraft.getMinecraft().thePlayer.sendChatMessage("/l");
                }
            });
            logGlitch.start();
            if (this.isToggled()) {
                this.toggle();
                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Lobbied! Auto Log Glitch toggled off."));
            }
        }
    }
}
