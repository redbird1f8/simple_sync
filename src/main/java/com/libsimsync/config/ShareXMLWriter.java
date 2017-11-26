package com.libsimsync.config;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
//y
public class ShareXMLWriter implements ShareWriter{
    Path shareFilesPath;

    public ShareXMLWriter(Path path){
        shareFilesPath = path;
    }

    public void write(Share share) throws XMLStreamException, IOException{
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(FileSystems.getDefault()
                        .getPath(shareFilesPath.toString(), share.getName()).toString()+".xml"));  //TODO do it less bulky way
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        eventWriter.add(eventFactory.createStartDocument());
        eventWriter.add(eventFactory.createStartElement("", "", "Share"));
        eventWriter.add(end);
        XMLWriteMethods.createNode(eventWriter, "Name", share.getName(), 1);   //should be redone
        XMLWriteMethods.createNode(eventWriter, "UUID", share.getUUID().toString(), 1);
        XMLWriteMethods.createNode(eventWriter, "RootPath", share.rootPath.toString(), 1);
        //Accepted devices section
        eventWriter.add(tab);//should be redone
        eventWriter.add(eventFactory.createStartElement("", "", "Devices"));
        eventWriter.add(end);
        for (Device d: share.acceptedDevices) {
            DeviceXMLNodeWriter.writeNode(eventWriter, d, 2);
        }
        eventWriter.add(tab);//should be redone
        eventWriter.add(eventFactory.createEndElement("", "", "Devices"));
        eventWriter.add(end);
        //FileLister section
        eventWriter.add(tab);
        writeFileLister(eventWriter, share.files, share.rootPath, 1);
        //end element
        eventWriter.add(eventFactory.createEndElement("", "", "Share"));
        eventWriter.add(end);
    }

    private void writeFileLister(XMLEventWriter eventWriter, FileLister fileLister, Path root, int currentTabLevel)
            throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createStartElement("", "", "Files"));
        eventWriter.add(end);
        // create Content
        for (FileEntry i: fileLister.pathList) {
            writeFileEntry(eventWriter, i, root, currentTabLevel + 1);
        }
        // create End node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createEndElement("", "", "Files"));
        eventWriter.add(end);
    }

    private void writeFileEntry(XMLEventWriter eventWriter, FileEntry fileEntry, Path root, int currentTabLevel)
            throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        for (int i = 0; i < currentTabLevel; i++) {
            eventWriter.add(tab);
        }
        eventWriter.add(eventFactory.createStartElement("", "", "FileEntry"));
        eventWriter.add(end);
        // create Content
        XMLWriteMethods.createNode(eventWriter, "Path", root.relativize(fileEntry.getPath()).toString(), currentTabLevel + 1);
        RuleXMLNodeWriter.write(eventWriter, fileEntry.getRule(), currentTabLevel + 1);
        // create End node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createEndElement("", "", "FileEntry"));
        eventWriter.add(end);
    }
}
