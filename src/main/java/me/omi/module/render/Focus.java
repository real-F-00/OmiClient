//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Focus extends Module
{
    public Focus() {
        super("Focus", "Hides all other players", Category.RENDER);
        OmiClient.instance.settingsManager.rSetting(new Setting("Show Players in Friends List", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Show Players in KOS List", (Module)this, true));
    }
    
    @SubscribeEvent
    public void RenderPlayerEvent(final RenderPlayerEvent.Pre e) {
        if (e.entityPlayer != null && e.entityPlayer.getName() != null) {
            final String player = e.entityPlayer.getName();
            if (OmiClient.instance.settingsManager.getSettingByName((Module)this, "Show Players in Friends List").getValBoolean() && ((PlayerList)OmiClient.instance.moduleManager.getModule("Player List")).friendList.contains(player)) {
                return;
            }
            if (OmiClient.instance.settingsManager.getSettingByName((Module)this, "Show Players in KOS List").getValBoolean() && ((PlayerList)OmiClient.instance.moduleManager.getModule("Player List")).kosList.contains(player)) {
                return;
            }
            if (player.contains(Focus.mc.thePlayer.getName()) || player.contains(this.argTest)) {
                return;
            }
        }
        e.setCanceled(true);
    }
}
