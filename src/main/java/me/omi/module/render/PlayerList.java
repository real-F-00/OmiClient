//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module.render;

import me.omi.utils.*;
import net.minecraft.entity.player.*;
import java.text.*;
import me.omi.module.*;
import me.omi.*;
import me.omi.settings.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import com.google.gson.*;
import java.io.*;
import org.apache.commons.io.*;

public class PlayerList extends Module
{
    private TimeHelper time;
    Map<EntityPlayer, String> playerListMap;
    private static DecimalFormat df;
    public ArrayList<String> kosList;
    public ArrayList<String> friendList;
    public ArrayList<String> prevList;
    List<String> DARK_ENCHANTS;
    JsonArray kosJsonObject;
    JsonArray friendsJsonObject;
    int i;
    Boolean flag;
    private Boolean simpleName;
    
    public PlayerList() {
        super("Player List", "Best Player List.", Category.RENDER);
        this.time = new TimeHelper(1);
        this.playerListMap = new HashMap<EntityPlayer, String>();
        this.kosList = new ArrayList<String>();
        this.friendList = new ArrayList<String>();
        this.prevList = new ArrayList<String>();
        this.DARK_ENCHANTS = Arrays.asList("Venom", "Misery", "Spite", "Needless Suffering", "Grim Reaper", "Nostalgia", "Hedge Fund", "Heartripper", "Sanguisuge", "Lycanthropy", "Mind Assault", "Golden Handcuffs");
        this.i = 0;
        this.flag = false;
        this.simpleName = true;
        OmiClient.instance.settingsManager.rSetting(new Setting("Y Pos", (Module)this, 50.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("X Pos", (Module)this, 80.0, 0.0, 1200.0, true, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Detect Dark People", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Sound for darks", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Detect Regular People", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Sound for regs", (Module)this, true));
        OmiClient.instance.settingsManager.rSetting(new Setting("Simple Name Style", (Module)this, true));
        this.hud = true;
        this.loadKosList();
        this.loadFriendsList();
    }
    
    @SubscribeEvent
    public void PlayerTickEvent(final TickEvent.PlayerTickEvent e) {
        if (e.phase == TickEvent.Phase.START) {
            if (PlayerList.mc.thePlayer == null) {
                return;
            }
            if (OmiClient.instance.isInPit && this.time.hasReached(100L)) {
                this.time.reset();
                this.simpleName = OmiClient.instance.settingsManager.getSettingByName((Module)this, "Simple Name Style").getValBoolean();
                this.playerListMap.clear();
                for (final EntityPlayer entityThing : PlayerList.mc.theWorld.playerEntities) {
                    if (entityThing == null) {
                        continue;
                    }
                    if (entityThing.getName() == null) {
                        continue;
                    }
                    Boolean flag = false;
                    String test = "";
                    String characteristics = "";
                    if (entityThing.inventory.armorInventory[1] != null && entityThing.inventory.armorInventory[1].getItem() == Item.getItemById(300)) {
                        if (entityThing.inventory.armorInventory[1].getDisplayName() == null) {
                            continue;
                        }
                        test = entityThing.inventory.armorInventory[1].getDisplayName();
                        if (OmiClient.instance.settingsManager.getSettingByName((Module)this, "Detect Regular People").getValBoolean() && test.contains("Rage")) {
                            String type = "";
                            final String nbtTing = entityThing.inventory.armorInventory[1].getTagCompound().toString();
                            if (nbtTing.contains("Regularity")) {
                                type = EnumChatFormatting.DARK_RED + "Regularity";
                                characteristics = characteristics + EnumChatFormatting.DARK_RED + "Regularities";
                                flag = true;
                                if (OmiClient.instance.settingsManager.getSettingByName((Module)this, "Sound for regs").getValBoolean() && this.prevList.indexOf(entityThing.getName()) == -1) {
                                    PlayerList.mc.thePlayer.playSound("note.pling", 2.0f, 1.0f);
                                }
                            }
                        }
                        if (OmiClient.instance.settingsManager.getSettingByName((Module)this, "Detect Dark People").getValBoolean() && (test.contains("Evil") || test.contains("I Dark"))) {
                            String type = "";
                            final String nbtTing = entityThing.inventory.armorInventory[1].getTagCompound().toString();
                            if (nbtTing.contains("Tier I ")) {
                                type = "Plain Darks";
                                characteristics += type;
                                flag = true;
                            }
                            else {
                                for (final String tmpTing : this.DARK_ENCHANTS) {
                                    if (nbtTing.contains(tmpTing)) {
                                        type = EnumChatFormatting.DARK_PURPLE + tmpTing;
                                        flag = true;
                                        if (OmiClient.instance.settingsManager.getSettingByName((Module)this, "Sound for darks").getValBoolean() && this.prevList.indexOf(entityThing.getName()) == -1) {
                                            PlayerList.mc.thePlayer.playSound("note.pling", 2.0f, 1.0f);
                                            break;
                                        }
                                        break;
                                    }
                                }
                                characteristics += type;
                            }
                        }
                    }
                    if (this.kosList.indexOf(entityThing.getName()) != -1) {
                        if (characteristics.length() < 1) {
                            characteristics = characteristics + EnumChatFormatting.RED + "KOS";
                        }
                        if (this.prevList.indexOf(entityThing.getName()) == -1) {
                            PlayerList.mc.thePlayer.playSound("note.pling", 2.0f, 1.0f);
                        }
                    }
                    if (this.friendList.indexOf(entityThing.getName()) != -1 && characteristics.length() < 1) {
                        characteristics = characteristics + EnumChatFormatting.GREEN + "Friend List";
                    }
                    if (characteristics.length() <= 1) {
                        continue;
                    }
                    this.playerListMap.put(entityThing, characteristics);
                }
                this.prevList.clear();
                for (final Map.Entry<EntityPlayer, String> key : this.playerListMap.entrySet()) {
                    this.prevList.add(key.getKey().getName());
                }
            }
        }
    }
    
    @SubscribeEvent
    public void RenderGameOverlayEvent(final RenderGameOverlayEvent.Post egoe) {
        if (PlayerList.mc.thePlayer == null) {
            return;
        }
        if (egoe.type == RenderGameOverlayEvent.ElementType.CHAT) {
            final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            final float pos = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "Y Pos").getValDouble();
            final float posX = (float)OmiClient.instance.settingsManager.getSettingByName((Module)this, "X Pos").getValDouble();
            int indexTing = (int)pos;
            if (!this.flag) {
                fr.drawStringWithShadow("Player List:", posX, (float)indexTing, 16777215);
                ++indexTing;
                for (final Map.Entry<EntityPlayer, String> key : this.playerListMap.entrySet()) {
                    try {
                        String name = "";
                        if (!this.simpleName) {
                            name = key.getKey().getDisplayName().getFormattedText();
                        }
                        else {
                            name = key.getKey().getName();
                        }
                        final Float dist = key.getKey().getDistanceToEntity((Entity)PlayerList.mc.thePlayer);
                        String distance = "";
                        if (dist != null) {
                            distance = PlayerList.df.format(dist);
                        }
                        distance = this.getDistColor(dist) + distance;
                        if (key.getKey().posY >= OmiClient.instance.spawnY) {
                            distance = EnumChatFormatting.GREEN + "SPAWN";
                        }
                        fr.drawStringWithShadow(name + " in " + key.getValue() + EnumChatFormatting.BLACK + " - " + distance, posX, (float)(indexTing + fr.FONT_HEIGHT), 16777215);
                        indexTing += fr.FONT_HEIGHT;
                    }
                    catch (Exception ex) {
                        System.out.println("[OMI CLIENT] Something broke.");
                        ex.printStackTrace();
                    }
                }
            }
            else {
                fr.drawStringWithShadow("", 5.0f, 5.0f, 16777215);
            }
        }
    }
    
