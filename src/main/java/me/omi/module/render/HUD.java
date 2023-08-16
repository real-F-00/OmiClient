//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class HUD extends Module
{
    public HUD() {
        super("HUD", "Draws the module list on your screen", Category.RENDER);
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 5.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 200.0, 0.0, 1200.0, true, true));
        this.hud = true;
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Post egoe) {
        final RenderGameOverlayEvent.ElementType type = egoe.type;
        final RenderGameOverlayEvent.ElementType type2 = egoe.type;
        if (!type.equals((Object)RenderGameOverlayEvent.ElementType.CROSSHAIRS) || OmiClient.instance.destructed || !this.toggled) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        float posY = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
        final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
        for (final Module mod : OmiClient.instance.moduleManager.getModuleList()) {
            if (!mod.getName().equalsIgnoreCase("HUD") && mod.isToggled() && mod.visible) {
                final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
                if (posX > sr.getScaledWidth() / 2) {
                    fr.drawString(mod.getName(), 88 + (int)posX - fr.getStringWidth(mod.getName()) - 1, (int)posY, 16777215);
                }
                else {
                    fr.drawString(mod.getName(), (int)posX - 1, (int)posY, 16777215);
                }
                posY += fr.FONT_HEIGHT;
            }
        }
    }
}
