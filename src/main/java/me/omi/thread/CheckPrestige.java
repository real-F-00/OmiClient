//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import net.minecraft.client.*;
import java.net.*;
import me.omi.module.render.*;
import me.omi.*;
import java.io.*;
import com.google.gson.*;

public class CheckPrestige implements Runnable
{
    int prestige;
    
    @Override
    public void run() {
        try {
            Thread.sleep(2000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            final URL url = new URL("https://api.hypixel.net/player?key=373a2956-abdb-4b7d-ae9f-fcb3801b1101&name=" + Minecraft.getMinecraft().getSession().getUsername());
            final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            final InputStream responseStream = connection.getInputStream();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(responseStream));
            final StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            try {
                final JsonObject jsonObject = new JsonParser().parse(response.toString()).getAsJsonObject();
                final JsonObject jsonObjectInner = jsonObject.get("player").getAsJsonObject();
                final JsonObject jsonObjectInner2 = jsonObjectInner.get("stats").getAsJsonObject();
                final JsonObject jsonObjectInner3 = jsonObjectInner2.get("Pit").getAsJsonObject();
                final JsonObject jsonObjectInner4 = jsonObjectInner3.get("profile").getAsJsonObject();
                final JsonObject jsonObjectInner5 = jsonObjectInner.get("achievements").getAsJsonObject();
                this.prestige = jsonObjectInner5.get("pit_prestiges").getAsInt();
                System.out.println("Detected prestige " + this.prestige);
            }
            catch (Exception ex3) {
                this.prestige = 0;
                System.out.println("Could not find prestige so we taking it as 0");
            }
            Double goldReqDone = 0.0;
            try {
                final JsonObject jsonObject2 = new JsonParser().parse(response.toString()).getAsJsonObject();
                final JsonObject jsonObjectInner6 = jsonObject2.get("player").getAsJsonObject();
                final JsonObject jsonObjectInner7 = jsonObjectInner6.get("stats").getAsJsonObject();
                final JsonObject jsonObjectInner8 = jsonObjectInner7.get("Pit").getAsJsonObject();
                final JsonObject jsonObjectInner9 = jsonObjectInner8.get("profile").getAsJsonObject();
                goldReqDone = jsonObjectInner9.get("cash_during_prestige_" + this.prestige).getAsDouble();
                System.out.println("Gold req detected is " + goldReqDone);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Something broke");
            }
            ((PitScoreboard)OmiClient.instance.moduleManager.getModule("Pit Scoreboard")).setPrestige(this.prestige);
            if (((PitScoreboard)OmiClient.instance.moduleManager.getModule("Pit Scoreboard")).currentGoldReq < goldReqDone) {
                ((PitScoreboard)OmiClient.instance.moduleManager.getModule("Pit Scoreboard")).setCurrentGoldReq(goldReqDone);
            }
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }
}
