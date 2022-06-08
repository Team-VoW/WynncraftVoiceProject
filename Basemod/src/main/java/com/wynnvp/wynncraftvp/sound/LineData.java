package com.wynnvp.wynncraftvp.sound;

import lombok.Data;

@Data
public class LineData {

    private String soundLine;
    private String realLine;

    public String getNPCName(){
          String name = realLine.substring(realLine.indexOf(']'));
          name = name.substring(1);
          name = name.trim();
          name = name.split(":")[0];
          return name;
    }

}
