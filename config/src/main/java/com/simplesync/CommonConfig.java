package com.simplesync;

import com.simplesync.methods.ChooseForeign;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс общей конфигурации. Содержит в себе идентификатор устройства, список шар, правило разрешенея
 * конфликтов по умолчанию.
 * TODO: 18.11.2017 Возможно должен быть Singleton'ом.
 */
public class CommonConfig {
    private static Rule defaultRules;
    private List<Share> shareList;
    private final Device thisDevice;

    /**
     * Изначальный конструктор. Должен вызываться для создания общей конфигурации при
     * отсутствии файла общей конфигурации. Например, при первом запуске программы.
     * @param name Имя устройства
     */
    public CommonConfig(String name){
        defaultRules = new Rule(new ChooseForeign(), false);
        shareList = new ArrayList<>(10);
        thisDevice = new Device(name);
    }

    // update by Nickz (make public) + У меня здесь почему-то  LinkedList (LinkedList -> List)
   public CommonConfig(Rule rule, Device device, List<Share> shareList){  //List
        defaultRules = rule;
        thisDevice = device;
        this.shareList = shareList;
    }

    /**
     * Возвращает правило по умолчанию
     * TODO смотри описание класса
     * @return
     */
    public static Rule getRules(){
        return defaultRules;
    }

    public List<Share> getShareList() {
        return shareList;
    }

    public void setDefaultRules(Rule defaultRules) {
        this.defaultRules = defaultRules;
    }

    public Device thisDevice() {
        return thisDevice;
    }
}
