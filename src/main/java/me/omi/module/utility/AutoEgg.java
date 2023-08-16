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

public class AutoEgg extends Module
{
    TimeHelper time;
    
    public AutoEgg() {
        super("Auto Egg", "Automatically uses healing egg under a certain health.", Category.UTILITY);
        this.time = new TimeHelper();
        OmiClient.instance.settingsManager.rSetting(new Setting("Timer UI", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Min Health", (Module)this, 10.0, 0.0, 12.0, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 200.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 200.0, 0.0, 1200.0, true, true));
        this.hud = true;
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && AutoEgg.mc.thePlayer != null) {
            this.hud = OmiClient.instance.settingsManager.getSettingByName((Module)this, "Timer UI").getValBoolean();
            if (AutoEgg.mc.gameSettings.keyBindAttack.isKeyDown() && AutoEgg.mc.thePlayer.inventory.getCurrentItem() != null && AutoEgg.mc.thePlayer.inventory.getCurrentItem().getItem() == Item.getItemById(383)) {
                this.time.reset();
            }
            if (AutoEgg.mc.thePlayer.getHealth() / 2.0f <= OmiClient.instance.settingsManager.getSettingByName((Module)this, "Min Health").getValDouble() && this.time.hasReached(10100L)) {
                this.useEgg();
            }
        }
    }
    
    @SubscribeEvent
    public void PlayerInteractEvent(final PlayerInteractEvent e) {
        if (AutoEgg.mc.thePlayer.inventory.getCurrentItem() != null && AutoEgg.mc.thePlayer.inventory.getCurrentItem().getItem() == Item.getItemById(383)) {
            this.time.reset();
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent egoe) {
        if (AutoEgg.mc.thePlayer == null) {
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
                if (diffy < 3000L) {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Egg: " + EnumChatFormatting.RED + number, posX, pos, 1634328);
                }
                else if (diffy > 7000L) {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Egg: " + EnumChatFormatting.GREEN + number, posX, pos, 1634328);
                }
                else {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Egg: " + EnumChatFormatting.DARK_GREEN + number, posX, pos, 1634328);
                }
            }
        }
    }
    
    void useEgg() {
        if (AutoEgg.mc.thePlayer.inventory.hasItem(Item.getItemById(383))) {
            for (int i = 0; i < AutoEgg.mc.thePlayer.inventory.mainInventory.length; ++i) {
                if (AutoEgg.mc.thePlayer.inventory.mainInventory[i] != null && AutoEgg.mc.thePlayer.inventory.mainInventory[i].getItem() == Item.getItemById(383) && AutoEgg.mc.thePlayer.inventory.mainInventory[i].getMetadata() == 96) {
                    if (i > 8) {
                        return;
                    }
                    final int temp = AutoEgg.mc.thePlayer.inventory.currentItem;
                    AutoEgg.mc.thePlayer.inventory.currentItem = i;
                    AutoEgg.mc.playerController.sendUseItem((EntityPlayer)AutoEgg.mc.thePlayer, (World)AutoEgg.mc.theWorld, AutoEgg.mc.thePlayer.inventory.mainInventory[i]);
                    AutoEgg.mc.thePlayer.inventory.currentItem = temp;
                    AutoEgg.mc.playerController.updateController();
                    this.time.reset();
                }
            }
        }
    }
}
