//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import java.net.*;
import me.omi.*;
import me.omi.module.*;
import java.io.*;
import java.util.*;

public class CheckEventsRunnable implements Runnable
{
    @Override
    public void run() {
        try {
            final URL url = new URL("https://events.mcpqndq.dev/");
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
            for (final Module tempModule : OmiClient.instance.moduleManager.modules) {
                if (tempModule.name == "Event List") {
                    tempModule.argTest = response.toString();
                    tempModule.getDescription();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
