package com.libsimsync.config;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.UUID;

//y
public class CommonConfigXMLReader {
    Path configPath;

    public CommonConfig read() throws IOException, XMLStreamException {
        Device device = null;
        Rule rule = null;
        LinkedList<Share> shares = new LinkedList<>();;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = inputFactory.createXMLEventReader(Files.newBufferedReader(configPath));

        while (eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                if(startElement.getName().getLocalPart().equals("Config")){

                }
                if(startElement.getName().getLocalPart().equals("Rule")){
                    rule = RuleXMLNodeReader.readNode(eventReader);
                }
                if(startElement.getName().getLocalPart().equals("Device")){
                    device = DeviceXMLNodeReader.readNode(eventReader);
                }
                if(startElement.getName().getLocalPart().equals("Shares")){
                    XMLEvent endCheck;
                    do {
                        endCheck = eventReader.nextEvent();
                        if(endCheck.isStartElement() &&
                                endCheck.asStartElement().getName().getLocalPart().equals("Share")) {
                            shares.add(ShareXMLNodeReader.readNode(eventReader));
                        }
                    }while(!(endCheck.isEndElement() &&
                            endCheck.asEndElement().getName().getLocalPart().equals("Shares")));
                }
            }
        }

        if(rule == null || device == null){
            throw new NullPointerException("ShareXMLNodeReader: read error!");
        }
        return new CommonConfig(rule, device, shares);
    }
}
