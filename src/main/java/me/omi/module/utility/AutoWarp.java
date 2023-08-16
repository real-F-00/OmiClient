//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.utility;

import me.omi.utils.*;
import me.omi.module.*;
import net.minecraftforge.fml.common.gameevent.*;
import me.omi.*;
import me.omi.module.render.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoWarp extends Module
{
    TimeHelper time;
    int i;
    
    public AutoWarp() {
        super("Auto Warps", "Automatically warps when there is space", Category.UTILITY);
        this.time = new TimeHelper();
        this.i = 0;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && OmiClient.instance.isInPit) {
            ++this.i;
            if (this.i == 20) {
                this.i = 0;
                try {
                    if (this.time.hasReached(2000L) && ((PlayerCounter)OmiClient.instance.moduleManager.getModule("Player Counter")).players < 81) {
                        this.time.reset();
                        AutoWarp.mc.thePlayer.sendChatMessage("/p warp");
                    }
                }
                catch (Exception ex) {
                    System.out.println("[OMI CLIENT] Auto warp broke.");
                }
            }
        }
    }
}
