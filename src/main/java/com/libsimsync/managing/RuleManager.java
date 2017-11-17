package com.libsimsync.managing;

import com.libsimsync.config.ResolvingMethod;
import com.libsimsync.config.Rule;
import com.libsimsync.config.methods.ChooseLocal;

/**
 * Created by Nickitakalinkin on 17.11.17.
 */
 class RuleManager {

     //Rule rule; // возможно, следует сделать общение посредством объекта

    static Rule createRule(ResolvingMethod resolvingMethod, byte priority) {
        return new Rule(resolvingMethod,priority);
    }

    static Rule setPriority(byte priority) {
         //Rule newRule = new Rule(ResolvingMethod.LOCAL,priority); // Может перечисление?
         //
         return new Rule(new ChooseLocal(), priority); // temp
     }


}
