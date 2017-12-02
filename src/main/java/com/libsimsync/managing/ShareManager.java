package com.libsimsync.managing;

import com.libsimsync.config.*;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Created by Nickitakalinkin on 18.11.17.
 * <p>
 * Данный класс инкапсулирует работу с Share
 * (здесь подразумевается создание объекта)
 *
 * Возможно(маловероятно), я его скрою. А экземпляр данного класса
 * можно будет получить только из ConfigManager(Не стоит на это обращать внимание)
 */
public class ShareManager {


    public Share share;


    /**
     * Конструктор
     */
    public ShareManager(Share share) {
        this.share = share;
    }

    /**
     * Конструктор (если нет возможности заполучить Share)
     *
     * @param name - имя нового share
     * @param path - путь к share
     */
    public ShareManager(String name, Path path) {
        this.share = new Share(name, path);
    }

    /**
     * Добавление устройства в список AcceptedDevices(Подтвержденные устройства)
     *
     * @param device
     */
    public void addDevice(Device device) {
        share.addDevice(device);
    }

    /**
     * Добавить новое устройство по имени (?)
     * @param name - имя устройства
     */
    public void addDevice(String name) {
        share.addDevice(new Device(name));
    }

    /**
     * Удаление устройства из AcceptedDevices(Подтвержденные устройства)
     *
     * @param device
     * @return
     */
    public Device removeDevice(Device device) {
        share.removeDevice(device);
        return device;
    }

    /**
     * Удаение устройства по имени (?)
     * @param name
     * @return
     */
    public Device removeDevice(String name) {
        Device deviceToDelete = new Device(name);
        share.removeDevice(deviceToDelete);
        return deviceToDelete;
    }

    /**
     * Изменить имя для Share
     * @param newName
     * @return
     */
    public String changeName(String newName) {
        // maybe setter?
        return newName; //
    }



    /**
     *
     * Данный метод - просто шедевр, очень жаль, что он не нужен (достоверность информации проверяется )
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
