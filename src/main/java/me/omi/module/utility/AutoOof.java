//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.utility;

import me.omi.module.*;
import net.minecraftforge.client.event.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoOof extends Module
{
    public AutoOof() {
        super("Auto Oof", "Automatically Oofs on Uber 400", Category.UTILITY);
    }
    
    @SubscribeEvent
    public void ClientChatReceivedEvent(final ClientChatReceivedEvent event) {
        if (event.message.getUnformattedText().contains("You cannot heal") && !AutoOof.mc.thePlayer.inventory.hasItem(Item.getItemById(288))) {
            AutoOof.mc.thePlayer.sendChatMessage("/oof");
        }
    }
}
