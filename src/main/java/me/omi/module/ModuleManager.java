//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.module;

import java.io.*;
import me.omi.module.render.*;
import me.omi.module.utility.*;
import java.util.*;

public class ModuleManager
{
    public ArrayList<Module> modules;
    private File dir;
    private File dataFile;
    
    public ModuleManager() {
        (this.modules = new ArrayList<Module>()).clear();
        this.modules.add(new ClickGUI());
        this.modules.add(new HUD());
        this.modules.add(new DarkList());
        this.modules.add(new RegList());
        this.modules.add(new Velocity());
        this.modules.add(new AutoAura());
        this.modules.add(new Focus());
        this.modules.add(new EventList());
        this.modules.add(new PlayerList());
        this.modules.add(new AutoLogGlitch());
        this.modules.add(new CakeClicker());
        this.modules.add(new PlayerCounter());
        this.modules.add(new Nametags());
        this.modules.add(new PitScoreboard());
        this.modules.add(new AutoEgg());
        this.modules.add(new AutoSteak());
        this.modules.add(new AutoOof());
        this.modules.add(new AutoWarp());
    }
    
    public Module getModule(final String name) {
        for (final Module m : this.modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
    
    public ArrayList<Module> getModuleList() {
        return this.modules;
    }
    
    public ArrayList<Module> getModulesInCategory(final Category c) {
        final ArrayList<Module> mods = new ArrayList<Module>();
        for (final Module m : this.modules) {
            if (m.getCategory() == c) {
                mods.add(m);
            }
        }
        return mods;
    }
}
