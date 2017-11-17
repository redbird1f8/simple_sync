package com.libsimsync.config;

import com.libsimsync.config.methods.ChooseForeign;
import com.libsimsync.config.methods.ChooseLocal;

public class ResolvingMethodFactory {

    static ResolvingMethod construct(String methodString){ //TODO static?
        String[] elements = methodString.split(":");
        ResolvingMethod ret = null;
        switch (elements[0]){
            case "local" : {
                ret = new ChooseLocal();
                break;
            }
            case "foreign" : {
                ret = new ChooseForeign();
            }
        }
        return ret;
    }
}
