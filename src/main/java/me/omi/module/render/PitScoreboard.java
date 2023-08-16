//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.gui.*;
import me.omi.utils.*;
import me.omi.module.*;
import me.omi.thread.*;
import java.util.*;
import java.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import me.omi.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.event.world.*;
import net.minecraft.client.gui.*;

public class PitScoreboard extends Module
{
    public CustomOverlay overlay;
    Double[] GoldReq;
    public Double currentGoldReq;
    public int prestige;
    Boolean flag;
    TimeHelper time;
    DecimalFormat df;
    int currentKills;
    int currentAsssists;
    float streakXP;
    float streakGold;
    int secondCounter;
    int status;
    TimeHelper streakTime;
    
    public PitScoreboard() {
        super("Pit Scoreboard", "New pit scoreboard", Category.RENDER);
        this.GoldReq = new Double[] { 10000.0, 22000.0, 24000.0, 26000.0, 28000.0, 30000.0, 70000.0, 80000.0, 100000.0, 120000.0, 160000.0, 200000.0, 240000.0, 280000.0, 320000.0, 360000.0, 400000.0, 480000.0, 560000.0, 800000.0, 900000.0, 1000000.0, 1200000.0, 1400000.0, 1600000.0, 1800000.0, 2400000.0, 2700000.0, 3000000.0, 6000000.0, 1.0E7, 1.212E7, 1.414E7, 1.616E7, 1.818E7, 0.0 };
        this.currentGoldReq = 0.0;
        this.prestige = 0;
        this.flag = false;
        this.time = new TimeHelper();
        this.currentKills = 0;
        this.currentAsssists = 0;
        this.streakXP = 0.0f;
        this.streakGold = 0.0f;
        this.secondCounter = 0;
        this.status = 0;
        this.streakTime = new TimeHelper();
        this.overlay = new CustomOverlay(PitScoreboard.mc);
        final CheckPrestige prestRunnable = new CheckPrestige();
        final Thread newThreadRunnable = new Thread((Runnable)prestRunnable);
        newThreadRunnable.start();
        this.flag = true;
        this.df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("es-ES")));
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent event) {
        if (!(PitScoreboard.mc.ingameGUI instanceof CustomOverlay)) {
            PitScoreboard.mc.ingameGUI = (GuiIngame)this.overlay;
        }
    }
    
    public void setPrestige(final int prestigeIn) {
        this.prestige = prestigeIn;
        this.updateGui();
    }
    
    public void updateGui() {
        this.overlay.updateScoreboard(this.GoldReq[this.prestige], this.currentGoldReq, this.currentKills, this.currentAsssists, this.streakXP, this.streakGold, this.status);
    }
    
    public void setCurrentGoldReq(final Double goldReqIn) {
        this.currentGoldReq = goldReqIn;
        this.updateGui();
    }
    
    @SubscribeEvent
    public void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
        if (OmiClient.instance.isInPit) {
            final String msg = event.message.getUnformattedText();
            try {
                if (((msg.contains("KILL!") || msg.contains("PENTA KILL ")) && msg.contains("7on ")) || msg.contains("ASSIST!")) {
                    if (this.status != 1 && OmiClient.instance.spawnY > PitScoreboard.mc.thePlayer.posY) {
                        this.resetStreak();
                        this.status = 1;
                    }
                    if (msg.contains("KILL!") || msg.contains("PENTA KILL ")) {
                        ++this.currentKills;
                    }
                    if (msg.contains("ASSIST!")) {
                        ++this.currentAsssists;
                    }
                    final String xpString = msg.split("\\+")[1];
                    final String goldString = msg.split("\\+")[2];
                    Float tempGold;
                    if (goldString.contains("9(")) {
                        tempGold = this.df.parse(goldString.split("g")[0].substring(2, goldString.split("g")[0].length())).floatValue();
                    }
                    else {
                        tempGold = this.df.parse(goldString.substring(2, goldString.length() - 1)).floatValue();
                    }
                    final Float tempXP = this.df.parse(xpString.substring(0, xpString.length() - 5)).floatValue();
                    this.streakXP += tempXP;
                    this.streakGold += tempGold;
                    this.currentGoldReq += (Float)tempGold;
                }
                if (msg.contains("SELF-CHECKOUT!")) {
                    if (msg.contains("3,000g")) {
                        System.out.println("Detected self check for 3000");
                        this.currentGoldReq += 3000.0;
                        this.streakGold += 3000.0f;
                    }
                    if (msg.contains("5,000g")) {
                        System.out.println("Detected self check for 5000");
                        this.currentGoldReq += 5000.0;
                        this.streakGold += 5000.0f;
                    }
                    if (msg.contains("2,000g")) {
                        System.out.println("Detected self check for 2000");
                        this.currentGoldReq += 2000.0;
                        this.streakGold += 2000.0f;
                    }
                }
                if (msg.contains("DEATH!") && this.status == 1) {
                    this.status = 2;
                }
                if (msg.contains("HIGHLANDER! Earned")) {
                    final String result = msg.substring(msg.indexOf("+") + 1, msg.indexOf("g "));
                    final Float change = this.df.parse(result).floatValue();
                    this.currentGoldReq += (Float)change;
                    this.streakGold += change;
                }
                if (msg.contains("unlocked prestige") && msg.contains(PitScoreboard.mc.thePlayer.getName())) {
                    this.currentGoldReq = 0.0;
                    ++this.prestige;
                }
                this.updateGui();
            }
            catch (Exception ex) {
                System.out.println("DebugMsg: " + msg);
                ex.printStackTrace();
            }
        }
    }
    
    private void resetStreak() {
        this.currentKills = 0;
        this.currentAsssists = 0;
        this.streakXP = 0.0f;
        this.streakGold = 0.0f;
        this.streakTime.reset();
        this.updateTime();
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && this.status == 1) {
            ++this.secondCounter;
            if (this.secondCounter == 20) {
                this.secondCounter = 0;
                this.updateTime();
            }
        }
    }
    
    public void updateTime() {
        final long timeInMilliSeconds = this.streakTime.getDifference();
        final long seconds = timeInMilliSeconds / 1000L;
        final long minutes = seconds / 60L;
        final long hours = minutes / 60L;
        if (hours % 24L > 0L) {
            this.overlay.updateTime(hours % 24L + "h " + minutes % 60L + "m " + seconds % 60L + "s");
        }
        else if (minutes % 60L > 0L) {
            this.overlay.updateTime(minutes % 60L + "m " + seconds % 60L + "s");
        }
        else {
            this.overlay.updateTime(seconds % 60L + "s");
        }
    }
    
    @SubscribeEvent
    public void WorldEvent(final WorldEvent.Load e) {
        if (this.status == 1) {
            this.status = 2;
        }
        if (this.time.hasReached(60000L)) {
            this.time.reset();
            final CheckPrestige prestRunnable = new CheckPrestige();
            final Thread newThreadRunnable = new Thread((Runnable)prestRunnable);
            newThreadRunnable.start();
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (!this.flag) {
            this.flag = true;
            final CheckPrestige prestRunnable = new CheckPrestige();
            final Thread newThreadRunnable = new Thread((Runnable)prestRunnable);
            newThreadRunnable.start();
        }
    }
}
