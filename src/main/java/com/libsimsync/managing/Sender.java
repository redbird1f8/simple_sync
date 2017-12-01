package com.libsimsync.managing;

import com.libsimsync.config.Device;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.UUID;

/**
 * Created by Nickitakalinkin on 01.12.17.
 *
 * временный интерфейс (для использования метода SendObject)
 */
 interface Sender {
     static void SendObject(Object data, UUID id){}
     static void SendObjecet(Object data, Device device){}
}
