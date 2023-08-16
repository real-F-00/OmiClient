//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "/home/f-00/MCP-919"!

//Decompiled by Procyon!

package me.omi.gui;

import net.minecraftforge.client.*;
import net.minecraft.client.*;
import java.text.*;
import net.minecraft.client.gui.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import java.util.*;

public class CustomOverlay extends GuiIngameForge
{
    Double goldReq;
    String goldReqString;
    String percentString;
    int streakKills;
    int streakAssists;
    String streakGold;
    String streakXP;
    String time;
    int status;
    
    public CustomOverlay(final Minecraft mcIn) {
        super(mcIn);
        this.goldReq = 10000.0;
        this.goldReqString = "";
        this.percentString = "0.00";
        this.time = "";
        this.status = 0;
    }
    
    public void setReq(final Double tempReq, final Double total, final Double current) {
        this.goldReq = tempReq;
        final DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("es-ES")));
        this.goldReqString = df.format(tempReq);
        final Double tempPercent = current / total * 100.0;
        final DecimalFormat df2 = new DecimalFormat("0.0");
        this.percentString = df2.format(tempPercent);
    }
    
    public void updateScoreboard(final Double total, final Double current, final int kills, final int assists, final Float streakXPIn, final Float streakGoldIn, final int statusIn) {
        final DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("es-ES")));
        this.goldReqString = df.format(total - current);
        final DecimalFormat df2 = new DecimalFormat("0.00");
        this.percentString = df2.format(current / total * 100.0);
        final DecimalFormat df3 = new DecimalFormat("0.0");
        this.streakKills = kills;
        this.streakAssists = assists;
        this.streakGold = df.format(streakGoldIn);
        this.streakXP = Integer.toString(Math.round(streakXPIn));
        this.status = statusIn;
    }
    
    public void updateTime(final String timeIn) {
        this.time = timeIn;
    }
    
    protected void renderScoreboard(final ScoreObjective objective, final ScaledResolution scaledRes) {
        final Scoreboard scoreboard = objective.getScoreboard();
        Collection<Score> collection = (Collection<Score>)scoreboard.getSortedScores(objective);
        final List<Score> list = (List<Score>)Lists.newArrayList(Iterables.filter((Iterable)collection, (Predicate)new Predicate<Score>() {
            public boolean apply(final Score p_apply_1_) {
                return p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#");
            }
        }));
        if (list.size() > 15) {
            collection = (Collection<Score>)Lists.newArrayList(Iterables.skip((Iterable)list, collection.size() - 15));
        }
        else {
            collection = list;
        }
        int i = this.getFontRenderer().getStringWidth(objective.getDisplayName());
        for (final Score score : collection) {
            final ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
            final String s = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName()) + ": " + EnumChatFormatting.RED + score.getScorePoints();
            i = Math.max(i, this.getFontRenderer().getStringWidth(s));
        }
        i = Math.max(i, this.getFontRenderer().getStringWidth("Req: " + EnumChatFormatting.GOLD + this.goldReqString + "g" + EnumChatFormatting.GRAY + " (" + EnumChatFormatting.GREEN + this.percentString + "%" + EnumChatFormatting.GRAY + ")"));
        final int i2 = collection.size() * this.getFontRenderer().FONT_HEIGHT;
        final int j1 = scaledRes.getScaledHeight() / 2 + i2 / 3;
        final int k1 = 3;
        final int l1 = scaledRes.getScaledWidth() - i - k1;
        int m = 0;
        if (this.status == 1) {
            int tempJ = -9;
            int tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            final int tempL = scaledRes.getScaledWidth() - k1 + 2;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Time: " + EnumChatFormatting.YELLOW + this.time, l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("XP: " + EnumChatFormatting.AQUA + this.streakXP + " XP", l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Gold: " + EnumChatFormatting.GOLD + this.streakGold + "g", l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Assists: " + EnumChatFormatting.GREEN + this.streakAssists, l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Kills: " + EnumChatFormatting.GREEN + this.streakKills, l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK - this.getFontRenderer().FONT_HEIGHT - 1, tempL, tempK - 1, 1610612736);
            drawRect(l1 - 2, tempK - 1, tempL, tempK, 1342177280);
            this.getFontRenderer().drawString("Streak (" + EnumChatFormatting.GREEN + "Active" + EnumChatFormatting.WHITE + ")", l1 + i / 2 - this.getFontRenderer().getStringWidth("Streak (" + EnumChatFormatting.GREEN + "Active" + EnumChatFormatting.GRAY + ")") / 2, tempK - this.getFontRenderer().FONT_HEIGHT, 553648127);
        }
        if (this.status == 2) {
            int tempJ = -9;
            int tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            final int tempL = scaledRes.getScaledWidth() - k1 + 2;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Time: " + EnumChatFormatting.YELLOW + this.time, l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("XP: " + EnumChatFormatting.AQUA + this.streakXP + " XP", l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Gold: " + EnumChatFormatting.GOLD + this.streakGold + "g", l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Assists: " + EnumChatFormatting.GREEN + this.streakAssists, l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString("Kills: " + EnumChatFormatting.GREEN + this.streakKills, l1, tempK, 553648127);
            ++tempJ;
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK, tempL, tempK + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            tempK = j1 - tempJ * this.getFontRenderer().FONT_HEIGHT;
            drawRect(l1 - 2, tempK - this.getFontRenderer().FONT_HEIGHT - 1, tempL, tempK - 1, 1610612736);
            drawRect(l1 - 2, tempK - 1, tempL, tempK, 1342177280);
            this.getFontRenderer().drawString("Streak (" + EnumChatFormatting.GOLD + "Past" + EnumChatFormatting.WHITE + ")", l1 + i / 2 - this.getFontRenderer().getStringWidth("Streak (" + EnumChatFormatting.GREEN + "Active" + EnumChatFormatting.GRAY + ")") / 2, tempK - this.getFontRenderer().FONT_HEIGHT, 553648127);
        }
        final Boolean flag = false;
        int num = collection.size();
        for (final Score score2 : collection) {
            ++m;
            final ScorePlayerTeam scoreplayerteam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
            final String s2 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam2, score2.getPlayerName());
            final String s3 = EnumChatFormatting.RED + "" + score2.getScorePoints();
            int k2 = j1 - m * this.getFontRenderer().FONT_HEIGHT;
            int l2 = scaledRes.getScaledWidth() - k1 + 2;
            drawRect(l1 - 2, k2, l2, k2 + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString(s2, l1, k2, 553648127);
            if (s2.contains("Gold") && this.goldReqString != "") {
                ++m;
                ++num;
                k2 = j1 - m * this.getFontRenderer().FONT_HEIGHT;
                l2 = scaledRes.getScaledWidth() - k1 + 2;
                drawRect(l1 - 2, k2, l2, k2 + this.getFontRenderer().FONT_HEIGHT, 1342177280);
                this.getFontRenderer().drawString("Req: " + EnumChatFormatting.GOLD + this.goldReqString + "g" + EnumChatFormatting.GRAY + " (" + EnumChatFormatting.GREEN + this.percentString + "%" + EnumChatFormatting.GRAY + ")", l1, k2, 553648127);
            }
            if (m == num) {
                final String s4 = objective.getDisplayName();
                drawRect(l1 - 2, k2 - this.getFontRenderer().FONT_HEIGHT - 1, l2, k2 - 1, 1610612736);
                drawRect(l1 - 2, k2 - 1, l2, k2, 1342177280);
                this.getFontRenderer().drawString(s4, l1 + i / 2 - this.getFontRenderer().getStringWidth(s4) / 2, k2 - this.getFontRenderer().FONT_HEIGHT, 553648127);
            }
        }
    }
}
