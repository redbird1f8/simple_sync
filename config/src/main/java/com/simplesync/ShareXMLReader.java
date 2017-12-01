package com.simplesync;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
//y
public class ShareXMLReader implements ShareReader{
    Path sharePath;

    ShareXMLReader(Path shareConfigPath, String name){
        sharePath = Paths.get(shareConfigPath.toString(), name+".xml");  //TODO make it more beautiful!!!
    }

    public ShareXMLReader(String name){
        sharePath = Paths.get(FileSystems.getDefault().getPath(System.getProperty("user.dir")).toString(), name+".xml");  //TODO make it more beautiful!!!
    }

    @Override
    public Share read() throws IOException, XMLStreamException {
        Share share = null;
        String name = null;
        UUID uuid = null;
        Path rootPath = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        LinkedList<Device> devices = null;
        LinkedList<FileEntry> files = null;
        XMLEventReader eventReader = inputFactory.createXMLEventReader(Files.newBufferedReader(sharePath));

        while (eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                if(startElement.getName().getLocalPart().equals("Share")){

                }
                if(startElement.getName().getLocalPart().equals("Name")){
                    event = eventReader.nextEvent();
                    name = event.asCharacters().getData();

                }
                if(startElement.getName().getLocalPart().equals("UUID")){
                    event = eventReader.nextEvent();
                    uuid = UUID.fromString(event.asCharacters().getData());
                }
                if(startElement.getName().getLocalPart().equals("RootPath")){
                    event = eventReader.nextEvent();
                    rootPath = FileSystems.getDefault().getPath(event.asCharacters().getData());
                }
                if(startElement.getName().getLocalPart().equals("Devices")){
                    devices = new LinkedList<>();
                    XMLEvent endCheck;
                    do {
                        endCheck = eventReader.nextEvent();
                        if(endCheck.isStartElement() &&
                                endCheck.asStartElement().getName().getLocalPart().equals("Device")) {
                            devices.add(DeviceXMLNodeReader.readNode(eventReader));
                        }
                    }while(!(endCheck.isEndElement() &&
                            endCheck.asEndElement().getName().getLocalPart().equals("Devices")));
                }
                if(startElement.getName().getLocalPart().equals("Files")){
                    files = new LinkedList<>();
                    XMLEvent endCheck;
                    do {
                        endCheck = eventReader.nextEvent();
                        if(endCheck.isStartElement() &&
                                endCheck.asStartElement().getName().getLocalPart().equals("FileEntry")) {
                            files.add(readFileEntryNode(eventReader, rootPath));
                        }
                    }while(!(endCheck.isEndElement() &&
                            endCheck.asEndElement().getName().getLocalPart().equals("Files")));
                }
            }

            if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("Share")){
                break;
            }
        }
        if(name == null || uuid == null || rootPath == null || devices == null || files == null){
            throw new NullPointerException("DeviceXMLNodeReader: read error!");
        }
        FileLister fileLister = new FileLister(files);
        return new Share(devices, name, uuid, rootPath, fileLister);
    }

    private FileEntry readFileEntryNode(XMLEventReader eventReader, Path root)
                    throws XMLStreamException, NullPointerException{
            Path path = null;
            Rule rule = null;
            while (eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();

                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();

                    if(startElement.getName().getLocalPart().equals("Path")){
                        event = eventReader.nextEvent();
                        path = root.resolve(event.asCharacters().getData());
                    }
                    if(startElement.getName().getLocalPart().equals("Rule")){
                        event = eventReader.nextEvent();
                        rule = RuleXMLNodeReader.readNode(eventReader);
                    }
                }
                if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("FileEntry")){
                    break;
                }
            }
            if(path == null || rule == null){
                throw new NullPointerException("FileEntry XMLNodeReader: read error!");
            }
            return new FileEntry(path, rule);
    }
}
