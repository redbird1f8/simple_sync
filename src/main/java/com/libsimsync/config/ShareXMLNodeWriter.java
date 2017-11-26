package com.libsimsync.config;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
//? n
class ShareXMLNodeWriter {
    public static void writeNode(XMLEventWriter eventWriter, Share share, int currentTabLevel)
            throws XMLStreamException{
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createStartElement("", "", "Share"));
        eventWriter.add(end);
        // create Content
        XMLWriteMethods.createNode(eventWriter, "Name", share.getName(), currentTabLevel + 1);
        XMLWriteMethods.createNode(eventWriter, "ID", share.getUUID().toString(), currentTabLevel + 1);
        // create End node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createEndElement("", "", "Share"));
        eventWriter.add(end);
    }
}
