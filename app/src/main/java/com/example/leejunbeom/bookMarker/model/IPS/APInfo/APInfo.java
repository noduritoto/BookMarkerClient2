package com.example.leejunbeom.bookMarker.model.IPS.APInfo;

import android.content.res.AssetManager;
import android.content.res.Resources;

import com.squareup.okhttp.internal.Platform;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by noduritoto on 2016. 10. 27..
 */

public class APInfo {

    ArrayList<APInfoEachspot> totalApInfoEachSpots;
    ArrayList<String> MAClist;
    Resources mResources;

    public APInfo(Resources resources){
        this.totalApInfoEachSpots = new ArrayList<APInfoEachspot>();
        this.MAClist = new ArrayList<String>();
        this.mResources = resources;
        setMacList();

    }
    public void setMacList(){

        // MAC List 정보 적재하기
        try {
            AssetManager assetMgr = mResources.getAssets();
            InputStream is = assetMgr.open("saveAP.txt");

            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = in.readLine()) != null) {
                MAClist.add(s.substring(0, 17));
            }
            in.close();

            // TODO:
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getMAClist(){
        return this.MAClist;
    }

     class APInfoEachspot{
         private String macAddress;
         private String number;

         APInfoEachspot(String macAddress, String number){
             this.macAddress = macAddress;
             this.number = number;
         }

         public String getMacAddress(){
             return this.macAddress;
         }

         public String getNumber(){
             return this.number;
         }
     }
}
