//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.module.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class Nametags extends Module
{
    Double maxDistance;
    Double scale;
    
    public Nametags() {
        super("Nametags", "Nametags", Category.RENDER);
        this.maxDistance = 200.0;
        this.scale = 0.8;
    }
    
    @SubscribeEvent
    public void onRender(final RenderPlayerEvent.Post event) {
        if (!this.toggled) {
            return;
        }
        if (event.entityPlayer == null | !(event.entityPlayer instanceof EntityPlayer)) {
            return;
        }
        if (event.entityPlayer.getName().equals(Minecraft.getMinecraft().thePlayer.getName())) {
            return;
        }
        if (!this.renderName(event.entityPlayer.getName())) {
            return;
        }
        if (!this.doRenderDistance((Entity)event.entityPlayer)) {
            return;
        }
        final double x = event.entityPlayer.lastTickPosX + (event.entityPlayer.posX - event.entityPlayer.lastTickPosX) * event.partialRenderTick - event.renderer.getRenderManager().viewerPosX;
        final double y = event.entityPlayer.lastTickPosY + (event.entityPlayer.posY - event.entityPlayer.lastTickPosY) * event.partialRenderTick - event.renderer.getRenderManager().viewerPosY;
        final double z = event.entityPlayer.lastTickPosZ + (event.entityPlayer.posZ - event.entityPlayer.lastTickPosZ) * event.partialRenderTick - event.renderer.getRenderManager().viewerPosZ;
        this.renderNametag(event.entityPlayer, (float)x, (float)y, (float)z);
    }
    
    @SubscribeEvent
    public void onFogRender(final EntityViewRenderEvent.FogDensity event) {
        if (!this.toggled) {
            return;
        }
        event.density = 0.0f;
        event.setCanceled(true);
    }
    
    private void renderNametag(final EntityPlayer player, final float x, float y, final float z) {
        y += (float)(1.55 + (player.isSneaking() ? 0.5 : 0.7));
        String str = "";
        if (player.inventory.armorInventory[1] != null && player.inventory.armorInventory[1].getTagCompound().getCompoundTag("display") != null && player.inventory.armorInventory[1].getTagCompound().getCompoundTag("display").getTagList("Lore", 8) != null) {
            for (int i = 2; !player.inventory.armorInventory[1].getTagCompound().getCompoundTag("display").getTagList("Lore", 8).getStringTagAt(i).isEmpty() || !player.inventory.armorInventory[1].getTagCompound().getCompoundTag("display").getTagList("Lore", 8).getStringTagAt(i + 1).isEmpty(); ++i) {
                final String tempStr = player.inventory.armorInventory[1].getTagCompound().getCompoundTag("display").getTagList("Lore", 8).getStringTagAt(i);
                if (tempStr.startsWith(EnumChatFormatting.LIGHT_PURPLE.toString()) || tempStr.startsWith(EnumChatFormatting.BLUE.toString())) {
                    if (!str.isEmpty()) {
                        str += ", ";
                    }
                    if (tempStr.contains("Used in the")) {
                        return;
                    }
                    str += tempStr;
                }
            }
        }
        if (str.isEmpty()) {
            return;
        }
        final FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        final double mult = this.scale;
        final double size = this.getSize(player) / 10.0f * 6.0f * 1.5 * mult;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glTranslatef(x, y, z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScaled(-0.01666666753590107 * size, -0.01666666753590107 * size, 0.01666666753590107 * size);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        final int j = -10;
        final int k = fontrenderer.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)(-k - 1), (double)(-1 + j), 0.0).color(0.0f, 0.0f, 0.0f, 0.15f).endVertex();
        worldrenderer.pos((double)(-k - 1), (double)(8 + j), 0.0).color(0.0f, 0.0f, 0.0f, 0.15f).endVertex();
        worldrenderer.pos((double)(k + 1), (double)(8 + j), 0.0).color(0.0f, 0.0f, 0.0f, 0.15f).endVertex();
        worldrenderer.pos((double)(k + 1), (double)(-1 + j), 0.0).color(0.0f, 0.0f, 0.0f, 0.15f).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, j, 553648127);
        GlStateManager.depthMask(true);
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, j, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    private float getSize(final EntityPlayer player) {
        return (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)player) / 4.0f <= 2.0f) ? 2.0f : (Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)player) / 4.0f);
    }
    
    private boolean doRenderDistance(final Entity player) {
        return Nametags.mc.thePlayer.getDistanceToEntity(player) <= this.maxDistance;
    }
    
    private boolean renderName(final String player) {
        return true;
    }
}
