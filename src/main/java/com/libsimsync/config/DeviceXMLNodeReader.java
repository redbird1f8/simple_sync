package com.libsimsync.config;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.UUID;

public class DeviceXMLNodeReader {
    static Device readNode(XMLEventReader eventReader)
            throws XMLStreamException, NullPointerException{
        String name = null;
        UUID id = null;
        while (eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();

                if(startElement.getName().getLocalPart().equals("Name")){
                    event = eventReader.nextEvent();
                    name = event.asCharacters().getData();
                }
                if(startElement.getName().getLocalPart().equals("ID")){
                    event = eventReader.nextEvent();
                    id = UUID.fromString(event.asCharacters().getData());
                }
            }
            if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("Device")){
                break;
            }
        }
        if(name == null || id == null){
            throw new NullPointerException("DeviceXMLNodeReader: read error!");
        }
        return new Device(name, id);
    }
}