    private String getDistColor(final Float dist) {
        if (dist < 10.0f) {
            return EnumChatFormatting.RED + "";
        }
        if (dist >= 10.0f && dist < 20.0f) {
            return EnumChatFormatting.GOLD + "";
        }
        if (dist >= 20.0f && dist < 30.0f) {
            return EnumChatFormatting.DARK_GREEN + "";
        }
        return EnumChatFormatting.GREEN + "";
    }
    
    @Override
    public void onDisable() {
        this.flag = true;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.flag = false;
    }
    
    public int getColor(final String type) {
        if (type.contains("Regularities")) {
            return 10695967;
        }
        if (this.DARK_ENCHANTS.indexOf(type) != -1) {
            return 11872232;
        }
        if (type.contains("KOS")) {
            return 12864049;
        }
        if (type.contains("Friend")) {
            return 5881131;
        }
        return 16777215;
    }
    
    public void loadKosList() {
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "omiclient");
        final File kosListJson = new File(dir, "kos.json");
        try {
            if (!kosListJson.exists()) {
                kosListJson.createNewFile();
                final PrintWriter pw = new PrintWriter(kosListJson);
                pw.println("{}");
                pw.close();
                this.kosJsonObject = new JsonArray();
            }
            else {
                final BufferedReader reader = new BufferedReader(new FileReader(kosListJson));
                final String line = reader.readLine();
                reader.close();
                if (line.contains("{}") && line.length() < 10) {
                    this.kosJsonObject = new JsonArray();
                }
                else {
                    this.kosJsonObject = new JsonParser().parse(line).getAsJsonArray();
                }
                for (final JsonElement tempVal : this.kosJsonObject) {
                    this.kosList.add(tempVal.getAsJsonObject().get("name").getAsString());
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addToKos(final String ign) {
        this.kosList.add(ign);
        final JsonObject innerObject = new JsonObject();
        innerObject.addProperty("name", ign);
        this.kosJsonObject.add((JsonElement)innerObject);
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "omiclient");
        final File kosListJson = new File(dir, "kos.json");
        try {
            if (!kosListJson.exists()) {
                kosListJson.createNewFile();
                try {
                    final PrintWriter pw = new PrintWriter(kosListJson);
                    pw.println(this.kosJsonObject.toString());
                    pw.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    final PrintWriter pw = new PrintWriter(kosListJson);
                    pw.println(this.kosJsonObject.toString());
                    pw.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int removeFromKos(final String string) {
        final Boolean flag = false;
        if (this.kosList.indexOf(string) != -1) {
            this.kosList.remove(this.kosList.indexOf(string));
            final JsonArray tempJsonObject = new JsonArray();
            for (final String ignInKos : this.kosList) {
                final JsonObject innerObject = new JsonObject();
                innerObject.addProperty("name", ignInKos);
                tempJsonObject.add((JsonElement)innerObject);
            }
            this.kosJsonObject = tempJsonObject;
            final File dir = new File(Minecraft.getMinecraft().mcDataDir, "omiclient");
            final File kosListJson = new File(dir, "kos.json");
            try {
                if (!kosListJson.exists()) {
                    kosListJson.createNewFile();
                    FileUtils.writeStringToFile(kosListJson, this.kosJsonObject.toString(), "utf-8");
                }
                else {
                    FileUtils.writeStringToFile(kosListJson, this.kosJsonObject.toString(), "utf-8");
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return 0;
        }
        return -1;
    }
    
    public void loadFriendsList() {
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "omiclient");
        final File kosListJson = new File(dir, "friends.json");
        try {
            if (!kosListJson.exists()) {
                kosListJson.createNewFile();
                final PrintWriter pw = new PrintWriter(kosListJson);
                pw.println("{}");
                pw.close();
                this.friendsJsonObject = new JsonArray();
            }
            else {
                final BufferedReader reader = new BufferedReader(new FileReader(kosListJson));
                final String line = reader.readLine();
                reader.close();
                if (line.contains("{}") && line.length() < 10) {
                    this.friendsJsonObject = new JsonArray();
                }
                else {
                    this.friendsJsonObject = new JsonParser().parse(line).getAsJsonArray();
                }
                for (final JsonElement tempVal : this.friendsJsonObject) {
                    this.friendList.add(tempVal.getAsJsonObject().get("name").getAsString());
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void addToFriends(final String ign) {
        this.friendList.add(ign);
        final JsonObject innerObject = new JsonObject();
        innerObject.addProperty("name", ign);
        this.friendsJsonObject.add((JsonElement)innerObject);
        final File dir = new File(Minecraft.getMinecraft().mcDataDir, "omiclient");
        final File kosListJson = new File(dir, "friends.json");
        try {
            if (!kosListJson.exists()) {
                kosListJson.createNewFile();
                try {
                    final PrintWriter pw = new PrintWriter(kosListJson);
                    pw.println(this.friendsJsonObject.toString());
                    pw.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    final PrintWriter pw = new PrintWriter(kosListJson);
                    pw.println(this.friendsJsonObject.toString());
                    pw.close();
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int removeFromFriends(final String string) {
        final Boolean flag = false;
        if (this.friendList.indexOf(string) != -1) {
            this.friendList.remove(this.friendList.indexOf(string));
            final JsonArray tempJsonObject = new JsonArray();
            for (final String ignInKos : this.friendList) {
                final JsonObject innerObject = new JsonObject();
                innerObject.addProperty("name", ignInKos);
                tempJsonObject.add((JsonElement)innerObject);
            }
            this.friendsJsonObject = tempJsonObject;
            final File dir = new File(Minecraft.getMinecraft().mcDataDir, "omiclient");
            final File kosListJson = new File(dir, "friends.json");
            try {
                if (!kosListJson.exists()) {
                    kosListJson.createNewFile();
                    try {
                        final PrintWriter pw = new PrintWriter(kosListJson);
                        pw.println(this.friendsJsonObject.toString());
                        pw.close();
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        final PrintWriter pw = new PrintWriter(kosListJson);
                        pw.println(this.friendsJsonObject.toString());
                        pw.close();
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return 0;
        }
        return -1;
    }
    
    static {
        PlayerList.df = new DecimalFormat("0.00");
    }
}
