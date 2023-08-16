//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.utility;

import me.omi.utils.*;
import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class AutoSteak extends Module
{
    TimeHelper time;
    
    public AutoSteak() {
        super("Auto Steak", "Automatically uses steaks.", Category.UTILITY);
        this.time = new TimeHelper();
        OmiClient.instance.settingsManager.rSetting(new Setting("Timer UI", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 200.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 200.0, 0.0, 1200.0, true, true));
        this.hud = true;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && AutoSteak.mc.thePlayer != null) {
            this.hud = OmiClient.instance.settingsManager.getSettingByName((Module)this, "Timer UI").getValBoolean();
            if (AutoSteak.mc.gameSettings.keyBindAttack.isKeyDown() && AutoSteak.mc.thePlayer.inventory.getCurrentItem() != null && AutoSteak.mc.thePlayer.inventory.getCurrentItem().getItem() == Item.getItemById(423)) {
                this.time.reset();
            }
            if (this.time.hasReached(10100L) && AutoSteak.mc.thePlayer.inventory.hasItem(Item.getItemById(423))) {
                this.useSteak();
            }
        }
    }
    
    @SubscribeEvent
    public void PlayerInteractEvent(final PlayerInteractEvent e) {
        if (AutoSteak.mc.thePlayer.inventory.getCurrentItem() != null && AutoSteak.mc.thePlayer.inventory.getCurrentItem().getItem() == Item.getItemById(423)) {
            this.time.reset();
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent egoe) {
        if (AutoSteak.mc.thePlayer == null) {
            return;
        }
        if (egoe.type == RenderGameOverlayEvent.ElementType.TEXT) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final long diffy = this.time.getDifference();
            if (diffy < 10000L && this.toggled && this.hud) {
                final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
                final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
                final String number = String.format("%.2f", (10000L - diffy) / 1000.0f);
                final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                if (diffy > 7000L) {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Steak: " + EnumChatFormatting.RED + number, posX, pos, 1634328);
                }
                else if (diffy < 3000L) {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Steak: " + EnumChatFormatting.GREEN + number, posX, pos, 1634328);
                }
                else {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Steak: " + EnumChatFormatting.DARK_GREEN + number, posX, pos, 1634328);
                }
            }
        }
    }
    
    void useSteak() {
        if (AutoSteak.mc.thePlayer.inventory.hasItem(Item.getItemById(423))) {
            for (int i = 0; i < AutoSteak.mc.thePlayer.inventory.mainInventory.length; ++i) {
                if (AutoSteak.mc.thePlayer.inventory.mainInventory[i] != null && AutoSteak.mc.thePlayer.inventory.mainInventory[i].getItem() == Item.getItemById(423)) {
                    if (i > 8) {
                        return;
                    }
                    final int temp = AutoSteak.mc.thePlayer.inventory.currentItem;
                    AutoSteak.mc.thePlayer.inventory.currentItem = i;
                    AutoSteak.mc.playerController.sendUseItem((EntityPlayer)AutoSteak.mc.thePlayer, (World)AutoSteak.mc.theWorld, AutoSteak.mc.thePlayer.inventory.mainInventory[i]);
                    AutoSteak.mc.thePlayer.inventory.currentItem = temp;
                    AutoSteak.mc.playerController.updateController();
                    this.time.reset();
                }
            }
        }
    }
}
