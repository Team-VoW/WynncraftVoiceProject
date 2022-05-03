package com.wynnvp.wynncraftvp.utils;

import com.wynnvp.wynncraftvp.ModCore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;

public class VersionChecker {





    public static float[] getVersionCheck(){
        String file = "";
        try {
            URL url = new URL("https://raw.githubusercontent.com/Team-WoV/ModVersion/main/version");
            String username = "user";
            String password = "gitpwd";

            URLConnection uc = url.openConnection();
            uc.setRequestProperty("X-Requested-With", "Curl");
            ArrayList<String> list = new ArrayList<String>();
            String userpass = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));//needs Base64 encoder, apache.commons.codec
            uc.setRequestProperty("Authorization", basicAuth);

            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null)
                file = file + line + "\n";


        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] split = file.split("\n");
        if (split.length < 2) return new float[0];

        split[0] = split[0].replace("newerstVersion: ", "");
        split[1] = split[1].replace("updateNotification: ", "");
        float[] ret = new float[2];
        ret[0] = Float.parseFloat(split[0]);
        ret[1] = Float.parseFloat(split[1]);

        return ret;
    }
}
