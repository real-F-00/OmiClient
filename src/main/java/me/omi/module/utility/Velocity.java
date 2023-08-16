//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.utility;

import me.omi.utils.*;
import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.client.entity.*;

public class Velocity extends Module
{
    private TimeHelper time;
    
    public Velocity() {
        super("Anti Punch", "Reduces knockback on punch", Category.UTILITY);
        this.time = new TimeHelper();
        OmiClient.instance.settingsManager.rSetting(new Setting("Vertical", (Module)this, 100.0, 0.0, 100.0, true));
    }
    
    @SubscribeEvent
    public void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
        if (event.message.getUnformattedText().contains(Velocity.mc.thePlayer.getName() + " by") && event.message.getUnformattedText().contains("PUNCH")) {
            this.time.reset();
            Velocity.mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "PUNCH enchant canceled!"));
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent e) {
        if (Velocity.mc.thePlayer == null) {
            return;
        }
        final float vertical = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Vertical").getValDouble();
        if (!this.time.hasReached(500L) && Velocity.mc.thePlayer.motionY > 2.0) {
            final EntityPlayerSP thePlayer = Velocity.mc.thePlayer;
            thePlayer.motionY *= vertical / 100.0f;
        }
    }
}
