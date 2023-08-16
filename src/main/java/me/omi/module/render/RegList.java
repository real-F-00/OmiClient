//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.utils.*;
import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class RegList extends Module
{
    private TimeHelper time;
    ArrayList<String> darkPeopleNotRacist;
    ArrayList<String> temp;
    List<String> DARK_ENCHANTS;
    int delay;
    int i;
    Boolean flag;
    
    public RegList() {
        super("Regularity List", "Displays the people wearing regularity pants in the lobby.", Category.RENDER);
        this.time = new TimeHelper(1);
        this.darkPeopleNotRacist = new ArrayList<String>();
        this.temp = new ArrayList<String>();
        this.DARK_ENCHANTS = Arrays.asList("Venom", "Misery", "Spite", "Needless Suffering", "Grim Reaper", "Nostalgia", "Hedge Fund", "Heartripper", "Sanguisuge", "Lycanthropy", "Mind Assault", "Golden Handcuffs");
        this.delay = 500;
        this.i = 0;
        this.flag = false;
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 50.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 80.0, 0.0, 1200.0, true, true));
        this.hud = true;
    }
    
    @SubscribeEvent
    public void PlayerTickEvent(final TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            if (RegList.mc.thePlayer == null) {
                return;
            }
            if (OmiClient.instance.isInPit && this.time.hasReached(1000L)) {
                this.time.reset();
                this.temp.clear();
                for (final EntityPlayer entityThing : RegList.mc.theWorld.playerEntities) {
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
                    if (!test.contains("Rage")) {
                        continue;
                    }
                    final String name = entityThing.getName();
                    String type = "";
                    final String nbtTing = entityThing.inventory.armorInventory[1].getTagCompound().toString();
                    if (!nbtTing.contains("Regularity")) {
                        continue;
                    }
                    type = "Regularity";
                    if (this.temp.contains(name + "," + type)) {
                        continue;
                    }
                    this.temp.add(name + "," + type);
                }
                this.darkPeopleNotRacist = (ArrayList<String>)this.temp.clone();
            }
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Post egoe) {
        if (RegList.mc.thePlayer == null) {
            return;
        }
        if (egoe.type == RenderGameOverlayEvent.ElementType.CHAT) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
            final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
            int indexTing = (int)pos;
            if (!this.flag) {
                fr.drawStringWithShadow("Regularity List:", posX, (float)indexTing, 16777215);
                for (final String entityThing : this.darkPeopleNotRacist) {
                    final String[] tmpName = entityThing.split(",");
                    if (tmpName.length < 2) {
                        return;
                    }
                    fr.drawStringWithShadow(tmpName[0] + " in " + tmpName[1], posX, (float)(indexTing + fr.FONT_HEIGHT), 16777215);
                    fr.drawStringWithShadow(tmpName[1], posX + fr.getStringWidth(tmpName[0] + " in "), (float)(indexTing + fr.FONT_HEIGHT), 7340032);
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
