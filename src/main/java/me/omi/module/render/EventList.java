//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.utils.*;
import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import me.omi.thread.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.time.*;
import com.google.gson.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class EventList extends Module
{
    TimeHelper time;
    TimeHelper timeRefresh;
    int timePassIndex;
    ArrayList<String> eventList;
    
    public EventList() {
        super("Event List", "Lists the next events.", Category.RENDER);
        this.time = new TimeHelper();
        this.timeRefresh = new TimeHelper();
        this.timePassIndex = 0;
        this.eventList = new ArrayList<String>();
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 50.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 5.0, 0.0, 1200.0, true, true));
        this.hud = true;
        final CheckEventsRunnable tempRunnable = new CheckEventsRunnable();
        final Thread newThread = new Thread((Runnable)tempRunnable);
        newThread.start();
    }
    
    @SubscribeEvent
    public void PlayerTickEvent(final TickEvent.PlayerTickEvent e) {
        if (this.timeRefresh.hasReached(600000L)) {
            this.timeRefresh.reset();
            final CheckEventsRunnable tempRunnable = new CheckEventsRunnable();
            final Thread newThread = new Thread((Runnable)tempRunnable);
            newThread.start();
        }
        if (this.time.hasReached(10000L)) {
            try {
                final JsonArray jsonObject = new JsonParser().parse(this.argTest.toString()).getAsJsonArray();
                this.eventList.clear();
                for (int i = this.timePassIndex; i < this.timePassIndex + 10; ++i) {
                    try {
                        String tempString = jsonObject.get(i).getAsJsonObject().get("event").getAsString() + ",";
                        final Long test = (jsonObject.get(i).getAsJsonObject().get("timestamp").getAsLong() - Instant.now().getEpochSecond() * 1000L) / 60000L;
                        if (test < 0L) {
                            ++this.timePassIndex;
                            return;
                        }
                        tempString += Long.toString(test);
                        this.eventList.add(tempString);
                    }
                    catch (Exception ex) {}
                }
                this.time.reset();
            }
            catch (Exception ex2) {}
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Post egoe) {
        if (EventList.mc.thePlayer == null) {
            return;
        }
        if (egoe.type == RenderGameOverlayEvent.ElementType.CHAT && this.toggled) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
            final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
            float indexTing = pos;
            for (final String event : this.eventList) {
                final String[] test = event.split(",");
                if (posX < sr.getScaledWidth() / 2) {
                    fr.drawStringWithShadow(test[0], posX, indexTing + fr.FONT_HEIGHT, this.getColor(test[0]));
                    fr.drawStringWithShadow(" in ", posX + fr.getStringWidth(test[0]), indexTing + fr.FONT_HEIGHT, 16777215);
                    fr.drawStringWithShadow(test[1] + "m", posX + fr.getStringWidth(test[0] + " in "), indexTing + fr.FONT_HEIGHT, 5384401);
                }
                else {
                    fr.drawStringWithShadow(test[0], 88.0f + posX - fr.getStringWidth(test[0] + " in " + test[1] + "m"), indexTing + fr.FONT_HEIGHT, this.getColor(test[0]));
                    fr.drawStringWithShadow(" in ", 88.0f + posX - fr.getStringWidth(" in " + test[1] + "m"), indexTing + fr.FONT_HEIGHT, 16777215);
                    fr.drawStringWithShadow(test[1] + "m", 88.0f + posX - fr.getStringWidth(test[1] + "m"), indexTing + fr.FONT_HEIGHT, 5384401);
                }
                indexTing += fr.FONT_HEIGHT;
            }
        }
    }
    
    public int getColor(final String event) {
        if (event.contains("Blockhead")) {
            return 16755200;
        }
        if (event.contains("Pizza")) {
            return 16733525;
        }
        if (event.contains("Beast")) {
            return 5635925;
        }
        if (event.contains("Robbery")) {
            return 16755200;
        }
        if (event.contains("Spire")) {
            return 11141290;
        }
        if (event.contains("Squads")) {
            return 5636095;
        }
        if (event.contains("Team Deathmatch")) {
            return 11141290;
        }
        if (event.contains("Raffle")) {
            return 16755200;
        }
        if (event.contains("Rage Pit")) {
            return 16733525;
        }
        if (event.contains("2x Rewards")) {
            return 43520;
        }
        if (event.contains("Giant Cake")) {
            return 16733695;
        }
        if (event.contains("KOTL")) {
            return 5635925;
        }
        if (event.contains("Dragon Egg")) {
            return 11141290;
        }
        if (event.contains("Auction")) {
            return 16777045;
        }
        if (event.contains("Quick Maths")) {
            return 11141290;
        }
        if (event.contains("KOTH")) {
            return 5636095;
        }
        if (event.contains("Care Package")) {
            return 16755200;
        }
        if (event.contains("All bounty")) {
            return 16755200;
        }
        return 16777215;
    }
    
    @Override
    public String getDescription() {
        this.timePassIndex = 0;
        return this.description;
    }
}
