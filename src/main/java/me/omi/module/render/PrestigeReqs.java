//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import java.text.*;
import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import me.omi.thread.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;

public class PrestigeReqs extends Module
{
    private static DecimalFormat df;
    Double[] GoldReq;
    public Double currentGoldReq;
    public int prestige;
    boolean done;
    
    public PrestigeReqs() {
        super("Prestige Tracker", "Tracks stats for your current prestige.", Category.RENDER);
        this.GoldReq = new Double[] { 10000.0, 22000.0, 24000.0, 26000.0, 28000.0, 30000.0, 70000.0, 80000.0, 100000.0, 120000.0, 160000.0, 200000.0, 240000.0, 280000.0, 320000.0, 360000.0, 400000.0, 480000.0, 560000.0, 800000.0, 900000.0, 1000000.0, 1200000.0, 1400000.0, 1600000.0, 1800000.0, 2400000.0, 2700000.0, 3000000.0, 6000000.0, 1.0E7, 1.212E7, 1.414E7, 1.616E7, 1.818E7, 0.0 };
        this.currentGoldReq = 0.0;
        this.prestige = 0;
        this.done = false;
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 5.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 40.0, 0.0, 1200.0, true, true));
        this.hud = true;
        final CheckPrestige prestRunnable = new CheckPrestige();
        final Thread newThreadRunnable = new Thread((Runnable)prestRunnable);
        newThreadRunnable.start();
    }
    
    @SubscribeEvent
    public void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
        if (OmiClient.instance.isInPit) {
            try {
                final String msg = event.message.getUnformattedText();
                if (msg.contains("KILL!") || msg.contains("ASSIST!")) {
                    final String xpString = msg.split("\\+")[1];
                    final String goldString = msg.split("\\+")[2];
                    final int xp = Integer.parseInt(xpString.substring(0, xpString.length() - 5));
                    final float gold = Float.parseFloat(goldString.substring(2, goldString.length() - 1));
                    this.currentGoldReq += (Float)gold;
                }
                if (msg.contains("SELF-CHECKOUT!")) {
                    final String nbtTing = PrestigeReqs.mc.thePlayer.inventory.armorInventory[1].getTagCompound().toString();
                    if (nbtTing.contains("Self-checkout I ")) {
                        this.currentGoldReq += 2000.0;
                    }
                    if (nbtTing.contains("Self-checkout II ")) {
                        this.currentGoldReq += 3000.0;
                    }
                    if (nbtTing.contains("Self-checkout III ")) {
                        this.currentGoldReq += 5000.0;
                    }
                }
                if (msg.contains("HIGHLANDER!")) {}
                if (msg.contains("unlocked prestige") && msg.contains(PrestigeReqs.mc.thePlayer.getName())) {
                    this.currentGoldReq = 0.0;
                    ++this.prestige;
                }
                ((PitScoreboard)OmiClient.instance.moduleManager.getModule("Pit Scoreboard")).overlay.setReq(this.GoldReq[this.prestige] - this.currentGoldReq, this.GoldReq[this.prestige], this.currentGoldReq);
            }
            catch (Exception ex) {}
        }
    }
    
    @SubscribeEvent
    public void WorldEvent(final WorldEvent.Load e) {
        if (!this.done) {
            this.done = true;
            final CheckPrestige prestRunnable = new CheckPrestige();
            final Thread newThreadRunnable = new Thread((Runnable)prestRunnable);
            newThreadRunnable.start();
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Post egoe) {
        if (PrestigeReqs.mc.thePlayer == null) {
            return;
        }
        if (egoe.type == RenderGameOverlayEvent.ElementType.CHAT && OmiClient.instance.isInPit && this.toggled) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
            final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
            final int indexTing = (int)pos;
            fr.drawStringWithShadow("" + EnumChatFormatting.GOLD + EnumChatFormatting.BOLD + "GOLD: " + PrestigeReqs.df.format(this.currentGoldReq) + "/" + this.GoldReq[this.prestige], posX, (float)indexTing, 16777215);
        }
    }
    
    static {
        PrestigeReqs.df = new DecimalFormat("0.00");
    }
}
