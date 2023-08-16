//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.thread;

import net.minecraft.client.*;
import java.net.*;
import me.omi.*;
import java.io.*;

public class AuthFlowRunnable implements Runnable
{
    String id;
    
    public AuthFlowRunnable(final String hwid) {
        this.id = hwid;
    }
    
    @Override
    public void run() {
        try {
            final String ign = Minecraft.getMinecraft().getSession().getUsername();
            final URL url = new URL("http://178.238.8.84/auth.php?hwid=" + this.id + "&ign=" + ign);
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
            if (!response.toString().contains(this.id)) {
                System.out.println("Auth Failed with HWID: " + this.id);
                OmiClient.instance.onDestruct();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Auth Failed with HWID: " + this.id);
            OmiClient.instance.onDestruct();
        }
    }
}
