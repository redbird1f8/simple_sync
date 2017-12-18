package com.simplesync.oldmanaging;

import com.simplesync.Device;

import java.util.UUID;

/**
 * Created by Nickitakalinkin on 01.12.17.
 * <p>
 * временный интерфейс (для использования метода SendObject)
 */
interface Sender {
    static void SendObject(Object data, UUID id) {
    }

    static void SendObjecet(Object data, Device device) {
    }
}
