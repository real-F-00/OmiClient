//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.utils;

public class TimeHelper
{
    private long lastTime;
    
    public TimeHelper() {
        this.lastTime = this.getCurrentTime();
        this.reset();
    }
    
    public TimeHelper(final int diffy) {
        this.lastTime = this.getCurrentTime();
        this.lastTime = 0L;
    }
    
    public long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public long getLastTime() {
        return this.lastTime;
    }
    
    public long getDifference() {
        return this.getCurrentTime() - this.lastTime;
    }
    
    public void reset() {
        this.lastTime = this.getCurrentTime();
    }
    
    public boolean hasReached(final long milliseconds) {
        return this.getDifference() >= milliseconds;
    }
}
