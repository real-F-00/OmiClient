//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import java.net.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.io.*;
import com.google.gson.*;

public class DenickRunnable implements Runnable
{
    String guyToDenick;
    
    public DenickRunnable(final String arg) {
        this.guyToDenick = arg;
    }
    
    @Override
    public void run() {
        if (this.guyToDenick != null) {
            for (final EntityPlayer entityThing : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (entityThing == null) {
                    continue;
                }
                if (entityThing.getName().contains(this.guyToDenick)) {
                    if (entityThing.inventory.armorInventory[1] != null && entityThing.inventory.armorInventory[1].getItem() == Item.getItemById(300)) {
                        final ItemStack leg = entityThing.inventory.armorInventory[1];
                        final NBTTagCompound info = leg.getTagCompound();
                        if (info.hasKey("ExtraAttributes")) {
                            try {
                                final int nonceTing = info.getCompoundTag("ExtraAttributes").getInteger("Nonce");
                                if (nonceTing != 0) {
                                    try {
                                        URL url = new URL("https://pitpanda.rocks/api/itemsearch/nonce" + nonceTing);
                                        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                                        connection.setRequestProperty("accept", "application/json");
                                        InputStream responseStream = connection.getInputStream();
                                        BufferedReader rd = new BufferedReader(new InputStreamReader(responseStream));
                                        StringBuilder response = new StringBuilder();
                                        String line;
                                        while ((line = rd.readLine()) != null) {
                                            response.append(line);
                                        }
                                        rd.close();
                                        System.out.println("Response obntained");
                                        if (response.length() > 100) {
                                            String uuid = "none";
                                            try {
                                                System.out.println(response.toString());
                                                final JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
                                                final JsonArray arrayTest = jsonObject.getAsJsonArray("items");
                                                uuid = arrayTest.get(0).getAsJsonObject().get("owner").getAsString();
                                            }
                                            catch (Exception err) {
                                                System.out.println(err);
                                            }
                                            if (uuid != "none") {
                                                System.out.println("Result is " + uuid);
                                                url = new URL("https://api.ashcon.app/mojang/v2/user/" + uuid);
                                                connection = (HttpURLConnection)url.openConnection();
                                                connection.setRequestProperty("accept", "application/json");
                                                responseStream = connection.getInputStream();
                                                rd = new BufferedReader(new InputStreamReader(responseStream));
                                                response = new StringBuilder();
                                                while ((line = rd.readLine()) != null) {
                                                    response.append(line);
                                                    response.append('\r');
                                                }
                                                rd.close();
                                                final JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
                                                final String username = jsonObject.get("username").getAsString();
                                                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Denicked Player! IGN is " + username.trim()));
                                                return;
                                            }
                                        }
                                    }
                                    catch (Exception ex) {
                                        System.out.println("[OMI CLIENT] We fucked up with the request.");
                                        System.out.println(ex);
                                    }
                                }
                            }
                            catch (Exception ex2) {
                                System.out.println("[OMI CLIENT] We fucked up.");
                            }
                        }
                    }
                    if (entityThing.inventory.getCurrentItem() != null && entityThing.inventory.getCurrentItem().getItem() == Item.getItemById(283)) {
                        final ItemStack sword = entityThing.inventory.getCurrentItem();
                        final NBTTagCompound info = sword.getTagCompound();
                        if (info.hasKey("ExtraAttributes")) {
                            try {
                                final int nonceTing = info.getCompoundTag("ExtraAttributes").getInteger("Nonce");
                                if (nonceTing != 0) {
                                    try {
                                        URL url = new URL("https://pitpanda.rocks/api/itemsearch/nonce" + nonceTing);
                                        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                                        connection.setRequestProperty("accept", "application/json");
                                        InputStream responseStream = connection.getInputStream();
                                        BufferedReader rd = new BufferedReader(new InputStreamReader(responseStream));
                                        StringBuilder response = new StringBuilder();
                                        String line;
                                        while ((line = rd.readLine()) != null) {
                                            response.append(line);
                                            response.append('\r');
                                        }
                                        rd.close();
                                        System.out.println("Response obntained");
                                        if (response.length() > 100) {
                                            String uuid = "none";
                                            try {
                                                System.out.println(response.toString());
                                                final JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
                                                final JsonArray arrayTest = jsonObject.getAsJsonArray("items");
                                                uuid = arrayTest.get(0).getAsJsonObject().get("owner").getAsString();
                                            }
                                            catch (Exception err) {
                                                System.out.println(err);
                                            }
                                            if (uuid != "none") {
                                                System.out.println("Result is " + uuid);
                                                url = new URL("https://api.ashcon.app/mojang/v2/user/" + uuid);
                                                connection = (HttpURLConnection)url.openConnection();
                                                connection.setRequestProperty("accept", "application/json");
                                                responseStream = connection.getInputStream();
                                                rd = new BufferedReader(new InputStreamReader(responseStream));
                                                response = new StringBuilder();
                                                while ((line = rd.readLine()) != null) {
                                                    response.append(line);
                                                    response.append('\r');
                                                }
                                                rd.close();
                                                final JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
                                                final String username = jsonObject.get("username").getAsString();
                                                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Denicked Player! IGN is " + username.trim()));
                                                return;
                                            }
                                        }
                                    }
                                    catch (Exception ex) {
                                        System.out.println("[OMI CLIENT] We fucked up with the request.");
                                    }
                                }
                            }
                            catch (Exception ex2) {
                                System.out.println("[OMI CLIENT] We fucked up.");
                            }
                        }
                    }
                    Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Failed to denick Player"));
                    break;
                }
            }
        }
        else {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + "[OMI CLIENT] Usage is /denick ign!"));
        }
    }
}
