package com.simplesync;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

//y
public class CommonConfigXMLWriter implements CommonConfigWriter {
    Path configPath;

    @Override
    public void write(CommonConfig config) throws XMLStreamException, IOException {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(Files.newBufferedWriter(configPath, StandardOpenOption.CREATE));
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        //start element
        eventWriter.add(eventFactory.createStartDocument());
        eventWriter.add(eventFactory.createStartElement("", "", "Config"));
        eventWriter.add(end);
        //
        RuleXMLNodeWriter.write(eventWriter, config.getRules(), 1);
        //
        DeviceXMLNodeWriter.writeNode(eventWriter, config.thisDevice(), 1);
        //Accepted devices section
        eventWriter.add(tab);//should be redone
        eventWriter.add(eventFactory.createStartElement("", "", "Shares"));
        for (Share d: config.getShareList()) {
            ShareXMLNodeWriter.writeNode(eventWriter, d, 2);
        }
        eventWriter.add(tab);//should be redone
        eventWriter.add(eventFactory.createEndElement("", "", "Shares"));
        eventWriter.add(end);
        //
        //end element
        eventWriter.add(eventFactory.createEndElement("", "", "Config"));
        eventWriter.add(end);
    }
}
