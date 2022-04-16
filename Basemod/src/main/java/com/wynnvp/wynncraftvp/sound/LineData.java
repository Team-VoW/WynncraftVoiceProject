package com.wynnvp.wynncraftvp.sound;

public class LineData {

    private String soundLine;
    private String realLine;

    public String getSoundLine() {
        return soundLine;
    }

    public String getRealLine() {
        return realLine;
    }

    public void setSoundLine(String soundLine) {
        this.soundLine = soundLine;
    }

    public void setRealLine(String realLine) {
        this.realLine = realLine;
    }

    public String getNPCName(){
          String name = realLine.substring(realLine.indexOf(']'));
          name = name.substring(1);
          name = name.trim();
          name = name.split(":")[0];
          return name;


    }

}
