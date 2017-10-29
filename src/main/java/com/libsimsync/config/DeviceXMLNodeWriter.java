package com.libsimsync.config;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;



public abstract class DeviceXMLNodeWriter {
    static void writeNode(XMLEventWriter eventWriter, Device device, int currentTabLevel)
            throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createStartElement("", "", "Device"));
        // create Content
        XMLWriteMethods.createNode(eventWriter, "Name", device.getName(), currentTabLevel + 1);
        XMLWriteMethods.createNode(eventWriter, "ID", device.getId().toString(), currentTabLevel + 1);
        // create End node
        eventWriter.add(eventFactory.createEndElement("", "", "Device"));
        eventWriter.add(end);
    }
}
