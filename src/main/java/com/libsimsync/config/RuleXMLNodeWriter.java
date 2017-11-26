package com.libsimsync.config;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
//n
public abstract class RuleXMLNodeWriter {
    static void write(XMLEventWriter eventWriter, Rule rule, int currentTabLevel)
            throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createStartElement("", "", "Rule"));
        eventWriter.add(end);
        // create Content
        XMLWriteMethods.createNode(eventWriter, "Priority", Boolean.toString(rule.getPriority()), currentTabLevel + 1);
        XMLWriteMethods.createNode(eventWriter, "Method", rule.getMethod().methodName(), currentTabLevel + 1);
        // create End node
        XMLWriteMethods.createIndentation(eventWriter, currentTabLevel);
        eventWriter.add(eventFactory.createEndElement("", "", "Rule"));
        eventWriter.add(end);
    }
}
