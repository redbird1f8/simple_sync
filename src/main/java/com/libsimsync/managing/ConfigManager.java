package com.libsimsync.managing;

import com.libsimsync.config.*;
import io.netty.util.NettyRuntime;


import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nickitakalinkin on 17.11.17.
 * Класс является чем-то похожим на Фасад, скрывающим сложность подсистемы работы с Config.
 *
 * Rule rule, Device device, LinkedList<Share> shareList - объекты, от которых
 * зависит состояние config наданный момент
 *
 */
// есть вопрос по конструкторам
 public class ConfigManager {
 // will be changed (поддразумевалось существование одного config)
    // Обращение  планируется посредством объекта
    // Static - временная версия
    // все это - костыли
    // хотя вышло забавно
    // Возможно, я создал свой антипаттерн (надеюсь его назовут в мою честь)


     private static Rule rule ;
     private static Device device;
     private static List<Share> shareList; // LinkedList?

    /**
     * Инициализация значением из XML (если такового нет, то стандартным значением)
     */
//    static {
//       getConfig();
//    }

    /**
     * Метод устанавливает правила синхронизации файлов.
     *
     * @param resolvingMethod  метод разрешения коллизий(локальные/внешние файлы)
     * @param priority  приоритет в синхронизации
     */
    public static void setRule(ResolvingMethod resolvingMethod, boolean priority ) {
         rule = RuleManager.createRule(resolvingMethod,priority);
    } // need more setRule (for God "setRule")

    /**
     * Метод устанавливает правила синхронизации файлов.
     * Использует стандартное значение для ResolvingMethod (метод разрешения коллизий)
     * @param priority  приоритет в синхронизации
     */
    public static void setRule(boolean priority) { // use Local
        rule  = RuleManager.setPriority(priority);
    }

    /**
     * Установить имя для текущего устройства
     *
     * @param name - имя устройства
     */
    public static void setDeviceName(String name) {
        device = DeviceManager.setDevice(name);
    }

    /**
     * Пока метод не готов
     * @param name самому интересно
     */
    public static void setShareList(String name) { // ?
        shareList = ShareManager.getShareList(name); // пока не работает
    }


    public static void addShare(String name, Path path) {
        shareList.add(new Share(name,path));
    }

    /**
     * Подтверждение конфига.
     * Запись конфига в xml файл.
     * (То, что происходит при нажатии кнопки "Принять")
     */
    public static void applyConfig() { // apply config
        CommonConfigXMLWriter commonConfigXMLWriter = new CommonConfigXMLWriter();
        CommonConfig config = createConfig(); // использует текущее состояние

        try {
            commonConfigXMLWriter.write(config);

        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Получение  сonfig из xml файла
     * Если получение невозможно, то вернется стандартное занчение
     * (Возможно изменить модификатор доступа)
     * @return config - настройки из xml
     */
     public static CommonConfig getConfig() { // default value = old value // was private (but why? What wrong with me)
        CommonConfigXMLReader commonConfigXMLReader = new CommonConfigXMLReader();

        CommonConfig config = new CommonConfig("My device"); // need default config

        try {
            config = commonConfigXMLReader.read();
        } catch ( XMLStreamException streamException) {
            System.err.println(streamException.getMessage()); // TODO: убрать в релизе
        } catch (IOException e) {
            System.err.println(e.getMessage()); // TODO: убрать в релизе
        }
        rule = config.getRules();
        device = config.thisDevice();
        shareList = config.getShareList();
        return config;
    }

    /**
     *  Метод создает config, основываясь на текущем состоянии полей класса ConfigManager(rule,device,shareList).
     *  Это позволяет изменять эти поля, подразумевая автоматическое изменение(получение) обЪекта класса CommonConfig.
     *
     * @return объект класса CommonConfig
     */
    private static CommonConfig createConfig() {//(Rule rule, Device device, LinkedList<Share> shareList) { //List
        CommonConfig commonConfig = new CommonConfig(rule,device,shareList);

        return commonConfig;
    }





}
