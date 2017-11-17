package com.libsimsync.managing;

import com.libsimsync.config.Share;
import com.libsimsync.config.ShareWriter;
import com.libsimsync.config.ShareXMLWriter;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by Nickitakalinkin on 18.11.17.
 */
 class ShareManager {

      static void addShare(Share newShare,Path path) {
          ShareWriter shareWriter = new ShareXMLWriter(path);
          try {
              shareWriter.write(newShare); // maybe need append in ShareWriter?
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
      static List<Share> getShares(){

      }



}
