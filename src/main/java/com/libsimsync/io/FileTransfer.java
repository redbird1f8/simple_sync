package com.libsimsync.io;

import com.libsimsync.config.Device;
import com.libsimsync.config.FileEntry;
import com.libsimsync.config.Share;

import java.util.List;

public class FileTransfer {
    /**
     * Возвращает список устройств в сети.
     * @return
     */
    public static List<Device> scan(){

    }

    /**
     * Инициирует синхронизацию, переводя оба устройства в режим передачи файлов.
     * @param share Шара, которую нужно синхронизировать
     * @param device Устройство, с которым нужно синхронизироваться
     * @return Объект SyncProcessor предоставляющий функции для передачи файлов
     */
    public static SyncProcessor estabilishShareSync(Share share, Device device){

    }

}
