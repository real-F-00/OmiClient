//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module;

import net.minecraft.client.*;
import me.omi.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;

public class Module
{
    protected static Minecraft mc;
    public String name;
    public String description;
    private int key;
    private Category category;
    public boolean toggled;
    public boolean visible;
    public boolean hud;
    public String argTest;
    
    public Module(final String name, final String description, final Category category) {
        this.visible = true;
        this.hud = false;
        this.argTest = "NothingToSeeHerer";
        this.name = name;
        this.description = description;
        this.key = 0;
        this.category = category;
        this.toggled = false;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public void setKey(final int key) {
        this.key = key;
        if (OmiClient.instance.saveLoad != null) {
            OmiClient.instance.saveLoad.save();
        }
    }
    
    public boolean isToggled() {
        return this.toggled;
    }
    
    public void setToggled(final boolean toggled) {
        this.toggled = toggled;
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        if (OmiClient.instance.saveLoad != null) {
            OmiClient.instance.saveLoad.save();
        }
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            if (this.name != "ClickGUI") {
                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Module Activated: " + EnumChatFormatting.DARK_GREEN + this.name));
            }
            this.onEnable();
        }
        else {
            if (this.name != "ClickGUI") {
                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Module Disabled: " + EnumChatFormatting.RED + this.name));
            }
            this.onDisable();
        }
        if (OmiClient.instance.saveLoad != null) {
            OmiClient.instance.saveLoad.save();
        }
    }
    
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    static {
        Module.mc = Minecraft.getMinecraft();
    }
}
