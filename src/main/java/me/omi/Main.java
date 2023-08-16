//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import me.omi.*;

@Mod(modid = "OmiClient", version = "b2", acceptedMinecraftVersions = "[1.8.9]")
public class Main
{
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        (OmiClient.instance = new OmiClient()).init();
    }
}
