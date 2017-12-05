package com.libsimsync.managing;

import com.libsimsync.config.*;
import sun.security.provider.SHA;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nickitakalinkin on 18.11.17.
 */
 class ShareManager {

    /**
     * ?
     * Добавить новый Share в список
     * @param newShare  добавляемыый Share
     * @param path Путь к директории с Share(s)
     */
      static void addShare(Share newShare,Path path) {
          ShareWriter shareWriter = new ShareXMLWriter(path);
          try {
              shareWriter.write(newShare);
          } catch (Exception e) {
              e.printStackTrace();
          }
      }

    /**
     * Получить Share
     * @param name Имя для поиска
     * @return Найденный Share
     */

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


    /**
     * Метод для получения списка Share(s)
     * Метод еще в разработке.
     * javadoc будет написан после завершения технических работ)
     * @param name
     * @return Список Share
     */
      static LinkedList<Share> getShareList(String name) { // Will be another declaration
          LinkedList<Share> shareList = new LinkedList<>();
          //
          // чтение в список Share
          //

          return shareList;
      }



}
