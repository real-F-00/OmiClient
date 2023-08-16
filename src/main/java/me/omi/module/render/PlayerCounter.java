//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class PlayerCounter extends Module
{
    int maxPlayers;
    public int players;
    int i;
    
    public PlayerCounter() {
        super("Player Counter", "Displays the amount of players in the lobby.", Category.RENDER);
        this.maxPlayers = 81;
        this.players = 0;
        this.i = 0;
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 50.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 80.0, 0.0, 1200.0, true, true));
        this.hud = true;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && OmiClient.instance.isInPit) {
            ++this.i;
            if (this.i == 20) {
                this.i = 0;
                if (PlayerCounter.mc.getNetHandler() != null && PlayerCounter.mc.getNetHandler().getPlayerInfoMap() != null) {
                    this.players = PlayerCounter.mc.getNetHandler().getPlayerInfoMap().size();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Post egoe) {
        if (PlayerCounter.mc.thePlayer == null) {
            return;
        }
        if (this.isToggled() && egoe.type == RenderGameOverlayEvent.ElementType.CHAT) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
            final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
            fr.drawStringWithShadow("Players: " + this.players + " / " + this.maxPlayers, posX, pos, 13736473);
        }
    }
}
