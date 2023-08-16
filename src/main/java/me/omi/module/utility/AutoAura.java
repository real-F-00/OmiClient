//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.utility;

import me.omi.utils.*;
import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class AutoAura extends Module
{
    private TimeHelper time;
    private boolean active;
    
    public AutoAura() {
        super("Aura Timer", "Display a countdown for true damage protection", Category.UTILITY);
        this.time = new TimeHelper(1);
        this.active = true;
        OmiClient.instance.settingsManager.rSetting(new Setting("Timer UI", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Auto Aura", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 200.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 200.0, 0.0, 1200.0, true, true));
        this.hud = true;
    }
    
    @SubscribeEvent
    public void PlayerInteractEvent(final PlayerInteractEvent e) {
        if (AutoAura.mc.thePlayer.inventory.getCurrentItem() != null && AutoAura.mc.thePlayer.inventory.getCurrentItem().getItem() == Item.getItemById(341)) {
            if (this.time.getDifference() < 1000L) {
                e.setCanceled(true);
                AutoAura.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "Double aura use canceled!"));
                return;
            }
            this.active = OmiClient.instance.settingsManager.getSettingByName((Module)this, "Timer UI").getValBoolean();
            this.time.reset();
        }
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && AutoAura.mc.thePlayer != null) {
            if (AutoAura.mc.gameSettings.keyBindAttack.isKeyDown() && AutoAura.mc.thePlayer.inventory.getCurrentItem() != null && AutoAura.mc.thePlayer.inventory.getCurrentItem().getItem() == Item.getItemById(341)) {
                this.time.reset();
            }
            if (OmiClient.instance.settingsManager.getSettingByName((Module)this, "Auto Aura").getValBoolean()) {
                final long diffy = this.time.getDifference();
                if (diffy < 14000L || diffy > 16000L) {
                    return;
                }
                if (AutoAura.mc.thePlayer.inventory.hasItem(Item.getItemById(341))) {
                    for (int i = 0; i < AutoAura.mc.thePlayer.inventory.mainInventory.length; ++i) {
                        if (AutoAura.mc.thePlayer.inventory.mainInventory[i] != null && AutoAura.mc.thePlayer.inventory.mainInventory[i].getItem() == Item.getItemById(341)) {
                            if (i > 8) {
                                return;
                            }
                            if (AutoAura.mc.thePlayer.inventory.mainInventory[i].stackSize == 1) {
                                return;
                            }
                            final int temp = AutoAura.mc.thePlayer.inventory.currentItem;
                            AutoAura.mc.thePlayer.inventory.currentItem = i;
                            AutoAura.mc.playerController.sendUseItem((EntityPlayer)AutoAura.mc.thePlayer, (World)AutoAura.mc.theWorld, AutoAura.mc.thePlayer.inventory.mainInventory[i]);
                            AutoAura.mc.thePlayer.inventory.currentItem = temp;
                            AutoAura.mc.playerController.updateController();
                            this.time.reset();
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent egoe) {
        if (AutoAura.mc.thePlayer == null) {
            return;
        }
        if (egoe.type == RenderGameOverlayEvent.ElementType.TEXT) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final long diffy = this.time.getDifference();
            if (diffy < 15000L) {
                if (!this.active) {
                    return;
                }
                final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
                final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
                final String number = String.format("%.2f", (15000L - diffy) / 1000.0f);
                final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                if (diffy > 10000L) {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Aura: " + EnumChatFormatting.RED + number, posX, pos, 1634328);
                }
                else if (diffy < 5000L) {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Aura: " + EnumChatFormatting.GREEN + number, posX, pos, 1634328);
                }
                else {
                    fr.drawStringWithShadow(EnumChatFormatting.WHITE + "Aura: " + EnumChatFormatting.DARK_GREEN + number, posX, pos, 1634328);
                }
            }
        }
    }
}
