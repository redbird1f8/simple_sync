package com.libsimsync.config;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.UUID;
//n
public class RuleXMLNodeReader {
    static Rule readNode(XMLEventReader eventReader)
            throws XMLStreamException, NullPointerException{
        boolean priority = false;
        ResolvingMethod method = null;
        while (eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();

                if(startElement.getName().getLocalPart().equals("Priority")){
                    event = eventReader.nextEvent();
                    priority = Boolean.parseBoolean(event.asCharacters().getData());
                }
                if(startElement.getName().getLocalPart().equals("Method")){
                    event = eventReader.nextEvent();
                    method = ResolvingMethodFactory.construct(event.asCharacters().getData()); //TODO Make factory
                }
            }
            if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("Rule")){
                break;
            }
        }
        if(method == null){
            throw new NullPointerException("RuleXMLNodeReader: read error!");
        }
        return new Rule(method, priority);
    }
}
