package com.libsimsync.config;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

public interface XMLWritable {
    void writeNode(XMLEventWriter eventWriter, Object object, int currentTabLevel) throws XMLStreamException;
}
