package com.libsimsync.managing;

import com.libsimsync.config.CommonConfig;
import com.libsimsync.config.CommonConfigXMLReader;
import com.libsimsync.config.CommonConfigXMLWriter;
import com.libsimsync.config.Rule;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Created by Nickitakalinkin on 17.11.17.
 */
 class ConfigManager {


    public Rule setRule()




    private static void setConfig(CommonConfig commonConfig) {

        CommonConfigXMLWriter commonConfigXMLWriter = new CommonConfigXMLWriter();
        CommonConfigXMLReader commonConfigXMLReader = new CommonConfigXMLReader();

        CommonConfig config = null; // Возможно нужен будет  конструктор по умолчанию (как базовая настройка)

        try {
            config = commonConfigXMLReader.read();
        } catch ( XMLStreamException streamException) {
            System.err.println(streamException.getMessage()); // TODO: убрать в релизе
        } catch (IOException e) {
            System.err.println(e.getMessage()); // TODO: убрать в релизе
        }


        commonConfigXMLWriter.write(commonConfig);




    }
}
