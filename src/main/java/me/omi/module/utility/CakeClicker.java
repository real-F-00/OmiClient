//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.utility;

import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import net.minecraft.client.settings.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.concurrent.*;

public class CakeClicker extends Module
{
    private long lastClick;
    private long hold;
    private double speed;
    private double holdLength;
    private double min;
    private double max;
    
    public CakeClicker() {
        super("Cake Clicker", "Spams the fuck out of the right click button.", Category.UTILITY);
        OmiClient.instance.settingsManager.rSetting(new Setting("Min CPS", (Module)this, 50.0, 30.0, 50.0, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Max CPS", (Module)this, 55.0, 30.0, 60.0, true));
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.RenderTickEvent e) {
        if (OmiClient.instance.destructed) {
            return;
        }
        if (Mouse.isButtonDown(1)) {
            if (System.currentTimeMillis() - this.lastClick > this.speed * 1000.0) {
                this.lastClick = System.currentTimeMillis();
                if (this.hold < this.lastClick) {
                    this.hold = this.lastClick;
                }
                final int key = CakeClicker.mc.gameSettings.keyBindUseItem.getKeyCode();
                KeyBinding.setKeyBindState(key, true);
                KeyBinding.onTick(key);
                this.updateVals();
            }
            else if (System.currentTimeMillis() - this.hold > this.holdLength * 1000.0) {
                KeyBinding.setKeyBindState(CakeClicker.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                this.updateVals();
            }
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.updateVals();
    }
    
    private void updateVals() {
        this.min = OmiClient.instance.settingsManager.getSettingByName((Module)this, "Min CPS").getValDouble();
        this.max = OmiClient.instance.settingsManager.getSettingByName((Module)this, "Max CPS").getValDouble();
        if (this.min >= this.max) {
            this.max = this.min + 1.0;
        }
        this.speed = 1.0 / ThreadLocalRandom.current().nextDouble(this.min - 0.2, this.max);
        this.holdLength = this.speed / ThreadLocalRandom.current().nextDouble(this.min, this.max);
    }
}
