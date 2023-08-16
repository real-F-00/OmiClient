//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi;

import me.omi.settings.*;
import me.omi.clickgui.*;
import me.omi.autosave.*;
import me.omi.utils.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import me.omi.command.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import me.omi.module.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.scoreboard.*;
import net.minecraftforge.event.world.*;



public class OmiClient
{
    public static OmiClient instance;
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGui clickGui;
    public SaveLoad saveLoad;
    private boolean JustJoined;
    private TimeHelper time;
    public boolean destructed;
    public boolean isInPit;
    public double spawnY;
    
    public OmiClient() {
        this.JustJoined = false;
        this.time = new TimeHelper();
        this.destructed = false;
        this.isInPit = false;
        this.spawnY = 0.0;
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.settingsManager = new SettingsManager();
        this.moduleManager = new ModuleManager();
        this.saveLoad = new SaveLoad();
        this.clickGui = new ClickGui();
        ClientCommandHandler.instance.registerCommand((ICommand)new Focus());
        ClientCommandHandler.instance.registerCommand((ICommand)new Denick());
        ClientCommandHandler.instance.registerCommand((ICommand)new kos());
        ClientCommandHandler.instance.registerCommand((ICommand)new friends());
        ClientCommandHandler.instance.registerCommand((ICommand)new lobbyFollow());
        ClientCommandHandler.instance.registerCommand((ICommand)new partybots());
    }
    
    @SubscribeEvent
    public void key(final InputEvent.KeyInputEvent e) {
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) {
            return;
        }
        try {
            if (Keyboard.isCreated() && Keyboard.getEventKeyState()) {
                final int keyCode = Keyboard.getEventKey();
                if (keyCode <= 0) {
                    return;
                }
                for (final Module m : this.moduleManager.modules) {
                    if (m.getKey() == keyCode && keyCode > 0) {
                        m.toggle();
                    }
                }
            }
        }
        catch (Exception q) {
            q.printStackTrace();
        }
    }
    
    public void onDestruct() {
        if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().thePlayer != null) {
            Minecraft.getMinecraft().thePlayer.closeScreen();
        }
        this.destructed = true;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        for (int k = 0; k < this.moduleManager.modules.size(); ++k) {
            final Module m = this.moduleManager.modules.get(k);
            MinecraftForge.EVENT_BUS.unregister((Object)m);
            this.moduleManager.getModuleList().remove(m);
        }
        this.moduleManager = null;
        this.clickGui = null;
        while (true) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] AUTH FAILED"));
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent e) {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return;
        }
        final int diffy = (int)this.time.getDifference();
        if (diffy > 800 && diffy < 1000 && this.JustJoined) {
            this.JustJoined = false;
            Scoreboard tempScoreboard = null;
            ScoreObjective tempScoreObjective = null;
            try {
                tempScoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
                tempScoreObjective = tempScoreboard.getObjectiveInDisplaySlot(1);
                if (tempScoreObjective.getName().contains("Pit")) {
                    this.isInPit = true;
                    if (this.spawnY == 0.0) {
                        for (final Entity tempEntity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                            if (tempEntity instanceof EntityVillager && tempEntity.posY - 1.0 != 65.0) {
                                this.spawnY = tempEntity.posY - 1.0;
                            }
                            if (this.spawnY != 0.0) {
                                System.out.println("[OMI CLIENT] Spawn Y detected at: " + this.spawnY);
                            }
                        }
                    }
                }
                else {
                    this.isInPit = false;
                }
            }
            catch (Exception ex) {
                System.out.println("[OMI CLIENT] This fucking broke im going to kill myself. " + ex);
            }
        }
    }
    
    @SubscribeEvent
    public void WorldEvent(final WorldEvent.Load e) {
        this.time.reset();
        this.JustJoined = true;
    }
}
