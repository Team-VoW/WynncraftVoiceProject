package com.wynnvp.wynncraftvp.sound.line;


import java.util.regex.Pattern;

public class LineData {

    private String soundLine;
    private String realLine;

    public String getSoundLine() {
        return soundLine;
    }

    public void setSoundLine(String soundLine) {
        this.soundLine = soundLine;
    }

    public String getRealLine() {
        return realLine;
    }

    public void setRealLine(String realLine) {
        this.realLine = realLine;
    }

    private static final Pattern NPC_DIALOGUE_PATTERN = Pattern.compile("^\\[\\d+/\\d+].+: .");


    public String getNPCName() {
        String[] split = realLine.split(": ");
        String name = split[0];
        name = name.substring(name.indexOf("]") + 1);

        return name.trim().toLowerCase().replaceAll("[^a-z\\d?]", "");
    }

    public boolean isNPCSentLine(){
        return NPC_DIALOGUE_PATTERN.matcher(realLine).find();
    }

}
