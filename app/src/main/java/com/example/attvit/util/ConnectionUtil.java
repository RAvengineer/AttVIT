package com.example.attvit.util;

import android.net.wifi.WifiManager;

public class ConnectionUtil {
    public WifiManager wifiManager;

    public void enableWifi() {
        if (!wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
    }

}
