//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.clickgui.component;

import me.omi.module.*;
import me.omi.clickgui.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import me.omi.*;

public class DragFrame
{
    private boolean open;
    private int width;
    private int y;
    private int x;
    private int barHeight;
    public Module mod;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    
    public DragFrame(final Module moduleTing) {
        this.mod = moduleTing;
        this.width = 88;
        this.x = 5;
        this.y = 5;
        this.barHeight = 13;
        this.dragX = 0;
        this.open = false;
        this.isDragging = false;
        final int tY = this.barHeight;
    }
    
    public void setX(final int newX) {
        this.x = newX;
    }
    
    public void setY(final int newY) {
        this.y = newY;
    }
    
    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public void renderFrame(final FontRenderer fontRenderer) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, ClickGui.color);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        fontRenderer.drawStringWithShadow(this.mod.name, (float)((this.x + 2) * 2 + 5), (this.y + 2.5f) * 2.0f + 5.0f, -1);
        GL11.glPopMatrix();
    }
    
    public void refresh() {
        final int off = this.barHeight;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
            OmiClient.instance.settingsManager.getSettingByName(this.mod, "Y Pos").setValDouble((double)this.y);
            OmiClient.instance.settingsManager.getSettingByName(this.mod, "X Pos").setValDouble((double)this.x);
        }
    }
    
    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}
