//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.clickgui;

import net.minecraft.client.gui.*;
import me.omi.*;
import me.omi.module.*;
import java.util.*;
import me.omi.clickgui.component.*;
import java.io.*;

public class ClickGui extends GuiScreen
{
    public static ArrayList<Frame> frames;
    public static ArrayList<DragFrame> draggableFrames;
    public static int color;
    
    public ClickGui() {
        ClickGui.frames = new ArrayList<Frame>();
        ClickGui.draggableFrames = new ArrayList<DragFrame>();
        int frameX = 100;
        for (final Category category : Category.values()) {
            final Frame frame = new Frame(category);
            frame.setX(frameX);
            ClickGui.frames.add(frame);
            frameX += frame.getWidth() + 1;
        }
        for (final Module tempModule : OmiClient.instance.moduleManager.modules) {
            if (tempModule.hud) {
                final DragFrame tempDrag = new DragFrame(tempModule);
                final int moduleY = (int)OmiClient.instance.settingsManager.getSettingByName(tempModule, "Y Pos").getValDouble();
                final int moduleX = (int)OmiClient.instance.settingsManager.getSettingByName(tempModule, "X Pos").getValDouble();
                tempDrag.setX(moduleX);
                tempDrag.setY(moduleY);
                ClickGui.draggableFrames.add(tempDrag);
                frameX += tempDrag.getWidth() + 1;
            }
        }
    }
    
    public void initGui() {
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        for (final Frame frame : ClickGui.frames) {
            frame.renderFrame(this.fontRendererObj);
            frame.updatePosition(mouseX, mouseY);
            for (final Component comp : frame.getComponents()) {
                comp.updateComponent(mouseX, mouseY);
            }
        }
        for (final DragFrame frame2 : ClickGui.draggableFrames) {
            if (frame2.mod.toggled && frame2.mod.hud) {
                frame2.renderFrame(this.fontRendererObj);
                frame2.updatePosition(mouseX, mouseY);
            }
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final Frame frame : ClickGui.frames) {
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                frame.setDrag(true);
                frame.dragX = mouseX - frame.getX();
                frame.dragY = mouseY - frame.getY();
                return;
            }
            if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                frame.setOpen(!frame.isOpen());
            }
            if (!frame.isOpen() || frame.getComponents().isEmpty()) {
                continue;
            }
            for (final Component component : frame.getComponents()) {
                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        for (final DragFrame frame2 : ClickGui.draggableFrames) {
            if (frame2.isWithinHeader(mouseX, mouseY) && mouseButton == 0 && frame2.mod.isToggled()) {
                frame2.setDrag(true);
                frame2.dragX = mouseX - frame2.getX();
                frame2.dragY = mouseY - frame2.getY();
                return;
            }
            if (!frame2.isWithinHeader(mouseX, mouseY) || mouseButton != 1) {
                continue;
            }
            frame2.setOpen(!frame2.isOpen());
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.keyTyped(typedChar, keyCode);
                }
            }
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Frame frame : ClickGui.frames) {
            frame.setDrag(false);
        }
        for (final DragFrame frame2 : ClickGui.draggableFrames) {
            frame2.setDrag(false);
        }
        for (final Frame frame : ClickGui.frames) {
            if (frame.isOpen() && !frame.getComponents().isEmpty()) {
                for (final Component component : frame.getComponents()) {
                    component.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return true;
    }
    
    static {
        ClickGui.color = -1;
    }
}
