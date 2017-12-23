package com.simplesync;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public abstract class XMLWriteMethods {
    static XMLEventFactory eventFactory = XMLEventFactory.newInstance(); //
    static XMLEvent end = eventFactory.createDTD("\n");
    static XMLEvent tab = eventFactory.createDTD("\t");

    public static void createNode(XMLEventWriter eventWriter, String name,
                            String value, int currentTabLevel) throws XMLStreamException {
        // create Start node
        createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createStartElement("", "", name));
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        eventWriter.add(eventFactory.createEndElement("", "", name));
        eventWriter.add(end);
    }

    public static void createIndentation(XMLEventWriter eventWriter, int currentTabLevel)
            throws XMLStreamException{
        for (int i = 0; i < currentTabLevel; i++) {
            eventWriter.add(tab);
        }
    }
}
