//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.autosave;

import net.minecraft.client.*;
import me.omi.*;
import me.omi.module.*;
import me.omi.settings.*;
import java.util.*;
import java.io.*;
import com.google.gson.*;

public class SaveLoad
{
    private File dir;
    private File dataFile;
    public JsonArray configJson;
    
    public SaveLoad() {
        this.dir = new File(Minecraft.getMinecraft().mcDataDir, "omiclient");
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        this.dataFile = new File(this.dir, "config.json");
        if (!this.dataFile.exists()) {
            this.save();
        }
        this.load();
    }
    
    public void save() {
        this.configJson = new JsonArray();
        for (final Module mod : OmiClient.instance.moduleManager.modules) {
            final JsonObject innerModuleObject = new JsonObject();
            innerModuleObject.addProperty("name", mod.getName());
            innerModuleObject.addProperty("enabled", Boolean.toString(mod.isToggled()));
            innerModuleObject.addProperty("bind", Integer.toString(mod.getKey()));
            final JsonArray propertyArray = new JsonArray();
            if (OmiClient.instance.settingsManager.getSettingsByMod(mod) != null) {
                for (final Setting set : OmiClient.instance.settingsManager.getSettingsByMod(mod)) {
                    final JsonObject specificSetting = new JsonObject();
                    specificSetting.addProperty("name", set.getName());
                    if (set.isCheck()) {
                        specificSetting.addProperty("value", Boolean.valueOf(set.getValBoolean()));
                    }
                    if (set.isCombo()) {
                        specificSetting.addProperty("value", (Number)set.getValDouble());
                    }
                    if (set.isSlider()) {
                        specificSetting.addProperty("value", (Number)set.getValDouble());
                    }
                    propertyArray.add((JsonElement)specificSetting);
                }
            }
            innerModuleObject.add("properties", (JsonElement)propertyArray);
            this.configJson.add((JsonElement)innerModuleObject);
        }
        try {
            final PrintWriter pw = new PrintWriter(this.dataFile);
            pw.println(this.configJson.toString());
            pw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void load() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
            final String line = reader.readLine();
            reader.close();
            if (line.contains("{}") && line.length() < 10) {
                this.configJson = new JsonArray();
            }
            else {
                this.configJson = new JsonParser().parse(line).getAsJsonArray();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        for (final JsonElement tempObject : this.configJson) {
            if (OmiClient.instance.moduleManager.getModule(tempObject.getAsJsonObject().get("name").getAsString()) != null) {
                final Module tempModule = OmiClient.instance.moduleManager.getModule(tempObject.getAsJsonObject().get("name").getAsString());
                tempModule.setToggled(Boolean.parseBoolean(tempObject.getAsJsonObject().get("enabled").getAsString()));
                tempModule.setKey(Integer.parseInt(tempObject.getAsJsonObject().get("bind").getAsString()));
                for (final JsonElement tempProperty : tempObject.getAsJsonObject().get("properties").getAsJsonArray()) {
                    final Setting set = OmiClient.instance.settingsManager.getSettingByName(tempModule, tempProperty.getAsJsonObject().get("name").getAsString());
                    if (set != null) {
                        if (set.isCheck()) {
                            set.setValBoolean(Boolean.parseBoolean(tempProperty.getAsJsonObject().get("value").getAsString()));
                        }
                        if (set.isCombo()) {
                            set.setValString(tempProperty.getAsJsonObject().get("value").getAsString());
                        }
                        if (!set.isSlider()) {
                            continue;
                        }
                        set.setValDouble(Double.parseDouble(tempProperty.getAsJsonObject().get("value").getAsString()));
                    }
                }
            }
        }
    }
}
