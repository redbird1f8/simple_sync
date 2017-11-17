package com.libsimsync.managing;

import com.libsimsync.config.*;
import sun.security.provider.SHA;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
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

      static Share getShare(String name){ // will be changed

          ShareXMLReader shareXMLReader = new ShareXMLReader(name);
          Share share = null; // don't be afraid

          try {
               share = shareXMLReader.read();
          } catch (IOException e) {
              e.printStackTrace();
          } catch (XMLStreamException e) {
              e.printStackTrace();
          }
          return share;

      }



}
