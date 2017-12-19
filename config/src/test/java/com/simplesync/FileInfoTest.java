package com.simplesync;

import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Nickitakalinkin on 19.12.17.
 */
public class FileInfoTest {
    @Test
    public  void SerializableTest() {
        FileInfo fileInfo = mock(FileInfo.class);
        assertTrue(fileInfo.getClass() instanceof Serializable);
    }

}