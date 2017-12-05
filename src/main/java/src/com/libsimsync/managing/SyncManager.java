package com.libsimsync.managing;

import com.libsimsync.config.CommonConfig;
import com.libsimsync.config.Device;
import com.libsimsync.config.FileEntry;
import com.libsimsync.config.Share;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Nickitakalinkin on 01.12.17.
 */
public class SyncManager {

    public static void syncShare() {
        CommonConfig currentConfig = ConfigManager.getConfig();
        List<Share> shareList = currentConfig.getShareList();

        for (Share currentShare : shareList)
            sendToDevices(currentShare);


    }


    private static void sendToDevices(Share share) {

        Iterator<FileEntry> iterator;
        for (Device device : share.getAcceptedDevices()) {
            iterator = share.GetFileIterator();
            while (iterator.hasNext()) {
                Sender.SendObjecet(iterator.next(), device);
            }
        }

    }
}
