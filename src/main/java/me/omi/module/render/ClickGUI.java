//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.module.*;
import me.omi.*;
import net.minecraft.client.gui.*;

public class ClickGUI extends Module
{
    public ClickGUI() {
        super("ClickGUI", "Allows you to enable and disable modules", Category.RENDER);
        this.setKey(54);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        ClickGUI.mc.displayGuiScreen((GuiScreen)OmiClient.instance.clickGui);
        this.setToggled(false);
    }
}
