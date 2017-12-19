package com.simplesync;

import org.junit.Test;
import org.mockito.Mockito.*;

import java.lang.management.PlatformLoggingMXBean;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;


/**
 * Created by Nickitakalinkin on 19.12.17.
 */
public class FileEntryTest {

    @Test
    public void equalsInstance() throws Exception {
        Path mockPath = mock(Path.class);
        Rule mockRule = mock(Rule.class);
        Rule anotherRule = mock(Rule.class);
        anotherRule.setPriority(true);

        FileEntry fileEntry = new FileEntry(mockPath, mockRule);
        FileEntry newFileEntry = new FileEntry(mockPath,mockRule);

        assertTrue(fileEntry.equals(newFileEntry));
        newFileEntry.setRule(anotherRule);
        assertFalse(fileEntry.equals(newFileEntry));
    }

    @Test
    public void equalNotInstance() throws Exception {

        Path mockPath = mock(Path.class);
        Rule mockRule = mock(Rule.class);
        FileEntry fileEntry = new FileEntry(mockPath, mockRule);

        assertFalse(fileEntry.equals(new String("Hello")));


    }

    @Test
    public void testHashCode() {
        Path mockPath = mock(Path.class);
        Rule mockRule = mock(Rule.class);

        FileEntry fileEntry = new FileEntry(mockPath,mockRule);
        int actual = fileEntry.hashCode();

        FileEntry newFileEntry = new FileEntry(mockPath,mockRule);
        int expected = newFileEntry.hashCode();

        assertEquals(expected,actual);
    }

}