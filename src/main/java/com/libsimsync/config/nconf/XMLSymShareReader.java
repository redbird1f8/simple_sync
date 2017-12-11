package com.libsimsync.config.nconf;

import com.libsimsync.config.*;

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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

public class XMLSymShareReader {
    static String sharePath = System.getProperty("user.dir");

    public static SymShare read() throws IOException, XMLStreamException {
        Share share = null;
        String rootPath = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        ArrayList<SyncDevice> devices = null;
        XMLEventReader eventReader = inputFactory.createXMLEventReader(Files.newBufferedReader(Paths.get(sharePath, "Share.xml")));

        while (eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if(event.isStartElement()){
                StartElement startElement = event.asStartElement();
                if(startElement.getName().getLocalPart().equals("Share")){

                }

                if(startElement.getName().getLocalPart().equals("RootPath")){
                    event = eventReader.nextEvent();
                    rootPath = event.asCharacters().getData();
                }
                if(startElement.getName().getLocalPart().equals("Devices")){
                    devices = new ArrayList<>();
                    XMLEvent endCheck;
                    do {
                        endCheck = eventReader.nextEvent();
                        if(endCheck.isStartElement() &&
                                endCheck.asStartElement().getName().getLocalPart().equals("Device")) {
                            String name = "";
                            String ip = "0.0.0.0";
                            while (eventReader.hasNext()){
                                event = eventReader.nextEvent();

                                if(event.isStartElement()){
                                    startElement = event.asStartElement();

                                    if(startElement.getName().getLocalPart().equals("Name")){
                                        event = eventReader.nextEvent();
                                        name = event.asCharacters().getData();
                                    }
                                    if(startElement.getName().getLocalPart().equals("IP")){
                                        event = eventReader.nextEvent();
                                        ip = event.asCharacters().getData();
                                    }
                                }
                                if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("Device")){
                                    break;
                                }
                            }

                            devices.add(new SyncDevice(name, ip));


                        }
                    }while(!(endCheck.isEndElement() &&
                            endCheck.asEndElement().getName().getLocalPart().equals("Devices")));
                }
            }

            if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("Share")){
                break;
            }
        }
        if(rootPath == null || devices == null){
            throw new NullPointerException("DeviceXMLNodeReader: read error!");
        }

        return new SymShare(rootPath, devices);
    }
}
