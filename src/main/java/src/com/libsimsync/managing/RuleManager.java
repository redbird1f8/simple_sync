package com.libsimsync.managing;

import com.libsimsync.config.ResolvingMethod;
import com.libsimsync.config.Rule;
import com.libsimsync.config.methods.ChooseLocal;

/**
 * Created by Nickitakalinkin on 17.11.17.
 */
 class RuleManager {

     //Rule rule; // возможно, следует сделать общение посредством объекта

    /**
     * Фабричный метод получения нового Rule
     * @param resolvingMethod метод разрешения коллизий
     * @param priority приоритет синхронизации
     * @return
     */
    static Rule createRule(ResolvingMethod resolvingMethod, boolean priority) {
        return new Rule(resolvingMethod,priority);
    }

    /**
     * Метод устанавливает правила синхронизации файлов.
     * Использует стандартное значение для ResolvingMethod (метод разрешения коллизий)
     * @param priority  приоритет в синхронизации
     */
    static Rule setPriority(boolean priority) {
         //Rule newRule = new Rule(ResolvingMethod.LOCAL,priority); // Может перечисление?
         //
         return new Rule(new ChooseLocal(), priority); // temp
     }


}
