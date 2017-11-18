package com.libsimsync.managing;

import com.libsimsync.config.Device;

/**
 * Created by Nickitakalinkin on 18.11.17.
 */
 class DeviceManager {

    /**
     *
     * @param name - новое имя устройства
     * @return объект класса Device
     */
     static Device setDevice(String name) {
         return new Device(name);
     }

}
