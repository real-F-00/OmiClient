//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class DarkList extends Module
{
    ArrayList<String> darkPeopleNotRacist;
    ArrayList<String> temp;
    List<String> DARK_ENCHANTS;
    int delay;
    int i;
    Boolean flag;
    
    public DarkList() {
        super("Dark List", "Displays the people wearing dark pants in the lobby.", Category.RENDER);
        this.darkPeopleNotRacist = new ArrayList<String>();
        this.temp = new ArrayList<String>();
        this.DARK_ENCHANTS = Arrays.asList("Venom", "Misery", "Spite", "Needless Suffering", "Grim Reaper", "Nostalgia", "Hedge Fund", "Heartripper", "Sanguisuge", "Lycanthropy", "Mind Assault", "Golden Handcuffs");
        this.delay = 500;
        this.i = 0;
        this.flag = false;
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 5.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 40.0, 0.0, 1200.0, true, true));
        this.hud = true;
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent e) {
        if (DarkList.mc.thePlayer == null) {
            return;
        }
        if (OmiClient.instance.isInPit) {
            if (this.i == this.delay) {
                this.temp.clear();
                for (final EntityPlayer entityThing : DarkList.mc.theWorld.playerEntities) {
                    if (entityThing == null) {
                        continue;
                    }
                    if (entityThing.inventory.armorInventory[1] == null || entityThing.inventory.armorInventory[1].getItem() != Item.getItemById(300)) {
                        continue;
                    }
                    String test = "";
                    if (entityThing.inventory.armorInventory[1].getDisplayName() == null) {
                        continue;
                    }
                    test = entityThing.inventory.armorInventory[1].getDisplayName();
                    if (!test.contains("Evil") && !test.contains("I Dark")) {
                        continue;
                    }
                    final String name = entityThing.getName();
                    String type = "";
                    final String nbtTing = entityThing.inventory.armorInventory[1].getTagCompound().toString();
                    if (nbtTing.contains("Tier I ")) {
                        type = "Plain Darks";
                    }
                    else {
                        for (final String tmpTing : this.DARK_ENCHANTS) {
                            if (nbtTing.contains(tmpTing)) {
                                type = tmpTing;
                                break;
                            }
                        }
                    }
                    if (this.temp.contains(name + "," + type)) {
                        continue;
                    }
                    this.temp.add(name + "," + type);
                }
                this.darkPeopleNotRacist = (ArrayList<String>)this.temp.clone();
                this.i = 0;
            }
            ++this.i;
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Post egoe) {
        if (DarkList.mc.thePlayer == null) {
            return;
        }
        if (egoe.type == RenderGameOverlayEvent.ElementType.CHAT) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
            final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
            int indexTing = (int)pos;
            if (!this.flag) {
                fr.drawStringWithShadow("Dark List:", posX, (float)indexTing, 16777215);
                for (final String entityThing : this.darkPeopleNotRacist) {
                    final String[] tmpName = entityThing.split(",");
                    if (tmpName.length < 2) {
                        return;
                    }
                    fr.drawStringWithShadow(tmpName[0] + " in ", posX, (float)(indexTing + fr.FONT_HEIGHT), 16777215);
                    fr.drawStringWithShadow(tmpName[1], posX + fr.getStringWidth(tmpName[0] + " in "), (float)(indexTing + fr.FONT_HEIGHT), 11872232);
                    indexTing += fr.FONT_HEIGHT;
                }
            }
            else {
                fr.drawStringWithShadow("", 5.0f, 5.0f, 16777215);
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.flag = true;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.flag = false;
    }
}
