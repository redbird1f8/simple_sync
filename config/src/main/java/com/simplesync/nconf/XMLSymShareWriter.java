package com.simplesync.nconf;


import com.simplesync.XMLWriteMethods;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;

public class XMLSymShareWriter {
    //static String shareFilesPath = System.getProperty("user.dir");
    static String shareFilesPath = System.getProperty("user.dir");

    public static void write(SymShare share) throws XMLStreamException, IOException {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(FileSystems.getDefault()
                        .getPath(shareFilesPath, "Share.xml").toString()));
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        eventWriter.add(eventFactory.createStartDocument());
        eventWriter.add(eventFactory.createStartElement("", "", "Share"));
        eventWriter.add(end);
        XMLWriteMethods.createNode(eventWriter, "RootPath", share.getRootPath(), 1);
        //Accepted devices section
        eventWriter.add(tab);//should be redone
        eventWriter.add(eventFactory.createStartElement("", "", "Devices"));
        eventWriter.add(end);
        for (SyncDevice d : share.acceptedDevices) {
            XMLWriteMethods.createIndentation(eventWriter, 2);
            eventWriter.add(eventFactory.createStartElement("", "", "Device"));
            eventWriter.add(end);
            // create Content
            XMLWriteMethods.createNode(eventWriter, "Name", d.getName(), 3);
            XMLWriteMethods.createNode(eventWriter, "IP", d.getIpAddress(), 3);
            // create End node
            XMLWriteMethods.createIndentation(eventWriter, 2);
            eventWriter.add(eventFactory.createEndElement("", "", "Device"));
            eventWriter.add(end);
        }
        eventWriter.add(tab);//should be redone
        eventWriter.add(eventFactory.createEndElement("", "", "Devices"));
        eventWriter.add(end);
        //end element
        eventWriter.add(eventFactory.createEndElement("", "", "Share"));
        eventWriter.add(end);
    }
}
