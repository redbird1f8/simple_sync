package com.simplesync.oldmanaging;

import com.simplesync.CommonConfig;
import com.simplesync.Device;
import com.simplesync.FileEntry;
import com.simplesync.Share;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Nickitakalinkin on 01.12.17.
 */

public class SyncManagerOld {


    static String localDirectory;

    public static void syncShare() {
        CommonConfig currentConfig = ConfigManagerOld.getConfig();
        List<Share> shareList = currentConfig.getShareList();

        for (Share currentShare : shareList)
            sendToDevices(currentShare);


    }


    private static void sendToDevices(Share share) {

        Iterator<FileEntry> iterator;
        for (Device device : share.getAcceptedDevices()) {
            iterator = share.getFileIterator();
            while (iterator.hasNext()) {
                Sender.SendObjecet(iterator.next(), device);
            }
        }

    }
}

//public class SyncManagerOld {
//
//    public static void syncShare() {
//        CommonConfig currentConfig = ConfigManagerOld.getConfig();
//        List<Share> shareList = currentConfig.getShareList();
//
//        for (Share currentShare : shareList)
//            //sendToDevices(currentShare);
//
//
//    }


//    private static void sendToDevices(Share share) {
//
//        Iterator<FileEntry> iterator;
//        for (Device device : share.getAcceptedDevices()) {
//            iterator = share.GetFileIterator();
//            while (iterator.hasNext()) {
//                Sender.SendObjecet(iterator.next(), device);
//            }
//        }
//
//    }
//}
