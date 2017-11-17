package com.libsimsync.managing;

import com.libsimsync.config.Device;

/**
 * Created by Nickitakalinkin on 18.11.17.
 */
 class DeviceManager {

     static Device setDevice(String name) {
         return new Device(name);
     }
}
