package com.libsimsync.managing;
import com.libsimsync.config.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Created by Nickitakalinkin on 18.11.17.
 */
public class ShareManager {


    public Share share;

    public ShareManager(Share share) {
        this.share = share;
    }

    public ShareManager(String name, Path path) {
        this.share = new Share(name, path);
    }

    public void addDevice(Device device) {
        share.addDevice(device);
    }

    public Device removeDevice(Device device) {
        share.removeDevice(device);
        return device;
    }


    public void addDevice(String name) {
        share.addDevice(new Device(name));
    }

    public Device removeDevice(String name) {
        Device deviceToDelete = new Device(name);
        share.removeDevice(deviceToDelete);
        return deviceToDelete;
    }

    public String changeName(String newName) {
        // maybe setter?
        return  newName; //
    }


    /**
     * ?
     * Добавить новый Share в список
     *
     * @param newShare добавляемыый Share
     * @param path     Путь к директории с Share(s)
     */
    static void addShare(Share newShare, Path path) {
        ShareWriter shareWriter = new ShareXMLWriter(path);
        try {
            shareWriter.write(newShare);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static Share createNewEmptyShare(String name, Path root) {
        return new Share(name, root);
    }


    /**
     * Получить Share
     *
     * @param name Имя для поиска
     * @return Найденный Share
     */

    static Share getShare(String name) { // will be changed

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
     *
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
